// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //
package MainPackage;

import SystemDB.DBconnection;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.border.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Panels.AdminMenusPanel;
import Panels.EmployeeManagerMenusPanel;
import Panels.HomePanel;
import Panels_EmployeeManager.Emp;
import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class MainNavigationHomePanel extends javax.swing.JFrame {

    //for Database Connection Variable
    Connection conn;

    GridBagLayout layout = new GridBagLayout();
    HomePanel p1;
    EmployeeManagerMenusPanel p2;
    AdminMenusPanel p3;

    //others
    String filename = null;
    String genderString;
    byte[] person_image = null;
    Image imgRefreshTable;
    Image imgRefreshHome;
    Image printingIcon;

    //for app name
    String mainAppNameFromDB; ///this string will get DATA from db

    //for company name
    String companyNameFromDB; ///this string will get DATA from db

    //Normal Popups Title
    String mainnameString; ///this string will get DATA from db

    //Error Popups Title
    String mainErrorString; ///this string will get DATA from db

    //for money currency
    String pesoSignString; ///this string will get DATA from db

    //for top app name
    String apptopNameFromDB; ///this string will get DATA from db

    //for SYSTEM TRAY
    public final PopupMenu popup = new PopupMenu();
    public final MenuItem menuHide = new MenuItem("Minimize only on Taskbar");
    public final MenuItem menuShow = new MenuItem("Show");
    public final MenuItem menuExit = new MenuItem("Exit");
    public final MenuItem menuLogoutTray = new MenuItem("Logout");

    //for TOAST
    final String printwait = "Please wait...";
    final String hidden = "Hidden on taskbar.";
    final String open = "System screen will show in a second.";
    final String close = "System closed.";
    final String theme1 = "Default theme selected.";
    final String theme1A = "Default theme already selected";
    final String theme2 = "IntelliJ-white theme selected.";
    final String theme2A = "IntelliJ-white theme already selected";
    final String theme3 = "Windows theme selected.";
    final String theme3A = "Windows theme already selected";
    final String theme4 = "Cross theme selected.";
    final String theme4A = "Cross theme already selected";
    final String selecttheme = "Please select theme.";
    final String restart = "System is Restarting...";
    final Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    final int widthvarToast = this.getSize().width;
    final int heightvatToast = this.getSize().height;
    final int xPosToast = (dimToast.width - widthvarToast) / 2;
    final int yPosToast = (dimToast.width - heightvatToast) / 2;

    final ToastManager printToast = new ToastManager(printwait, xPosToast, yPosToast);
    final ToastManager hiddenToast = new ToastManager(hidden, xPosToast, yPosToast);
    final ToastManager openToast = new ToastManager(open, xPosToast, yPosToast);
    final ToastManager closeToast = new ToastManager(close, xPosToast, yPosToast);

    final ToastManager theme1Toast = new ToastManager(theme1, xPosToast, yPosToast);
    final ToastManager theme1AToast = new ToastManager(theme1A, xPosToast, yPosToast);
    final ToastManager theme2Toast = new ToastManager(theme2, xPosToast, yPosToast);
    final ToastManager theme2AToast = new ToastManager(theme2A, xPosToast, yPosToast);
    final ToastManager theme3Toast = new ToastManager(theme3, xPosToast, yPosToast);
    final ToastManager theme3AToast = new ToastManager(theme3A, xPosToast, yPosToast);
    final ToastManager theme4Toast = new ToastManager(theme4, xPosToast, yPosToast);
    final ToastManager theme4AToast = new ToastManager(theme4A, xPosToast, yPosToast);
    final ToastManager selectthemeToast = new ToastManager(selecttheme, xPosToast, yPosToast);
    final ToastManager restartToast = new ToastManager(restart, xPosToast, yPosToast);

    AboutSystem aboutApppanel = new AboutSystem();
    AboutDeveloper aboutDevpanel = new AboutDeveloper();

    //for restart
    public static final String SUN_JAVA_COMMAND = "sun.java.command";
    Runnable runBeforeRestart;

    //for finding the saving and opening file location from the current running jar/exe file
    File current_file = new File(System.getProperty("user.dir"));
    File current_dir = current_file.getAbsoluteFile();
    String currentRunningPath = current_dir.toString();

    /**
     * Creates new form CardPanels
     *
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public MainNavigationHomePanel() throws SQLException, IOException {
        initComponents();

        //connection to database
        DBconnection c = new DBconnection();
        conn = c.getconnection();

        this.imgRefreshHome = ImageIO.read(getClass().getResource("/Images/reset_16px.png"));
        this.imgRefreshTable = ImageIO.read(getClass().getResource("/Images/update icon.png"));
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        MainNavigationHomePanel.this.getRootPane().setBorder(new LineBorder(new Color(0, 102, 204)));
        lblTitle.setText(this.getTitle());
        setColor(homeBTN);

        currentdate();
        showtime();
        showtime2();
        currentday();

        //disable COPY/CUT/PASTE on TEXTFIELD
        noPasteCut();

        //GUI Naming from db
        GUINaming_DATA();

        //set visible NOFICATION LOADER
        consoleLoading.setVisible(false); //loading bar
        consoleText.setVisible(false); //loading bar infos

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

        p1 = new HomePanel();
        p2 = new EmployeeManagerMenusPanel();
        p3 = new AdminMenusPanel();
        pnlDisplay.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDisplay.add(p1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDisplay.add(p2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDisplay.add(p3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (OSUtils.getOSType() == OSUtils.OSType.MacOS) {
            pnlTop.remove(pnlTitle);
            pnlTop.remove(pnlDisplay);

            pnlTop.add(pnlTitle, BorderLayout.EAST);
            pnlTop.add(pnlActions, BorderLayout.WEST);

            pnlActions.remove(lblClose);
            //pnlActions.remove(lblMaximize);
            pnlActions.remove(lblMinimize);

            pnlActions.add(lblClose);
            //pnlActions.add(lblMaximize);
            pnlActions.add(lblMinimize);

            pnlTitle.remove(lblTitle);
            pnlTitle.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 6));
            pnlTitle.add(lblTitle);
        }

        if (OSUtils.getOSType() == OSUtils.OSType.Windows) {
            pnlTop.remove(pnlTitle);
            pnlTop.remove(pnlDisplay);

            pnlTop.add(pnlTitle, BorderLayout.WEST);
            pnlTop.add(pnlActions, BorderLayout.EAST);

            pnlActions.remove(lblClose);
            //pnlActions.remove(lblMaximize);
            pnlActions.remove(lblMinimize);

            pnlActions.add(lblMinimize);
            //pnlActions.add(lblMaximize);
            pnlActions.add(lblClose);

            pnlTitle.remove(lblTitle);
            pnlTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 6));
            pnlTitle.add(lblTitle);
        }
    }

    //-------------------- START VOID CODES HERE --------------------//
    //GUINaming from Database
    public void GUINaming_DATA() throws SQLException {
        try {
            ResultSet rsGNaming;
            try (Statement stGNaming = conn.createStatement()) {
                rsGNaming = stGNaming.executeQuery("select * FROM GUINames");

                //set the GUI Title
                mainAppNameFromDB = rsGNaming.getString("MainAppName");
                lblTitle.setText(mainAppNameFromDB);
                this.setTitle(mainAppNameFromDB);

                //top app name
                apptopNameFromDB = rsGNaming.getString("MainTopAppName");
                topappnameLBL.setText(apptopNameFromDB);

                //company name
                companyNameFromDB = rsGNaming.getString("MainCompanyName");
                companynameLBL.setText(companyNameFromDB);

                //currency symbol
                pesoSignString = rsGNaming.getString("CurrencySign");

                //set the Default Normal Popups Title Message
                mainnameString = rsGNaming.getString("PopupNormal");

                //set the Default Error Popups Title Message
                mainErrorString = rsGNaming.getString("PopupError");

                stGNaming.close();
            }
            rsGNaming.close();

        } catch (SQLException e) {
        }

        /*set the TEXT of THE STRING FROM THE LEFT OF THE CODE
        get the DATA from DATABASE that will set to STRING from the RIGHT OF THIS CODE*/
        //mainPopupTitleNormalGUI = mainnameString;
        //mainPopupTitleErrorGUI = mainErrorString;
        //string 4 panel   //string from db data
        //mainAppNameString = mainAppNameFromDB;
    }

    //setText the username from Auth 
    public void usernameVoid(String usernameString) {
        useronlineTF.setText(usernameString);
    }

    //setText the userlevel from Auth 
    public void userlevelVoid(String userlevelString) {
        userlvlTF.setText(userlevelString);
    }

    //for opening files and folders
    static int clicked1 = 0;
    static int clicked2 = 0;
    static int clicked3 = 0;
    static int clicked4 = 0;
    static int clicked5 = 0;
    static int clicked6 = 0;

    //restart void
    public void restartSystem() throws IOException {
        RestartPopupTRAYMessage(); //<-- Restart Tray Message
        restartToast.showToast(); //show Restart Toast Message
        getToolkit().beep();

        try {
            // java binary
            String java = System.getProperty("java.home") + "/bin/java";
            // vm arguments
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            vmArguments.stream().filter((arg) -> (!arg.contains("-agentlib"))).map((arg) -> {
                vmArgsOneLine.append(arg);
                return arg;
            }).forEachOrdered((_item) -> {
                vmArgsOneLine.append(" ");
            });

            // if it's the agent argument : we ignore it otherwise the
            // address of the old application and the new one will be in conflict
            // init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

            // program main and program arguments
            String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
            // program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
                // if it's a jar, add -jar mainJar
                cmd.append("-jar ").append(new File(mainCommand[0]).getPath());
            }
            if (mainCommand[0].endsWith(".exe")) {
                // if it's a jar, add -jar mainJar
                cmd.append("-exe ").append(new File(mainCommand[0]).getPath());
            }
            if (mainCommand[0].endsWith(".exe")) {
                // if it's a jar, add -jar mainJar
                cmd.append("-exec ").append(new File(mainCommand[0]).getPath());

            } else {
                // else it's a .class, add the classpath and mainClass
                cmd.append("-cp \"").append(System.getProperty("java.class.path")).append("\" ").append(mainCommand[0]);
            }

            // finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }

            // execute the command in a shutdown hook, to be sure that all the
            // resources have been disposed before restarting the application
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                    }
                }
            });

            // execute some custom code before restarting
            if (runBeforeRestart != null) {
                runBeforeRestart.run();
            }

            // exit
            System.exit(0);
        } catch (Exception e) {
            // something went wrong
            throw new IOException("Error while trying to restart the " + mainAppNameFromDB + "", e);
        }
    }

    //disable COPY/CUT/PASTE on TEXTFIELD
    public void noPasteCut() {
        //fullnameTFsave.setTransferHandler(null);
    }

    //HOME panel time
    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    //notification time
    public static String now2(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
        return sdf1.format(cal.getTime());
    }

    public void currentdate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, YYYY");
        //dateLABEL.setText(sdf.format(d));
    }

    public void currentday() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        //dayLABEL.setText("Today is "+sdf.format(d));
        //dayLABEL.setText("Today is "+now("EEEEEE"));  
    }

    void showtime() {
        new Timer(0, (ActionEvent e) -> {
            Date d = new Date();
            //SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
            //currenttimeLABEL.setText(sdf.format(d));
        }).start();
    }

    public void currentday2() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        //dayLABEL.setText("Today is "+sdf.format(d));
        //dayLABEL.setText("Today is "+now("EEEEEE"));

        //time on notification 
        //notifTime.setText(now("EEEEEE")+ ", "+ (now("hh:mm:ss aaa")));
    }

    void showtime2() {
        new Timer(0, (ActionEvent e) -> {
            Date d1 = new Date();
            //SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm:ss a");
            //currenttimeLABEL.setText(sdf.format(d));
            notifTime.setText(now2("EEEEEE") + ", " + (sdf1.format(d1)));
        }).start();
    }

    public void counthome() throws SQLException {
        Statement stmt = conn.createStatement();
        {

            try (ResultSet rsTOTAL = stmt.executeQuery("select count(*) from Questions")) {
                rsTOTAL.next();
                //createdquestionTFhome.setText(rsTOTAL.getInt("count(*)")+" questions");

                /*
                //count Children
                ResultSet rsChildren = stmt.executeQuery("select count(*) from members where ORGANIZATION = 'Children'");
                rsChildren.next();
                totalhome.setText(rsChildren.getInt("count(*)")+"");
                 */
                rsTOTAL.close();
            }
            stmt.close();
            //createdquestionTFhome.setText(rsTOTAL.getInt("count(*)")+" questions");
        }
    }

    //audit logout event
    public void logoutAudit() {
        PreparedStatement pstLogout = null;
        try {

            Date currentDate = GregorianCalendar.getInstance().getTime();
            DateFormat df = DateFormat.getDateInstance();
            String dateString = df.format(currentDate);

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            String timeString = sdf.format(d);

            String value0 = timeString;
            String values = dateString;

            String value = Emp.empId;
            String reg = "insert into Audit (emp_id,date,status) values ('" + value + "','" + value0 + " / " + values + "','Logged out')";
            pstLogout = conn.prepareStatement(reg);
            pstLogout.execute();

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, e);
            System.err.println("ERR: " + e);

        } finally {
            try {
                pstLogout.close();
            } catch (SQLException e) {
            }
        }
    }

    public void exitViaTray() {
        forceclose();
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        popup.removeAll();
        popup.add(menuHide);
        popup.addSeparator();
        popup.add(menuLogoutTray);
        popup.remove(menuExit);
        popup.remove(menuShow);
    }

    public void openViaTray() {
        openToast.showToast();
        MainNavigationHomePanel.this.setState(Frame.NORMAL);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        popup.removeAll();
        popup.add(menuHide);
        popup.add(menuExit);
        popup.addSeparator();
        popup.add(menuLogoutTray);
        popup.remove(menuShow);
    }

    public void hideViaTray() {
        int p = JOptionPane.showConfirmDialog(null, "<html><center>Are You Sure That Do You Want to Close the Program Window?<br><br><br>This Action Will Hide the Program on the Taskbar, But the Program is Still Running on Background.<br><br>Take Note that your account is still online!,<br>If You Want to Logout, Click 'NO' and then Find the 'LOGOUT' Button to logout.</br></center></html>", "Minimize on Taskbar (Run the Program on Backgroud)", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            new Thread(() -> {
                try {
                    for (int loading = 1; loading <= 10; loading++) {
                        Thread.sleep(20);
                        System.gc();
                        //openViaTray();
                        this.setVisible(true);
                        consoleLoading.setVisible(true);
                        consoleLoading.setValue(loading);
                        consoleLoading.setStringPainted(false);
                        consoleLoading.setIndeterminate(true);
                        consoleLoading.setMinimum(1);
                        consoleLoading.setMaximum(10);
                        consoleText.setVisible(true);
                        consoleText.setText("Hiding on taskbar...");
                        //sidepane.setEnabled(false);
                        //pnlParent.setEnabled(false);
                        //pnlTop.setEnabled(false);
                        getToolkit().beep();
                    }
                } catch (InterruptedException e) {
                }
                //closing code here
                MainNavigationHomePanel.this.setDefaultCloseOperation(javax.swing.JFrame.HIDE_ON_CLOSE);
                MainNavigationHomePanel.this.setVisible(false);
                consoleLoading.setVisible(false);
                consoleLoading.setStringPainted(false);
                consoleLoading.setIndeterminate(true);
                consoleText.setVisible(false);
                consoleText.setText("");

                //popup.addNotify();
                popup.removeAll();
                popup.add(menuShow);
                popup.add(menuExit);
                popup.addSeparator();
                popup.add(menuLogoutTray);
                popup.remove(menuHide);

                hiddenToast.showToast();

                hideViaTrayPopupMessage(); //<-- show the tray message
            }).start();
        } else {
            lblClose.doClick();
        }
    }

    //display system tray-Icon and tray-Message
    public void createSystemTrayIcon() {
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Images/TOP_ICON.png"));

            MouseListener mouseListener
                    = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }
            };

            //exit
            ActionListener exitListener
                    = (ActionEvent e) -> {
                        exitViaTray();
                    };

            //hide
            ActionListener hideListener
                    = (ActionEvent e) -> {
                        //Runtime r = Runtime.getRuntime();
                        System.out.println("Hiding...");
                        hideViaTray();
                    };

            //show
            ActionListener showListener
                    = (ActionEvent e) -> {
                        System.out.println("Opening...");
                        openViaTray();
                    };

            //logout
            ActionListener logoutListener
                    = (ActionEvent e) -> {
                        System.out.println("Logging out...");
                        forcelogout();
                    };

            menuHide.addActionListener(hideListener);
            menuShow.addActionListener(showListener);
            menuExit.addActionListener(exitListener);
            menuLogoutTray.addActionListener(logoutListener);
            popup.add(menuHide);
            popup.add(menuExit);
            popup.addSeparator();
            popup.add(menuLogoutTray);

            trayIcon = new TrayIcon(image, "" + mainAppNameFromDB + " - Right click this tray icon to show shortcut menu", popup);

            ActionListener actionListener
                    = (ActionEvent e) -> {
                        trayIcon.displayMessage(
                                "Welcome to " + mainAppNameFromDB + "\n\t" + userlvlTF.getText() + " Panel",
                                "Logged in as [" + userlvlTF.getText() + "] " + useronlineTF.getText() + "",
                                TrayIcon.MessageType.INFO);
                    };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
                //trayIcon.displayMessage("My program ", "version: blahblah", TrayIcon.MessageType.INFO);
                trayIcon.displayMessage("Welcome to " + mainAppNameFromDB + "\n\t" + userlvlTF.getText() + " Panel", "Logged in as [" + userlvlTF.getText() + "] " + useronlineTF.getText() + "", TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            //  System Tray is not supported
        }
    }

    //Tray Message - Hide on Screen
    public void hideViaTrayPopupMessage() {
        try {
            trayIcon.displayMessage("" + mainAppNameFromDB + " Is Now Running on Background.", "Right Click the App Icon on the Icons Panel in Taskbar to Open the Program Window.", MessageType.INFO);
        } catch (Exception e) {
            //  System Tray is not supported
        }
    }

    //Tray Message - Restart
    public void RestartPopupTRAYMessage() {
        try {
            trayIcon.displayMessage("" + mainAppNameFromDB + " Is Restarting...", "Please wait for a moment.", MessageType.INFO);
        } catch (Exception e) {
            //  System Tray is not supported
        }
    }

    public void auditForceShutdown() {
        Date currentDate = GregorianCalendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance();
        String dateString = df.format(currentDate);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        String timeString = sdf.format(d);

        String value0 = timeString;
        String value1 = dateString;
        String val = useronlineTF.getText();
        try {
            String reg = "insert into Audit (emp_id, date, status) values ('" + val + "','" + value0 + " / " + value1 + "','Force shutdown of System by: " + val + "')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //force close
    public void forceclose() {
        //close permanently
        int p = JOptionPane.showConfirmDialog(null, "<html><center>Are You Sure That Do You Want to Exit?<br><br>Take Note that your account is still online!,<br>If You Want to Logout, Click 'NO' and then Find the 'LOGOUT' Button to logout.</br></center></html>", "Exit", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            new Thread(() -> {
                try {
                    try {
                        for (int loading = 1; loading <= 50; loading++) {
                            Thread.sleep(10);
                            System.gc();
                            consoleLoading.setVisible(true);
                            consoleLoading.setValue(loading);
                            consoleLoading.setStringPainted(false);
                            consoleLoading.setIndeterminate(true);
                            consoleLoading.setMinimum(1);
                            consoleLoading.setMaximum(50);
                            consoleText.setVisible(true);
                            consoleText.setText("Force stop on progress...");
                            useronlineTF.setText("");
                            useronlineTF.setVisible(false);
                            loggedInAs.setVisible(false);
                            pnlParent.setEnabled(false);
                            pnlTop.setEnabled(false);
                            getToolkit().beep();
                        }
                    } catch (InterruptedException e) {
                    }
                    //closing code here
                    this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
                    this.setVisible(false);
                    this.dispose();
                    closeToast.showToast();
                    consoleLoading.setVisible(true);
                    consoleLoading.setStringPainted(false);
                    consoleLoading.setIndeterminate(true);
                    consoleText.setVisible(false);
                    consoleText.setText("");
                    conn.close();
                    Runtime.getRuntime().exit(0);
                    System.exit(0);
                } catch (SQLException ex) {
                    Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            auditForceShutdown();
            //can code here
        } else {
            lblClose.doClick();
        }
    }

    //force logout()
    public void forcelogout() {
        tray.remove(trayIcon);
        try {
            final JProgressBar progressBar1 = new JProgressBar();
            progressBar1.setIndeterminate(true);
            consoleLoading.setIndeterminate(true);
            consoleLoading.setStringPainted(false);
            consoleText.setText("Logging out...");
            consoleText.setVisible(true);
            consoleLoading.setVisible(true);
            final JOptionPane optionPane1 = new JOptionPane("<html><center>Logging out...<br/>Please wait for a moment.</center><html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                    logoutAudit(); // audit logout
                    dialog1.dispose();
                    consoleText.setVisible(false);
                    consoleLoading.setVisible(false);
                    consoleLoading.setStringPainted(true);
                    consoleLoading.setIndeterminate(false);

                    conn.close();
                    deleteLogoutFile(); //<-- delete the user detector file
                    deleteUserLevelFile(); //<-- this void will delete the UserLevel detector
                    deleteUsernameFile(); //<-- this void will delete the username detector, dispose this panel and setVisible(true) the Login.

                } catch (SQLException ex) {
                    Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }).start();
            dialog1.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete the login detector file
    public void deleteLogoutFile() {
        try {
            File file = new File(currentRunningPath + "/data/userdata.ecoders");
            if (file.delete()) { //delete file
                System.out.println("Deleted User detector File");

            } else {
                System.out.println("Cannot Delete the File");
            }
        } catch (Exception ex) {
            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete the Username detector file and the Login panel will become visible(true)
    public void deleteUserLevelFile() {
        try {
            File file = new File(currentRunningPath + "/data/level/" + userlvlTF.getText());
            if (file.delete()) { //delete file
                System.out.println("Deleted User File");

            } else {
                System.out.println("Cannot Delete the File");
            }
        } catch (Exception ex) {
            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete the Username detector file and the Login panel will become visible(true)
    public void deleteUsernameFile() {
        try {
            File file = new File(currentRunningPath + "/data/user/" + useronlineTF.getText());
            if (file.delete()) { //delete file
                System.out.println("Deleted User File");

                this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
                this.setVisible(false);
                this.dispose();
                LoginFrame forceAuth = new LoginFrame();
                forceAuth.setVisible(true);

            } else {
                System.out.println("Cannot Delete the File");
                this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
                this.setVisible(false);
                this.dispose();
                LoginFrame forceAuth = new LoginFrame();
                forceAuth.setVisible(true);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //old close
    public void closePermanently() {
        //ImageIcon printicon = new ImageIcon(getClass().getResource("/Images/word_64px.png"));
        final JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frame.pack();
        String[] options = new String[3];
        options[0] = "Close the System Permanently";
        options[1] = "Minimize only on Taskbar";
        options[2] = "Cancel";
        int jop = JOptionPane.showOptionDialog(frame.getContentPane(), "<html><center>Please choose:</center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);

        switch (jop) {
            case 0:
                //close permanently
                new Thread(() -> {
                    try {
                        try {
                            for (int loading = 1; loading <= 10; loading++) {
                                Thread.sleep(2);
                                System.gc();
                                consoleLoading.setVisible(true);
                                consoleLoading.setValue(loading);
                                consoleLoading.setStringPainted(false);
                                consoleLoading.setIndeterminate(true);
                                consoleLoading.setMinimum(1);
                                consoleLoading.setMaximum(10);
                                consoleText.setVisible(true);
                                consoleText.setText("Force stop on progress...");
                                useronlineTF.setText("");
                                useronlineTF.setVisible(false);
                                loggedInAs.setVisible(false);
                                topNavigationPane.setEnabled(false);
                                pnlParent.setEnabled(false);
                                pnlTop.setEnabled(false);
                                getToolkit().beep();
                            }
                        } catch (InterruptedException e) {
                        }
                        //closing code here
                        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        closeToast.showToast();
                        consoleLoading.setVisible(true);
                        consoleLoading.setStringPainted(false);
                        consoleLoading.setIndeterminate(true);
                        consoleText.setVisible(false);
                        consoleText.setText("");
                        conn.close();
                        Runtime.getRuntime().exit(0);
                        System.exit(0);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
                auditForceShutdown();
                //can code here
                break;

            case 1:
                //hide on taskbar 
                hideViaTray();
                break;

            default:
                //code here
                break;
        }
    }

    //another void here
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExitButtonAction_JDialog = new javax.swing.JDialog();
        mainpanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        viewpanel = new javax.swing.JPanel();
        logoutBTN1 = new javax.swing.JButton();
        fullExitBTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        hideOnTaskbarBTN = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        txt_emp = new javax.swing.JLabel();
        pnlTopJDialog_Exit = new javax.swing.JPanel();
        lblTitle_JDialogEXIT = new javax.swing.JLabel();
        exitBTNJDIalogExit = new javax.swing.JButton();
        pnlTop = new javax.swing.JPanel();
        pnlActions = new javax.swing.JPanel();
        lblSettings = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        lblClose = new javax.swing.JButton();
        pnlTitle = new javax.swing.JPanel();
        lblTitleIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        pnlParent = new javax.swing.JPanel();
        topNavigationPane = new javax.swing.JPanel();
        topappnameLBL = new javax.swing.JLabel();
        rSPanelImage1 = new rojerusan.RSPanelImage();
        companynameLBL = new javax.swing.JLabel();
        logoutBTN = new javax.swing.JButton();
        homeBTN = new javax.swing.JButton();
        employeemanagerBTN = new javax.swing.JButton();
        userlvlTF = new javax.swing.JLabel();
        pnlDisplay = new javax.swing.JPanel();
        pnlDown = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        useronlineTF = new javax.swing.JLabel();
        notifTime = new javax.swing.JLabel();
        consoleText = new javax.swing.JLabel();
        consoleLoading = new javax.swing.JProgressBar();
        loggedInAs = new javax.swing.JLabel();
        themeLABEL = new javax.swing.JLabel();

        ExitButtonAction_JDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        ExitButtonAction_JDialog.setTitle("SYSTEM");
        ExitButtonAction_JDialog.setBackground(new java.awt.Color(249, 250, 253));
        ExitButtonAction_JDialog.setUndecorated(true);
        ExitButtonAction_JDialog.setResizable(false);
        ExitButtonAction_JDialog.setType(java.awt.Window.Type.UTILITY);
        ExitButtonAction_JDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                ExitButtonAction_JDialogWindowOpened(evt);
            }
        });
        ExitButtonAction_JDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Please choose action:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutBTN1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        logoutBTN1.setText("<html><center>Logout</br></center></html>");
        logoutBTN1.setToolTipText("<html><center>Logout the current account logged-in.<br><br>The Login Window will become visible again after clicking this button.</br></center></html>");
        logoutBTN1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutBTN1.setDefaultCapable(false);
        logoutBTN1.setDoubleBuffered(true);
        logoutBTN1.setFocusPainted(false);
        logoutBTN1.setFocusable(false);
        logoutBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBTN1ActionPerformed(evt);
            }
        });
        viewpanel.add(logoutBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 240, 100));

        fullExitBTN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        fullExitBTN.setText("<html><center>Full Exit<br>Payroll Mgmt. System</br></center></html>");
        fullExitBTN.setToolTipText("<html><center>(stops system monitoring tasks)<br><br>Take not that your current <br>Logged-in account is still online!<br>Click the Logout Button if you want to logout.</br></center></html>");
        fullExitBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fullExitBTN.setDefaultCapable(false);
        fullExitBTN.setDoubleBuffered(true);
        fullExitBTN.setFocusPainted(false);
        fullExitBTN.setFocusable(false);
        fullExitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullExitBTNActionPerformed(evt);
            }
        });
        viewpanel.add(fullExitBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 100));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("<html><center>Logout the current account logged-in.<br><br>The Login Window will become visible again after clicking this button.</br></center></html>");
        viewpanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 240, 80));

        hideOnTaskbarBTN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        hideOnTaskbarBTN.setText("<html><center>Close<br>Program Window</br></center></html>");
        hideOnTaskbarBTN.setToolTipText("MainNavigationHomePanel_S_ADMIN");
        hideOnTaskbarBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hideOnTaskbarBTN.setDefaultCapable(false);
        hideOnTaskbarBTN.setDoubleBuffered(true);
        hideOnTaskbarBTN.setFocusPainted(false);
        hideOnTaskbarBTN.setFocusable(false);
        hideOnTaskbarBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideOnTaskbarBTNActionPerformed(evt);
            }
        });
        viewpanel.add(hideOnTaskbarBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 240, 100));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("<html><center>(stops system monitoring tasks)<br><br>Take not that your current <br>Logged-in account is still online!<br>Click the Logout Button if you want to logout.</br></center></html>");
        viewpanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 140, 250, 110));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel62.setText("<html><center>Payroll Mgmt. System will become Hidden on Taskbar and run on background.<br><br>The Program Window can be open by clicking the Icons menu in the right Taskbar and Click the \"Show\" button to open the Main Window.</br></center></html>");
        viewpanel.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 240, 140));

        jScrollPane2.setViewportView(viewpanel);

        mainpanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 780, 290));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        mainpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 90, -1));

        ExitButtonAction_JDialog.getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 800, 340));

        pnlTopJDialog_Exit.setBackground(new java.awt.Color(0, 102, 204));
        pnlTopJDialog_Exit.setPreferredSize(new java.awt.Dimension(116, 30));
        pnlTopJDialog_Exit.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTopJDialog_ExitMouseDragged(evt);
            }
        });
        pnlTopJDialog_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTopJDialog_ExitMousePressed(evt);
            }
        });
        pnlTopJDialog_Exit.setLayout(null);

        lblTitle_JDialogEXIT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTitle_JDialogEXIT.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle_JDialogEXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TOP_ICON_18px.png"))); // NOI18N
        lblTitle_JDialogEXIT.setText("SYSTEM");
        pnlTopJDialog_Exit.add(lblTitle_JDialogEXIT);
        lblTitle_JDialogEXIT.setBounds(5, 0, 230, 30);

        exitBTNJDIalogExit.setBackground(new java.awt.Color(0, 102, 204));
        exitBTNJDIalogExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit_button_icon.png"))); // NOI18N
        exitBTNJDIalogExit.setToolTipText("Cancel");
        exitBTNJDIalogExit.setBorder(null);
        exitBTNJDIalogExit.setBorderPainted(false);
        exitBTNJDIalogExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitBTNJDIalogExit.setDefaultCapable(false);
        exitBTNJDIalogExit.setFocusPainted(false);
        exitBTNJDIalogExit.setFocusable(false);
        exitBTNJDIalogExit.setIconTextGap(0);
        exitBTNJDIalogExit.setRequestFocusEnabled(false);
        exitBTNJDIalogExit.setVerifyInputWhenFocusTarget(false);
        exitBTNJDIalogExit.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                exitBTNJDIalogExitMouseMoved(evt);
            }
        });
        exitBTNJDIalogExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitBTNJDIalogExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitBTNJDIalogExitMouseExited(evt);
            }
        });
        exitBTNJDIalogExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBTNJDIalogExitActionPerformed(evt);
            }
        });
        pnlTopJDialog_Exit.add(exitBTNJDIalogExit);
        exitBTNJDIalogExit.setBounds(775, 0, 20, 30);

        ExitButtonAction_JDialog.getContentPane().add(pnlTopJDialog_Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Payroll System");
        setLocationByPlatform(true);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTop.setBackground(new java.awt.Color(0, 102, 204));
        pnlTop.setPreferredSize(new java.awt.Dimension(1024, 30));
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
        pnlTop.setLayout(new java.awt.BorderLayout(0, 5));

        pnlActions.setBackground(new java.awt.Color(0, 102, 204));
        pnlActions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlActionsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlActionsMouseExited(evt);
            }
        });
        pnlActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu1_18px.png"))); // NOI18N
        lblSettings.setToolTipText("More");
        lblSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSettingsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblSettingsMouseReleased(evt);
            }
        });
        pnlActions.add(lblSettings);

        lblMinimize.setBackground(new java.awt.Color(0, 102, 204));
        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_minus_18px_1.png"))); // NOI18N
        lblMinimize.setToolTipText("Minimize");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMinimizeMousePressed(evt);
            }
        });
        pnlActions.add(lblMinimize);

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_multiply_18px_1.png"))); // NOI18N
        lblClose.setToolTipText("Close");
        lblClose.setBorder(null);
        lblClose.setBorderPainted(false);
        lblClose.setContentAreaFilled(false);
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.setDefaultCapable(false);
        lblClose.setFocusPainted(false);
        lblClose.setFocusable(false);
        lblClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblCloseActionPerformed(evt);
            }
        });
        pnlActions.add(lblClose);

        pnlTop.add(pnlActions, java.awt.BorderLayout.LINE_END);

        pnlTitle.setBackground(new java.awt.Color(0, 102, 204));
        pnlTitle.setPreferredSize(new java.awt.Dimension(400, 28));
        pnlTitle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleMouseDragged(evt);
            }
        });
        pnlTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTitleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleMousePressed(evt);
            }
        });
        pnlTitle.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 6));

        lblTitleIcon.setBackground(new java.awt.Color(0, 102, 204));
        lblTitleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TOP_ICON_18px.png"))); // NOI18N
        lblTitleIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblTitleIconMousePressed(evt);
            }
        });
        pnlTitle.add(lblTitleIcon);

        lblTitle.setBackground(new java.awt.Color(0, 102, 204));
        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("PAYROLL SYSTEM");
        lblTitle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblTitleMouseMoved(evt);
            }
        });
        lblTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblTitleMousePressed(evt);
            }
        });
        pnlTitle.add(lblTitle);

        pnlTop.add(pnlTitle, java.awt.BorderLayout.LINE_START);

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1313, -1));

        pnlParent.setLayout(new java.awt.BorderLayout());

        topNavigationPane.setBackground(new java.awt.Color(45, 118, 232));
        topNavigationPane.setForeground(new java.awt.Color(51, 51, 51));
        topNavigationPane.setPreferredSize(new java.awt.Dimension(250, 60));
        topNavigationPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                topNavigationPaneMouseDragged(evt);
            }
        });
        topNavigationPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                topNavigationPaneMousePressed(evt);
            }
        });
        topNavigationPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topappnameLBL.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        topappnameLBL.setForeground(new java.awt.Color(255, 255, 255));
        topappnameLBL.setText("PAYROLL SYSTEM");
        topNavigationPane.add(topappnameLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 510, 40));

        rSPanelImage1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/Images/TOP_LEFT_ICON_SHADOW_64px.png"))); // NOI18N

        javax.swing.GroupLayout rSPanelImage1Layout = new javax.swing.GroupLayout(rSPanelImage1);
        rSPanelImage1.setLayout(rSPanelImage1Layout);
        rSPanelImage1Layout.setHorizontalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        rSPanelImage1Layout.setVerticalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        topNavigationPane.add(rSPanelImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 60, 60));

        companynameLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        companynameLBL.setForeground(new java.awt.Color(255, 255, 255));
        companynameLBL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        companynameLBL.setText("ECoders");
        companynameLBL.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        topNavigationPane.add(companynameLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 30, 200, -1));

        logoutBTN.setBackground(new java.awt.Color(45, 118, 232));
        logoutBTN.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        logoutBTN.setForeground(new java.awt.Color(255, 255, 255));
        logoutBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout_menu_32px.png"))); // NOI18N
        logoutBTN.setText("LOG OUT");
        logoutBTN.setToolTipText("");
        logoutBTN.setBorder(null);
        logoutBTN.setBorderPainted(false);
        logoutBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutBTN.setDefaultCapable(false);
        logoutBTN.setDoubleBuffered(true);
        logoutBTN.setFocusPainted(false);
        logoutBTN.setFocusable(false);
        logoutBTN.setIconTextGap(2);
        logoutBTN.setRequestFocusEnabled(false);
        logoutBTN.setVerifyInputWhenFocusTarget(false);
        logoutBTN.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                logoutBTNMouseMoved(evt);
            }
        });
        logoutBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBTNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBTNMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutBTNMousePressed(evt);
            }
        });
        logoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBTNActionPerformed(evt);
            }
        });
        topNavigationPane.add(logoutBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 0, 120, 60));

        homeBTN.setBackground(new java.awt.Color(45, 118, 232));
        homeBTN.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        homeBTN.setForeground(new java.awt.Color(255, 255, 255));
        homeBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home_menu_2_32px.png"))); // NOI18N
        homeBTN.setText("HOME");
        homeBTN.setToolTipText("");
        homeBTN.setBorder(null);
        homeBTN.setBorderPainted(false);
        homeBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeBTN.setDefaultCapable(false);
        homeBTN.setDoubleBuffered(true);
        homeBTN.setFocusPainted(false);
        homeBTN.setFocusable(false);
        homeBTN.setIconTextGap(2);
        homeBTN.setRequestFocusEnabled(false);
        homeBTN.setVerifyInputWhenFocusTarget(false);
        homeBTN.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                homeBTNMouseMoved(evt);
            }
        });
        homeBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeBTNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeBTNMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                homeBTNMousePressed(evt);
            }
        });
        homeBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBTNActionPerformed(evt);
            }
        });
        topNavigationPane.add(homeBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 110, 60));

        employeemanagerBTN.setBackground(new java.awt.Color(45, 118, 232));
        employeemanagerBTN.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        employeemanagerBTN.setForeground(new java.awt.Color(255, 255, 255));
        employeemanagerBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/employee_management_menu_32px.png"))); // NOI18N
        employeemanagerBTN.setText("EMPLOYEE MANAGER");
        employeemanagerBTN.setToolTipText("");
        employeemanagerBTN.setBorder(null);
        employeemanagerBTN.setBorderPainted(false);
        employeemanagerBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeemanagerBTN.setDefaultCapable(false);
        employeemanagerBTN.setDoubleBuffered(true);
        employeemanagerBTN.setFocusPainted(false);
        employeemanagerBTN.setFocusable(false);
        employeemanagerBTN.setIconTextGap(2);
        employeemanagerBTN.setRequestFocusEnabled(false);
        employeemanagerBTN.setVerifyInputWhenFocusTarget(false);
        employeemanagerBTN.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                employeemanagerBTNMouseMoved(evt);
            }
        });
        employeemanagerBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                employeemanagerBTNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                employeemanagerBTNMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                employeemanagerBTNMousePressed(evt);
            }
        });
        employeemanagerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeemanagerBTNActionPerformed(evt);
            }
        });
        topNavigationPane.add(employeemanagerBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 190, 60));

        userlvlTF.setForeground(new java.awt.Color(45, 118, 232));
        userlvlTF.setText("jLabel1");
        topNavigationPane.add(userlvlTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 60, -1));

        pnlParent.add(topNavigationPane, java.awt.BorderLayout.PAGE_START);

        pnlDisplay.setBackground(new java.awt.Color(249, 250, 253));
        pnlDisplay.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlParent.add(pnlDisplay, java.awt.BorderLayout.CENTER);
        pnlDisplay.getAccessibleContext().setAccessibleParent(this);

        getContentPane().add(pnlParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1313, 631));

        pnlDown.setBackground(new java.awt.Color(0, 102, 204));
        pnlDown.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 102, 204)));
        pnlDown.setPreferredSize(new java.awt.Dimension(1024, 28));
        pnlDown.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlDownMouseDragged(evt);
            }
        });
        pnlDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDownMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlDownMousePressed(evt);
            }
        });
        pnlDown.setLayout(null);

        lblTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/notification_16px.png"))); // NOI18N
        lblTitle1.setText("Notifications");
        pnlDown.add(lblTitle1);
        lblTitle1.setBounds(0, 0, 100, 28);

        useronlineTF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        useronlineTF.setForeground(new java.awt.Color(255, 255, 255));
        pnlDown.add(useronlineTF);
        useronlineTF.setBounds(205, 0, 110, 28);

        notifTime.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        notifTime.setForeground(new java.awt.Color(255, 255, 255));
        notifTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notifTime.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(0, 0, 255)));
        pnlDown.add(notifTime);
        notifTime.setBounds(1170, 0, 138, 28);

        consoleText.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        consoleText.setForeground(new java.awt.Color(255, 255, 255));
        consoleText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        consoleText.setText("Console");
        pnlDown.add(consoleText);
        consoleText.setBounds(650, 0, 350, 28);

        consoleLoading.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        consoleLoading.setStringPainted(true);
        pnlDown.add(consoleLoading);
        consoleLoading.setBounds(1010, 5, 146, 18);

        loggedInAs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        loggedInAs.setForeground(new java.awt.Color(255, 255, 255));
        loggedInAs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loggedInAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/online_16px.png"))); // NOI18N
        loggedInAs.setText("Logged in As:");
        pnlDown.add(loggedInAs);
        loggedInAs.setBounds(100, 0, 100, 28);

        themeLABEL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        themeLABEL.setForeground(new java.awt.Color(255, 255, 255));
        themeLABEL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        themeLABEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/defaulttheme_16px.png"))); // NOI18N
        themeLABEL.setText("Default Theme");
        pnlDown.add(themeLABEL);
        themeLABEL.setBounds(315, 0, 160, 28);

        getContentPane().add(pnlDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 661, 1313, -1));

        setSize(new java.awt.Dimension(1313, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    int xy, xx;
    int xy1, xx1;
    TrayIcon trayIcon; //<-- for system tray icon and message
    SystemTray tray; //for system tray
    private void topNavigationPaneMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topNavigationPaneMouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_topNavigationPaneMouseDragged

    private void topNavigationPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topNavigationPaneMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_topNavigationPaneMousePressed


    private void lblMinimizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMousePressed
        MainNavigationHomePanel.this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMousePressed

    private void pnlTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_pnlTopMousePressed

    private void pnlTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnlTopMouseDragged

    private void pnlTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseClicked
        //FULLSCREEN the SCREEN
        //if (evt.getClickCount() == 2 && !evt.isConsumed()) {
        //if (CardPanels.this.getExtendedState() == MAXIMIZED_BOTH) {
        //CardPanels.this.setExtendedState(JFrame.NORMAL);
        //} else {
        //CardPanels.this.setExtendedState(MAXIMIZED_BOTH);
        //}
        //}

        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuMinimizeTaskbar = new JMenuItem("Close Program Window");
        final JMenuItem menuClose = new JMenuItem("Full Exit " + mainAppNameFromDB);
        final JMenuItem menuRestart = new JMenuItem("Restart");
        final JMenuItem menuLogout = new JMenuItem("Logout");
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
                        MainNavigationHomePanel.this.setState(Frame.ICONIFIED);
                    };
            menuMinimize.addActionListener(minimizeActionListener);

            //------------------------------------------//
            //minimize taskbar
            ActionListener minimizeTaskbarActionListener
                    = (ActionEvent e) -> {
                        hideViaTray();
                    };
            menuMinimizeTaskbar.addActionListener(minimizeTaskbarActionListener);

            //------------------------------------------//
            //close taskbar
            ActionListener closeActionListener
                    = (ActionEvent e) -> {
                        forceclose();
                    };
            menuClose.addActionListener(closeActionListener);

            //------------------------------------------//
            //logout taskbar
            ActionListener logoutActionListener
                    = (ActionEvent e) -> {
                        forcelogout();
                    };
            menuLogout.addActionListener(logoutActionListener);

            //------------------------------------------//
            //restart taskbar
            ActionListener restartActionListener
                    = (ActionEvent e) -> {
                        try {
                            restartSystem();
                        } catch (IOException ex) {
                            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    };
            menuRestart.addActionListener(restartActionListener);

            //----------------------------------------------------------//
            //set jmenus cursor
            menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimizeTaskbar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuRestart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            //set popup background
            popupRightClick.setBackground(new Color(249, 250, 253));

            //add popup on click
            popupRightClick.removeAll();
            popupRightClick.add(menuRestore);
            popupRightClick.addSeparator();
            popupRightClick.add(ShortcutsLBL);
            popupRightClick.add(menuMinimize);
            popupRightClick.add(menuMinimizeTaskbar);
            popupRightClick.add(menuLogout);
            popupRightClick.add(menuRestart);
            popupRightClick.add(menuClose);
            popupRightClick.updateUI();

            //show popup on right click.
            //popupRightClick.show(pnlTop, evt.getXOnScreen(), evt.getYOnScreen()/3);
            popupRightClick.show(pnlTop, evt.getX(), evt.getY() / 3);
        }
    }//GEN-LAST:event_pnlTopMouseClicked

    private void pnlDownMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDownMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlDownMouseDragged

    private void pnlDownMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDownMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlDownMouseClicked

    private void pnlDownMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDownMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlDownMousePressed

    private void pnlActionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlActionsMouseEntered
    }//GEN-LAST:event_pnlActionsMouseEntered

    private void pnlActionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlActionsMouseExited
    }//GEN-LAST:event_pnlActionsMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        homeBTN.doClick();

        MainNavigationHomePanel.this.setState(Frame.NORMAL);
        this.setEnabled(true);

        //display TRAY
        createSystemTrayIcon();

    }//GEN-LAST:event_formWindowOpened

    private void lblTitleIconMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleIconMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTitleIconMousePressed

    private void lblTitleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTitleMouseMoved

    private void lblTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTitleMouseEntered

    private void lblTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTitleMouseExited

    private void lblTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTitleMousePressed

    private void pnlTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlTitleMouseEntered

    private void pnlTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlTitleMouseExited

    private void logoutBTNMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBTNMouseMoved

    }//GEN-LAST:event_logoutBTNMouseMoved

    private void logoutBTNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBTNMouseEntered

    }//GEN-LAST:event_logoutBTNMouseEntered

    private void logoutBTNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBTNMouseExited

    }//GEN-LAST:event_logoutBTNMouseExited

    private void logoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBTNActionPerformed

        int p = JOptionPane.showConfirmDialog(null, "Are You Sure That Do You Want To Logout?", mainnameString + " | LOG OUT", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            tray.remove(trayIcon);
            try {
                final JProgressBar progressBar1 = new JProgressBar();
                progressBar1.setIndeterminate(true);
                consoleLoading.setIndeterminate(true);
                consoleLoading.setStringPainted(false);
                consoleText.setText("Logging out...");
                consoleText.setVisible(true);
                consoleLoading.setVisible(true);
                final JOptionPane optionPane1 = new JOptionPane("<html><center>Logging out...<br/>Please wait for a moment.</center><html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                        }
                        logoutAudit(); //audit logout to admin activities
                        dialog1.dispose();
                        consoleText.setVisible(false);
                        consoleLoading.setVisible(false);
                        consoleLoading.setStringPainted(true);
                        consoleLoading.setIndeterminate(false);

                        conn.close();
                        deleteLogoutFile(); //<-- delete the user detector file
                        deleteUserLevelFile(); //<-- this void will delete the UserLevel detector
                        deleteUsernameFile(); //<-- this void will delete the username detector, dispose this panel and setVisible(true) the Login.

                    } catch (SQLException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }).start();
                dialog1.setVisible(true);

            } catch (IOException ex) {
                Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_logoutBTNActionPerformed

    private void homeBTNMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBTNMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_homeBTNMouseMoved

    private void homeBTNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBTNMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_homeBTNMouseEntered

    private void homeBTNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBTNMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_homeBTNMouseExited

    private void homeBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBTNActionPerformed
        p1.setVisible(true);
        p2.setVisible(false);
        p3.setVisible(false);
    }//GEN-LAST:event_homeBTNActionPerformed

    private void homeBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBTNMousePressed
        setColor(homeBTN);
        resetColor(employeemanagerBTN);
        resetColor(logoutBTN);
    }//GEN-LAST:event_homeBTNMousePressed

    private void logoutBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBTNMousePressed
        resetColor(homeBTN);
        resetColor(employeemanagerBTN);
        setColor(logoutBTN);
    }//GEN-LAST:event_logoutBTNMousePressed

    private void employeemanagerBTNMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeemanagerBTNMouseMoved
    }//GEN-LAST:event_employeemanagerBTNMouseMoved

    private void employeemanagerBTNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeemanagerBTNMouseEntered
    }//GEN-LAST:event_employeemanagerBTNMouseEntered

    private void employeemanagerBTNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeemanagerBTNMouseExited
    }//GEN-LAST:event_employeemanagerBTNMouseExited

    private void employeemanagerBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeemanagerBTNMousePressed
        resetColor(homeBTN);
        setColor(employeemanagerBTN);
        resetColor(logoutBTN);
    }//GEN-LAST:event_employeemanagerBTNMousePressed

    private void employeemanagerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeemanagerBTNActionPerformed
        p1.setVisible(false);
        p2.setVisible(true);
        p3.setVisible(false);
    }//GEN-LAST:event_employeemanagerBTNActionPerformed

    private void lblSettingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingsMousePressed

        //set the items
        JPopupMenu popupSettings = new JPopupMenu();
        JMenuItem menuAboutApp = new JMenuItem("About " + mainAppNameFromDB + "");
        JMenuItem menuAboutDev = new JMenuItem("About Developer");
        JMenuItem menuTheme1 = new JMenuItem("★ Default Theme (Recommended)");
        JMenuItem menuTheme2 = new JMenuItem("IntelliJ-white Theme");
        JMenuItem menuTheme3 = new JMenuItem("Windows Theme");
        JMenuItem menuTheme4 = new JMenuItem("Cross Theme");

        JLabel AboutLBL = new JLabel();
        AboutLBL.setText(" About");
        AboutLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10));

        JLabel ThemesLBL = new JLabel();
        ThemesLBL.setText(" Themes");
        ThemesLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10));

        //----------------------------------------------------------//
        //about app menu action command
        ActionListener aboutAppActionListener
                = (ActionEvent e) -> {
                    aboutApppanel.setVisible(true);
                };
        menuAboutApp.addActionListener(aboutAppActionListener);

        //----------------------------------------------------------//
        //about dev menu action command
        ActionListener aboutDevActionListener
                = (ActionEvent e) -> {
                    aboutDevpanel.setVisible(true);
                };
        menuAboutDev.addActionListener(aboutDevActionListener);

        //----------------------------------------------------------//
        //theme1 action command
        ActionListener Theme1ActionListener
                = (ActionEvent e) -> {
                    try {
                        if (themeLABEL.getText().equals("Default Theme")) {
                            popupSettings.setVisible(false);
                            theme1AToast.showToast();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                        } else {
                            FlatLightLaf.install();
                            SwingUtilities.updateComponentTreeUI(this);
                            this.pack();
                            themeLABEL.setText("Default Theme"); //set the name of Theme to label
                            //set label icon
                            ImageIcon themeIcon1 = new ImageIcon(ImageIO.read(getClass().getResource("/Images/defaulttheme_16px.png")));
                            themeLABEL.setIcon(themeIcon1);
                            themeLABEL.updateUI();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                            theme1Toast.showToast(); //show toast
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
        menuTheme1.addActionListener(Theme1ActionListener);

        //----------------------------------------------------------//
        //theme2 action listener
        ActionListener Theme2ActionListener
                = (ActionEvent e) -> {
                    try {
                        if (themeLABEL.getText().equals("IntelliJ-white Theme")) {
                            popupSettings.setVisible(false);
                            theme2AToast.showToast();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                        } else {
                            try {
                                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                            } catch (UnsupportedLookAndFeelException ex) {
                                Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SwingUtilities.updateComponentTreeUI(this);
                            this.pack();
                            themeLABEL.setText("IntelliJ-white Theme"); //set the name of Theme to label
                            //set label icon
                            ImageIcon themeIcon2 = new ImageIcon(ImageIO.read(getClass().getResource("/Images/intellijtheme_16px.png")));
                            themeLABEL.setIcon(themeIcon2);
                            themeLABEL.updateUI();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                            theme2Toast.showToast(); //show toast
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
        menuTheme2.addActionListener(Theme2ActionListener);

        //----------------------------------------------------------//
        //theme3 action listener
        ActionListener Theme3ActionListener
                = (ActionEvent e) -> {
                    //
                    try {
                        if (themeLABEL.getText().equals("Windows Theme")) {
                            popupSettings.setVisible(false);
                            theme3AToast.showToast();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                        } else {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                            SwingUtilities.updateComponentTreeUI(this);
                            this.pack();
                            themeLABEL.setText("Windows Theme"); //set the name of Theme to label
                            //set label icon
                            ImageIcon themeIcon3 = new ImageIcon(ImageIO.read(getClass().getResource("/Images/windowstheme_16px.png")));
                            themeLABEL.setIcon(themeIcon3);
                            themeLABEL.updateUI();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                            theme3Toast.showToast(); //show toast
                        }

                    } catch (IOException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
        menuTheme3.addActionListener(Theme3ActionListener);

        //----------------------------------------------------------//
        //theme4 action listener
        ActionListener Theme4ActionListener
                = (ActionEvent e) -> {
                    //
                    try {
                        if (themeLABEL.getText().equals("Cross Theme")) {
                            popupSettings.setVisible(false);
                            theme4AToast.showToast();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                        } else {
                            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                            SwingUtilities.updateComponentTreeUI(this);
                            this.pack();
                            themeLABEL.setText("Cross Theme"); //set the name of Theme to label
                            //set label icon
                            ImageIcon themeIcon4 = new ImageIcon(ImageIO.read(getClass().getResource("/Images/closetheme_16px.png")));
                            themeLABEL.setIcon(themeIcon4);
                            themeLABEL.updateUI();
                            popupSettings.removeAll();
                            popupSettings.add(AboutLBL);
                            popupSettings.add(menuAboutApp);
                            popupSettings.add(menuAboutDev);
                            popupSettings.addSeparator();
                            popupSettings.add(ThemesLBL);
                            popupSettings.add(menuTheme1);
                            popupSettings.add(menuTheme2);
                            popupSettings.add(menuTheme3);
                            popupSettings.add(menuTheme4);
                            popupSettings.updateUI();
                            theme4Toast.showToast(); //show toast
                        }

                    } catch (IOException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
        menuTheme4.addActionListener(Theme4ActionListener);

        //----------------------------------------------------------//
        //set jmenus cursor
        menuAboutApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAboutDev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuTheme1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuTheme2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuTheme3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuTheme4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        //set popup background
        popupSettings.setBackground(new Color(249, 250, 253));

        //add popup on click
        popupSettings.removeAll();
        popupSettings.add(AboutLBL);
        popupSettings.add(menuAboutApp);
        popupSettings.add(menuAboutDev);
        popupSettings.addSeparator();
        popupSettings.add(ThemesLBL);
        popupSettings.add(menuTheme1);
        popupSettings.add(menuTheme2);
        popupSettings.add(menuTheme3);
        popupSettings.add(menuTheme4);
        popupSettings.updateUI();

        //show popup on click
        popupSettings.show(lblSettings, lblSettings.getWidth() - 220, lblSettings.getHeight() / 1);
    }//GEN-LAST:event_lblSettingsMousePressed

    private void lblSettingsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingsMouseReleased
    }//GEN-LAST:event_lblSettingsMouseReleased

    private void pnlTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleMouseClicked
        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuMinimizeTaskbar = new JMenuItem("Close Program Window");
        final JMenuItem menuClose = new JMenuItem("Full Exit " + mainAppNameFromDB);
        final JMenuItem menuRestart = new JMenuItem("Restart");
        final JMenuItem menuLogout = new JMenuItem("Logout");
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
                        MainNavigationHomePanel.this.setState(Frame.ICONIFIED);
                    };
            menuMinimize.addActionListener(minimizeActionListener);

            //------------------------------------------//
            //minimize taskbar
            ActionListener minimizeTaskbarActionListener
                    = (ActionEvent e) -> {
                        hideViaTray();
                    };
            menuMinimizeTaskbar.addActionListener(minimizeTaskbarActionListener);

            //------------------------------------------//
            //close taskbar
            ActionListener closeActionListener
                    = (ActionEvent e) -> {
                        forceclose();
                    };
            menuClose.addActionListener(closeActionListener);

            //------------------------------------------//
            //logout taskbar
            ActionListener logoutActionListener
                    = (ActionEvent e) -> {
                        forcelogout();
                    };
            menuLogout.addActionListener(logoutActionListener);

            //------------------------------------------//
            //restart taskbar
            ActionListener restartActionListener
                    = (ActionEvent e) -> {
                        try {
                            restartSystem();
                        } catch (IOException ex) {
                            Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    };
            menuRestart.addActionListener(restartActionListener);

            //----------------------------------------------------------//
            //set jmenus cursor
            menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuMinimizeTaskbar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            menuRestart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            //set popup background
            popupRightClick.setBackground(new Color(249, 250, 253));

            //add popup on click
            popupRightClick.removeAll();
            popupRightClick.add(menuRestore);
            popupRightClick.addSeparator();
            popupRightClick.add(ShortcutsLBL);
            popupRightClick.add(menuMinimize);
            popupRightClick.add(menuMinimizeTaskbar);
            popupRightClick.add(menuLogout);
            popupRightClick.add(menuRestart);
            popupRightClick.add(menuClose);
            popupRightClick.updateUI();

            //show popup on right click.
            //popupRightClick.show(pnlTitle, evt.getXOnScreen(), evt.getYOnScreen()/3);
            popupRightClick.show(pnlTitle, evt.getX(), evt.getY() / 3);
        }
    }//GEN-LAST:event_pnlTitleMouseClicked

    private void pnlTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_pnlTitleMousePressed

    private void pnlTitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_pnlTitleMouseDragged

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        MainNavigationHomePanel.this.setState(Frame.NORMAL);
        this.setEnabled(true);
    }//GEN-LAST:event_formWindowActivated

    private void logoutBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBTN1ActionPerformed

        ExitButtonAction_JDialog.dispose();

        forcelogout();
    }//GEN-LAST:event_logoutBTN1ActionPerformed

    private void fullExitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullExitBTNActionPerformed
        ExitButtonAction_JDialog.dispose();

        forceclose();
    }//GEN-LAST:event_fullExitBTNActionPerformed

    private void hideOnTaskbarBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideOnTaskbarBTNActionPerformed
        ExitButtonAction_JDialog.dispose();

        hideViaTray();
    }//GEN-LAST:event_hideOnTaskbarBTNActionPerformed

    private void exitBTNJDIalogExitMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBTNJDIalogExitMouseMoved

    }//GEN-LAST:event_exitBTNJDIalogExitMouseMoved

    private void exitBTNJDIalogExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBTNJDIalogExitMouseEntered
        setColorExitJDialog(exitBTNJDIalogExit);

        exitBTNJDIalogExit.getToolTipText();
    }//GEN-LAST:event_exitBTNJDIalogExitMouseEntered

    private void exitBTNJDIalogExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBTNJDIalogExitMouseExited
        resetColorExitJDialog(exitBTNJDIalogExit);
    }//GEN-LAST:event_exitBTNJDIalogExitMouseExited

    private void exitBTNJDIalogExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNJDIalogExitActionPerformed
        // Exit/Close Button code:
        new Thread(() -> {
            try {
                setColorExitJDialog(exitBTNJDIalogExit); //<-- set the color to red
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            resetColorExitJDialog(exitBTNJDIalogExit); //set the color to default
            ExitButtonAction_JDialog.dispose(); //exit the exitBTN
        }).start();
    }//GEN-LAST:event_exitBTNJDIalogExitActionPerformed

    private void pnlTopJDialog_ExitMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopJDialog_ExitMouseDragged
        int x1 = evt.getXOnScreen();
        int y1 = evt.getYOnScreen();
        ExitButtonAction_JDialog.setLocation(x1 - xx1, y1 - xy1);
    }//GEN-LAST:event_pnlTopJDialog_ExitMouseDragged

    private void pnlTopJDialog_ExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopJDialog_ExitMousePressed
        xx1 = evt.getX();
        xy1 = evt.getY();
    }//GEN-LAST:event_pnlTopJDialog_ExitMousePressed

    private void ExitButtonAction_JDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ExitButtonAction_JDialogWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_ExitButtonAction_JDialogWindowOpened

    private void lblCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCloseActionPerformed
        //set the Title of ExitButtonAction_JDialog
        lblTitle_JDialogEXIT.setText(mainAppNameFromDB + " | EXIT");
        ExitButtonAction_JDialog.setTitle(mainAppNameFromDB + " | EXIT");

        //set the Matte Border of ExitButtonAction_JDialog
        ExitButtonAction_JDialog.getRootPane().setBorder(new MatteBorder(0, 1, 1, 1, (new Color(0, 102, 204))));

        //set Visible the ExitButtonAction_JDialog
        final JDialog frame = ExitButtonAction_JDialog;
        frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setFocusable(false);
        frame.setModal(true);
        frame.setLocationRelativeTo(null); //center the dialog
        frame.setVisible(true);

        /*
        String[] options = new String[3];
        options[0] = "Full Exit " + ApplicationNameDB;
        options[1] = "Close Program Window";
        options[2] = "Cancel";
        int jop = JOptionPane.showOptionDialog(frame.getContentPane(), "<html><center>Please choose action:</center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);

        switch (jop) {
            case 0:
                //close ask question
                //closeButtonAskAction();
                int p = JOptionPane.showConfirmDialog(null, "<html><center>Are You Sure That Do You Want to Exit?<br><br>Note that your account is still online!,<br>Click the LOGOUT Button if you want to logout.</br></center></html>", "Exit", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    System.exit(0);
                } else {
                    lblClose.doClick();
                }
                break;

            case 1:
                //hide on taskbar
                hideViaTray();
                break;

            default:
                //code here
                break;

        }*/
    }//GEN-LAST:event_lblCloseActionPerformed

    // set Color to EXIT BUTTON on JDialog Exit
    void setColorExitJDialog(JButton btn1) {
        btn1.setBackground(new Color(255, 51, 51));
    }

    void resetColorExitJDialog(JButton btn1) {
        btn1.setBackground(new Color(0, 102, 204));
    }

    // set Color to TopButtons
    void setColor(JButton btn1) {
        btn1.setBackground(new Color(155, 182, 211));
    }

    void resetColor(JButton btn1) {
        btn1.setBackground(new Color(45, 118, 232));
    }

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
            System.err.println("Failed to initialize modern LaF" + ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainNavigationHomePanel().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(MainNavigationHomePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ExitButtonAction_JDialog;
    private javax.swing.JLabel companynameLBL;
    public javax.swing.JProgressBar consoleLoading;
    public javax.swing.JLabel consoleText;
    private javax.swing.JButton employeemanagerBTN;
    private javax.swing.JButton exitBTNJDIalogExit;
    public javax.swing.JButton fullExitBTN;
    public javax.swing.JButton hideOnTaskbarBTN;
    private javax.swing.JButton homeBTN;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton lblClose;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblSettings;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lblTitle_JDialogEXIT;
    private javax.swing.JLabel loggedInAs;
    private javax.swing.JButton logoutBTN;
    public javax.swing.JButton logoutBTN1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JLabel notifTime;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlDisplay;
    private javax.swing.JPanel pnlDown;
    private javax.swing.JPanel pnlParent;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlTopJDialog_Exit;
    private rojerusan.RSPanelImage rSPanelImage1;
    private javax.swing.JLabel themeLABEL;
    private javax.swing.JPanel topNavigationPane;
    private javax.swing.JLabel topappnameLBL;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JLabel userlvlTF;
    public javax.swing.JLabel useronlineTF;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables
}
