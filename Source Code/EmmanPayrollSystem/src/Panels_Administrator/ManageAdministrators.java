// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_Administrator;

import SystemDB.DBconnection;
import MainPackage.ToastManager;
import Panels_EmployeeManager.Emp;
import Panels_EmployeeManager.EmployeeDeductionGUI;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class ManageAdministrators extends javax.swing.JDialog {
    Connection conn;
    ResultSet rs=null;
    PreparedStatement pst=null;

    //for app name
    String mainAppNameFromDB; ///this string will get DATA from db
    
    //for company name
    String companyNameFromDB; ///this string will get DATA from db
    
    //Normal Popups Title
    String mainnameString; ///this string will get DATA from db
    
    //Error Popups Title
    String mainErrorString; ///this string will get DATA from db
    
    String noneCB;
    String userCB;
    String adminCB;
    
    //for TOAST
    final String restartstring = "System is Restarting...";
    final Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    final int widthvarToast = this.getSize().width;
    final int heightvatToast = this.getSize().height;
    final int xPosToast = (dimToast.width - widthvarToast) / 2;
    final int yPosToast = (dimToast.width - heightvatToast) / 2;
    final ToastManager restartToast = new ToastManager(restartstring, xPosToast, yPosToast);

    //for restart
    public static final String SUN_JAVA_COMMAND = "sun.java.command";
    Runnable runBeforeRestart;
    
    /**
     * Creates new form AddEmployeeGUI
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public ManageAdministrators() throws SQLException, IOException {
        initComponents();
        //connection to database
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, 
        size.height/2 - getHeight()/2);
        
        this.getRootPane().setBorder(new LineBorder(new Color(0,102,204)));
        this.setModal(true); //this.setAlwaysOnTop(true);
        txt_emp.setText(String.valueOf(Emp.empId));   
        
        GUINaming_DATA();
        fillCombo();
        
        updateBTN.setEnabled(false);
        deleteBTN.setEnabled(false);
        
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
                
                //company name
                companyNameFromDB = rsGNaming.getString("MainCompanyName");
                
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
    
    public void fillCombo() {
        divisionCB.removeAllItems();
        try {
            ResultSet rs1;
            try (Statement st1 = conn.createStatement()) {
                rs1 = st1.executeQuery("select * FROM GUINames");
                
                noneCB = rs1.getString("DefaultNone");
                divisionCB.addItem(noneCB);
                
                userCB =rs1.getString("DefaultUser");
                divisionCB.addItem(userCB);

                adminCB =rs1.getString("DefaultAdmin");
                divisionCB.addItem(adminCB);
                
                st1.close();
            }
            rs1.close();
            divisionCB.updateUI();
        } catch (SQLException e) {
        }
    }
    
    public void fillCombo1() {
        divisionCB.removeAllItems();
        try {
            ResultSet rs1;
            try (Statement st1 = conn.createStatement()) {
                rs1 = st1.executeQuery("select * FROM GUINames");
                
                userCB =rs1.getString("DefaultUser");
                divisionCB.addItem(userCB);

                adminCB =rs1.getString("DefaultAdmin");
                divisionCB.addItem(adminCB);
                
                st1.close();
            }
            rs1.close();
            divisionCB.updateUI();
        } catch (SQLException e) {
        }
    }
    
    public void auditUpdate() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','"+divisionCB.getSelectedItem().toString()+": "+idTF.getText()+"-"+fullnameTF.getText()+" is Updated by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void auditDelete() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','"+divisionCB.getSelectedItem().toString()+": "+idTF.getText()+"-"+fullnameTF.getText()+" is Deleted by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void clearall() {
        fillCombo();
        usernameTF.setText("");
        fullnameTF.setText("");
        divisionCB.setSelectedItem(noneCB);
        idTF.setText("");
        
        txt_search.setText("");
        txt_search.requestFocusInWindow();
                
        updateBTN.setEnabled(false);
        deleteBTN.setEnabled(false);
            
        divisionCB.setEnabled(false);
        fullnameTF.setEditable(false);
        usernameTF.setEditable(false);
    }
    
    //restart void
    public void restartSystem() throws IOException {
        getToolkit().beep();
        restartToast.showToast();
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
                // if it's a exe file
                cmd.append("-exe ").append(new File(mainCommand[0]).getPath());
            }
            if (mainCommand[0].endsWith(".exe")) {
                // if it's a exe file
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
            throw new IOException("Error while trying to restart the System.", e);
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
    public void deleteUserLevelFiles() {
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

    //restart the system and delete detector files.
    public void restartOnChange_ASK() {
        final JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frame.setModal(true);
        frame.pack();
        String[] options = new String[1];
        options[0] = "Okay, Restart the System Now";
        int jop = JOptionPane.showOptionDialog(frame.getContentPane(), "<html><center>You need to Restart the "+ mainAppNameFromDB +" <br>and Login Again to Take Effect Your Changes on the Database.</br></center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        switch (jop) {
            case 0:
                forceCloseDeleteDetectorFiles(); //<-- delete the userData detector file.
                deleteUserLevelFiles(); //<-- delete the userLevel detector file.
                deleteUsernameFiles(); //<-- delete the userName detector file.
                break;

            default:
                getToolkit().beep();
                restartOnChange_ASK(); //<-- open the void JDIALOG again, when the user click the X (EXIT) Button
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        pnlActions = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        pnlTitle = new javax.swing.JPanel();
        lblTitleIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewpanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fullnameTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        idTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        searchempBTN = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        divisionCB = new javax.swing.JComboBox<>();
        txt_emp = new javax.swing.JLabel();
        updateBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(249, 250, 253));
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
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

        lblClose.setBackground(new java.awt.Color(0, 102, 204));
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_multiply_18px_1.png"))); // NOI18N
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblCloseMouseMoved(evt);
            }
        });
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCloseMousePressed(evt);
            }
        });
        pnlActions.add(lblClose);

        pnlTop.add(pnlActions, java.awt.BorderLayout.LINE_END);

        pnlTitle.setBackground(new java.awt.Color(0, 102, 204));
        pnlTitle.setPreferredSize(new java.awt.Dimension(400, 28));
        pnlTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTitleMouseExited(evt);
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
        lblTitle.setText("PAYROLL");
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

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, -1));

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANAGE SYSTEM ADMINISTRATORS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(249, 250, 253));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Search Admins");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        fullnameTF.setEditable(false);
        fullnameTF.setBackground(new java.awt.Color(249, 250, 253));
        fullnameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullnameTFKeyPressed(evt);
            }
        });
        jPanel1.add(fullnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 160, -1));

        jLabel3.setText("Username");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 20));

        usernameTF.setEditable(false);
        usernameTF.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.add(usernameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 160, -1));

        jLabel2.setText("Fullname");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        jLabel4.setText("Id");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        idTF.setEditable(false);
        idTF.setBackground(new java.awt.Color(249, 250, 253));
        idTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idTFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idTFKeyTyped(evt);
            }
        });
        jPanel1.add(idTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 160, -1));

        jLabel5.setText("Division");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        searchempBTN.setBackground(new java.awt.Color(249, 250, 253));
        searchempBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_12px.png"))); // NOI18N
        searchempBTN.setToolTipText("Search");
        searchempBTN.setBorder(null);
        searchempBTN.setBorderPainted(false);
        searchempBTN.setContentAreaFilled(false);
        searchempBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchempBTN.setDefaultCapable(false);
        searchempBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchempBTNActionPerformed(evt);
            }
        });
        jPanel1.add(searchempBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 30, 25, 20));

        txt_search.setBackground(new java.awt.Color(249, 250, 253));
        txt_search.setOpaque(false);
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_searchKeyTyped(evt);
            }
        });
        jPanel1.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 160, -1));

        divisionCB.setBackground(new java.awt.Color(249, 250, 253));
        divisionCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        divisionCB.setEnabled(false);
        divisionCB.setLightWeightPopupEnabled(false);
        divisionCB.setNextFocusableComponent(fullnameTF);
        divisionCB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                divisionCBKeyPressed(evt);
            }
        });
        jPanel1.add(divisionCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 160, -1));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 280, 200));

        viewpanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        viewpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 62, 20));

        updateBTN.setBackground(new java.awt.Color(249, 250, 253));
        updateBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateBTN.setText("Update");
        updateBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBTN.setDefaultCapable(false);
        updateBTN.setEnabled(false);
        updateBTN.setFocusPainted(false);
        updateBTN.setOpaque(false);
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });
        viewpanel.add(updateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 80, -1));

        deleteBTN.setBackground(new java.awt.Color(249, 250, 253));
        deleteBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteBTN.setText("Delete");
        deleteBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBTN.setDefaultCapable(false);
        deleteBTN.setEnabled(false);
        deleteBTN.setFocusPainted(false);
        deleteBTN.setOpaque(false);
        deleteBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBTNActionPerformed(evt);
            }
        });
        viewpanel.add(deleteBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 80, -1));

        clearBTN.setBackground(new java.awt.Color(249, 250, 253));
        clearBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBTN.setText("Clear");
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.setDefaultCapable(false);
        clearBTN.setFocusPainted(false);
        clearBTN.setOpaque(false);
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        viewpanel.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 80, -1));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 470, 190));

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 240));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseMoved

    }//GEN-LAST:event_lblCloseMouseMoved

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered

    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited

    }//GEN-LAST:event_lblCloseMouseExited

    private void lblCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMousePressed
        this.dispose();
    }//GEN-LAST:event_lblCloseMousePressed

    private void pnlActionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlActionsMouseEntered

    }//GEN-LAST:event_pnlActionsMouseEntered

    private void pnlActionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlActionsMouseExited

    }//GEN-LAST:event_pnlActionsMouseExited

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

    private void pnlTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseDragged

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
    }//GEN-LAST:event_pnlTopMouseClicked

    private void pnlTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMousePressed

    }//GEN-LAST:event_pnlTopMousePressed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed

        int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete record?", mainnameString,JOptionPane.YES_NO_OPTION);
        if(p==0){
            String sql ="delete from Users where fullname=? ";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1, fullnameTF.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null,""+divisionCB.getSelectedItem().toString()+": "+fullnameTF.getText()+" Account is Deleted!", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);    
                clearall();
                auditDelete();
                
                new Thread(() -> {
                    try {
                        //start
                        restartOnChange_ASK(); //delete the detector files
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    
                    //close
                    try {
                        restartSystem(); //restart the system
                    } catch (IOException ex) {
                        Logger.getLogger(ManageAdministrators.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                
            } finally {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.out.println("ERR:" + e);
                }
            }
        }
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to update record?","Update Record",JOptionPane.YES_NO_OPTION);
        if(p==0){
            try{

                String value1 = idTF.getText();
                String value2 = usernameTF.getText();
                String value3 = fullnameTF.getText();
                String value4 = divisionCB.getSelectedItem().toString();

                String sql= "update Users set id='"+value1+"', username='"+value2+"', fullname='"+value3+"', division='"+value4+"' where id='"+value1+"' ";

                pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null,""+divisionCB.getSelectedItem().toString()+": "+fullnameTF.getText()+" Account is Updated!", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);    
                clearall();
                auditUpdate();
                
                new Thread(() -> {
                    try {
                        //start
                        restartOnChange_ASK(); //delete the detector files
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    
                    //close
                    try {
                        restartSystem(); //restart the system
                    } catch (IOException ex) {
                        Logger.getLogger(ManageAdministrators.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);

            } finally {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.out.println("ERR:" + e);
                }
            }
        }
    }//GEN-LAST:event_updateBTNActionPerformed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        clearall();
    }//GEN-LAST:event_clearBTNActionPerformed

    private void idTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idTFKeyTyped
        //numbers only.
        char c=evt.getKeyChar();
        //if (!(Character.isDigit(c1))) {
        if (!((c >= '0') && (c <='9') ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
            evt.consume();
        }
    }//GEN-LAST:event_idTFKeyTyped

    private void txt_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyTyped
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
        } else if(c==KeyEvent.VK_SPACE) {
            evt.consume();  
        }
    }//GEN-LAST:event_txt_searchKeyTyped

    private void searchempBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchempBTNActionPerformed
       
        try {
            if(txt_search.getText().isEmpty()) {
                fillCombo();
                txt_search.requestFocusInWindow();
                
                updateBTN.setEnabled(false);
                deleteBTN.setEnabled(false);
            
                divisionCB.setEnabled(false);
                fullnameTF.setEditable(false);
                usernameTF.setEditable(false);
            }
        
            String sql ="select * from Users where username=? ";

            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_search.getText());
            rs=pst.executeQuery();
        try {
            if (rs.next()) {
                
            fillCombo1();
                
            String add1 =rs.getString("id");
            idTF.setText(add1);

            String add2 =rs.getString("division");
            divisionCB.setSelectedItem(add2);

            String add3 =rs.getString("fullname");
            fullnameTF.setText(add3);

            String add4 =rs.getString("username");
            usernameTF.setText(add4);
            
            rs.close();
            pst.close();
            
            idTF.requestFocusInWindow();
            updateBTN.setEnabled(true);
            deleteBTN.setEnabled(true);
            
            divisionCB.setEnabled(true);
            fullnameTF.setEditable(true);
            usernameTF.setEditable(true);
            
            } else {
                
                JOptionPane.showMessageDialog(null,"Employee not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                clearall();
                
                fillCombo();
                txt_search.setText("");
                txt_search.requestFocusInWindow();
                
                updateBTN.setEnabled(false);
                deleteBTN.setEnabled(false);
            
                divisionCB.setEnabled(false);
                fullnameTF.setEditable(false);
                usernameTF.setEditable(false);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDeductionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDeductionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchempBTNActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchempBTN.doClick();
            
        } else if(txt_search.getText().isEmpty()|| evt.getKeyCode() == KeyEvent.VK_BACK_SPACE|| evt.getKeyCode() == KeyEvent.VK_DELETE) {
            clearall();
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void fullnameTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullnameTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            usernameTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_fullnameTFKeyPressed

    private void idTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            divisionCB.requestFocusInWindow();
            divisionCB.showPopup();
            
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            JOptionPane.showMessageDialog(null, "Press the Enter Key instead.", mainnameString, JOptionPane.INFORMATION_MESSAGE);
            idTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_idTFKeyPressed

    private void divisionCBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_divisionCBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            fullnameTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_divisionCBKeyPressed

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
            System.err.println( "Failed to initialize modern LaF" +ex );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ManageAdministrators().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(ManageAdministrators.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearBTN;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JComboBox<String> divisionCB;
    private javax.swing.JTextField fullnameTF;
    private javax.swing.JTextField idTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JButton searchempBTN;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JTextField txt_search;
    private javax.swing.JButton updateBTN;
    private javax.swing.JTextField usernameTF;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

}
