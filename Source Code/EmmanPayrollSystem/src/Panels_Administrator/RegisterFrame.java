// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_Administrator;

import SystemDB.DBconnection;
import Panels_EmployeeManager.Emp;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class RegisterFrame extends javax.swing.JDialog {
    Connection conn;
    static int clicked = 0;

    //default none
    String noneDB; ///this string will get DATA from db
    
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
    
    //main application name
    String GUITopNameDB; ///this string will get DATA from db
    
    //for restart
    public static final String SUN_JAVA_COMMAND = "sun.java.command";
    Runnable runBeforeRestart;
    
    /**
     * Creates new form LoginFrame
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public RegisterFrame() throws SQLException, IOException {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        this.setModal(true);
        RegisterFrame.this.getRootPane().setBorder(new MatteBorder(0, 1, 1, 1, (new Color(0,102,204))));
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        JTextFieldLimit();
        clearText();
        noPasteCutPassword();
        GUINaming_DATA();
        fillCombo();
        txt_emp.setText(String.valueOf(Emp.empId));
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
        exitButton.setBackground(new Color(0,102,204));
        
        exitButton.setSelected(false);
        
        exitButton.setFocusPainted(false);
        
        exitButton.setDefaultCapable(false);
    }

  
    //-------------------- START VOID CODES HERE --------------------//
    
    //GUINaming from Database
    public void GUINaming_DATA() throws SQLException {
        try {
            ResultSet rs;
            try (Statement st = conn.createStatement()) {
                rs = st.executeQuery("select * FROM GUINames");
                
                //set the GUI Title
                GUITopNameDB = rs.getString("MainAppName");
                guiTitle.setText(GUITopNameDB);
                this.setTitle(GUITopNameDB);
                
                //set the Default User
                userDB = rs.getString("DefaultUser");
                userDB_CAPS = rs.getString("DefaultUserCAPS");
                
                //set the Default Admin
                adminDB = rs.getString("DefaultAdmin");
                adminDB_CAPS = rs.getString("DefaultAdminCAPS");

                //set the Default None
                noneDB = rs.getString("DefaultNone");
                
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
    
    public void auditRegistered() {
        Date currentDate = GregorianCalendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance();
        String dateString = df.format(currentDate);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        String timeString = sdf.format(d);

        String value0 = timeString;
        String value1 = dateString;
        String val = txt_emp.getText();
        try {
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','"+fullnameTF.getText()+" ("+usernameTF.getText()+") is Registered by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //add item to ComboBox from Database Positions
    public void fillCombo() {
        
        userlevelCB.removeAllItems();
        try {
            ResultSet rs1;
            try (Statement st1 = conn.createStatement()) {
                rs1 = st1.executeQuery("select * FROM GUINames");
                
                String noneCB = rs1.getString("DefaultNone");
                userlevelCB.addItem(noneCB);
                
                String userCB =rs1.getString("DefaultUser");
                userlevelCB.addItem(userCB);

                String adminCB =rs1.getString("DefaultAdmin");
                userlevelCB.addItem(adminCB);
                
                st1.close();
            }
            rs1.close();
            userlevelCB.updateUI();
        } catch (SQLException e) {
        }
    }
    
    public void showpasswordchar1() {
        passwordTF.setEchoChar('\u25cf');
    }
    
    public void showpasswordchar2() {
        confirmpasswordTF.setEchoChar('\u25cf');
    }
    
    public void showpinchar() {
        pincodeTF.setEchoChar('\u25cf');
    }
    
    public void showpasswordchar_ClickLogin_1() {
        //currently showing password
        if(showpasswordCB1.isSelected()) {
            showpasswordCB1.setSelected(false);
            passwordTF.setEchoChar('\u25cf');
        
        //currently not showing password
        } else if(!showpasswordCB1.isSelected()) {
            showpasswordCB1.setSelected(false);
            passwordTF.setEchoChar('\u25cf');
        }
    }
    
    public void showpasswordchar_ClickLogin_2() {
        //currently showing password
        if(showpasswordCB2.isSelected()) {
            showpasswordCB2.setSelected(false);
            confirmpasswordTF.setEchoChar('\u25cf');
        
        //currently not showing password
        } else if(!showpasswordCB2.isSelected()) {
            showpasswordCB2.setSelected(false);
            confirmpasswordTF.setEchoChar('\u25cf');
        }
    }

    public void showpinchar_ClickLogin() {
        //currently showing pin
        if(showpincodeCB.isSelected()) {
            showpincodeCB.setSelected(false);
            pincodeTF.setEchoChar('\u25cf');
        
        //currently not showing pin
        } else if(!showpincodeCB.isSelected()) {
            showpincodeCB.setSelected(false);
            pincodeTF.setEchoChar('\u25cf');
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
                    for (int loading = 0; loading <=30; loading++) {
                        Thread.sleep(90);
                        progressBar.setValue(loading);
                    }
                    dialog.dispose();
                }   catch (InterruptedException ex) {
                    Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            dialog.setVisible(true);

            // Set a timer
            new Thread(() -> {
                try {
                    for (int loading = 31; loading <=70; loading++) {
                        Thread.sleep(55);
                        progressBar.setValue(loading);
                    }
                    dialog.dispose();
                }   catch (InterruptedException ex) {
                    Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            dialog.setVisible(true);
            
            // Set a timer
            new Thread(() -> {
                try {
                    for (int loading = 71; loading <=100; loading++) {
                        Thread.sleep(30);
                        progressBar.setValue(loading);
                    }
                    dialog.dispose();
                }   catch (InterruptedException ex) {
                    Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void registerpanelloading() throws IOException {
            //JOptionPane.showOptionDialog(null, "Hello","Empty?", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            //ImageIcon iconofJOP = new ImageIcon("");
            final JOptionPane optionPane = new JOptionPane("<html><center>Creating your account...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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

    public void registerpanelloadingcheck() throws IOException {
            //JOptionPane.showOptionDialog(null, "Hello","Empty?", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            //ImageIcon iconofJOP = new ImageIcon("");
            final JOptionPane optionPane = new JOptionPane("<html><center>Creating your account...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
            final JOptionPane optionPane1 = new JOptionPane("<html><center>Sorry, You have reached maximum allowed attempts (3/3). <br>The system will exit automatically</center></html>", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
    
    public void acccreatedsuccess() throws SQLException, IOException {
        String usernameString = usernameTF.getText();
        String fullnameString = fullnameTF.getText();
        //ImageIcon printicon = new ImageIcon(getClass().getResource("/Images/excel_64px.png"));
        final JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setModal(true);
        String[] options = new String[3];
        options[0] = "Yes, LOG IN NOW";
        options[1] = "EXIT SYSTEM";
        options[2] = "CANCEL";
        int jop = JOptionPane.showOptionDialog(frame.getContentPane(), "<html><center>"+fullnameString+" Account is Successfully Created!</center><br>YOUR ACCCOUNT LOGIN INFORMATION:<br>User-Level: "+userString+"<br>Account Username: "+usernameString+"<br><br><center>DO YOU WANT TO LOG IN THE "+usernameString+" ACCOUNT NOW?</center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (jop) {
            case 0:
                //yes
                getToolkit().beep();                
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
                if (runBeforeRestart!= null) {
                    runBeforeRestart.run();
                }
        
                // exit
                System.exit(0);
                } catch (Exception e) {
                    // something went wrong
                    throw new IOException("Error while trying to restart the "+GUITopNameDB+"", e);
                }
                break;
            case 1:
                // exit
                clearText();
                exitButton.doClick();
                this.dispose();
                break;
            case 2:
                // cancel
                frame.dispose();
                clearText();
                break;
            default:
                break;
        }
    }
    
    //letter / number limit in a jtextfield
    public void JTextFieldLimit() {
        fullnameTF.setDocument(new JTextFieldLimitAPI(80));
        usernameTF.setDocument(new JTextFieldLimitAPI(20));
        passwordTF.setDocument(new JTextFieldLimitAPI(8));
        confirmpasswordTF.setDocument(new JTextFieldLimitAPI(8));
        pincodeTF.setDocument(new JTextFieldLimitAPI(4));
    }
    
    public void clearText() {
        fullnameTF.setText("");
        usernameTF.setText("");
        pincodeTF.setText("");
        userlevelCB.setSelectedItem("None");
        passwordTF.setText("");
        confirmpasswordTF.setText("");
        fullnameTF.requestFocusInWindow();
        showpasswordCB1.setSelected(false);
        showpasswordCB2.setSelected(false);
        showpincodeCB.setSelected(false);
    }
    
    public void noPasteCutPassword() {
        passwordTF.setTransferHandler(null);
        confirmpasswordTF.setTransferHandler(null);
        usernameTF.setTransferHandler(null);
        pincodeTF.setTransferHandler(null);
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
        userlevelLABEL = new javax.swing.JLabel();
        showpasswordCB1 = new javax.swing.JCheckBox();
        passwordLABEL = new javax.swing.JLabel();
        passwordTF = new javax.swing.JPasswordField();
        pnlTop = new javax.swing.JPanel();
        guiTitle = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        registerBTN = new rojerusan.RSMaterialButtonRectangle();
        clearBTN = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pincodeLABEL = new javax.swing.JLabel();
        confirmpasswordLABEL = new javax.swing.JLabel();
        showpasswordCB2 = new javax.swing.JCheckBox();
        confirmpasswordTF = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        fullnameTF = new javax.swing.JTextField();
        fullnameLABEL = new javax.swing.JLabel();
        lrnLABEL = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        userlevelCB = new javax.swing.JComboBox<>();
        showpincodeCB = new javax.swing.JCheckBox();
        pincodeTF = new javax.swing.JPasswordField();
        txt_emp = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("LOG IN AS ADMIN");
        jMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("AUTHENTICATION SYSTEM");
        setUndecorated(true);
        setResizable(false);

        pnlMAIN.setBackground(new java.awt.Color(253, 253, 253));
        pnlMAIN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userlevelLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        userlevelLABEL.setForeground(new java.awt.Color(102, 102, 102));
        userlevelLABEL.setText("user level");
        pnlMAIN.add(userlevelLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        showpasswordCB1.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCB1.setToolTipText("Show/Hide Password");
        showpasswordCB1.setBorderPaintedFlat(true);
        showpasswordCB1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCB1.setDoubleBuffered(true);
        showpasswordCB1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCB1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCB1ActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 358, -1, -1));

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTopMousePressed(evt);
            }
        });
        pnlTop.setLayout(null);

        guiTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        guiTitle.setForeground(new java.awt.Color(255, 255, 255));
        guiTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TOP_ICON_18px.png"))); // NOI18N
        guiTitle.setText("Payroll System");
        pnlTop.add(guiTitle);
        guiTitle.setBounds(5, 0, 230, 30);

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
        exitButton.setOpaque(false);
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
        exitButton.setBounds(705, 0, 18, 30);

        pnlMAIN.add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, -1));

        registerBTN.setBackground(new java.awt.Color(51, 204, 0));
        registerBTN.setText("REGISTER");
        registerBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        registerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(registerBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, -1, 39));

        clearBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBTN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clearBTN.setText("CLEAR");
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.setPreferredSize(new java.awt.Dimension(118, 10));
        clearBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                clearBTNMousePressed(evt);
            }
        });
        pnlMAIN.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 447, 280, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainIcon2_128px.png"))); // NOI18N
        pnlMAIN.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        pincodeLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        pincodeLABEL.setForeground(new java.awt.Color(102, 102, 102));
        pincodeLABEL.setText("pin code");
        pnlMAIN.add(pincodeLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, -1, -1));

        confirmpasswordLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        confirmpasswordLABEL.setForeground(new java.awt.Color(102, 102, 102));
        confirmpasswordLABEL.setText("confirm password");
        pnlMAIN.add(confirmpasswordLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, -1, -1));

        showpasswordCB2.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCB2.setToolTipText("Show/Hide Password");
        showpasswordCB2.setBorderPaintedFlat(true);
        showpasswordCB2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCB2.setDoubleBuffered(true);
        showpasswordCB2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCB2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCB2ActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 358, -1, -1));

        confirmpasswordTF.setBackground(new java.awt.Color(253, 253, 253));
        confirmpasswordTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmpasswordTF.setForeground(new java.awt.Color(102, 102, 102));
        confirmpasswordTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        confirmpasswordTF.setDoubleBuffered(true);
        confirmpasswordTF.setEchoChar('\u25cf');
        confirmpasswordTF.setOpaque(false);
        confirmpasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmpasswordTFActionPerformed(evt);
            }
        });
        confirmpasswordTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmpasswordTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confirmpasswordTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                confirmpasswordTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(confirmpasswordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, 278, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setText("ADMIN REGISTRATION");
        pnlMAIN.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        fullnameTF.setBackground(new java.awt.Color(253, 253, 253));
        fullnameTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fullnameTF.setForeground(new java.awt.Color(102, 102, 102));
        fullnameTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        fullnameTF.setNextFocusableComponent(null);
        fullnameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnameTFActionPerformed(evt);
            }
        });
        fullnameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullnameTFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fullnameTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(fullnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 278, -1));

        fullnameLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        fullnameLABEL.setForeground(new java.awt.Color(102, 102, 102));
        fullnameLABEL.setText("fullname");
        pnlMAIN.add(fullnameLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        lrnLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lrnLABEL.setForeground(new java.awt.Color(102, 102, 102));
        lrnLABEL.setText("username");
        pnlMAIN.add(lrnLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, -1, -1));

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(usernameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 278, -1));

        userlevelCB.setBackground(new java.awt.Color(253, 253, 253));
        userlevelCB.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        userlevelCB.setForeground(new java.awt.Color(102, 102, 102));
        userlevelCB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        userlevelCB.setLightWeightPopupEnabled(false);
        userlevelCB.setOpaque(false);
        userlevelCB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userlevelCBKeyPressed(evt);
            }
        });
        pnlMAIN.add(userlevelCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 280, -1));

        showpincodeCB.setBackground(new java.awt.Color(253, 253, 253));
        showpincodeCB.setToolTipText("Show/Hide Password");
        showpincodeCB.setBorderPaintedFlat(true);
        showpincodeCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpincodeCB.setDoubleBuffered(true);
        showpincodeCB.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpincodeCB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpincodeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpincodeCBActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpincodeCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 288, -1, -1));

        pincodeTF.setBackground(new java.awt.Color(253, 253, 253));
        pincodeTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pincodeTF.setForeground(new java.awt.Color(102, 102, 102));
        pincodeTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        pincodeTF.setDoubleBuffered(true);
        pincodeTF.setEchoChar('\u25cf');
        pincodeTF.setOpaque(false);
        pincodeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pincodeTFActionPerformed(evt);
            }
        });
        pincodeTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pincodeTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pincodeTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pincodeTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(pincodeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 278, -1));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        pnlMAIN.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 90, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(730, 501));
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
        
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        setColor1(exitButton);
        
        exitButton.getToolTipText();
    }//GEN-LAST:event_exitButtonMouseEntered

    private void exitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseExited
        resetColor1(exitButton);
    }//GEN-LAST:event_exitButtonMouseExited

    private void exitButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseMoved
    }//GEN-LAST:event_exitButtonMouseMoved

    private void passwordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            confirmpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_passwordTFKeyPressed

    private void passwordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyReleased

    }//GEN-LAST:event_passwordTFKeyReleased

    private void registerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBTNActionPerformed
        String fullnameString = fullnameTF.getText();
        String usernameString = usernameTF.getText();
        showpasswordchar_ClickLogin_1();
        showpasswordchar_ClickLogin_2();
        showpinchar_ClickLogin();
        try {
            //REGISTER BUTTON CODE            
            Statement stUSER = conn.createStatement();
            ResultSet rsUSER=stUSER.executeQuery("select username from Users where username like '"+'%'+usernameString+'%'+"'");

            Statement stFULLNAME = conn.createStatement();
            ResultSet rsFULLNAME=stFULLNAME.executeQuery("select fullname from Users where fullname like '"+'%'+fullnameString+'%'+"'");
            
            if (fullnameTF.getText().isEmpty()|usernameTF.getText().isEmpty()|pincodeTF.getText().isEmpty()|userlevelCB.getSelectedItem().equals("None") | passwordTF.getText().isEmpty() | confirmpasswordTF.getText().isEmpty()) {
                getToolkit().beep();
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "One of the required field is empty", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                fullnameTF.requestFocusInWindow();
                
            } else if(!(passwordTF.getText().length()>=8)) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "Invalid, Password must have 8 characters exactly!", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                passwordTF.requestFocusInWindow();
                
            } else if(!(pincodeTF.getText().length()>=4)) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "Invalid, Pincode must have 4 characters exactly!", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                pincodeTF.requestFocusInWindow();
                
            } else if(rsUSER.next()){
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "User "+usernameString+" is already registered!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                usernameTF.requestFocusInWindow();
                rsUSER.close();
                stUSER.close();
                
            } else if(rsFULLNAME.next()) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "Fullname "+usernameString+" is already registered!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                fullnameTF.requestFocusInWindow();
                rsFULLNAME.close();
                stFULLNAME.close();
                
            } else {
                try {
                    if(passwordTF.getText().equals(confirmpasswordTF.getText())){
                        String query1 = "INSERT INTO Users (division,fullname,password,username,pincode) values(?,?,?,?,?)";
                        try (PreparedStatement pst1 = conn.prepareStatement(query1)) {
                            pst1.setString(1, userlevelCB.getSelectedItem().toString());
                            pst1.setString(2, fullnameTF.getText());
                            pst1.setString(3, passwordTF.getText());
                            pst1.setString(4, usernameTF.getText());
                            pst1.setString(5, pincodeTF.getText());
                            pst1.executeUpdate();
                            pst1.close();
                        }
                        registerpanelloading();
                        auditRegistered();
                        acccreatedsuccess();
                    } else {
                        registerpanelloadingcheck();
                        JOptionPane.showMessageDialog(null,"<html><center>Password not match! <br>Please re-check your password.</center></html>", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                        passwordTF.requestFocusInWindow();
                    }
                    
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                    
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_registerBTNActionPerformed

    private void showpasswordCB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCB1ActionPerformed
        if(showpasswordCB1.isSelected()) {
            passwordTF.setEchoChar((char)0);
            passwordTF.requestFocusInWindow();
        } else {
            showpasswordchar1();
            passwordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCB1ActionPerformed

    private void passwordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTFActionPerformed
    }//GEN-LAST:event_passwordTFActionPerformed

    private void confirmpasswordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmpasswordTFActionPerformed
    }//GEN-LAST:event_confirmpasswordTFActionPerformed

    private void confirmpasswordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmpasswordTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            showpasswordchar_ClickLogin_1();
            showpasswordchar_ClickLogin_2();
            showpinchar_ClickLogin();
            registerBTN.requestFocusInWindow();
            registerBTN.doClick();
        }
    }//GEN-LAST:event_confirmpasswordTFKeyPressed

    private void confirmpasswordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmpasswordTFKeyReleased
    }//GEN-LAST:event_confirmpasswordTFKeyReleased

    private void showpasswordCB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCB2ActionPerformed
        if(showpasswordCB2.isSelected()) {
            confirmpasswordTF.setEchoChar((char)0);
            confirmpasswordTF.requestFocusInWindow();
        } else {
            showpasswordchar2();
            confirmpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCB2ActionPerformed

    private void fullnameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameTFActionPerformed

    private void fullnameTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullnameTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            usernameTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_fullnameTFKeyPressed

    private void usernameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTFActionPerformed

    private void usernameTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            userlevelCB.requestFocusInWindow();
            userlevelCB.showPopup();
            
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            JOptionPane.showMessageDialog(null, "Press the Enter Key instead.", mainnameString, JOptionPane.INFORMATION_MESSAGE);
            usernameTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_usernameTFKeyPressed

    private void userlevelCBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userlevelCBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            pincodeTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_userlevelCBKeyPressed

    private void fullnameTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullnameTFKeyTyped
        //letters only.
        char c=evt.getKeyChar();
        if (!(Character.isAlphabetic(c) ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_COMMA) ||
                (c==KeyEvent.VK_UNDERSCORE) ||
                (c==KeyEvent.VK_MINUS) ||
                (c==KeyEvent.VK_PERIOD) ||
                (c==KeyEvent.VK_SPACE) ||
                (c==KeyEvent.VK_SLASH) ||
                (c==KeyEvent.VK_LEFT_PARENTHESIS) ||
                (c==KeyEvent.VK_RIGHT_PARENTHESIS) ||
                (c==KeyEvent.VK_ALT) ||
                (c==KeyEvent.VK_UNDEFINED) ||
                (c==KeyEvent.VK_COPY) ||
                (c==KeyEvent.VK_CONTROL) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
                evt.consume();        
        }
        
        /*
        //limit the string length
        String l = fullnameTF.getText();
            if (!(l.length()<60))
            {
            evt.consume();
        }*/
    }//GEN-LAST:event_fullnameTFKeyTyped

    private void usernameTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTFKeyReleased
    }//GEN-LAST:event_usernameTFKeyReleased

    private void usernameTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTFKeyTyped
        //letters and some numbers only
        char c=evt.getKeyChar();
        if (!(Character.isAlphabetic(c) ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_COMMA) ||
                (c==KeyEvent.VK_SHIFT) ||
                (c==KeyEvent.VK_MINUS) ||
                (c==KeyEvent.VK_PLUS) ||
                (c==KeyEvent.VK_UNDERSCORE) ||
                //(c==KeyEvent.VK_SPACE) ||
                (c==KeyEvent.VK_SLASH) ||
                (c==KeyEvent.VK_ALT) ||
                (c==KeyEvent.VK_UNDEFINED) ||
                (c==KeyEvent.VK_COPY) ||
                (c==KeyEvent.VK_CONTROL) ||
                (c==KeyEvent.VK_1) || (c==KeyEvent.VK_2) || (c==KeyEvent.VK_3) || (c==KeyEvent.VK_4) || (c==KeyEvent.VK_5) ||
                (c==KeyEvent.VK_6) || (c==KeyEvent.VK_7) || (c==KeyEvent.VK_8) || (c==KeyEvent.VK_9) || (c==KeyEvent.VK_0) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
                evt.consume();    
        }
    }//GEN-LAST:event_usernameTFKeyTyped

    private void clearBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBTNMousePressed
        clearText();
    }//GEN-LAST:event_clearBTNMousePressed

    private void passwordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTFKeyTyped

    }//GEN-LAST:event_passwordTFKeyTyped

    private void confirmpasswordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmpasswordTFKeyTyped

    }//GEN-LAST:event_confirmpasswordTFKeyTyped

    private void pincodeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pincodeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pincodeTFActionPerformed

    private void pincodeTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pincodeTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            passwordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_pincodeTFKeyPressed

    private void pincodeTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pincodeTFKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_pincodeTFKeyReleased

    private void pincodeTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pincodeTFKeyTyped
        //numbers only
            char c=evt.getKeyChar();
            if (!((c >= '0') && (c <='9') ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_CONTROL) ||
                (c==KeyEvent.VK_CAPS_LOCK) ||
                (c==KeyEvent.VK_INSERT) ||
                (c==KeyEvent.VK_UNDO) ||
                (c==KeyEvent.VK_TAB))) {
                evt.consume();     
        }
    }//GEN-LAST:event_pincodeTFKeyTyped

    private void showpincodeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpincodeCBActionPerformed
        if(showpincodeCB.isSelected()) {
            pincodeTF.setEchoChar((char)0);
            pincodeTF.requestFocusInWindow();
        } else {
            showpinchar();
            pincodeTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpincodeCBActionPerformed

    // set Color to pnlTop (EXIT)
    void setColor1(JButton btn1) {
        btn1.setBackground(new Color(255,51,51)); 
    }

    void resetColor1(JButton btn1) {
        btn1.setBackground(new Color(0,102,204));
    }
    
    // set Color to pnlTop (MINIMIZE, SETTINGS)
    void setColor2(JButton btn2) {
        btn2.setBackground(new Color(155,182,211));
    }

    void resetColor2(JButton btn2) {
        btn2.setBackground(new Color(0,102,204));
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
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize Modern LaF" +ex );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new RegisterFrame().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clearBTN;
    private javax.swing.JLabel confirmpasswordLABEL;
    private javax.swing.JPasswordField confirmpasswordTF;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel fullnameLABEL;
    public javax.swing.JTextField fullnameTF;
    private javax.swing.JLabel guiTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lrnLABEL;
    private javax.swing.JLabel passwordLABEL;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JLabel pincodeLABEL;
    private javax.swing.JPasswordField pincodeTF;
    private javax.swing.JPanel pnlMAIN;
    private javax.swing.JPanel pnlTop;
    private rojerusan.RSMaterialButtonRectangle registerBTN;
    private javax.swing.JCheckBox showpasswordCB1;
    private javax.swing.JCheckBox showpasswordCB2;
    private javax.swing.JCheckBox showpincodeCB;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JComboBox<String> userlevelCB;
    private javax.swing.JLabel userlevelLABEL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //