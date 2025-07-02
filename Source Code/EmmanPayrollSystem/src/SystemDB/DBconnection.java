// © MADE BY EMMAN BARRAMEDA ©
package SystemDB;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.sqlite.SQLiteDataSource;

public class DBconnection implements AutoCloseable {

    private Connection conn;
    private static boolean connectionTested = false;

    // Configuration settings
    private static final boolean USE_MYSQL = Config.getBoolean("USE_MYSQL", false);
    private static final boolean USE_EMBEDDED_SQLITE = Config.getBoolean("USE_EMBEDDED_SQLITE", true);

    // MySQL Configuration
    private static final String MYSQL_HOST = Config.get("MYSQL_HOST", "localhost");
    private static final String MYSQL_PORT = Config.get("MYSQL_PORT", "3306");
    private static final String MYSQL_DATABASE = Config.get("MYSQL_DATABASE", "netbeansPayrollSystem");
    private static final String MYSQL_USER = Config.get("MYSQL_USER", "root");
    private static final String MYSQL_PASSWORD = Config.get("MYSQL_PASSWORD", "");
    private static final String MYSQL_URL = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true",
            MYSQL_HOST, MYSQL_PORT, MYSQL_DATABASE
    );

    // SQLite Configuration
    private static final String SQLITE_FILE_PATH = System.getProperty("user.dir")
            + File.separator + "database" + File.separator + "dbpayroll.sqlite";
    private static final String EMBEDDED_SQLITE_PATH = "/SystemDB/database.sqlite";

    public Connection getconnection() throws IOException, SQLException, ClassNotFoundException {
        if (conn != null && !conn.isClosed()) {
            return conn;
        }

        try {
            if (USE_MYSQL) {
                conn = connectMySQL();
            } else if (USE_EMBEDDED_SQLITE) {
                conn = connectEmbeddedSQLite();
            } else {
                conn = connectSQLiteFile();
            }

            if (!connectionTested) {
                testConnection();
                connectionTested = true;
            }

            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            showErrorAndExit("Database Connection Error: " + e.getMessage());
            throw e; // Re-throw after showing error
        }
    }

    private Connection connectMySQL() throws ClassNotFoundException, SQLException {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Additional connection parameters for better reliability
        java.util.Properties props = new java.util.Properties();
        props.setProperty("user", MYSQL_USER);
        props.setProperty("password", MYSQL_PASSWORD);
        props.setProperty("useUnicode", "true");
        props.setProperty("characterEncoding", "UTF-8");
        props.setProperty("connectTimeout", "30000");

        conn = DriverManager.getConnection(MYSQL_URL, props);
        System.out.println("✔ MySQL connection established to " + MYSQL_DATABASE);
        return conn;
    }

    private Connection connectEmbeddedSQLite() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite::resource:" + getClass().getResource(EMBEDDED_SQLITE_PATH));
        conn = ds.getConnection();
        System.out.println("✔ Embedded SQLite connection established");
        return conn;
    }

    private Connection connectSQLiteFile() throws ClassNotFoundException, SQLException, IOException {
        File dbDir = new File(System.getProperty("user.dir") + File.separator + "database");
        if (!dbDir.exists() && !dbDir.mkdirs()) {
            throw new IOException("Failed to create database directory");
        }

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + SQLITE_FILE_PATH);
        System.out.println("✔ SQLite file connection established at: " + SQLITE_FILE_PATH);
        return conn;
    }

    private void testConnection() throws SQLException {
        System.out.println("✅ Connection test successful");
    }

    private void showErrorAndExit(String message) throws IOException {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        JOptionPane optionPane = new JOptionPane(
                "<html><center><b>Database Connection Failed</b><br>" + message + "<br>Application will now exit.</center></html>",
                JOptionPane.ERROR_MESSAGE);

        JDialog dialog = new JDialog();
        optionPane.add(progressBar, BorderLayout.SOUTH);
        dialog.setTitle("Database Error");
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();

        try {
            Image icon = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
            dialog.setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Icon loading failed: " + e.getMessage());
        }

        dialog.setLocationRelativeTo(null);

        // Auto-close after delay
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            System.exit(1);
        }).start();

        dialog.setVisible(true);
    }

    @Override
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (DBconnection db = new DBconnection()) {
            Connection conn = db.getconnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
            }
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
