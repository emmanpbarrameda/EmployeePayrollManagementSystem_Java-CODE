// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //
//PAYROLL
package MainPackage;

import SystemDB.DBconnection;
import Panels_EmployeeManager.Emp;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class LoginFrame extends javax.swing.JFrame {

    Connection conn;
    static int clicked = 0;
    String attemptover = "/3";

    //default admin name
    String adminString; //for this panel
    String adminDB; ///this string will get DATA from db

    //default admin name (CAPS)
    String adminString_CAPS; //for this panel
    String adminDB_CAPS; ///this string will get DATA from db

    //default USER name
    String userString; //for this panel
    String userDB; ///this string will get DATA from db

    //default USER name (CAPS)
    String userString_CAPS; //for this panel
    String userDB_CAPS; ///this string will get DATA from db

    //Normal Popups Title
    String mainnameString; //for this panel
    String mainPopupTitleNormalGUI; ///this string will get DATA from db

    //Error Popups Title
    String mainErrorString; //for this panel
    String mainPopupTitleErrorGUI; ///this string will get DATA from db

    //for ComboBox, add items from Database
    String noneCB;
    String userCB;
    String adminCB;

    int count = 0;

    //string to check current loggedIn user
    String currentLoggedInUser = null;
    //string to check current loggedIn user
    String currentLoggedInLevel = null;    
    
    //for checking of UserName and UserLevel on AutoLogin or Not AutoLogin
    String usernameString = null;
    String userlevelString = null;
    
    /**
     * Creates new form LoginFrame
     *
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public LoginFrame() throws SQLException, IOException {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        LoginFrame.this.getRootPane().setBorder(new MatteBorder(0, 1, 1, 1, (new Color(0, 102, 204))));
        DBconnection c = new DBconnection();
        conn = c.getconnection();
        clearText1();
        JTextFieldLimitAPI();
        noPasteCutPassword();
        GUINaming_DATA();
        fillCombo();
        //bluredlogin.setVisible(false);
        //guiTitle.setText(this.getTitle());

        //--------------------- DISABLE the AUTO TAB AND SHIFT in JTextFields UI ---------------------// 
        //this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.<KeyStroke>emptySet());
        this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.<KeyStroke>emptySet());
        this.setFocusTraversalKeys(KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS, Collections.<KeyStroke>emptySet());
        this.setFocusTraversalKeys(KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, Collections.<KeyStroke>emptySet());

        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        this.setFocusTraversalKeys(KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        this.setFocusTraversalKeys(KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, Collections.EMPTY_SET);

        //--------------------- FOR BUTTONS ---------------------// 
        exitButton.setBackground(new Color(0, 102, 204));
        minimizeButton.setBackground(new Color(0, 102, 204));

        exitButton.setSelected(false);
        minimizeButton.setSelected(false);

        exitButton.setFocusPainted(false);
        minimizeButton.setFocusPainted(false);

        exitButton.setDefaultCapable(false);
        minimizeButton.setDefaultCapable(false);

        //--------------------- NO BUTTONS ENABLED ON START ---------------------// 
        loginBTN.setText("LOG IN");
        loginBTN.setEnabled(false);
        clearBTN.setEnabled(false);
        usernameTF.setEnabled(false);
        passwordTF.setEnabled(false);
        showpasswordCB.setEnabled(false);
        userlevelCB.setSelectedItem(noneCB);

        chooselevel.setVisible(true);
        chooselevel.setText("Please Select User Level to Login or Register to create an account.");

    }

    //-------------------- START VOID CODES HERE --------------------//
    //GUINaming from Database
    public void GUINaming_DATA() throws SQLException {
        try {
            ResultSet rs;
            try (Statement st = conn.createStatement()) {
                rs = st.executeQuery("select * FROM GUINames");

                //set the GUI Title
                final String GUITopNameDB = rs.getString("GUINameLogin");
                guiTitle.setText(GUITopNameDB);
                this.setTitle(GUITopNameDB);

                //set the Default User
                userDB = rs.getString("DefaultUser");
                userDB_CAPS = rs.getString("DefaultUserCAPS");

                //set the Default Admin
                adminDB = rs.getString("DefaultAdmin");
                adminDB_CAPS = rs.getString("DefaultAdminCAPS");

                //set the Default Normal Popups Title Message
                mainnameString = rs.getString("PopupNormal");

                //set the Default Error Popups Title Message
                mainErrorString = rs.getString("PopupError");

                st.close();
            }
            rs.close();

        } catch (SQLException e) {
        }

        /*set the TEXT of THE STRING FROM THE LEFT OF THE CODE
        get the DATA from DATABASE that will set to STRING from the RIGHT OF THIS CODE*/
        userString = userDB;
        userString_CAPS = userDB_CAPS;

        adminString = adminDB;
        adminString_CAPS = adminDB_CAPS;

        mainPopupTitleNormalGUI = mainnameString;

        mainPopupTitleErrorGUI = mainErrorString;
    }

    //add item to ComboBox from Database Positions
    public void fillCombo() {

        userlevelCB.removeAllItems();
        try {
            ResultSet rs1;
            try (Statement st1 = conn.createStatement()) {
                rs1 = st1.executeQuery("select * FROM GUINames");

                noneCB = rs1.getString("DefaultNone");
                userlevelCB.addItem(noneCB);

                userCB = rs1.getString("DefaultUser");
                userlevelCB.addItem(userCB);

                adminCB = rs1.getString("DefaultAdmin");
                userlevelCB.addItem(adminCB);

                st1.close();
            }
            rs1.close();
            userlevelCB.updateUI();
        } catch (SQLException e) {
        }
    }

    public void showpasswordchar() {
        passwordTF.setEchoChar('\u25cf');
    }

    public void showpasswordchar_ClickLogin() {
        //currently showing password
        if (showpasswordCB.isSelected()) {
            showpasswordCB.setSelected(false);
            passwordTF.setEchoChar('\u25cf');

            //currently not showing password
        } else if (!showpasswordCB.isSelected()) {
            showpasswordCB.setSelected(false);
            passwordTF.setEchoChar('\u25cf');
        }
    }

    public void connectingtodb() throws IOException, AWTException {
        final JProgressBar progressBar = new JProgressBar();
        //progressBar.setIndeterminate(true);
        progressBar.setValue(1);
        progressBar.setStringPainted(true);
        final JOptionPane optionPane = new JOptionPane("<html><center>Connecting to the Server...<br>Please wait PATIENTLY!</center></html>", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        optionPane.add(progressBar, BorderLayout.CENTER);
        dialog.setTitle(mainnameString);
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        Image imageloadingtop;
        imageloadingtop = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
        dialog.setIconImage(imageloadingtop); //set icon to dialog
        dialog.setLocationRelativeTo(null); //center the dialog
        //dialog.setSize(200,200);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        // Set a timer
        new Thread(() -> {
            try {
                for (int loading = 0; loading <= 30; loading++) {
                    Thread.sleep(30);
                    progressBar.setValue(loading);
                }
                dialog.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        dialog.setVisible(true);

        // Set a timer
        new Thread(() -> {
            try {
                for (int loading = 31; loading <= 70; loading++) {
                    Thread.sleep(20);
                    progressBar.setValue(loading);
                }
                dialog.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        dialog.setVisible(true);

        // Set a timer
        new Thread(() -> {
            try {
                for (int loading = 71; loading <= 100; loading++) {
                    Thread.sleep(10);
                    progressBar.setValue(loading);
                }
                dialog.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        dialog.setVisible(true);

        final JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setIndeterminate(true);
        final JOptionPane optionPane1 = new JOptionPane("<html><center>Successfully Connected!<br>Launching...</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog1 = new JDialog();
        optionPane1.add(progressBar1, BorderLayout.CENTER);
        progressBar1.setIndeterminate(true);
        dialog1.setTitle(mainnameString);
        dialog1.setModal(true);
        dialog1.setContentPane(optionPane1);
        dialog1.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog1.pack();
        Image imageloadingtop1;
        imageloadingtop1 = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
        dialog1.setIconImage(imageloadingtop1); //set icon to dialog
        dialog1.setLocationRelativeTo(null); //center the dialog
        //dialog.setSize(200,200);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        // Set a timer
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            //displayTray();
            dialog1.dispose();
        }).start();
        dialog1.setVisible(true);

    }

    public void loginpanelloading() throws IOException {
        //JOptionPane.showOptionDialog(null, "Hello","Empty?", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        //ImageIcon iconofJOP = new ImageIcon("");
        final JOptionPane optionPane = new JOptionPane("<html><center>Logging in...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        dialog.setTitle(mainnameString);
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        Image imageloadingtop;
        imageloadingtop = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
        dialog.setIconImage(imageloadingtop); //set icon to dialog
        dialog.setLocationRelativeTo(null); //center the dialog
        //dialog.setSize(200,200);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        // Set a timer
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            dialog.dispose();
        }).start();
        dialog.setVisible(true);
    }

    public void loginpanelloadingcheck() throws IOException {
        //JOptionPane.showOptionDialog(null, "Hello","Empty?", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        //ImageIcon iconofJOP = new ImageIcon("");
        final JOptionPane optionPane = new JOptionPane("<html><center>Logging in...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        dialog.setTitle(mainnameString);
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        Image imageloadingtop;
        imageloadingtop = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
        dialog.setIconImage(imageloadingtop); //set icon to dialog
        dialog.setLocationRelativeTo(null); //center the dialog
        //dialog.setSize(200,200);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        // Set a timer
        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            dialog.dispose();
        }).start();
        dialog.setVisible(true);
    }

    public void shuttingdown() throws IOException {
        final JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setIndeterminate(true);
        final JOptionPane optionPane1 = new JOptionPane("<html><center>Sorry, You have reached maximum allowed attempts (3/3).<br>The system will exit automatically</center></html>", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog1 = new JDialog();
        optionPane1.add(progressBar1, BorderLayout.CENTER);
        progressBar1.setIndeterminate(true);
        dialog1.setTitle(mainnameString);
        dialog1.setModal(true);
        dialog1.setContentPane(optionPane1);
        dialog1.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog1.pack();
        Image imageloadingtop1;
        imageloadingtop1 = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
        dialog1.setIconImage(imageloadingtop1); //set icon to dialog
        dialog1.setLocationRelativeTo(null); //center the dialog
        //dialog.setSize(200,200);
        //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        // Set a timer
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            dialog1.dispose();
            //System.exit(0);
        }).start();
        dialog1.setVisible(true);
    }

    public void clearText1() {
        //usernameTF.setText("");
        passwordTF.setText("");
        showpasswordCB.setSelected(false);
    }

    public void clearText2() {
        usernameTF.setText("");
        passwordTF.setText("");
        showpasswordCB.setSelected(false);
    }

    public void JTextFieldLimitAPI() {
        usernameTF.setDocument(new JTextFieldLimitAPI(20));
        passwordTF.setDocument(new JTextFieldLimitAPI(8));
    }

    public void noPasteCutPassword() {
        passwordTF.setTransferHandler(null);
    }

    //setText the userlevel from Register
    public void userlevelVoid(String userlevelString) {
        userlevelCB.setSelectedItem(userlevelString);
        usernameTF.requestFocus();
        usernameTF.requestFocusInWindow();
    }

    public void audit() {
        Date currentDate = GregorianCalendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance();
        String dateString = df.format(currentDate);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        String timeString = sdf.format(d);

        String value0 = timeString;
        String value1 = dateString;
        String val = usernameTF.getText();
        try {
            String reg = "insert into Audit (emp_id, date, status) values ('" + val + "','" + value0 + " / " + value1 + "','Logged in as " + userlevelCB.getSelectedItem().toString() + ": " + usernameTF.getText() + "')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //get the userName from the file path for AutoLogin if account is not logged-out
    public void getUsername() {
        
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();

        System.out.println(path);

        File file1 = new File(path+"/data/user/"+usernameTF.getText() + "");
        try {
            if (file1.createNewFile()) {
                System.out.println("File created");
            }

        } catch (IOException e) {
        }
    }
    
    //get the userLevel from the file path for AutoLogin if account is not logged-out
    public void getUserLevel() {
        
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();

        System.out.println(path);

        File file1 = new File(path+"/data/level/"+userlevelCB.getSelectedItem().toString()+"");
        try {
            if (file1.createNewFile()) {
                System.out.println("File created");
            }

        } catch (IOException e) {
        }
    }
    
    
    //check if the user will AutologgedIn or not VIA USERNAME.
    public void checkDestinationPanel() throws IOException, URISyntaxException, SQLException {

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

                userlevelFileDetector();
                
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
                    //LoginGUI.setVisible(true);
                    //this is void, no need to declared that this panel will visible.
                    //the LOGIN BUTTON will handle the Visibility of this Panel
                    
                } else {
                    System.out.println("Cannot create login file. - 1");
                }
            }

        } catch (IOException e) {
            //shutdownProblem(); //<-- display the error
        }
    }

    //check if the user will AutologgedIn or not VIA USER-LEVEL.
    public void userlevelFileDetector() throws IOException, SQLException {

        //find the filepath of the current running/open jar/exe file
        File f = new File(System.getProperty("user.dir"));
        File dir = f.getAbsoluteFile();
        String path = dir.toString();
        System.out.println(path); //<-- print the file destination

        File file1 = new File(path + "/data/userdata.ecoders");

        //for visible of panels
        MainNavigationHomePanel NormalAdminGUI = new MainNavigationHomePanel();
        MainNavigationHomePanel_S_ADMIN SuperAdminGUI = new MainNavigationHomePanel_S_ADMIN();
        
        try {

            //file is already exist (THIS WILL BECOME AUTO LOGIN)
            if (file1.exists()) {
                System.out.println("File is Already Exist");

                //get the usernameDetector path for pasting the current user logged in.
                File userlevelFile = new File(path + "/data/level/");
                File[] listOfFiles = userlevelFile.listFiles();

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

                //admin
                if(userCB.equalsIgnoreCase(currentLoggedInLevel)) {
                    usernameString = currentLoggedInUser; //get the username
                    userlevelString = currentLoggedInLevel; //get the userlevel
                    
                    NormalAdminGUI.usernameVoid(usernameString); //set the username from here to userlevel panel
                    NormalAdminGUI.userlevelVoid(userlevelString); //set the userlevel from here to userlevel panel
                    NormalAdminGUI.setVisible(true);

                //ptr
                } else if(adminCB.equalsIgnoreCase(currentLoggedInLevel)) {
                    usernameString = currentLoggedInUser; //get the username
                    userlevelString = currentLoggedInLevel; //get the userlevel
                    
                    SuperAdminGUI.usernameVoid(usernameString); //set the username from here to userlevel panel
                    SuperAdminGUI.userlevelVoid(userlevelString); //set the userlevel from here to userlevel panel
                    SuperAdminGUI.setVisible(true);
                    
                }
                
            }

            //file is not exist (LOGIN FIRST)
            if (!(file1.exists())) {
                System.out.println("File is Not Exist");

                if (file1.createNewFile()) {
                    System.out.println("File created");
                    //LoginGUI.setVisible(true);
                    //this is void, no need to declared that this panel will visible.
                    //the LOGIN BUTTON will handle the Visibility of this Panel

                } else {
                    System.out.println("Cannot create login file. - 1");
                }
            }
            
        } catch (IOException e) {
            //shutdownProblem(); //<-- display the error
        }        
    }

    //forceClose/Clicked on X (Exit) button. and the Detector files will become deleted
    public void forceCloseDeleteDetectorFiles() {
        
        //delete the userdata.ecoders
        try {
            
            File f = new File(System.getProperty("user.dir"));
            File dir = f.getAbsoluteFile();
            String path = dir.toString();

            File file1 = new File(path + "/data/userdata.ecoders");
            
            if (file1.exists()) {
                if (file1.delete());
                System.out.println("File deleted");

            } else {
                System.out.println("Cannot delete login detector file.");
            }

        } catch (Exception e) {
            System.err.println("ERR:" + e);
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
    private void shutdownProblem() throws IOException {
        final JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setIndeterminate(true);
        final JOptionPane optionPane1 = new JOptionPane("<html><center>Something went wrong while disposing the Login Window!<br>Please re-install/restart the program or contact the system developer.<br><br>Exiting now...</br></center></html>", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
        imageloadingtop1 = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        pnlMAIN = new javax.swing.JPanel();
        usernameLABEL = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        showpasswordCB = new javax.swing.JCheckBox();
        passwordLABEL = new javax.swing.JLabel();
        passwordTF = new javax.swing.JPasswordField();
        pnlTop = new javax.swing.JPanel();
        guiTitle = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        minimizeButton = new javax.swing.JButton();
        loginBTN = new rojerusan.RSMaterialButtonRectangle();
        forgotpasswordBTN = new rojerusan.RSMaterialButtonRectangle();
        jLabel1 = new javax.swing.JLabel();
        userlevelLABEL = new javax.swing.JLabel();
        userlevelCB = new javax.swing.JComboBox<>();
        chooselevel = new javax.swing.JLabel();
        clearBTN = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("LOG IN AS ADMIN");
        jMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("AUTHENTICATION SYSTEM");
        setUndecorated(true);
        setResizable(false);

        pnlMAIN.setBackground(new java.awt.Color(253, 253, 253));
        pnlMAIN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        usernameLABEL.setForeground(new java.awt.Color(102, 102, 102));
        usernameLABEL.setText("username");
        pnlMAIN.add(usernameLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        usernameTF.setBackground(new java.awt.Color(253, 253, 253));
        usernameTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        usernameTF.setForeground(new java.awt.Color(102, 102, 102));
        usernameTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        usernameTF.setNextFocusableComponent(null);
        usernameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTFActionPerformed(evt);
            }
        });
        usernameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameTFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(usernameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 278, -1));

        showpasswordCB.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCB.setToolTipText("Show/Hide Password");
        showpasswordCB.setBorderPaintedFlat(true);
        showpasswordCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCB.setDoubleBuffered(true);
        showpasswordCB.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCBActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 358, -1, -1));

        passwordLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        passwordLABEL.setForeground(new java.awt.Color(102, 102, 102));
        passwordLABEL.setText("password");
        pnlMAIN.add(passwordLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        passwordTF.setBackground(new java.awt.Color(253, 253, 253));
        passwordTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordTF.setForeground(new java.awt.Color(102, 102, 102));
        passwordTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        passwordTF.setDoubleBuffered(true);
        passwordTF.setEchoChar('\u25cf');
        passwordTF.setOpaque(false);
        passwordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTFActionPerformed(evt);
            }
        });
        passwordTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(passwordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 278, -1));

        pnlTop.setBackground(new java.awt.Color(0, 102, 204));
        pnlTop.setPreferredSize(new java.awt.Dimension(116, 30));
        pnlTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTopMouseDragged(evt);
            }
        });
        pnlTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTopMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTopMousePressed(evt);
            }
        });
        pnlTop.setLayout(null);

        guiTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        guiTitle.setForeground(new java.awt.Color(255, 255, 255));
        guiTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TOP_ICON_18px.png"))); // NOI18N
        guiTitle.setText("LOG IN");
        guiTitle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                guiTitleMouseDragged(evt);
            }
        });
        guiTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guiTitleMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                guiTitleMousePressed(evt);
            }
        });
        pnlTop.add(guiTitle);
        guiTitle.setBounds(5, 0, 250, 30);

        exitButton.setBackground(new java.awt.Color(0, 102, 204));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit_button_icon.png"))); // NOI18N
        exitButton.setToolTipText("Close");
        exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.setDefaultCapable(false);
        exitButton.setFocusPainted(false);
        exitButton.setFocusable(false);
        exitButton.setIconTextGap(0);
        exitButton.setRequestFocusEnabled(false);
        exitButton.setVerifyInputWhenFocusTarget(false);
        exitButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                exitButtonMouseMoved(evt);
            }
        });
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButtonMouseExited(evt);
            }
        });
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        pnlTop.add(exitButton);
        exitButton.setBounds(334, 0, 18, 30);

        minimizeButton.setBackground(new java.awt.Color(0, 102, 204));
        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/minimize_button_icon.png"))); // NOI18N
        minimizeButton.setToolTipText("Minimize");
        minimizeButton.setBorder(null);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizeButton.setDefaultCapable(false);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setFocusable(false);
        minimizeButton.setIconTextGap(0);
        minimizeButton.setRequestFocusEnabled(false);
        minimizeButton.setVerifyInputWhenFocusTarget(false);
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseExited(evt);
            }
        });
        minimizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizeButtonActionPerformed(evt);
            }
        });
        pnlTop.add(minimizeButton);
        minimizeButton.setBounds(310, 0, 18, 30);

        pnlMAIN.add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 362, -1));

        loginBTN.setBackground(new java.awt.Color(51, 204, 0));
        loginBTN.setText("LOG IN");
        loginBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        loginBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(loginBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, 39));

        forgotpasswordBTN.setBackground(new java.awt.Color(12, 91, 160));
        forgotpasswordBTN.setText("FORGOT PASSWORD");
        forgotpasswordBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        forgotpasswordBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotpasswordBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(forgotpasswordBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 423, -1, 39));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainIcon2_128px.png"))); // NOI18N
        pnlMAIN.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 56, -1, -1));

        userlevelLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        userlevelLABEL.setForeground(new java.awt.Color(102, 102, 102));
        userlevelLABEL.setText("user level");
        pnlMAIN.add(userlevelLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        userlevelCB.setBackground(new java.awt.Color(253, 253, 253));
        userlevelCB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userlevelCB.setForeground(new java.awt.Color(102, 102, 102));
        userlevelCB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        userlevelCB.setLightWeightPopupEnabled(false);
        userlevelCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userlevelCBActionPerformed(evt);
            }
        });
        pnlMAIN.add(userlevelCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 278, -1));

        chooselevel.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        chooselevel.setForeground(new java.awt.Color(255, 0, 0));
        chooselevel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pnlMAIN.add(chooselevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 484, 320, 20));

        clearBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBTN.setText("CLEAR");
        clearBTN.setBorder(null);
        clearBTN.setBorderPainted(false);
        clearBTN.setContentAreaFilled(false);
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.setDefaultCapable(false);
        clearBTN.setFocusPainted(false);
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 280, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(362, 501));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    int xy, xx; //<-- for pnlTop
    private void pnlTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnlTopMouseDragged

    private void pnlTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_pnlTopMousePressed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // Exit/Close Button code:
        setColor1(exitButton);
        resetColor1(minimizeButton);

        int p = JOptionPane.showConfirmDialog(null, "Do You Want To Exit?", mainnameString, JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            forceCloseDeleteDetectorFiles();
            deleteUserLevelFiles();
            deleteUsernameFiles();
            System.exit(0);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void minimizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeButtonActionPerformed
        // Minimize Button code:
        resetColor2(exitButton);
        setColor2(minimizeButton);

        LoginFrame.this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeButtonActionPerformed

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        setColor1(exitButton);
        resetColor1(minimizeButton);

        exitButton.getToolTipText();
    }//GEN-LAST:event_exitButtonMouseEntered

    private void exitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseExited
        resetColor1(exitButton);
        resetColor1(minimizeButton);
    }//GEN-LAST:event_exitButtonMouseExited

    private void exitButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseMoved
    }//GEN-LAST:event_exitButtonMouseMoved

    private void minimizeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeButtonMouseEntered
        resetColor2(exitButton);
        setColor2(minimizeButton);

        minimizeButton.getToolTipText();
    }//GEN-LAST:event_minimizeButtonMouseEntered

    private void minimizeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeButtonMouseExited
        resetColor2(exitButton);
        resetColor2(minimizeButton);
    }//GEN-LAST:event_minimizeButtonMouseExited

    private void usernameTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            passwordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_usernameTFKeyPressed

    private void passwordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            showpasswordchar_ClickLogin();
            loginBTN.requestFocusInWindow();
            loginBTN.doClick();
        }
    }//GEN-LAST:event_passwordTFKeyPressed

    private void passwordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyReleased

    }//GEN-LAST:event_passwordTFKeyReleased

    private void loginBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBTNActionPerformed
        //LOGIN BUTTON CODE
        showpasswordchar_ClickLogin();

        try {
            //none
            if (userlevelCB.getSelectedItem().equals(noneCB) | usernameTF.getText().isEmpty() | passwordTF.getText().equals("")) {
                //bluredlogin.setVisible(true);
                //usernameTF.setVisible(false); passwordTF.setVisible(false); loginBTN.setVisible(false); createaccBTN.setVisible(false); userlevelCB.setVisible(false); showpasswordCB.setVisible(false);
                loginpanelloadingcheck();
                getToolkit().beep();
                JOptionPane.showMessageDialog(null, "One of the required field is empty!", mainErrorString, JOptionPane.ERROR_MESSAGE, null);
                //bluredlogin.setVisible(false);
                //usernameTF.setVisible(true); passwordTF.setVisible(true); loginBTN.setVisible(true); createaccBTN.setVisible(true); userlevelCB.setVisible(true); showpasswordCB.setVisible(true);
                usernameTF.requestFocusInWindow();

            } else {

                clicked++;

                if (clicked <= 3) {

                    //ADMINISTRATOR
                    if (userlevelCB.getSelectedItem().equals(adminString)) {
                        usernameTF.requestFocus();
                        usernameTF.requestFocusInWindow();
                        String sql = "SELECT * FROM Users WHERE division=? and username=? and password=? ";
                        ResultSet rs = null;
                        try (PreparedStatement pst = conn.prepareStatement(sql)) {
                            pst.setString(1, userlevelCB.getSelectedItem().toString());
                            pst.setString(2, usernameTF.getText());
                            pst.setString(3, passwordTF.getText());
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                //usernameTF.setVisible(false); passwordTF.setVisible(false); loginBTN.setVisible(false); createaccBTN.setVisible(false); userlevelCB.setVisible(false); showpasswordCB.setVisible(false);
                                //bluredlogin.setVisible(true);
                                loginpanelloading();
                                JOptionPane.showMessageDialog(null, "Login Successfully!", mainnameString, JOptionPane.INFORMATION_MESSAGE, null);
                                connectingtodb();
                                audit();
                                this.dispose();
                                //bluredlogin.setVisible(false);
                                //usernameTF.setVisible(true); passwordTF.setVisible(true); loginBTN.setVisible(true); createaccBTN.setVisible(true); userlevelCB.setVisible(true); showpasswordCB.setVisible(true);
                                MainNavigationHomePanel_S_ADMIN mainAdmin = new MainNavigationHomePanel_S_ADMIN();

                                count = 0; //set reset count to 0 

                                usernameString = usernameTF.getText();
                                mainAdmin.usernameVoid(usernameString); //set the username from here to main panel

                                userlevelString = userlevelCB.getSelectedItem().toString();
                                mainAdmin.userlevelVoid(userlevelString); //set the userlevel from here to main panel

                                checkDestinationPanel(); //<-- check if the login file detector is exist or not
                                //userlevelFileDetector(); //check if the userLevel file detector is exist or not
                                getUsername(); //<-- get the username to system data
                                getUserLevel(); //<-- get the username to system data
                                
                                Emp.empId = usernameString;
                                mainAdmin.setVisible(true);
                            } else {
                                //usernameTF.setVisible(false); passwordTF.setVisible(false); loginBTN.setVisible(false); createaccBTN.setVisible(false); userlevelCB.setVisible(false); showpasswordCB.setVisible(false);
                                //bluredlogin.setVisible(true);
                                loginpanelloading();
                                getToolkit().beep();
                                JOptionPane.showMessageDialog(null, "<html><center>Username and password is incorrect, please try again. <br>Attempt: " + clicked + "" + attemptover + " </center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE, null);
                                //bluredlogin.setVisible(false);
                                //usernameTF.setVisible(true); passwordTF.setVisible(true); loginBTN.setVisible(true); createaccBTN.setVisible(true); userlevelCB.setVisible(true); showpasswordCB.setVisible(true);
                                usernameTF.setText("");
                                passwordTF.setText("");
                                usernameTF.requestFocusInWindow();

                            }
                            //pst.close();
                        } catch (AWTException | URISyntaxException ex) {
                            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        rs.close();

                        //USER
                    } else if (userlevelCB.getSelectedItem().equals(userString)) {
                        usernameTF.requestFocus();
                        usernameTF.requestFocusInWindow();
                        String sql = "SELECT * FROM Users WHERE division=? and username=? and password=? ";
                        ResultSet rs = null;
                        try (PreparedStatement pst = conn.prepareStatement(sql)) {
                            pst.setString(1, userlevelCB.getSelectedItem().toString());
                            pst.setString(2, usernameTF.getText());
                            pst.setString(3, passwordTF.getText());
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                //usernameTF.setVisible(false); passwordTF.setVisible(false); loginBTN.setVisible(false); createaccBTN.setVisible(false); userlevelCB.setVisible(false); showpasswordCB.setVisible(false);
                                //bluredlogin.setVisible(true);
                                loginpanelloading();
                                JOptionPane.showMessageDialog(null, "Login Successfully!", mainnameString, JOptionPane.INFORMATION_MESSAGE, null);
                                connectingtodb();
                                audit();
                                this.dispose();
                                //bluredlogin.setVisible(false);
                                //usernameTF.setVisible(true); passwordTF.setVisible(true); loginBTN.setVisible(true); createaccBTN.setVisible(true); userlevelCB.setVisible(true); showpasswordCB.setVisible(true);
                                MainNavigationHomePanel mainUser = new MainNavigationHomePanel();

                                count = 0; //set reset count to 0 

                                usernameString = usernameTF.getText();
                                mainUser.usernameVoid(usernameString); //set the username from here to main panel

                                userlevelString = userlevelCB.getSelectedItem().toString();
                                mainUser.userlevelVoid(userlevelString); //set the userlevel from here to main panel

                                checkDestinationPanel(); //<-- check if the login file detector is exist or not
                                //userlevelFileDetector(); //check if the userLevel file detector is exist or not
                                getUsername(); //<-- get the username to system data
                                getUserLevel(); //<-- get the username to system data
                                
                                Emp.empId = usernameString;

                                mainUser.setVisible(true);

                            } else {
                                //usernameTF.setVisible(false); passwordTF.setVisible(false); loginBTN.setVisible(false); createaccBTN.setVisible(false); userlevelCB.setVisible(false); showpasswordCB.setVisible(false);
                                //bluredlogin.setVisible(true);
                                loginpanelloading();
                                getToolkit().beep();
                                JOptionPane.showMessageDialog(null, "<html><center>Username and password is incorrect, please try again. <br>Attempt: " + clicked + "" + attemptover + " </center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE, null);
                                //bluredlogin.setVisible(false);
                                //usernameTF.setVisible(true); passwordTF.setVisible(true); loginBTN.setVisible(true); createaccBTN.setVisible(true); userlevelCB.setVisible(true); showpasswordCB.setVisible(true);
                                usernameTF.setText("");
                                passwordTF.setText("");
                                usernameTF.requestFocusInWindow();

                            }
                        } catch (AWTException | URISyntaxException ex) {
                            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        rs.close();
                    }

                } else if (clicked > 3) {
                    getToolkit().beep();
                    shuttingdown();
                    System.exit(0);
                }
            }

        } catch (HeadlessException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong! ERR: while_login " + e, mainErrorString, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loginBTNActionPerformed

    private void showpasswordCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCBActionPerformed
        if (showpasswordCB.isSelected()) {
            passwordTF.setEchoChar((char) 0);
            passwordTF.requestFocusInWindow();
        } else {
            showpasswordchar();
            passwordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCBActionPerformed

    private void usernameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTFActionPerformed

    }//GEN-LAST:event_usernameTFActionPerformed

    private void passwordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTFActionPerformed
    }//GEN-LAST:event_passwordTFActionPerformed

    private void userlevelCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userlevelCBActionPerformed
        if (userlevelCB.getSelectedItem().equals(noneCB)) {
            usernameTF.requestFocus();
            usernameTF.requestFocusInWindow();
            loginBTN.setText("LOG IN");
            loginBTN.updateUI();
            loginBTN.setEnabled(false);
            forgotpasswordBTN.setEnabled(true);
            clearBTN.setEnabled(false);
            usernameTF.setEnabled(false);
            passwordTF.setEnabled(false);
            showpasswordCB.setEnabled(false);
            chooselevel.setVisible(true);
            chooselevel.setText("Please Select User Level to Login or Register to create an account.");
            clearText2();

        } else if (userlevelCB.getSelectedItem().equals(adminString)) {
            usernameTF.requestFocus();
            usernameTF.requestFocusInWindow();
            loginBTN.setText("LOG IN");
            loginBTN.updateUI();
            loginBTN.setEnabled(true);
            forgotpasswordBTN.setEnabled(false);
            clearBTN.setEnabled(false);
            usernameTF.setEnabled(true);
            passwordTF.setEnabled(true);
            showpasswordCB.setEnabled(true);
            chooselevel.setVisible(false);
            chooselevel.setText("");
            clearText2();

        } else if (userlevelCB.getSelectedItem().equals(userString)) {
            usernameTF.requestFocus();
            usernameTF.requestFocusInWindow();
            loginBTN.setText("LOG IN AS " + userString_CAPS);
            loginBTN.updateUI();
            loginBTN.setEnabled(true);
            forgotpasswordBTN.setEnabled(true);
            clearBTN.setEnabled(true);
            usernameTF.setEnabled(true);
            passwordTF.setEnabled(true);
            showpasswordCB.setEnabled(true);
            chooselevel.setVisible(false);
            chooselevel.setText("");
            clearText2();
        }
    }//GEN-LAST:event_userlevelCBActionPerformed

    private void usernameTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTFKeyTyped
        //letters and some numbers only
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_SHIFT)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PLUS)
                || (c == KeyEvent.VK_UNDERSCORE)
                || //(c==KeyEvent.VK_SPACE) ||
                (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_1) || (c == KeyEvent.VK_2) || (c == KeyEvent.VK_3) || (c == KeyEvent.VK_4) || (c == KeyEvent.VK_5)
                || (c == KeyEvent.VK_6) || (c == KeyEvent.VK_7) || (c == KeyEvent.VK_8) || (c == KeyEvent.VK_9) || (c == KeyEvent.VK_0)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }
    }//GEN-LAST:event_usernameTFKeyTyped

    private void passwordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyTyped
    }//GEN-LAST:event_passwordTFKeyTyped

    private void forgotpasswordBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotpasswordBTNActionPerformed
        try {
            ResetPasswordFrame mainResetPassword = new ResetPasswordFrame();
            mainResetPassword.setVisible(true);
            this.dispose();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_forgotpasswordBTNActionPerformed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed

    }//GEN-LAST:event_clearBTNActionPerformed

    private void guiTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiTitleMouseClicked
        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuClose = new JMenuItem("Close Permanently");
        final JMenuItem menuRestore = new JMenuItem("Restore to Default Location");

        JLabel ShortcutsLBL = new JLabel();
        ShortcutsLBL.setText(" Shortcuts");
        ShortcutsLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10));

        if (SwingUtilities.isRightMouseButton(evt)) {

            //------------------------------------------//
            //restore
            ActionListener restoreActionListener
                    = (ActionEvent e) -> {
                        this.setLocationRelativeTo(null);
                    };
            menuRestore.addActionListener(restoreActionListener);

            //------------------------------------------//
            //minimize
            ActionListener minimizeActionListener
                    = (ActionEvent e) -> {
                        LoginFrame.this.setState(Frame.ICONIFIED);
                    };
            menuMinimize.addActionListener(minimizeActionListener);

            //------------------------------------------//
            //close taskbar
            ActionListener closeActionListener
                    = (ActionEvent e) -> {
                        forceCloseDeleteDetectorFiles();
                        deleteUserLevelFiles();
                        deleteUsernameFiles();
                        System.exit(0);
                    };
            menuClose.addActionListener(closeActionListener);

            //----------------------------------------------------------//
            //set jmenus cursor
            menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            //set popup background
            popupRightClick.setBackground(new Color(249, 250, 253));

            //add popup on click
            popupRightClick.removeAll();
            popupRightClick.add(menuRestore);
            popupRightClick.addSeparator();
            popupRightClick.add(ShortcutsLBL);
            popupRightClick.add(menuMinimize);
            popupRightClick.add(menuClose);
            popupRightClick.updateUI();

            //show popup on right click.
            //popupRightClick.show(pnlTop, evt.getXOnScreen(), evt.getYOnScreen()/3);
            popupRightClick.show(guiTitle, evt.getX(), evt.getY() / 3);
        }
    }//GEN-LAST:event_guiTitleMouseClicked

    private void pnlTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseClicked
        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuClose = new JMenuItem("Close Permanently");
        final JMenuItem menuRestore = new JMenuItem("Restore to Default Location");

        JLabel ShortcutsLBL = new JLabel();
        ShortcutsLBL.setText(" Shortcuts");
        ShortcutsLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10));

        if (SwingUtilities.isRightMouseButton(evt)) {

            //------------------------------------------//
            //restore
            ActionListener restoreActionListener
                    = (ActionEvent e) -> {
                        this.setLocationRelativeTo(null);
                    };
            menuRestore.addActionListener(restoreActionListener);

            //------------------------------------------//
            //minimize
            ActionListener minimizeActionListener
                    = (ActionEvent e) -> {
                        LoginFrame.this.setState(Frame.ICONIFIED);
                    };
            menuMinimize.addActionListener(minimizeActionListener);

            //------------------------------------------//
            //close taskbar
            ActionListener closeActionListener
                    = (ActionEvent e) -> {
                        forceCloseDeleteDetectorFiles();
                        deleteUserLevelFiles();
                        deleteUsernameFiles();
                        System.exit(0);
                    };
            menuClose.addActionListener(closeActionListener);

            //----------------------------------------------------------//
            //set jmenus cursor
            menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            //set popup background
            popupRightClick.setBackground(new Color(249, 250, 253));

            //add popup on click
            popupRightClick.removeAll();
            popupRightClick.add(menuRestore);
            popupRightClick.addSeparator();
            popupRightClick.add(ShortcutsLBL);
            popupRightClick.add(menuMinimize);
            popupRightClick.add(menuClose);
            popupRightClick.updateUI();

            //show popup on right click.
            //popupRightClick.show(pnlTop, evt.getXOnScreen(), evt.getYOnScreen()/3);
            popupRightClick.show(pnlTop, evt.getX(), evt.getY() / 3);
        }
    }//GEN-LAST:event_pnlTopMouseClicked

    private void guiTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiTitleMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_guiTitleMousePressed

    private void guiTitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiTitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_guiTitleMouseDragged

    // set Color to pnlTop (EXIT)
    void setColor1(JButton btn1) {
        btn1.setBackground(new Color(255, 51, 51));
    }

    void resetColor1(JButton btn1) {
        btn1.setBackground(new Color(0, 102, 204));
    }

    // set Color to pnlTop (MINIMIZE, SETTINGS)
    void setColor2(JButton btn2) {
        btn2.setBackground(new Color(155, 182, 211));
    }

    void resetColor2(JButton btn2) {
        btn2.setBackground(new Color(0, 102, 204));
    }

    /*    
    // set border on Field when Focus
    void setBorder1(JTextField border) {
        border.setBorder(new MatteBorder(2, 2, 2, 2, (new Color(12,91,160))));
    }

    void resetBorder1(JTextField border) {
        border.setBorder(new MatteBorder(0, 0, 2, 0, (new Color(12,91,160))));
    }
    
    // set border on Field when Focus
    void setBorder2(JPasswordField border) {
        border.setBorder(new MatteBorder(2, 2, 2, 2, (new Color(12,91,160))));
    }

    void resetBorder2(JPasswordField border) {
        border.setBorder(new MatteBorder(0, 0, 2, 0, (new Color(12,91,160))));
    }*/

    //next code here
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize Modern LaF" + ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new LoginFrame().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chooselevel;
    private javax.swing.JButton clearBTN;
    private javax.swing.JButton exitButton;
    private rojerusan.RSMaterialButtonRectangle forgotpasswordBTN;
    private javax.swing.JLabel guiTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private rojerusan.RSMaterialButtonRectangle loginBTN;
    private javax.swing.JButton minimizeButton;
    private javax.swing.JLabel passwordLABEL;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JPanel pnlMAIN;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JCheckBox showpasswordCB;
    public javax.swing.JComboBox<String> userlevelCB;
    private javax.swing.JLabel userlevelLABEL;
    private javax.swing.JLabel usernameLABEL;
    public javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //
