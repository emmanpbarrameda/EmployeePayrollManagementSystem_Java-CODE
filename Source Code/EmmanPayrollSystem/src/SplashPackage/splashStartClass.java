// © MADE BY EMMAN BARRAMEDA ©
package SplashPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the application
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class splashStartClass {
    private static final Logger LOGGER = Logger.getLogger(splashStartClass.class.getName());

    public static void main(String args[]) {
        // Initialize Swing components on the EDT
        SwingUtilities.invokeLater(() -> {
            try {
                // Start the splash main class
                splashMainClass.main(new String[0]);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "IO Error during application startup", e);
                showErrorAndExit("Failed to start application due to file system error");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database Error during application startup", e);
                showErrorAndExit("Failed to connect to database");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Unexpected error during application startup", e);
                showErrorAndExit("Application failed to start");
            }
        });
    }

    private static void showErrorAndExit(String message) {
        JOptionPane.showMessageDialog(null,
            message,
            "Application Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}