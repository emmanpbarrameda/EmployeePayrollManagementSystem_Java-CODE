// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //
package Panels_Administrator;

import Panels_EmployeeManager.*;
import SystemDB.DBconnection;
import MainPackage.ToastManager;
import UppercaseTypeFilterPackage.UppercaseALLFilter_API;
import UppercaseTypeFilterPackage.UppercaseDocumentFilter_API;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class EditGUIOptions extends javax.swing.JDialog {

    Connection conn;
    ResultSet rsORIG = null;
    PreparedStatement pstORIG = null;

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

    //for main depending
    String maindependingFromDB; ///this string will get DATA from db

    //for TOAST
    final String restartwait = "System is Restarting...";
    final Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    final int widthvarToast = this.getSize().width;
    final int heightvatToast = this.getSize().height;
    final int xPosToast = (dimToast.width - widthvarToast) / 2;
    final int yPosToast = (dimToast.width - heightvatToast) / 2;
    final ToastManager restartToast = new ToastManager(restartwait, xPosToast, yPosToast);

    //for restart
    public static final String SUN_JAVA_COMMAND = "sun.java.command";
    Runnable runBeforeRestart;

    DocumentFilter firstletterCaps = new UppercaseDocumentFilter_API();

    DocumentFilter ALLCAPS = new UppercaseALLFilter_API();

    /**
     * Creates new form AddEmployeeGUI
     *
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public EditGUIOptions() throws SQLException, IOException {
        initComponents();
        //connection to database
        DBconnection c = new DBconnection();
        conn = c.getconnection();

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);

        this.getRootPane().setBorder(new LineBorder(new Color(0, 102, 204)));
        this.setModal(true); //this.setAlwaysOnTop(true);
        txt_emp.setText(String.valueOf(Emp.empId));
        GUINaming_DATA();
        insertGUINaming();
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

        //CAPS on first Letter
        ((AbstractDocument) tf1.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf2.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf3.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf4.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf5.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf6.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf10.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf11.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf12.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf14.getDocument()).setDocumentFilter(firstletterCaps);
        ((AbstractDocument) tf16.getDocument()).setDocumentFilter(firstletterCaps);

        //all caps
        ((AbstractDocument) tf7.getDocument()).setDocumentFilter(ALLCAPS);
        ((AbstractDocument) tf13.getDocument()).setDocumentFilter(ALLCAPS);
        ((AbstractDocument) tf15.getDocument()).setDocumentFilter(ALLCAPS);

        //no paste
        tf8.setTransferHandler(null);
        tf9.setTransferHandler(null);
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

                //currency symbol
                pesoSignString = rsGNaming.getString("CurrencySign");

                //set the Default Normal Popups Title Message
                mainnameString = rsGNaming.getString("PopupNormal");

                //set the Default Error Popups Title Message
                mainErrorString = rsGNaming.getString("PopupError");

                //set the main depending
                maindependingFromDB = rsGNaming.getString("Depending");
                lblmain.setText(maindependingFromDB);

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

    public void auditEdit() {
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
            String reg = "insert into Audit (emp_id, date, status) values ('" + val + "','" + value0 + " / " + value1 + "','Name is Edited by " + txt_emp.getText() + "')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void clear() throws SQLException {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
        tf6.setText("");
        tf7.setText("");
        tf8.setText("");
        tf9.setText("");
        tf10.setText("");
        tf11.setText("");
        tf12.setText("");
        tf13.setText("");
        tf14.setText("");
        tf15.setText("");
        tf16.setText("");

        cb1.setSelected(false);
        cb2.setSelected(false);
        cb3.setSelected(false);
        cb4.setSelected(false);
        cb5.setSelected(false);
        cb6.setSelected(false);
        cb7.setSelected(false);
        cb8.setSelected(false);
        cb9.setSelected(false);
        cb10.setSelected(false);
        cb11.setSelected(false);
        cb12.setSelected(false);
        cb13.setSelected(false);
        cb14.setSelected(false);
        cb15.setSelected(false);
        cb16.setSelected(false);

        cb1.setEnabled(true);
        cb2.setEnabled(true);
        cb3.setEnabled(true);
        cb4.setEnabled(true);
        cb5.setEnabled(true);
        cb6.setEnabled(true);
        cb7.setEnabled(true);
        cb8.setEnabled(true);
        cb9.setEnabled(true);
        cb10.setEnabled(true);
        cb11.setEnabled(true);
        cb12.setEnabled(true);
        cb13.setEnabled(true);
        cb14.setEnabled(true);
        cb15.setEnabled(true);
        cb16.setEnabled(true);

        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);
        tf4.setEditable(false);
        tf5.setEditable(false);
        tf6.setEditable(false);
        tf7.setEditable(false);
        tf8.setEditable(false);
        tf9.setEditable(false);
        tf10.setEditable(false);
        tf11.setEditable(false);
        tf12.setEditable(false);
        tf13.setEditable(false);
        tf14.setEditable(false);
        tf15.setEditable(false);
        tf16.setEditable(false);

        tf1.requestFocus(false);
        tf2.requestFocus(false);
        tf3.requestFocus(false);
        tf4.requestFocus(false);
        tf5.requestFocus(false);
        tf6.requestFocus(false);
        tf7.requestFocus(false);
        tf8.requestFocus(false);
        tf9.requestFocus(false);
        tf10.requestFocus(false);
        tf11.requestFocus(false);
        tf12.requestFocus(false);
        tf13.requestFocus(false);
        tf14.requestFocus(false);
        tf15.requestFocus(false);
        tf16.requestFocus(false);

        savechangesBTN.setEnabled(true);
        checkAllBTN.setEnabled(true);
        uncheckAllBTN.setEnabled(true);
        reset2defaultBTN.setEnabled(true);
        applyBTN.setEnabled(false);
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

    //restart automatically
    public void restartEditGUI() throws IOException, SQLException {
        final JDialog frameEditGUI = new JDialog();
        frameEditGUI.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frameEditGUI.pack();
        frameEditGUI.setModal(true);
        String[] options = new String[1];
        options[0] = "Restart the " + mainAppNameFromDB + " Now";
        int jop = JOptionPane.showOptionDialog(frameEditGUI.getContentPane(), "<html><center>Updated Successfully! <br>You need to Restart the " + mainAppNameFromDB + " to save your Changes on the Database.</center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (jop) {
            case 0:
                //yes now
                getToolkit().beep();

                forceCloseDeleteDetectorFiles(); //<-- delete the userData detector file.
                deleteUserLevelFiles(); //<-- delete the userLevel detector file.
                deleteUsernameFiles(); //<-- delete the userName detector file.

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
                break;

            /*
            case 1:
                // restart later
                frameEditGUI.dispose();
                frameEditGUI.setVisible(false);
                clear();
                insertGUINaming();
                GUINaming_DATA();
                break;
                
            default:
                frameEditGUI.dispose();
                frameEditGUI.setVisible(false);
                clear();
                insertGUINaming();
                GUINaming_DATA();
                break;*/
        }
    }

    private void insertGUINaming() throws SQLException {
        try {
            String query = "SELECT * FROM GUINames WHERE Depending=?";
            ResultSet rsGUINaming;
            try (PreparedStatement pstGUINaming = conn.prepareStatement(query)) {
                pstGUINaming.setString(1, lblmain.getText());
                rsGUINaming = pstGUINaming.executeQuery();
                if (lblmain.getText().isEmpty()) {
                    //getToolkit().beep();
                    clear();

                } else if (rsGUINaming.next()) {

                    String s1 = rsGUINaming.getString("MainAppName");
                    tf1.setText(s1);

                    String s2 = rsGUINaming.getString("MainCompanyName");
                    tf2.setText(s2);

                    String s3 = rsGUINaming.getString("MainTopAppName");
                    tf3.setText(s3);

                    String s4 = rsGUINaming.getString("PopupNormal");
                    tf4.setText(s4);

                    String s5 = rsGUINaming.getString("PopupError");
                    tf5.setText(s5);

                    String s6 = rsGUINaming.getString("CurrencySign");
                    tf6.setText(s6);

                    String s7 = rsGUINaming.getString("CurrencyCode");
                    tf7.setText(s7);

                    String s8 = rsGUINaming.getString("OvertimeRate");
                    tf8.setText(s8);

                    String s9 = rsGUINaming.getString("RPHRate");
                    tf9.setText(s9);

                    String s10 = rsGUINaming.getString("GUINameLogin");
                    tf10.setText(s10);

                    String s11 = rsGUINaming.getString("GUINameResetPassword");
                    tf11.setText(s11);

                    String s12 = rsGUINaming.getString("DefaultUser");
                    tf12.setText(s12);

                    String s13 = rsGUINaming.getString("DefaultUserCAPS");
                    tf13.setText(s13);

                    String s14 = rsGUINaming.getString("DefaultAdmin");
                    tf14.setText(s14);

                    String s15 = rsGUINaming.getString("DefaultAdminCAPS");
                    tf15.setText(s15);

                    String s16 = rsGUINaming.getString("DefaultNone");
                    tf16.setText(s16);

                    pstGUINaming.close();
                }
            }
            rsGUINaming.close();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong! ERR: " + e, mainErrorString, JOptionPane.ERROR_MESSAGE);
            clear();
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
        lbl1 = new javax.swing.JLabel();
        cb1 = new javax.swing.JCheckBox();
        tf1 = new javax.swing.JTextField();
        lbl2 = new javax.swing.JLabel();
        cb2 = new javax.swing.JCheckBox();
        tf2 = new javax.swing.JTextField();
        lbl3 = new javax.swing.JLabel();
        cb3 = new javax.swing.JCheckBox();
        tf3 = new javax.swing.JTextField();
        lbl4 = new javax.swing.JLabel();
        cb4 = new javax.swing.JCheckBox();
        tf4 = new javax.swing.JTextField();
        cb5 = new javax.swing.JCheckBox();
        lbl5 = new javax.swing.JLabel();
        tf5 = new javax.swing.JTextField();
        lbl6 = new javax.swing.JLabel();
        cb6 = new javax.swing.JCheckBox();
        tf6 = new javax.swing.JTextField();
        lbl7 = new javax.swing.JLabel();
        cb7 = new javax.swing.JCheckBox();
        tf7 = new javax.swing.JTextField();
        lbl8 = new javax.swing.JLabel();
        cb8 = new javax.swing.JCheckBox();
        tf8 = new javax.swing.JTextField();
        lbl9 = new javax.swing.JLabel();
        cb9 = new javax.swing.JCheckBox();
        tf9 = new javax.swing.JTextField();
        lbl10 = new javax.swing.JLabel();
        cb10 = new javax.swing.JCheckBox();
        tf10 = new javax.swing.JTextField();
        lbl11 = new javax.swing.JLabel();
        cb11 = new javax.swing.JCheckBox();
        tf11 = new javax.swing.JTextField();
        lbl12 = new javax.swing.JLabel();
        cb12 = new javax.swing.JCheckBox();
        tf12 = new javax.swing.JTextField();
        lbl13 = new javax.swing.JLabel();
        cb13 = new javax.swing.JCheckBox();
        tf13 = new javax.swing.JTextField();
        lbl14 = new javax.swing.JLabel();
        cb14 = new javax.swing.JCheckBox();
        tf14 = new javax.swing.JTextField();
        cb15 = new javax.swing.JCheckBox();
        tf15 = new javax.swing.JTextField();
        lbl15 = new javax.swing.JLabel();
        lbl16 = new javax.swing.JLabel();
        cb16 = new javax.swing.JCheckBox();
        tf16 = new javax.swing.JTextField();
        uncheckAllBTN = new javax.swing.JButton();
        applyBTN = new javax.swing.JButton();
        checkAllBTN = new javax.swing.JButton();
        savechangesBTN = new javax.swing.JButton();
        reset2defaultBTN = new javax.swing.JButton();
        txt_emp = new javax.swing.JLabel();
        lblmain = new javax.swing.JLabel();

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

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, -1));

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONFIGURE GUI OPTIONS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl1.setText("Main Application Name:");
        viewpanel.add(lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 30));

        cb1.setBackground(new java.awt.Color(249, 250, 253));
        cb1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb1.setContentAreaFilled(false);
        cb1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb1.setFocusPainted(false);
        cb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb1ActionPerformed(evt);
            }
        });
        viewpanel.add(cb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, 30));

        tf1.setEditable(false);
        tf1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf1.setOpaque(false);
        tf1.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 180, 30));

        lbl2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl2.setText("Main Company Name:");
        viewpanel.add(lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 150, 30));

        cb2.setBackground(new java.awt.Color(249, 250, 253));
        cb2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb2.setContentAreaFilled(false);
        cb2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb2.setFocusPainted(false);
        cb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb2ActionPerformed(evt);
            }
        });
        viewpanel.add(cb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, 30));

        tf2.setEditable(false);
        tf2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf2.setOpaque(false);
        tf2.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 180, 30));

        lbl3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl3.setText("Home Menu Top Name:");
        viewpanel.add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 150, 30));

        cb3.setBackground(new java.awt.Color(249, 250, 253));
        cb3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb3.setContentAreaFilled(false);
        cb3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb3.setFocusPainted(false);
        cb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb3ActionPerformed(evt);
            }
        });
        viewpanel.add(cb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, 30));

        tf3.setEditable(false);
        tf3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf3.setOpaque(false);
        tf3.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 180, 30));

        lbl4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl4.setText("Popup Normal Name:");
        viewpanel.add(lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 150, 30));

        cb4.setBackground(new java.awt.Color(249, 250, 253));
        cb4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb4.setContentAreaFilled(false);
        cb4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb4.setFocusPainted(false);
        cb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb4ActionPerformed(evt);
            }
        });
        viewpanel.add(cb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, -1, 30));

        tf4.setEditable(false);
        tf4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf4.setOpaque(false);
        tf4.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 180, 30));

        cb5.setBackground(new java.awt.Color(249, 250, 253));
        cb5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb5.setContentAreaFilled(false);
        cb5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb5.setFocusPainted(false);
        cb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb5ActionPerformed(evt);
            }
        });
        viewpanel.add(cb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, 30));

        lbl5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl5.setText("Popup Error Name:");
        viewpanel.add(lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 150, 30));

        tf5.setEditable(false);
        tf5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf5.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf5.setOpaque(false);
        tf5.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 180, 30));

        lbl6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl6.setText("Currency Symbol:");
        viewpanel.add(lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 150, 30));

        cb6.setBackground(new java.awt.Color(249, 250, 253));
        cb6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb6.setContentAreaFilled(false);
        cb6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb6.setFocusPainted(false);
        cb6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb6ActionPerformed(evt);
            }
        });
        viewpanel.add(cb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, -1, 30));

        tf6.setEditable(false);
        tf6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf6.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf6.setOpaque(false);
        tf6.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf6KeyTyped(evt);
            }
        });
        viewpanel.add(tf6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 180, 30));

        lbl7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl7.setText("Currency Code:");
        viewpanel.add(lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 150, 30));

        cb7.setBackground(new java.awt.Color(249, 250, 253));
        cb7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb7.setContentAreaFilled(false);
        cb7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb7.setFocusPainted(false);
        cb7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb7ActionPerformed(evt);
            }
        });
        viewpanel.add(cb7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, -1, 30));

        tf7.setEditable(false);
        tf7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf7.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf7.setOpaque(false);
        tf7.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 180, 30));

        lbl8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl8.setText("Overtime Rate:");
        viewpanel.add(lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 150, 30));

        cb8.setBackground(new java.awt.Color(249, 250, 253));
        cb8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb8.setContentAreaFilled(false);
        cb8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb8.setFocusPainted(false);
        cb8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb8ActionPerformed(evt);
            }
        });
        viewpanel.add(cb8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, -1, 30));

        tf8.setEditable(false);
        tf8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf8.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf8.setOpaque(false);
        tf8.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf8KeyTyped(evt);
            }
        });
        viewpanel.add(tf8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 180, 30));

        lbl9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl9.setText("RPH Rate:");
        viewpanel.add(lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 150, 30));

        cb9.setBackground(new java.awt.Color(249, 250, 253));
        cb9.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb9.setContentAreaFilled(false);
        cb9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb9.setFocusPainted(false);
        cb9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb9ActionPerformed(evt);
            }
        });
        viewpanel.add(cb9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, -1, 30));

        tf9.setEditable(false);
        tf9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf9.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf9.setOpaque(false);
        tf9.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf9KeyTyped(evt);
            }
        });
        viewpanel.add(tf9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 180, 30));

        lbl10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl10.setText("Login GUI Top Name:");
        viewpanel.add(lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 150, 30));

        cb10.setBackground(new java.awt.Color(249, 250, 253));
        cb10.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb10.setContentAreaFilled(false);
        cb10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb10.setFocusPainted(false);
        cb10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb10ActionPerformed(evt);
            }
        });
        viewpanel.add(cb10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, -1, 30));

        tf10.setEditable(false);
        tf10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf10.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf10.setOpaque(false);
        tf10.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 180, 30));

        lbl11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl11.setText("Reset Pass. GUI Top Name:");
        viewpanel.add(lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 150, 30));

        cb11.setBackground(new java.awt.Color(249, 250, 253));
        cb11.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb11.setContentAreaFilled(false);
        cb11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb11.setFocusPainted(false);
        cb11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb11ActionPerformed(evt);
            }
        });
        viewpanel.add(cb11, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, -1, 30));

        tf11.setEditable(false);
        tf11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf11.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf11.setOpaque(false);
        tf11.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        viewpanel.add(tf11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 180, 30));

        lbl12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl12.setText("Default User:");
        viewpanel.add(lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 150, 30));

        cb12.setBackground(new java.awt.Color(249, 250, 253));
        cb12.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb12.setContentAreaFilled(false);
        cb12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb12.setFocusPainted(false);
        cb12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb12ActionPerformed(evt);
            }
        });
        viewpanel.add(cb12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, -1, 30));

        tf12.setEditable(false);
        tf12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf12.setOpaque(false);
        tf12.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf12KeyTyped(evt);
            }
        });
        viewpanel.add(tf12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 180, 30));

        lbl13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl13.setText("Default User (CAPS):");
        viewpanel.add(lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 150, 30));

        cb13.setBackground(new java.awt.Color(249, 250, 253));
        cb13.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb13.setContentAreaFilled(false);
        cb13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb13.setFocusPainted(false);
        cb13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb13ActionPerformed(evt);
            }
        });
        viewpanel.add(cb13, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, -1, 30));

        tf13.setEditable(false);
        tf13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf13.setOpaque(false);
        tf13.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf13KeyTyped(evt);
            }
        });
        viewpanel.add(tf13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 180, 30));

        lbl14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl14.setText("Default Admin:");
        viewpanel.add(lbl14, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 150, 30));

        cb14.setBackground(new java.awt.Color(249, 250, 253));
        cb14.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb14.setContentAreaFilled(false);
        cb14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb14.setFocusPainted(false);
        cb14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb14ActionPerformed(evt);
            }
        });
        viewpanel.add(cb14, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, -1, 30));

        tf14.setEditable(false);
        tf14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf14.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf14.setOpaque(false);
        tf14.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf14KeyTyped(evt);
            }
        });
        viewpanel.add(tf14, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 180, 30));

        cb15.setBackground(new java.awt.Color(249, 250, 253));
        cb15.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb15.setContentAreaFilled(false);
        cb15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb15.setFocusPainted(false);
        cb15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb15ActionPerformed(evt);
            }
        });
        viewpanel.add(cb15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 210, -1, 30));

        tf15.setEditable(false);
        tf15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf15.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf15.setOpaque(false);
        tf15.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf15ActionPerformed(evt);
            }
        });
        tf15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf15KeyTyped(evt);
            }
        });
        viewpanel.add(tf15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 180, 30));

        lbl15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl15.setText("Default Admin (CAPS):");
        viewpanel.add(lbl15, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 150, 30));

        lbl16.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lbl16.setText("Default None:");
        viewpanel.add(lbl16, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 150, 30));

        cb16.setBackground(new java.awt.Color(249, 250, 253));
        cb16.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cb16.setContentAreaFilled(false);
        cb16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb16.setFocusPainted(false);
        cb16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb16ActionPerformed(evt);
            }
        });
        viewpanel.add(cb16, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 250, -1, 30));

        tf16.setEditable(false);
        tf16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        tf16.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tf16.setOpaque(false);
        tf16.setSelectedTextColor(new java.awt.Color(249, 250, 253));
        tf16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf16KeyTyped(evt);
            }
        });
        viewpanel.add(tf16, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 180, 30));

        uncheckAllBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        uncheckAllBTN.setText("Uncheck All");
        uncheckAllBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uncheckAllBTN.setFocusPainted(false);
        uncheckAllBTN.setFocusable(false);
        uncheckAllBTN.setOpaque(false);
        uncheckAllBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uncheckAllBTNActionPerformed(evt);
            }
        });
        viewpanel.add(uncheckAllBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, -1, -1));

        applyBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        applyBTN.setText("Apply");
        applyBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        applyBTN.setEnabled(false);
        applyBTN.setFocusPainted(false);
        applyBTN.setFocusable(false);
        applyBTN.setOpaque(false);
        applyBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyBTNActionPerformed(evt);
            }
        });
        viewpanel.add(applyBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 440, -1, -1));

        checkAllBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        checkAllBTN.setText("Check All");
        checkAllBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkAllBTN.setFocusPainted(false);
        checkAllBTN.setFocusable(false);
        checkAllBTN.setOpaque(false);
        checkAllBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAllBTNActionPerformed(evt);
            }
        });
        viewpanel.add(checkAllBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, -1, -1));

        savechangesBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        savechangesBTN.setText("Save Changes");
        savechangesBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        savechangesBTN.setFocusPainted(false);
        savechangesBTN.setFocusable(false);
        savechangesBTN.setOpaque(false);
        savechangesBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savechangesBTNActionPerformed(evt);
            }
        });
        viewpanel.add(savechangesBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, -1, -1));

        reset2defaultBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        reset2defaultBTN.setText("Reset to Default");
        reset2defaultBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reset2defaultBTN.setFocusPainted(false);
        reset2defaultBTN.setFocusable(false);
        reset2defaultBTN.setOpaque(false);
        reset2defaultBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset2defaultBTNActionPerformed(evt);
            }
        });
        viewpanel.add(reset2defaultBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 440, -1, -1));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 880, 480));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        mainpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 90, -1));

        lblmain.setBackground(new java.awt.Color(249, 250, 253));
        lblmain.setForeground(new java.awt.Color(249, 250, 253));
        lblmain.setFocusable(false);
        mainpanel.add(lblmain, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, 140, 20));

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 900, 530));

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

    private void cb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb1ActionPerformed
        if (cb1.isSelected()) {
            tf1.setEditable(true);
            tf1.requestFocusInWindow();
        } else {
            tf1.setEditable(false);
        }
    }//GEN-LAST:event_cb1ActionPerformed

    private void cb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb2ActionPerformed
        if (cb2.isSelected()) {
            tf2.setEditable(true);
            tf2.requestFocusInWindow();
        } else {
            tf2.setEditable(false);
        }
    }//GEN-LAST:event_cb2ActionPerformed

    private void cb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb3ActionPerformed
        if (cb3.isSelected()) {
            tf3.setEditable(true);
            tf3.requestFocusInWindow();
        } else {
            tf3.setEditable(false);
        }
    }//GEN-LAST:event_cb3ActionPerformed

    private void cb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb4ActionPerformed
        if (cb4.isSelected()) {
            tf4.setEditable(true);
            tf4.requestFocusInWindow();
        } else {
            tf4.setEditable(false);
        }
    }//GEN-LAST:event_cb4ActionPerformed

    private void cb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb5ActionPerformed
        if (cb5.isSelected()) {
            tf5.setEditable(true);
            tf5.requestFocusInWindow();
        } else {
            tf5.setEditable(false);
        }
    }//GEN-LAST:event_cb5ActionPerformed

    private void cb6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb6ActionPerformed
        if (cb6.isSelected()) {
            tf6.setEditable(true);
            tf6.requestFocusInWindow();
        } else {
            tf6.setEditable(false);
        }
    }//GEN-LAST:event_cb6ActionPerformed

    private void cb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb7ActionPerformed
        if (cb7.isSelected()) {
            tf7.setEditable(true);
            tf7.requestFocusInWindow();
        } else {
            tf7.setEditable(false);
        }
    }//GEN-LAST:event_cb7ActionPerformed

    private void cb8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb8ActionPerformed
        if (cb8.isSelected()) {
            tf8.setEditable(true);
            tf8.requestFocusInWindow();
        } else {
            tf8.setEditable(false);
        }
    }//GEN-LAST:event_cb8ActionPerformed

    private void cb9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb9ActionPerformed
        if (cb9.isSelected()) {
            tf9.setEditable(true);
            tf9.requestFocusInWindow();
        } else {
            tf9.setEditable(false);
        }
    }//GEN-LAST:event_cb9ActionPerformed

    private void cb10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb10ActionPerformed
        if (cb10.isSelected()) {
            tf10.setEditable(true);
            tf10.requestFocusInWindow();
        } else {
            tf10.setEditable(false);
        }
    }//GEN-LAST:event_cb10ActionPerformed

    private void cb11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb11ActionPerformed
        if (cb11.isSelected()) {
            tf11.setEditable(true);
            tf11.requestFocusInWindow();
        } else {
            tf11.setEditable(false);
        }
    }//GEN-LAST:event_cb11ActionPerformed

    private void cb12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb12ActionPerformed
        if (cb12.isSelected()) {
            tf12.setEditable(true);
            tf12.requestFocusInWindow();
        } else {
            tf12.setEditable(false);
        }
    }//GEN-LAST:event_cb12ActionPerformed

    private void cb13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb13ActionPerformed
        if (cb13.isSelected()) {
            tf13.setEditable(true);
            tf13.requestFocusInWindow();
        } else {
            tf13.setEditable(false);
        }
    }//GEN-LAST:event_cb13ActionPerformed

    private void cb14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb14ActionPerformed
        if (cb14.isSelected()) {
            tf14.setEditable(true);
            tf14.requestFocusInWindow();
        } else {
            tf14.setEditable(false);
        }
    }//GEN-LAST:event_cb14ActionPerformed

    private void cb15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb15ActionPerformed
        if (cb15.isSelected()) {
            tf15.setEditable(true);
            tf15.requestFocusInWindow();
        } else {
            tf15.setEditable(false);
        }
    }//GEN-LAST:event_cb15ActionPerformed

    private void cb16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb16ActionPerformed
        if (cb16.isSelected()) {
            tf16.setEditable(true);
            tf16.requestFocusInWindow();
        } else {
            tf16.setEditable(false);
        }
    }//GEN-LAST:event_cb16ActionPerformed

    private void applyBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyBTNActionPerformed
        if (tf1.getText().isEmpty() | tf2.getText().isEmpty() | tf3.getText().isEmpty() | tf4.getText().isEmpty() | tf5.getText().isEmpty() | tf6.getText().isEmpty() | tf7.getText().isEmpty() | tf8.getText().isEmpty() | tf9.getText().isEmpty() | tf10.getText().isEmpty() | tf11.getText().isEmpty() | tf12.getText().isEmpty() | tf13.getText().isEmpty() | tf14.getText().isEmpty() | tf15.getText().isEmpty() | tf16.getText().isEmpty()) {
            if (tf1.isEditable() | tf2.isEditable() | tf3.isEditable() | tf4.isEditable() | tf5.isEditable() | tf6.isEditable() | tf7.isEditable() | tf8.isEditable() | tf9.isEditable() | tf10.isEditable() | tf11.isEditable() | tf12.isEditable() | tf13.isEditable() | tf14.isEditable() | tf15.isEditable() | tf16.isEditable()) {
                JOptionPane.showMessageDialog(null, "<html><center>One of the Required field is empty!</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            int opendata = JOptionPane.showConfirmDialog(null, "<html><center>Are you sure that do you want to update this changes? <br>You cannot undo this action!</center></html>", mainnameString, JOptionPane.YES_NO_OPTION);
            if (opendata == 0) {
                try {
                    String value = lblmain.getText();
                    String query;

                    query = "UPDATE GUINames SET MainAppName=?,MainCompanyName=?,MainTopAppName=?,PopupNormal=?,PopupError=?,CurrencySign=?,CurrencyCode=?,OvertimeRate=?,RPHRate=?,GUINameLogin=?,GUINameResetPassword=?,DefaultUser=?,DefaultUserCAPS=?,DefaultAdmin=?,DefaultAdminCAPS=?,DefaultNone=? WHERE Depending='" + value + "'";

                    try (PreparedStatement pstGNUpdate = conn.prepareStatement(query)) {
                        pstGNUpdate.setString(1, tf1.getText());
                        pstGNUpdate.setString(2, tf2.getText());
                        pstGNUpdate.setString(3, tf3.getText());
                        pstGNUpdate.setString(4, tf4.getText());
                        pstGNUpdate.setString(5, tf5.getText());
                        pstGNUpdate.setString(6, tf6.getText());
                        pstGNUpdate.setString(7, tf7.getText());
                        pstGNUpdate.setString(8, tf8.getText());
                        pstGNUpdate.setString(9, tf9.getText());
                        pstGNUpdate.setString(10, tf10.getText());
                        pstGNUpdate.setString(11, tf11.getText());
                        pstGNUpdate.setString(12, tf12.getText());
                        pstGNUpdate.setString(13, tf13.getText());
                        pstGNUpdate.setString(14, tf14.getText());
                        pstGNUpdate.setString(15, tf15.getText());
                        pstGNUpdate.setString(16, tf16.getText());

                        pstGNUpdate.executeUpdate();
                        pstGNUpdate.close();
                    }
                    //JOptionPane.showMessageDialog(null,"Updated Successfully.",mainnameString,JOptionPane.INFORMATION_MESSAGE,null);
                    auditEdit();
                    savechangesBTN.setEnabled(true);
                    checkAllBTN.setEnabled(true);
                    uncheckAllBTN.setEnabled(true);
                    reset2defaultBTN.setEnabled(true);
                    applyBTN.setEnabled(false);
                    restartEditGUI();
                } catch (SQLException e) {

                } catch (IOException ex) {
                    Logger.getLogger(EditGUIOptions.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //
                cb1.setSelected(false);
                cb2.setSelected(false);
                cb3.setSelected(false);
                cb4.setSelected(false);
                cb5.setSelected(false);
                cb6.setSelected(false);
                cb7.setSelected(false);
                cb8.setSelected(false);
                cb9.setSelected(false);
                cb10.setSelected(false);
                cb11.setSelected(false);
                cb12.setSelected(false);
                cb13.setSelected(false);
                cb14.setSelected(false);
                cb15.setSelected(false);
                cb16.setSelected(false);

                cb1.setEnabled(true);
                cb2.setEnabled(true);
                cb3.setEnabled(true);
                cb4.setEnabled(true);
                cb5.setEnabled(true);
                cb6.setEnabled(true);
                cb7.setEnabled(true);
                cb8.setEnabled(true);
                cb9.setEnabled(true);
                cb10.setEnabled(true);
                cb11.setEnabled(true);
                cb12.setEnabled(true);
                cb13.setEnabled(true);
                cb14.setEnabled(true);
                cb15.setEnabled(true);
                cb16.setEnabled(true);

                tf1.setEditable(false);
                tf2.setEditable(false);
                tf3.setEditable(false);
                tf4.setEditable(false);
                tf5.setEditable(false);
                tf6.setEditable(false);
                tf7.setEditable(false);
                tf8.setEditable(false);
                tf9.setEditable(false);
                tf10.setEditable(false);
                tf11.setEditable(false);
                tf12.setEditable(false);
                tf13.setEditable(false);
                tf14.setEditable(false);
                tf15.setEditable(false);
                tf16.setEditable(false);

                tf1.requestFocus(false);
                tf2.requestFocus(false);
                tf3.requestFocus(false);
                tf4.requestFocus(false);
                tf5.requestFocus(false);
                tf6.requestFocus(false);
                tf7.requestFocus(false);
                tf8.requestFocus(false);
                tf9.requestFocus(false);
                tf10.requestFocus(false);
                tf11.requestFocus(false);
                tf12.requestFocus(false);
                tf13.requestFocus(false);
                tf14.requestFocus(false);
                tf15.requestFocus(false);
                tf16.requestFocus(false);

                savechangesBTN.setEnabled(true);
                checkAllBTN.setEnabled(true);
                uncheckAllBTN.setEnabled(true);
                reset2defaultBTN.setEnabled(true);
                applyBTN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_applyBTNActionPerformed

    private void checkAllBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAllBTNActionPerformed
        //selected
        if (cb1.isSelected() | cb2.isSelected() | cb3.isSelected() | cb4.isSelected() | cb5.isSelected() | cb6.isSelected() | cb7.isSelected() | cb8.isSelected() | cb9.isSelected() | cb10.isSelected() | cb11.isSelected() | cb12.isSelected() | cb13.isSelected() | cb14.isSelected() | cb15.isSelected() | cb16.isSelected()) {
            JOptionPane.showMessageDialog(null, "<html><center>All Checkbox is Already Selected.</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
            System.out.println("Already Selected");
            //not selected
        } else if (!(cb1.isSelected() | cb2.isSelected() | cb3.isSelected() | cb4.isSelected() | cb5.isSelected() | cb6.isSelected() | cb7.isSelected() | cb8.isSelected() | cb9.isSelected() | cb10.isSelected() | cb11.isSelected() | cb12.isSelected() | cb13.isSelected() | cb14.isSelected() | cb15.isSelected() | cb16.isSelected())) {
            System.out.println("All CB is Selected");
            cb1.setSelected(true);
            cb2.setSelected(true);
            cb3.setSelected(true);
            cb4.setSelected(true);
            cb5.setSelected(true);
            cb6.setSelected(true);
            cb7.setSelected(true);
            cb8.setSelected(true);
            cb9.setSelected(true);
            cb10.setSelected(true);
            cb11.setSelected(true);
            cb12.setSelected(true);
            cb13.setSelected(true);
            cb14.setSelected(true);
            cb15.setSelected(true);
            cb16.setSelected(true);

            tf1.setEditable(true);
            tf2.setEditable(true);
            tf3.setEditable(true);
            tf4.setEditable(true);
            tf5.setEditable(true);
            tf6.setEditable(true);
            tf7.setEditable(true);
            tf8.setEditable(true);
            tf9.setEditable(true);
            tf10.setEditable(true);
            tf11.setEditable(true);
            tf12.setEditable(true);
            tf13.setEditable(true);
            tf14.setEditable(true);
            tf15.setEditable(true);
            tf16.setEditable(true);

            //checkAllBTN.setEnabled(false);
            //uncheckAllBTN.setEnabled(true);
        }
    }//GEN-LAST:event_checkAllBTNActionPerformed

    private void uncheckAllBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uncheckAllBTNActionPerformed
        //selected
        if (cb1.isSelected() | cb2.isSelected() | cb3.isSelected() | cb4.isSelected() | cb5.isSelected() | cb6.isSelected() | cb7.isSelected() | cb8.isSelected() | cb9.isSelected() | cb10.isSelected() | cb11.isSelected() | cb12.isSelected() | cb13.isSelected() | cb14.isSelected() | cb15.isSelected() | cb16.isSelected()) {
            System.out.println("All CB is Unselected");
            cb1.setSelected(false);
            cb2.setSelected(false);
            cb3.setSelected(false);
            cb4.setSelected(false);
            cb5.setSelected(false);
            cb6.setSelected(false);
            cb7.setSelected(false);
            cb8.setSelected(false);
            cb9.setSelected(false);
            cb10.setSelected(false);
            cb11.setSelected(false);
            cb12.setSelected(false);
            cb13.setSelected(false);
            cb14.setSelected(false);
            cb15.setSelected(false);
            cb16.setSelected(false);

            tf1.setEditable(false);
            tf2.setEditable(false);
            tf3.setEditable(false);
            tf4.setEditable(false);
            tf5.setEditable(false);
            tf6.setEditable(false);
            tf7.setEditable(false);
            tf8.setEditable(false);
            tf9.setEditable(false);
            tf10.setEditable(false);
            tf11.setEditable(false);
            tf12.setEditable(false);
            tf13.setEditable(false);
            tf14.setEditable(false);
            tf15.setEditable(false);
            tf16.setEditable(false);

            tf1.requestFocus(false);
            tf2.requestFocus(false);
            tf3.requestFocus(false);
            tf4.requestFocus(false);
            tf5.requestFocus(false);
            tf6.requestFocus(false);
            tf7.requestFocus(false);
            tf8.requestFocus(false);
            tf9.requestFocus(false);
            tf10.requestFocus(false);
            tf11.requestFocus(false);
            tf12.requestFocus(false);
            tf13.requestFocus(false);
            tf14.requestFocus(false);
            tf15.requestFocus(false);
            tf16.requestFocus(false);
            //checkAllBTN.setEnabled(false);
            //uncheckAllBTN.setEnabled(true);

            //not selected
        } else if (!(cb1.isSelected() | cb2.isSelected() | cb3.isSelected() | cb4.isSelected() | cb5.isSelected() | cb6.isSelected() | cb7.isSelected() | cb8.isSelected() | cb9.isSelected() | cb10.isSelected() | cb11.isSelected() | cb12.isSelected() | cb13.isSelected() | cb14.isSelected() | cb15.isSelected() | cb16.isSelected())) {
            JOptionPane.showMessageDialog(null, "<html><center>All Checkbox is Already Unselected.</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
            System.out.println("Already Unselected");
        }
    }//GEN-LAST:event_uncheckAllBTNActionPerformed

    private void savechangesBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savechangesBTNActionPerformed
        if (tf1.getText().isEmpty() | tf2.getText().isEmpty() | tf3.getText().isEmpty() | tf4.getText().isEmpty() | tf5.getText().isEmpty() | tf6.getText().isEmpty() | tf7.getText().isEmpty() | tf8.getText().isEmpty() | tf9.getText().isEmpty() | tf10.getText().isEmpty() | tf11.getText().isEmpty() | tf12.getText().isEmpty() | tf13.getText().isEmpty() | tf14.getText().isEmpty() | tf15.getText().isEmpty() | tf16.getText().isEmpty()) {
            if (tf1.isEditable() | tf2.isEditable() | tf3.isEditable() | tf4.isEditable() | tf5.isEditable() | tf6.isEditable() | tf7.isEditable() | tf8.isEditable() | tf9.isEditable() | tf10.isEditable() | tf11.isEditable() | tf12.isEditable() | tf13.isEditable() | tf14.isEditable() | tf15.isEditable() | tf16.isEditable()) {
                JOptionPane.showMessageDialog(null, "<html><center>One of the Required field is empty!</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
            }
            if (!(tf1.isEditable() | tf2.isEditable() | tf3.isEditable() | tf4.isEditable() | tf5.isEditable() | tf6.isEditable() | tf7.isEditable() | tf8.isEditable() | tf9.isEditable() | tf10.isEditable() | tf11.isEditable() | tf12.isEditable() | tf13.isEditable() | tf14.isEditable() | tf15.isEditable() | tf16.isEditable())) {
                JOptionPane.showMessageDialog(null, "<html><center>One of the Required field is empty!</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cb1.setSelected(false);
            cb2.setSelected(false);
            cb3.setSelected(false);
            cb4.setSelected(false);
            cb5.setSelected(false);
            cb6.setSelected(false);
            cb7.setSelected(false);
            cb8.setSelected(false);
            cb9.setSelected(false);
            cb10.setSelected(false);
            cb11.setSelected(false);
            cb12.setSelected(false);
            cb13.setSelected(false);
            cb14.setSelected(false);
            cb15.setSelected(false);
            cb16.setSelected(false);

            cb1.setEnabled(false);
            cb2.setEnabled(false);
            cb3.setEnabled(false);
            cb4.setEnabled(false);
            cb5.setEnabled(false);
            cb6.setEnabled(false);
            cb7.setEnabled(false);
            cb8.setEnabled(false);
            cb9.setEnabled(false);
            cb10.setEnabled(false);
            cb11.setEnabled(false);
            cb12.setEnabled(false);
            cb13.setEnabled(false);
            cb14.setEnabled(false);
            cb15.setEnabled(false);
            cb16.setEnabled(false);

            tf1.setEditable(false);
            tf2.setEditable(false);
            tf3.setEditable(false);
            tf4.setEditable(false);
            tf5.setEditable(false);
            tf6.setEditable(false);
            tf7.setEditable(false);
            tf8.setEditable(false);
            tf9.setEditable(false);
            tf10.setEditable(false);
            tf11.setEditable(false);
            tf12.setEditable(false);
            tf13.setEditable(false);
            tf14.setEditable(false);
            tf15.setEditable(false);
            tf16.setEditable(false);

            tf1.requestFocus(false);
            tf2.requestFocus(false);
            tf3.requestFocus(false);
            tf4.requestFocus(false);
            tf5.requestFocus(false);
            tf6.requestFocus(false);
            tf7.requestFocus(false);
            tf8.requestFocus(false);
            tf9.requestFocus(false);
            tf10.requestFocus(false);
            tf11.requestFocus(false);
            tf12.requestFocus(false);
            tf13.requestFocus(false);
            tf14.requestFocus(false);
            tf15.requestFocus(false);
            tf16.requestFocus(false);

            savechangesBTN.setEnabled(false);
            checkAllBTN.setEnabled(false);
            uncheckAllBTN.setEnabled(false);
            reset2defaultBTN.setEnabled(false);
            applyBTN.setEnabled(true);
            applyBTN.requestFocusInWindow();
        }
    }//GEN-LAST:event_savechangesBTNActionPerformed

    private void tf8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf8KeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        } else if (c == KeyEvent.VK_SPACE) {
            evt.consume();
        }

        //limit the number length
        String l = tf8.getText();
        if (!(l.length() < 5)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf8KeyTyped

    private void tf9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf9KeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        } else if (c == KeyEvent.VK_SPACE) {
            evt.consume();
        }

        //limit the number length
        String l = tf9.getText();
        if (!(l.length() < 5)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf9KeyTyped

    private void tf6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf6KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf6.getText();
        if (!(l.length() < 1)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf6KeyTyped

    private void tf12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf12KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf12.getText();
        if (!(l.length() < 30)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf12KeyTyped

    private void tf13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf13KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf13.getText();
        if (!(l.length() < 30)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf13KeyTyped

    private void tf14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf14KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf14.getText();
        if (!(l.length() < 30)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf14KeyTyped

    private void tf15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf15ActionPerformed

    private void tf15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf15KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf15.getText();
        if (!(l.length() < 30)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf15KeyTyped

    private void tf16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf16KeyTyped
        //letters only.
        char c = evt.getKeyChar();
        if (!(Character.isAlphabetic(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)
                || (c == KeyEvent.VK_COMMA)
                || (c == KeyEvent.VK_UNDERSCORE)
                || (c == KeyEvent.VK_MINUS)
                || (c == KeyEvent.VK_PERIOD)
                || (c == KeyEvent.VK_SPACE)
                || (c == KeyEvent.VK_SLASH)
                || (c == KeyEvent.VK_LEFT_PARENTHESIS)
                || (c == KeyEvent.VK_RIGHT_PARENTHESIS)
                || (c == KeyEvent.VK_ALT)
                || (c == KeyEvent.VK_UNDEFINED)
                || (c == KeyEvent.VK_COPY)
                || (c == KeyEvent.VK_CONTROL)
                || (c == KeyEvent.VK_ENTER)
                || (c == KeyEvent.VK_TAB))) {
            evt.consume();
        }

        //limit the string length
        String l = tf16.getText();
        if (!(l.length() < 30)) {
            evt.consume();
        }
    }//GEN-LAST:event_tf16KeyTyped

    private void reset2defaultBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset2defaultBTNActionPerformed
        try {
            insertGUINaming();
        } catch (SQLException ex) {
            Logger.getLogger(EditGUIOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_reset2defaultBTNActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new EditGUIOptions().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EditGUIOptions.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyBTN;
    private javax.swing.JCheckBox cb1;
    private javax.swing.JCheckBox cb10;
    private javax.swing.JCheckBox cb11;
    private javax.swing.JCheckBox cb12;
    private javax.swing.JCheckBox cb13;
    private javax.swing.JCheckBox cb14;
    private javax.swing.JCheckBox cb15;
    private javax.swing.JCheckBox cb16;
    private javax.swing.JCheckBox cb2;
    private javax.swing.JCheckBox cb3;
    private javax.swing.JCheckBox cb4;
    private javax.swing.JCheckBox cb5;
    private javax.swing.JCheckBox cb6;
    private javax.swing.JCheckBox cb7;
    private javax.swing.JCheckBox cb8;
    private javax.swing.JCheckBox cb9;
    private javax.swing.JButton checkAllBTN;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl16;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lblmain;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JButton reset2defaultBTN;
    private javax.swing.JButton savechangesBTN;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf10;
    private javax.swing.JTextField tf11;
    private javax.swing.JTextField tf12;
    private javax.swing.JTextField tf13;
    private javax.swing.JTextField tf14;
    private javax.swing.JTextField tf15;
    private javax.swing.JTextField tf16;
    private javax.swing.JTextField tf2;
    private javax.swing.JTextField tf3;
    private javax.swing.JTextField tf4;
    private javax.swing.JTextField tf5;
    private javax.swing.JTextField tf6;
    private javax.swing.JTextField tf7;
    private javax.swing.JTextField tf8;
    private javax.swing.JTextField tf9;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JButton uncheckAllBTN;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables
}