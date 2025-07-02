// © MADE BY EMMAN BARRAMEDA ©
// PAYROLL SYSTEM
package SplashPackage;

import SystemDB.DBconnection;
import MainPackage.LoginFrame;
import MainPackage.MainNavigationHomePanel;
import MainPackage.MainNavigationHomePanel_S_ADMIN;
import Panels_EmployeeManager.Emp;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * Enhanced payroll system splash screen with improved error handling and
 * resource management
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class splashMainClass {

    private static final Logger LOGGER = Logger.getLogger(splashMainClass.class.getName());
    private static final String DATA_DIR = "data";
    private static final String USER_DIR = DATA_DIR + "/user";
    private static final String LEVEL_DIR = DATA_DIR + "/level";
    private static final String USERDATA_FILE = DATA_DIR + "/userdata.ecoders";

    // UI Components
    static LoginFrame LoginGUI;
    static MainNavigationHomePanel NormalAdminGUI;
    static MainNavigationHomePanel_S_ADMIN SuperAdminGUI;

    // User data
    static String NormalAdminStringFromDB; // normal admin
    static String SuperAdminStringFromDB; // super admin
    static String currentLoggedInUser = null;
    static String currentLoggedInLevel = null;
    static String usernameString = null;
    static String userlevelString = null;

    // Database connection
    static Connection conn = null;
    static DBconnection c;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        // Initialize look and feel
        initializeLookAndFeel();

        // Initialize database connection
        initializeDatabaseConnection();

        // Show splash screen
        SplashGUI sp = new SplashGUI();
        sp.setVisible(true);

        // Initialize application in background thread
        new Thread(() -> {
            try {
                initializeApplication(sp);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(splashMainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    private static void initializeLookAndFeel() {
        try {
            FlatLightLaf.install();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize look and feel", e);
            showErrorAndExit("Failed to initialize application UI", e);
        }
    }

   
    private static void initializeDatabaseConnection() throws SQLException, IOException, ClassNotFoundException {
        c = new DBconnection();
        conn = c.getconnection();
        userlevels();
    }

    private static void initializeApplication(SplashGUI sp) throws ClassNotFoundException {
        try {
            // Initialize main components
            LoginGUI = new LoginFrame();
            NormalAdminGUI = new MainNavigationHomePanel();
            SuperAdminGUI = new MainNavigationHomePanel_S_ADMIN();

            // Simulate loading process
            simulateLoading(sp);

            // Check destination panel after loading completes
            checkDestinationPanel();

            // Hide splash screen when done
            SwingUtilities.invokeLater(() -> sp.setVisible(false));

        } catch (IOException | InterruptedException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Application initialization failed", e);
            showErrorAndExit("Application initialization failed", e);
        } finally {
            // Clean up database resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close database connection", e);
            }
        }
    }

    private static void updateProgress(SplashGUI sp, int progress) {
        final int currentProgress = progress;

        SwingUtilities.invokeLater(() -> {
            // Handle special cases for progress 0 and 1
            if (currentProgress == 0) {
                sp.mainAnimatedIcon.setVisible(false);
                sp.mainAnimatedIcon.setAnchoProgress(30);
                sp.progress.setVisible(false);
            } else if (currentProgress == 1) {
                sp.mainAnimatedIcon.setAnchoProgress(4);
                sp.mainAnimatedIcon.setVisible(true);
                sp.progress.setVisible(true);
            }

            sp.jLabel1.setText(currentProgress + "%");
            sp.progress.setValue(currentProgress);
        });
    }

    private static void simulateLoading(SplashGUI sp) throws InterruptedException {
        for (int i = 0; i <= 101; i++) {
            final int currentProgress = i;
            Thread.sleep(calculateSleepTime(i));

            SwingUtilities.invokeLater(() -> {
                updateProgress(sp, currentProgress);
            });

            if (i == 101) {
                SwingUtilities.invokeLater(() -> {
                    sp.jLabel1.setText(currentProgress + "%");
                    sp.mainAnimatedIcon.setVisible(false);
                    sp.progress.setVisible(false);
                });
                Thread.sleep(600);
            }
        }
    }

    private static long calculateSleepTime(int progress) {
        if (progress == 0) {
            return 100;
        }
        if (progress == 1) {
            return 2000;
        }
        if (progress >= 2 && progress <= 5) {
            return 100;
        }
        if (progress >= 6 && progress <= 30) {
            return 10;
        }
        if (progress >= 31 && progress <= 80) {
            return 2;
        }
        if (progress >= 81 && progress <= 92) {
            return 40;
        }
        if (progress == 100) {
            return 500;
        }
        return 30;
    }

    public static void checkDestinationPanel() {
        try {
            Path appPath = Paths.get(System.getProperty("user.dir"));
            LOGGER.info("Current application path: " + appPath);

            // Create necessary directories if they don't exist
            createDirectories(appPath);

            Path userdataFile = appPath.resolve(USERDATA_FILE);

            if (Files.exists(userdataFile)) {
                handleAutoLogin(appPath);
            } else {
                handleFirstTimeLogin(appPath);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in checkDestinationPanel", e);
            showErrorAndExit("Error determining login state", e);
        }
    }

    private static void createDirectories(Path appPath) throws IOException {
        Path dataDir = appPath.resolve(DATA_DIR);
        Path userDir = appPath.resolve(USER_DIR);
        Path levelDir = appPath.resolve(LEVEL_DIR);

        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
            LOGGER.info("Created data directory: " + dataDir);
        }

        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
            LOGGER.info("Created user directory: " + userDir);
        }

        if (!Files.exists(levelDir)) {
            Files.createDirectories(levelDir);
            LOGGER.info("Created level directory: " + levelDir);
        }
    }

    private static void handleAutoLogin(Path appPath) throws IOException {
        LOGGER.info("Auto login file exists");

        // Check user directory for logged in user
        Path userDir = appPath.resolve(USER_DIR);
        String currentLoggedInUser = findFirstFile(userDir);

        if (currentLoggedInUser == null) {
            LOGGER.info("No user file found - requiring login");
            cleanLoginFiles(appPath);
            SwingUtilities.invokeLater(() -> LoginGUI.setVisible(true));
            return;
        }

        // Check level directory for user level
        Path levelDir = appPath.resolve(LEVEL_DIR);
        String currentLoggedInLevel = findFirstFile(levelDir);

        if (currentLoggedInLevel == null) {
            LOGGER.info("No level file found - requiring login");
            cleanLoginFiles(appPath);
            SwingUtilities.invokeLater(() -> LoginGUI.setVisible(true));
            return;
        }

        // Handle login based on user level
        handleUserLevelLogin(currentLoggedInUser, currentLoggedInLevel);
    }

    private static String findFirstFile(Path directory) throws IOException {
        return Files.list(directory)
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .findFirst()
                .orElse(null);
    }

    private static void handleUserLevelLogin(String username, String userlevel) {
        usernameString = username;
        userlevelString = userlevel;
        Emp.empId = usernameString;

        SwingUtilities.invokeLater(() -> {
            if (NormalAdminStringFromDB.equalsIgnoreCase(userlevel)) {
                NormalAdminGUI.usernameVoid(usernameString);
                NormalAdminGUI.userlevelVoid(userlevelString);
                NormalAdminGUI.setVisible(true);
            } else if (SuperAdminStringFromDB.equalsIgnoreCase(userlevel)) {
                SuperAdminGUI.usernameVoid(usernameString);
                SuperAdminGUI.userlevelVoid(userlevelString);
                SuperAdminGUI.setVisible(true);
            } else {
                // Invalid user level - clean up and show login
                try {
                    cleanLoginFiles(Paths.get(System.getProperty("user.dir")));
                    LoginGUI.setVisible(true);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Failed to clean login files", e);
                    showErrorAndExit("Invalid user level detected", e);
                }
            }
        });
    }

    private static void handleFirstTimeLogin(Path appPath) throws IOException {
        LOGGER.info("First time login - no auto login file");
        Path userdataFile = appPath.resolve(USERDATA_FILE);

        Files.createFile(userdataFile);
        LOGGER.info("Created userdata file: " + userdataFile);

        cleanLoginFiles(appPath);
        SwingUtilities.invokeLater(() -> LoginGUI.setVisible(true));
    }

    private static void cleanLoginFiles(Path appPath) throws IOException {
        deleteFilesInDirectory(appPath.resolve(USER_DIR));
        deleteFilesInDirectory(appPath.resolve(LEVEL_DIR));
    }

    private static void deleteFilesInDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.list(directory)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                            LOGGER.info("Deleted file: " + file);
                        } catch (IOException e) {
                            LOGGER.log(Level.WARNING, "Failed to delete file: " + file, e);
                        }
                    });
        }
    }

    private static void showErrorAndExit(String message, Exception error) {
        SwingUtilities.invokeLater(() -> {
            String errorDetails = String.format(
                    "<html><center><b>%s</b><br><br>"
                    + "Error type: %s<br>"
                    + "Error details: %s<br><br>"
                    + "Please re-install/restart the program or contact support.<br><br>"
                    + "Exiting now...</center></html>",
                    message,
                    error.getClass().getSimpleName(),
                    error.getMessage()
            );

            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);

            JOptionPane optionPane = new JOptionPane(
                    errorDetails,
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{},
                    null
            );

            JDialog dialog = new JDialog();
            optionPane.add(progressBar, BorderLayout.CENTER);
            dialog.setTitle("SYSTEM ERROR | ECoders");
            dialog.setAlwaysOnTop(true);
            dialog.setModal(true);
            dialog.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            try {
                Image icon = ImageIO.read(splashMainClass.class.getResource("/Images/wait_16px.png"));
                dialog.setIconImage(icon);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Could not load error dialog icon", e);
            }

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            // Close dialog and exit after delay
            new Thread(() -> {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.exit(0);
                }
            }).start();
        });
    }

    // Get the user levels available from database
    public static void userlevels() {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM guinames");

            if (rs.next()) {
                NormalAdminStringFromDB = rs.getString("DefaultUser");
                SuperAdminStringFromDB = rs.getString("DefaultAdmin");

                LOGGER.info("Normal admin level: " + NormalAdminStringFromDB);
                LOGGER.info("Super admin level: " + SuperAdminStringFromDB);
            } else {
                LOGGER.warning("No user levels found in database");
                throw new SQLException("No user levels found in GUINames table");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve user levels from database", e);
            showErrorAndExit("Failed to retrieve user levels", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close database resources", e);
            }
        }
    }
}
