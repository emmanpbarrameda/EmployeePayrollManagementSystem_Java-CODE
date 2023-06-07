// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //
//PAYROLL SYSTEM
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

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class splashMainClass {

    static LoginFrame LoginGUI;
    static MainNavigationHomePanel NormalAdminGUI;
    static MainNavigationHomePanel_S_ADMIN SuperAdminGUI;

    static String noneString;
    static String NormalAdminStringFromDB; //normal admin
    static String SuperAdminStringFromDB; //super admin

    //for database
    static Connection conn = null;
    static DBconnection c;

    //string to check current loggedIn user
    static String currentLoggedInUser = null;
    //string to check current loggedIn user
    static String currentLoggedInLevel = null;

    //for checking of UserName and UserLevel on AutoLogin or Not AutoLogin
    static String usernameString = null;
    static String userlevelString = null;

    public static void main(String args[]) throws SQLException, IOException {

        //get connection from db
        c = new DBconnection();
        conn = c.getconnection();

        //install the System LaF
        FlatLightLaf.install();

        //set Visible the Splash
        SplashGUI sp = new SplashGUI();
        sp.setVisible(true);

        //START HERE
        LoginGUI = new LoginFrame();
        NormalAdminGUI = new MainNavigationHomePanel();
        SuperAdminGUI = new MainNavigationHomePanel_S_ADMIN();
        userlevels(); //<-- get the user levels

        try {
            for (int i = 0; i <= 101; i++) {
                Thread.sleep(30);
                sp.jLabel1.setText(Integer.toString(i) + "%");
                sp.progress.setValue(i);
                //sp.jLabel5.setValue(i);

                if (i == 101) {
                    sp.setVisible(false);
                    checkDestinationPanel();

                } else if (sp.jLabel1.getText().equals("0%") | sp.jLabel1.getText().equals("0") | i == 0) {
                    sp.mainAnimatedIcon.setVisible(false);
                    sp.mainAnimatedIcon.setAnchoProgress(30);
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    sp.progress.setVisible(false);
                    Thread.sleep(100);

                } else if (i == 1) {
                    sp.mainAnimatedIcon.setAnchoProgress(4);
                    sp.mainAnimatedIcon.setVisible(true);
                    sp.progress.setVisible(true);
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    Thread.sleep(2000);

                } else if (i >= 2 && i <= 5) {
                    Thread.sleep(100);

                } else if (i >= 6 && i <= 30) {
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    Thread.sleep(10);

                } else if (i >= 31 && i <= 80) {
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    Thread.sleep(2);

                } else if (i >= 81 && i <= 92) {
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    Thread.sleep(40);

                } else if (i >= 92 && i <= 99) {
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");

                } else if (i == 100) {
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    Thread.sleep(500);
                    sp.jLabel1.setText("" + Integer.toString(i) + "%");
                    sp.mainAnimatedIcon.setVisible(false);
                    sp.progress.setVisible(false);
                    Thread.sleep(600);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (URISyntaxException ex) {
            Logger.getLogger(splashMainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //check if the user will AutologgedIn or not VIA USERNAME.
    public static void checkDestinationPanel() throws IOException, URISyntaxException {

        //find the filepath of the current running/open jar/exe file
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();
        System.out.println(path); //<-- print the file destination

        File file1 = new File(path + "/data/userdata.ecoders");

        try {

            //file is already exist (THIS WILL BECOME AUTO LOGIN)
            if (file1.exists()) {
                System.out.println("File is Already Exist");

                //get the usernameDetector path for pasting the current user logged in.
                File usernameFile = new File(path + "/data/user/");
                File[] listOfFiles = usernameFile.listFiles();

                try {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        //get files
                        if (listOfFiles[i].isFile()) {
                            System.out.println("Files:" + listOfFiles[i].getName());

                            currentLoggedInUser = listOfFiles[i].getName();

                            usernameString = currentLoggedInUser;

                            //get the directories (folders)
                        } else if (listOfFiles[i].isDirectory()) {
                            System.out.println("Directories/Folder:" + listOfFiles[i].getName());
                        }
                    }
                } catch (Exception e) {
                    //shutdown the system if the folder is not exist
                    shutdownProblem();
                }

                //Check if the USER folder have 1 file, if the folder have LESSTHAN 0 data, it will go to LogIn Again.
                File fileCountChecker = new File(path + "/data/user/");
                File[] listOfFilesChecker = fileCountChecker.listFiles();

                if (listOfFilesChecker.length > 0) {
                    //theres have a file on the folder
                    System.out.println("Mayroong mga Files");
                    userlevelFileDetector(); //<-- go to userlevel detector void if the USER folder have data
                } else {
                    //no file on the folder
                    deleteUserLevelFiles(); //<-- delete the files on the USERLEVEL folder for Security
                    deleteUsernameFiles(); //<-- delete the files on the USER - (USERNAME) folder for Security
                    LoginGUI.setVisible(true); //<-- go to LOGIN if the USER folder have no data
                }

                //AdminGUI.usernameVoid(usernameString); //set the username from here to main panel
                //get the username data from username file detector to mainMenu
                //String fn = currentLoggedInUser;
                //main.gencode(fn);
                //set visible - true - the Main_Menu
                //main.setVisible(true);
            }

            //file is not exist (LOGIN FIRST)
            if (!(file1.exists())) {
                System.out.println("File is Not Exist");

                if (file1.createNewFile()) {
                    System.out.println("File created");
                    deleteUserLevelFiles(); //delete the User Level Files
                    deleteUsernameFiles(); //delete the Username Files
                    LoginGUI.setVisible(true); //set visible the Login Panel

                } else {
                    System.out.println("Cannot create login file. - 1");
                }
            }

        } catch (IOException e) {
            shutdownProblem(); //<-- display the error
        }
    }

    //check if the user will AutologgedIn or not VIA USER-LEVEL.
    public static void userlevelFileDetector() throws IOException {

        //find the filepath of the current running/open jar/exe file
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();
        System.out.println(path); //<-- print the file destination

        File file1 = new File(path + "/data/userdata.ecoders");
        File userlevelFile = null;

        try {

            //file is already exist (THIS WILL BECOME AUTO LOGIN)
            if (file1.exists()) {
                System.out.println("File is Already Exist");

                //get the usernameDetector path for pasting the current user logged in.
                userlevelFile = new File(path + "/data/level/");

                File[] listOfFiles = userlevelFile.listFiles();

                try {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        //get files
                        if (listOfFiles[i].isFile()) {
                            System.out.println("Files:" + listOfFiles[i].getName());

                            currentLoggedInLevel = listOfFiles[i].getName();

                            //get the directories (folders)
                        } else if (listOfFiles[i].isDirectory()) {
                            System.out.println("Directories/Folder:" + listOfFiles[i].getName());
                        }
                    }
                } catch (Exception e) {
                    //shutdown the system if the folder is not exist
                    shutdownProblem();

                }

                //detect if normaladmin or superAdmin
                //normaladmin
                if (NormalAdminStringFromDB.equalsIgnoreCase(currentLoggedInLevel)) {
                    usernameString = currentLoggedInUser; //get the username
                    userlevelString = currentLoggedInLevel; //get the userlevel

                    Emp.empId = usernameString;
                    
                    NormalAdminGUI.usernameVoid(usernameString); //set the username from here to userlevel panel
                    NormalAdminGUI.userlevelVoid(userlevelString); //set the userlevel from here to userlevel panel
                    NormalAdminGUI.setVisible(true);
                    
                    //superAdmin
                } else if (SuperAdminStringFromDB.equalsIgnoreCase(currentLoggedInLevel)) {
                    usernameString = currentLoggedInUser; //get the username
                    userlevelString = currentLoggedInLevel; //get the userlevel

                    Emp.empId = usernameString;
                    
                    SuperAdminGUI.usernameVoid(usernameString); //set the username from here to userlevel panel
                    SuperAdminGUI.userlevelVoid(userlevelString); //set the userlevel from here to userlevel panel
                    SuperAdminGUI.setVisible(true);

                    /*go to login if the file detector is not equal to UserLevel Detector String from Database,
                and delete the current user and level*/
                } else {
                    deleteUserLevelFiles(); //delete the User Level Files
                    deleteUsernameFiles(); //delete the Username Files
                    LoginGUI.setVisible(true); //go to login panel
                }
            }

            //Check if the LEVEL folder have 1 file, if the folder have LESSTHAN 0 data, it will go to LogIn Again.
            File fileCountChecker = new File(path + "/data/level/");
            File[] listOfFilesChecker = fileCountChecker.listFiles();

            if (listOfFilesChecker.length > 0) {
                //theres have a file on the folder
                System.out.println("Mayroong mga Files");
            } else {
                //no file on the folder
                deleteUsernameFiles(); //<-- delete the files on the USER - (USERNAME) folder for Security
                deleteUserLevelFiles(); //delete the User Level File folder for Security
                LoginGUI.setVisible(true);
            }

            //file is not exist (LOGIN FIRST)
            if (!(file1.exists())) {
                System.out.println("File is Not Exist");

                if (file1.createNewFile()) {
                    System.out.println("File created");
                    LoginGUI.setVisible(true);

                } else {
                    System.out.println("Cannot create login file. - 1");
                }
            }

        } catch (IOException e) {
            shutdownProblem(); //<-- display the error
        }
    }

    //delete the UserLevel Files
    public static void deleteUserLevelFiles() {
        //find the filepath of the current running/open jar/exe file
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();

        File file = new File(path + "/data/level/");
        for (File dirFilesDelete : file.listFiles()) {
            if (!dirFilesDelete.isDirectory()) {
                dirFilesDelete.delete();
            }
        }
    }

    //delete the User (USERNAME) Files
    public static void deleteUsernameFiles() {
        //find the filepath of the current running/open jar/exe file
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();

        File file = new File(path + "/data/user/");
        for (File dirFilesDelete : file.listFiles()) {
            if (!dirFilesDelete.isDirectory()) {
                dirFilesDelete.delete();
            }
        }
    }

    //shutting down void (if there's have a problem on Detector Files)
    private static void shutdownProblem() throws IOException {
        final JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setIndeterminate(true);
        final JOptionPane optionPane1 = new JOptionPane("<html><center>Something went wrong while starting the program!<br>Please re-install/restart the program or contact the system developer.<br><br>Exiting now...</br></center></html>", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog1 = new JDialog();
        optionPane1.add(progressBar1, BorderLayout.CENTER);
        progressBar1.setIndeterminate(true);
        dialog1.setTitle("SYSTEM ERROR | ECoders");
        dialog1.setAlwaysOnTop(true);
        dialog1.setModal(true);
        dialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        dialog1.setContentPane(optionPane1);
        dialog1.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog1.pack();
        Image imageloadingtop1;
        imageloadingtop1 = ImageIO.read(splashMainClass.class.getResource("/Images/wait_16px.png"));
        dialog1.setIconImage(imageloadingtop1); //set icon to dialog
        dialog1.setLocationRelativeTo(null); //center the dialog

        new Thread(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
            }
            dialog1.dispose();
            System.exit(0); //<-- force stop the program if there's a problem.
        }).start();
        dialog1.setVisible(true);
    }

    //get the user levels available
    public static void userlevels() {
        ResultSet rs1 = null;
        Statement st1 = null;
        try {

            st1 = conn.createStatement();
            rs1 = st1.executeQuery("select * FROM GUINames");

            NormalAdminStringFromDB = rs1.getString("DefaultUser");
            //userlevelCB.addItem(userCB);
            System.out.println(NormalAdminStringFromDB);

            SuperAdminStringFromDB = rs1.getString("DefaultAdmin");
            //userlevelCB.addItem(adminCB);
            System.out.println(SuperAdminStringFromDB);

            //userlevelCB.updateUI();
        } catch (SQLException | NullPointerException e) {
            System.out.println("ERR:" + e);
        } finally {
            try {
                st1.close();
                rs1.close();
            } catch (SQLException | NullPointerException e) {
            }
        }

    }

}
