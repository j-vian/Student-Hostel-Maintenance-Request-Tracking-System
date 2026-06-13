package studenthostelmaintenancerequest.trackingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC persistence layer for the hostel maintenance system (Chapter 9 pattern).
 *
 * @author vian
 */
public class DatabaseHandler implements DataAccess {

    private static DatabaseHandler instance;
    private final MaintenanceFactory requestFactory = new MaintenanceFactory();

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD);
    }

    @Override
    public boolean testConnection() {
        try (Connection conn = openConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User authenticate(String email, String plainPassword) throws DatabaseException {
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();
        if (normalizedEmail.isEmpty() || plainPassword == null || plainPassword.isEmpty()) {
            throw new DatabaseException("Email and password are required.");
        }

        String sql = """
                SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.password,
                       u.role, u.staff_role, u.other_expertise,
                       r.room_id, r.room_number, r.place_name
                FROM users u
                LEFT JOIN rooms r ON u.room_id = r.room_id
                WHERE LOWER(u.email) = ?
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizedEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new DatabaseException("Invalid email or password.");
                }

                String storedPassword = rs.getString("password");
                if (!plainPassword.equals(storedPassword)) {
                    throw new DatabaseException("Invalid email or password.");
                }

                UserRole role = UserRole.fromDatabaseValue(rs.getString("role"));
                validateAdminLogin(normalizedEmail, role);

                return mapUser(rs, role);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to authenticate user.", ex);
        }
    }

    private void validateAdminLogin(String email, UserRole role) throws DatabaseException {
        boolean adminEmail = email.endsWith(DatabaseConfig.ADMIN_EMAIL_DOMAIN.toLowerCase());
        if (adminEmail && role != UserRole.MANAGER) {
            throw new DatabaseException("Admin email must belong to a manager account.");
        }
        if (role == UserRole.MANAGER && !adminEmail) {
            throw new DatabaseException("Manager accounts must use an @admin.com.my email.");
        }
    }

    @Override
    public void registerStudent(SignUpData data) throws DatabaseException {
        validateSignUpData(data, UserRole.STUDENT);
        blockAdminEmail(data.getEmail());

        int roomId = findOrCreateRoom(data.getRoomNumber(), data.getPlaceName());
        insertUser(data, roomId);
    }

    @Override
    public void registerStaff(SignUpData data) throws DatabaseException {
        validateSignUpData(data, UserRole.STAFF);
        blockAdminEmail(data.getEmail());
        insertUser(data, 0);
    }

    private void validateSignUpData(SignUpData data, UserRole expectedRole) throws DatabaseException {
        if (data == null) {
            throw new DatabaseException("Sign-up data is missing.");
        }
        if (isBlank(data.getUserId()) || isBlank(data.getUsername()) || isBlank(data.getFirstName())
                || isBlank(data.getLastName()) || isBlank(data.getEmail()) || isBlank(data.getPassword())) {
            throw new DatabaseException("Please complete all required fields.");
        }
        if (data.getRole() != expectedRole) {
            throw new DatabaseException("Invalid role selected.");
        }
        if (expectedRole == UserRole.STUDENT
                && (isBlank(data.getRoomNumber()) || isBlank(data.getPlaceName()))) {
            throw new DatabaseException("Room number and place name are required for students.");
        }
        if (expectedRole == UserRole.STAFF && isBlank(data.getStaffRole())) {
            throw new DatabaseException("Staff expertise is required.");
        }
        if (emailExists(data.getEmail())) {
            throw new DatabaseException("An account with this email already exists.");
        }
        if (userIdExists(data.getUserId())) {
            throw new DatabaseException("This user ID is already registered.");
        }
        if (usernameExists(data.getUsername())) {
            throw new DatabaseException("This username is already taken.");
        }
    }

    private void blockAdminEmail(String email) throws DatabaseException {
        if (email != null && email.trim().toLowerCase().endsWith(DatabaseConfig.ADMIN_EMAIL_DOMAIN)) {
            throw new DatabaseException("Admin emails cannot be registered through sign-up.");
        }
    }

    private int findOrCreateRoom(String roomNumber, String placeName) throws DatabaseException {
        String selectSql = """
                SELECT room_id FROM rooms
                WHERE room_number = ? AND place_name = ?
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setString(1, roomNumber.trim());
            ps.setString(2, placeName.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("room_id");
                }
            }

            String insertSql = "INSERT INTO rooms (room_number, place_name) VALUES (?, ?)";
            try (PreparedStatement insertPs = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertPs.setString(1, roomNumber.trim());
                insertPs.setString(2, placeName.trim());
                insertPs.executeUpdate();
                try (ResultSet keys = insertPs.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to save room information.", ex);
        }

        throw new DatabaseException("Unable to create room record.");
    }

    private void insertUser(SignUpData data, int roomId) throws DatabaseException {
        String sql = """
                INSERT INTO users
                (user_id, username, first_name, last_name, email, password, role, room_id, staff_role, other_expertise)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, data.getUserId().trim());
            ps.setString(2, data.getUsername().trim());
            ps.setString(3, data.getFirstName().trim());
            ps.setString(4, data.getLastName().trim());
            ps.setString(5, data.getEmail().trim().toLowerCase());
            ps.setString(6, data.getPassword());
            ps.setString(7, data.getRole().name());
            if (data.getRole() == UserRole.STUDENT) {
                ps.setInt(8, roomId);
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setString(9, isBlank(data.getStaffRole()) ? null : data.getStaffRole().trim());
            ps.setString(10, isBlank(data.getOtherExpertise()) ? null : data.getOtherExpertise().trim());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to create account.", ex);
        }
    }

    private boolean emailExists(String email) throws DatabaseException {
        return exists("SELECT 1 FROM users WHERE LOWER(email) = ?", email.trim().toLowerCase());
    }

    private boolean userIdExists(String userId) throws DatabaseException {
        return exists("SELECT 1 FROM users WHERE user_id = ?", userId.trim());
    }

    private boolean usernameExists(String username) throws DatabaseException {
        return exists("SELECT 1 FROM users WHERE LOWER(username) = ?", username.trim().toLowerCase());
    }

    private boolean exists(String sql, String value) throws DatabaseException {
        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to validate account details.", ex);
        }
    }

    @Override
    public String generateNextRequestId() throws DatabaseException {
        String sql = """
                SELECT request_id FROM maintenance_requests
                WHERE request_id LIKE 'REQ%'
                ORDER BY request_id DESC
                LIMIT 1
                """;

        int nextNumber = 1;
        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String latestId = rs.getString("request_id");
                nextNumber = Integer.parseInt(latestId.substring(3)) + 1;
            }
        } catch (SQLException | NumberFormatException ex) {
            throw new DatabaseException("Unable to generate the next request ID.", ex);
        }

        return String.format("REQ%03d", nextNumber);
    }

    @Override
    public void insertRequest(MaintenanceRequest request, String studentId, int roomId, String changedBy)
            throws DatabaseException {
        String sql = """
                INSERT INTO maintenance_requests
                (request_id, student_id, room_id, request_type, description, priority, status,
                 assigned_staff_id, date_raised)
                VALUES (?, ?, ?, ?, ?, ?, ?, NULL, ?)
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getRequestId());
            ps.setString(2, studentId);
            ps.setInt(3, roomId);
            ps.setString(4, normalizeRequestType(request));
            ps.setString(5, request.getDescription());
            ps.setString(6, request.getPriority());
            ps.setString(7, request.getStatus().name());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to insert maintenance request.", ex);
        }

        insertHistoryEntry(request.getRequestId(), request.getStatus(), changedBy);
    }

    @Override
    public void updateRequestStatus(String requestId, Status newStatus, String changedBy)
            throws DatabaseException {
        String sql = "UPDATE maintenance_requests SET status = ? WHERE request_id = ?";

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus.name());
            ps.setString(2, requestId.trim().toUpperCase());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Request [" + requestId + "] was not found.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to update request status.", ex);
        }

        insertHistoryEntry(requestId, newStatus, changedBy);
    }

    @Override
    public void assignStaffToRequest(String requestId, String staffId, String changedBy)
            throws DatabaseException {
        String sql = """
                UPDATE maintenance_requests
                SET assigned_staff_id = ?, status = ?
                WHERE request_id = ?
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffId);
            ps.setString(2, Status.IN_PROGRESS.name());
            ps.setString(3, requestId.trim().toUpperCase());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Request [" + requestId + "] was not found.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to assign staff.", ex);
        }

        insertHistoryEntry(requestId, Status.IN_PROGRESS, changedBy);
    }

    @Override
    public void deleteRequest(String requestId) throws DatabaseException {
        String historySql = "DELETE FROM request_status_history WHERE request_id = ?";
        String requestSql = "DELETE FROM maintenance_requests WHERE request_id = ?";

        try (Connection conn = openConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement historyPs = conn.prepareStatement(historySql);
                    PreparedStatement requestPs = conn.prepareStatement(requestSql)) {
                historyPs.setString(1, requestId.trim().toUpperCase());
                historyPs.executeUpdate();
                requestPs.setString(1, requestId.trim().toUpperCase());
                int rows = requestPs.executeUpdate();
                if (rows == 0) {
                    throw new DatabaseException("Request [" + requestId + "] was not found.");
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to delete request.", ex);
        }
    }

    @Override
    public MaintenanceRequest findRequestById(String requestId) throws DatabaseException {
        MaintenanceRequest[] requests = fetchRequests(
                BASE_REQUEST_QUERY + " WHERE mr.request_id = ?",
                requestId.trim().toUpperCase());
        return requests.length == 0 ? null : requests[0];
    }

    @Override
    public MaintenanceRequest[] fetchAllRequests() throws DatabaseException {
        return fetchRequests(BASE_REQUEST_QUERY + " ORDER BY mr.date_raised DESC");
    }

    @Override
    public MaintenanceRequest[] fetchRequestsByStudent(String studentId) throws DatabaseException {
        return fetchRequests(
                BASE_REQUEST_QUERY + " WHERE mr.student_id = ? ORDER BY mr.date_raised DESC",
                studentId.trim());
    }

    @Override
    public MaintenanceRequest[] fetchRequestsByStaff(String staffId) throws DatabaseException {
        return fetchRequests(
                BASE_REQUEST_QUERY + " WHERE mr.assigned_staff_id = ? ORDER BY mr.date_raised DESC",
                staffId.trim());
    }

    @Override
    public User[] fetchAllStaff() throws DatabaseException {
        String sql = """
                SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.password,
                       u.role, u.staff_role, u.other_expertise,
                       NULL AS room_id, NULL AS room_number, NULL AS place_name
                FROM users u
                WHERE u.role = 'STAFF'
                ORDER BY u.first_name, u.last_name
                """;

        List<User> staffMembers = new ArrayList<>();
        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                staffMembers.add(mapUser(rs, UserRole.STAFF));
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to fetch staff list.", ex);
        }

        return staffMembers.toArray(new User[0]);
    }

    private static final String BASE_REQUEST_QUERY = """
            SELECT mr.request_id, mr.request_type, mr.description, mr.priority, mr.status,
                   mr.student_id, mr.assigned_staff_id, mr.date_raised,
                   r.room_id, r.room_number, r.place_name
            FROM maintenance_requests mr
            INNER JOIN rooms r ON mr.room_id = r.room_id
            """;

    private MaintenanceRequest[] fetchRequests(String sql, String... params) throws DatabaseException {
        List<MaintenanceRequest> requests = new ArrayList<>();

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRequest(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to fetch maintenance requests.", ex);
        }

        return requests.toArray(new MaintenanceRequest[0]);
    }

    private MaintenanceRequest mapRequest(ResultSet rs) throws SQLException {
        Room room = new Room(
                rs.getInt("room_id"),
                rs.getString("room_number"),
                rs.getString("place_name"));

        MaintenanceRequest request = requestFactory.createRequest(
                rs.getString("request_type"),
                rs.getString("request_id"),
                rs.getString("description"),
                rs.getString("priority"),
                room);

        request.setStatus(Status.valueOf(rs.getString("status")));
        return request;
    }

    private User mapUser(ResultSet rs, UserRole role) throws SQLException {
        String userId = rs.getString("user_id");
        String username = rs.getString("username");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String staffRole = rs.getString("staff_role");

        switch (role) {
            case STUDENT:
                Room room = null;
                int roomId = rs.getInt("room_id");
                if (!rs.wasNull()) {
                    room = new Room(roomId, rs.getString("room_number"), rs.getString("place_name"));
                }
                return new Student(userId, username, firstName, lastName, email, password, room);
            case STAFF:
                return new Staff(userId, username, firstName, lastName, email, password, staffRole);
            case MANAGER:
                return new HostelAdmin(userId, username, firstName, lastName, email, password);
            default:
                throw new SQLException("Unsupported role: " + role);
        }
    }

    private void insertHistoryEntry(String requestId, Status status, String changedBy) throws DatabaseException {
        String sql = """
                INSERT INTO request_status_history (request_id, status, changed_at, changed_by)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, requestId.trim().toUpperCase());
            ps.setString(2, status.name());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            if (isBlank(changedBy)) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, changedBy.trim());
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to save request history.", ex);
        }
    }

    private String normalizeRequestType(MaintenanceRequest request) {
        if (request instanceof ElectricalRequest) {
            return "electrical";
        }
        if (request instanceof PlumbingRequest) {
            return "plumbing";
        }
        if (request instanceof FurnitureRequest) {
            return "furniture";
        }
        return "electrical";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
