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
    /** Placeholder until the student submits a request with the actual place name. */
    private static final String STUDENT_REGISTRATION_PLACE = "TBD";

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

        int roomId = findOrCreateRoomInternal(data.getRoomNumber(), STUDENT_REGISTRATION_PLACE);
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
        if (expectedRole == UserRole.STUDENT && isBlank(data.getRoomNumber())) {
            throw new DatabaseException("Room number is required for students.");
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

    @Override
    public int findOrCreateRoom(String roomNumber, String placeName) throws DatabaseException {
        return findOrCreateRoomInternal(roomNumber, placeName);
    }

    private int findOrCreateRoomInternal(String roomNumber, String placeName) throws DatabaseException {
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

    @Override
    public boolean isEmailRegistered(String email) throws DatabaseException {
        if (email == null || email.isBlank()) {
            return false;
        }
        return emailExists(email);
    }

    @Override
    public void updatePassword(String email, String newPassword) throws DatabaseException {
        if (email == null || email.isBlank()) {
            throw new DatabaseException("Email is required.");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            throw new DatabaseException("New password is required.");
        }
        if (!isEmailRegistered(email)) {
            throw new DatabaseException("No account found for this email address.");
        }

        String sql = "UPDATE users SET password = ? WHERE LOWER(email) = ?";
        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, email.trim().toLowerCase());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new DatabaseException("Unable to update password.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to update password.", ex);
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

    private static final String RECENT_ACTIVITY_JOIN = """
            LEFT JOIN (
                SELECT request_id, MAX(changed_at) AS last_activity
                FROM request_status_history
                GROUP BY request_id
            ) rah ON rah.request_id = mr.request_id
            """;

    private static final String RECENT_ACTIVITY_ORDER =
            " ORDER BY COALESCE(rah.last_activity, mr.date_raised) DESC";

    @Override
    public MaintenanceRequest[] fetchAllRequests() throws DatabaseException {
        return fetchRequests(BASE_REQUEST_QUERY + RECENT_ACTIVITY_ORDER);
    }

    @Override
    public MaintenanceRequest[] fetchRequestsByStudent(String studentId) throws DatabaseException {
        return fetchRequests(
                BASE_REQUEST_QUERY + " WHERE mr.student_id = ?" + RECENT_ACTIVITY_ORDER,
                studentId.trim());
    }

    @Override
    public MaintenanceRequest[] fetchRequestsByStaff(String staffId) throws DatabaseException {
        return fetchRequests(
                BASE_REQUEST_QUERY + " WHERE mr.assigned_staff_id = ?" + RECENT_ACTIVITY_ORDER,
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

    private static final String MANAGER_LIST_QUERY = """
            SELECT mr.request_id, mr.request_type, mr.description, mr.priority, mr.status,
                   mr.date_raised,
                   s.first_name AS student_first_name, s.last_name AS student_last_name,
                   st.first_name AS staff_first_name, st.last_name AS staff_last_name
            FROM maintenance_requests mr
            INNER JOIN users s ON mr.student_id = s.user_id
            LEFT JOIN users st ON mr.assigned_staff_id = st.user_id
            """
            + RECENT_ACTIVITY_JOIN;

    public int[] fetchDashboardCounts() throws DatabaseException {
        int[] counts = new int[4];
        String sql = """
                SELECT
                    COUNT(*) AS total_count,
                    SUM(CASE WHEN status = 'IN_PROGRESS' THEN 1 ELSE 0 END) AS active_count,
                    SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_count,
                    SUM(CASE WHEN status = 'CANCELLED' THEN 1 ELSE 0 END) AS cancelled_count
                FROM maintenance_requests
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                counts[0] = rs.getInt("total_count");
                counts[1] = rs.getInt("active_count");
                counts[2] = rs.getInt("completed_count");
                counts[3] = rs.getInt("cancelled_count");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to load dashboard statistics.", ex);
        }
        return counts;
    }

    public Object[][] fetchOverviewRequestRows(int limit) throws DatabaseException {
        String sql = MANAGER_LIST_QUERY
                + " WHERE mr.status IN ('SUBMITTED', 'IN_PROGRESS')"
                + RECENT_ACTIVITY_ORDER
                + " LIMIT ?";
        List<Object[]> rows = queryManagerRows(sql, limit, null);
        Object[][] data = new Object[rows.size()][6];
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);
            data[i] = new Object[]{
                row[0], row[1], row[2], row[3], row[7], row[4]
            };
        }
        return data;
    }

    public Object[][] fetchManageActiveRows(String requestIdSearch) throws DatabaseException {
        String sql = MANAGER_LIST_QUERY + " WHERE mr.status = 'IN_PROGRESS'";
        if (!isBlank(requestIdSearch)) {
            sql += " AND UPPER(mr.request_id) LIKE ?";
        }
        sql += RECENT_ACTIVITY_ORDER;

        List<Object[]> rows = queryManagerRows(sql, 0, normalizeSearchId(requestIdSearch));
        Object[][] data = new Object[rows.size()][7];
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);
            data[i] = new Object[]{
                row[0], row[1], row[2], row[3], row[5], row[7], row[4]
            };
        }
        return data;
    }

    public Object[][] fetchAssignStaffRows(String requestIdSearch) throws DatabaseException {
        String sql = MANAGER_LIST_QUERY
                + " WHERE mr.status = 'SUBMITTED' AND mr.assigned_staff_id IS NULL";
        if (!isBlank(requestIdSearch)) {
            sql += " AND UPPER(mr.request_id) LIKE ?";
        }
        sql += RECENT_ACTIVITY_ORDER;

        List<Object[]> rows = queryManagerRows(sql, 0, normalizeSearchId(requestIdSearch));
        Object[][] data = new Object[rows.size()][7];
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);
            data[i] = new Object[]{
                row[0], row[1], row[2], row[5], row[7], row[4], row[3]
            };
        }
        return data;
    }

    public Object[][] fetchHistoryRows(String requestIdSearch) throws DatabaseException {
        String sql = MANAGER_LIST_QUERY;
        if (!isBlank(requestIdSearch)) {
            sql += " WHERE UPPER(mr.request_id) LIKE ?";
        }
        sql += RECENT_ACTIVITY_ORDER;

        List<Object[]> rows = queryManagerRows(sql, 0, normalizeSearchId(requestIdSearch));
        Object[][] data = new Object[rows.size()][8];
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);
            data[i] = new Object[]{
                row[0], row[1], row[2], row[3], row[6], row[5], row[7], row[4]
            };
        }
        return data;
    }

    public ManagerRoomDetails fetchRoomDetailsByRequestId(String requestId) throws DatabaseException {
        return fetchRoomDetails(requestId, null);
    }

    public ManagerRoomDetails fetchRoomDetailsForAssignedStaff(String requestId, String staffId)
            throws DatabaseException {
        if (isBlank(staffId)) {
            return null;
        }
        return fetchRoomDetails(requestId, staffId.trim());
    }

    private ManagerRoomDetails fetchRoomDetails(String requestId, String assignedStaffId)
            throws DatabaseException {
        if (isBlank(requestId)) {
            return null;
        }

        String sql = """
                SELECT mr.request_id, mr.request_type,
                       s.first_name AS student_first_name, s.last_name AS student_last_name,
                       r.room_number, r.place_name
                FROM maintenance_requests mr
                INNER JOIN users s ON mr.student_id = s.user_id
                INNER JOIN rooms r ON mr.room_id = r.room_id
                WHERE UPPER(mr.request_id) = ?
                """;
        if (assignedStaffId != null) {
            sql += " AND mr.assigned_staff_id = ?";
        }

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, requestId.trim().toUpperCase());
            if (assignedStaffId != null) {
                ps.setString(2, assignedStaffId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new ManagerRoomDetails(
                        rs.getString("request_id"),
                        rs.getString("room_number"),
                        rs.getString("place_name"),
                        formatFullName(rs.getString("student_first_name"), rs.getString("student_last_name")),
                        formatRequestType(rs.getString("request_type")));
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to load room details.", ex);
        }
    }

    public String findStaffIdByDisplayName(String displayName) throws DatabaseException {
        if (isBlank(displayName)) {
            return null;
        }

        String fullName = displayName;
        int roleStart = displayName.indexOf(" (");
        if (roleStart > 0) {
            fullName = displayName.substring(0, roleStart).trim();
        }

        String sql = """
                SELECT user_id FROM users
                WHERE role = 'STAFF'
                  AND CONCAT(first_name, ' ', last_name) = ?
                """;

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_id");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to match staff member.", ex);
        }
        return null;
    }

    private List<Object[]> queryManagerRows(String sql, int limit, String requestIdSearch)
            throws DatabaseException {
        List<Object[]> rows = new ArrayList<>();

        try (Connection conn = openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (!isBlank(requestIdSearch)) {
                ps.setString(paramIndex++, requestIdSearch);
            }
            if (limit > 0) {
                ps.setInt(paramIndex, limit);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String staffFirst = rs.getString("staff_first_name");
                    String staffLast = rs.getString("staff_last_name");
                    Object assignedStaff = (staffFirst == null || staffLast == null)
                            ? null
                            : formatFullName(staffFirst, staffLast);

                    Timestamp timestamp = rs.getTimestamp("date_raised");
                    LocalDateTime dateRaised = timestamp == null ? null : timestamp.toLocalDateTime();

                    rows.add(new Object[]{
                        rs.getString("request_id"),
                        formatRequestType(rs.getString("request_type")),
                        formatFullName(rs.getString("student_first_name"), rs.getString("student_last_name")),
                        assignedStaff,
                        formatDisplayStatus(rs.getString("status")),
                        formatDate(dateRaised),
                        rs.getString("description"),
                        ManagerService.formatPriority(rs.getString("priority"))
                    });
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to load manager request data.", ex);
        }
        return rows;
    }

    private String normalizeSearchId(String requestIdSearch) {
        if (isBlank(requestIdSearch)) {
            return null;
        }
        return "%" + requestIdSearch.trim().toUpperCase() + "%";
    }

    private static String formatFullName(String firstName, String lastName) {
        return ((firstName == null ? "" : firstName.trim()) + " "
                + (lastName == null ? "" : lastName.trim())).trim();
    }

    private static String formatRequestType(String requestType) {
        if (requestType == null || requestType.isBlank()) {
            return "";
        }
        return switch (requestType.trim().toLowerCase()) {
            case "electrical" -> "Electrical";
            case "plumbing" -> "Plumbing";
            case "furniture" -> "Furniture";
            default -> requestType.substring(0, 1).toUpperCase() + requestType.substring(1);
        };
    }

    private static String formatDisplayStatus(String dbStatus) {
        if (dbStatus == null) {
            return "";
        }
        return dbStatus.trim().replace('_', ' ').toUpperCase();
    }

    private static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("d MMMM yyyy", java.util.Locale.ENGLISH));
    }

    private static final String BASE_REQUEST_QUERY = """
            SELECT mr.request_id, mr.request_type, mr.description, mr.priority, mr.status,
                   mr.student_id, mr.assigned_staff_id, mr.date_raised,
                   r.room_id, r.room_number, r.place_name
            FROM maintenance_requests mr
            INNER JOIN rooms r ON mr.room_id = r.room_id
            """
            + RECENT_ACTIVITY_JOIN;

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
        java.sql.Timestamp raisedAt = rs.getTimestamp("date_raised");
        if (raisedAt != null) {
            request.setDateRaised(raisedAt.toLocalDateTime());
        }
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
