package studenthostelmaintenancerequest.trackingsystem;

/**
 * JDBC connection settings for XAMPP MySQL (Chapter 9 slides).
 */
public final class DatabaseConfig {

    public static final String HOST = "localhost";
    public static final int PORT = 3306;
    public static final String DATABASE = "hostel_maintenance_db";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static final String ADMIN_EMAIL_DOMAIN = "@admin.com.my";
    public static final String DEFAULT_ADMIN_EMAIL = "youradminusername@admin.com.my";

    private DatabaseConfig() {
    }
}
