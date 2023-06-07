// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package MainPackage;

import SystemDB.DBconnection;
import Panels_EmployeeManager.Emp;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
public final class ResetPasswordFrame extends javax.swing.JFrame {
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
        
    /**
     * Creates new form LoginFrame
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public ResetPasswordFrame() throws SQLException, IOException {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        ResetPasswordFrame.this.getRootPane().setBorder(new MatteBorder(0, 1, 1, 1, (new Color(0,102,204))));
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        JTextFieldLimit();
        clearText();
        noPasteCutPassword();
        GUINaming_DATA();
        //bluredlogin.setVisible(false);
        //guiTitle.setText(this.getTitle());
        
        txt_emp.setText(String.valueOf(Emp.empId));

        exitButton.setToolTipText("Close");
        minimizeButton.setToolTipText("Minimize");
        
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
        minimizeButton.setBackground(new Color(0,102,204));
        
        exitButton.setSelected(false);
        minimizeButton.setSelected(false);
        
        exitButton.setFocusPainted(false);
        minimizeButton.setFocusPainted(false);
        
        exitButton.setDefaultCapable(false);
        minimizeButton.setDefaultCapable(false);
        
    }

    //-------------------- START VOID CODES HERE --------------------//

    //GUINaming from Database
    public void GUINaming_DATA() throws SQLException {
        try {
            ResultSet rs;
            try (Statement st = conn.createStatement()) {
                rs = st.executeQuery("select * FROM GUINames");
                
                //set the GUI Title
                final String GUITopNameDB = rs.getString("GUINameResetPassword");
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
    
    public void showpasswordcharOLD() {
        oldpasswordTF.setEchoChar('\u25cf');
    }
    
    public void showpasswordcharNEW() {
        newpasswordTF.setEchoChar('\u25cf');
    }
    
    public void showpasswordcharNEWCONFIRM() {
        confirmnewpasswordTF.setEchoChar('\u25cf');
    }

    public void showpinchar() {
        pincodeTF.setEchoChar('\u25cf');
    }
    
    public void showpasswordcharOLD_ClickLogin() {
        //currently showing password
        if(showpasswordCBold.isSelected()) {
            showpasswordCBold.setSelected(false);
            oldpasswordTF.setEchoChar('\u25cf');
        
        //currently not showing password
        } else if(!showpasswordCBold.isSelected()) {
            showpasswordCBold.setSelected(false);
            oldpasswordTF.setEchoChar('\u25cf');
        }
    }
    
    public void showpasswordcharNEW_ClickLogin() {
        //currently showing password
        if(showpasswordCBnew.isSelected()) {
            showpasswordCBnew.setSelected(false);
            newpasswordTF.setEchoChar('\u25cf');
        
        //currently not showing password
        } else if(!showpasswordCBnew.isSelected()) {
            showpasswordCBnew.setSelected(false);
            newpasswordTF.setEchoChar('\u25cf');
        }
    }
    
    public void showpasswordcharNEWCONFIRM_ClickLogin() {
        //currently showing password
        if(showpasswordCBconfirm.isSelected()) {
            showpasswordCBconfirm.setSelected(false);
            confirmnewpasswordTF.setEchoChar('\u25cf');
        
        //currently not showing password
        } else if(!showpasswordCBconfirm.isSelected()) {
            showpasswordCBconfirm.setSelected(false);
            confirmnewpasswordTF.setEchoChar('\u25cf');
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
                    Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
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
            final JOptionPane optionPane = new JOptionPane("<html><center>Resetting your password...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
            final JOptionPane optionPane = new JOptionPane("<html><center>Resetting your password...<br>Please wait for a moment</center></html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
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
    
    public void resetsuccess() throws SQLException, IOException {
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
        int jop = JOptionPane.showOptionDialog(frame.getContentPane(), "<html><center>Password has been reset successfully!</center><br><center>DO YOU WANT TO LOG IN NOW?</center></html>", mainnameString, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (jop) {
            case 0:
                //yes
                getToolkit().beep();                
                LoginFrame mainLogin = new LoginFrame();

                String userlevelString = userString;
                mainLogin.userlevelVoid(userlevelString);
                
                mainLogin.setVisible(true);
                this.dispose();
                break;
            case 1:
                // exit
                clearText();
                System.exit(0);
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
        oldpasswordTF.setDocument(new JTextFieldLimitAPI(8));
        newpasswordTF.setDocument(new JTextFieldLimitAPI(8));
        confirmnewpasswordTF.setDocument(new JTextFieldLimitAPI(8));
        pincodeTF.setDocument(new JTextFieldLimitAPI(4));
    }
    
    public void clearText() {
        fullnameTF.setText("");
        oldpasswordTF.setText("");
        newpasswordTF.setText("");
        confirmnewpasswordTF.setText("");
        pincodeTF.setText("");
        fullnameTF.requestFocusInWindow();
        showpasswordCBold.setSelected(false);
        showpasswordCBnew.setSelected(false);
        showpasswordCBconfirm.setSelected(false);
        showpincodeCB.setSelected(false);
    }
    
    public void noPasteCutPassword() {
        oldpasswordTF.setTransferHandler(null);
        newpasswordTF.setTransferHandler(null);
        confirmnewpasswordTF.setTransferHandler(null);
        pincodeTF.setTransferHandler(null);
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
        String val = txt_emp.getText();
        try {
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Password of: "+fullnameTF.getText()+" is Resetted by: "+val+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
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

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        pnlMAIN = new javax.swing.JPanel();
        oldpasswordLABEL = new javax.swing.JLabel();
        showpasswordCBnew = new javax.swing.JCheckBox();
        newpasswordLABEL = new javax.swing.JLabel();
        newpasswordTF = new javax.swing.JPasswordField();
        pnlTop = new javax.swing.JPanel();
        guiTitle = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        minimizeButton = new javax.swing.JButton();
        resetpasswordBTN = new rojerusan.RSMaterialButtonRectangle();
        backBTN = new rojerusan.RSMaterialButtonRectangle();
        clearBTN = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        confirmpasswordLABEL = new javax.swing.JLabel();
        showpasswordCBconfirm = new javax.swing.JCheckBox();
        confirmnewpasswordTF = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        fullnameTF = new javax.swing.JTextField();
        fullnameLABEL = new javax.swing.JLabel();
        showpasswordCBold = new javax.swing.JCheckBox();
        oldpasswordTF = new javax.swing.JPasswordField();
        pincodeLABEL = new javax.swing.JLabel();
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

        oldpasswordLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        oldpasswordLABEL.setForeground(new java.awt.Color(102, 102, 102));
        oldpasswordLABEL.setText("old password");
        pnlMAIN.add(oldpasswordLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        showpasswordCBnew.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCBnew.setToolTipText("Show/Hide Password");
        showpasswordCBnew.setBorderPaintedFlat(true);
        showpasswordCBnew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCBnew.setDoubleBuffered(true);
        showpasswordCBnew.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCBnew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCBnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCBnewActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCBnew, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 288, -1, -1));

        newpasswordLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        newpasswordLABEL.setForeground(new java.awt.Color(102, 102, 102));
        newpasswordLABEL.setText("new password");
        pnlMAIN.add(newpasswordLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, -1, -1));

        newpasswordTF.setBackground(new java.awt.Color(253, 253, 253));
        newpasswordTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newpasswordTF.setForeground(new java.awt.Color(102, 102, 102));
        newpasswordTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        newpasswordTF.setDoubleBuffered(true);
        newpasswordTF.setEchoChar('\u25cf');
        newpasswordTF.setOpaque(false);
        newpasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpasswordTFActionPerformed(evt);
            }
        });
        newpasswordTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newpasswordTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newpasswordTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newpasswordTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(newpasswordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 278, -1));

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
        guiTitle.setText("Reset Password");
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
        guiTitle.setBounds(5, 0, 320, 30);

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
        exitButton.setBounds(705, 0, 18, 30);

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
        minimizeButton.setBounds(680, 0, 18, 30);

        pnlMAIN.add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, -1));

        resetpasswordBTN.setBackground(new java.awt.Color(51, 204, 0));
        resetpasswordBTN.setText("RESET PASSWORD");
        resetpasswordBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        resetpasswordBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                resetpasswordBTNMousePressed(evt);
            }
        });
        resetpasswordBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetpasswordBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(resetpasswordBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 417, -1, 39));

        backBTN.setBackground(new java.awt.Color(12, 91, 160));
        backBTN.setText("BACK TO LOG IN");
        backBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });
        pnlMAIN.add(backBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, -1, 39));

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
        pnlMAIN.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 486, 280, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainIcon2_128px.png"))); // NOI18N
        pnlMAIN.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        confirmpasswordLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        confirmpasswordLABEL.setForeground(new java.awt.Color(102, 102, 102));
        confirmpasswordLABEL.setText("confirm new password");
        pnlMAIN.add(confirmpasswordLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        showpasswordCBconfirm.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCBconfirm.setToolTipText("Show/Hide Password");
        showpasswordCBconfirm.setBorderPaintedFlat(true);
        showpasswordCBconfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCBconfirm.setDoubleBuffered(true);
        showpasswordCBconfirm.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCBconfirm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCBconfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCBconfirmActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCBconfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 358, -1, -1));

        confirmnewpasswordTF.setBackground(new java.awt.Color(253, 253, 253));
        confirmnewpasswordTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmnewpasswordTF.setForeground(new java.awt.Color(102, 102, 102));
        confirmnewpasswordTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        confirmnewpasswordTF.setDoubleBuffered(true);
        confirmnewpasswordTF.setEchoChar('\u25cf');
        confirmnewpasswordTF.setOpaque(false);
        confirmnewpasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmnewpasswordTFActionPerformed(evt);
            }
        });
        confirmnewpasswordTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmnewpasswordTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confirmnewpasswordTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                confirmnewpasswordTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(confirmnewpasswordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 278, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setText("RESET PASSWORD");
        pnlMAIN.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, 80));

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
        pnlMAIN.add(fullnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 278, -1));

        fullnameLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        fullnameLABEL.setForeground(new java.awt.Color(102, 102, 102));
        fullnameLABEL.setText("fullname");
        pnlMAIN.add(fullnameLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        showpasswordCBold.setBackground(new java.awt.Color(253, 253, 253));
        showpasswordCBold.setToolTipText("Show/Hide Password");
        showpasswordCBold.setBorderPaintedFlat(true);
        showpasswordCBold.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpasswordCBold.setDoubleBuffered(true);
        showpasswordCBold.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        showpasswordCBold.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showpasswordCBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordCBoldActionPerformed(evt);
            }
        });
        pnlMAIN.add(showpasswordCBold, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 288, -1, -1));

        oldpasswordTF.setBackground(new java.awt.Color(253, 253, 253));
        oldpasswordTF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        oldpasswordTF.setForeground(new java.awt.Color(102, 102, 102));
        oldpasswordTF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(12, 91, 160)));
        oldpasswordTF.setDoubleBuffered(true);
        oldpasswordTF.setEchoChar('\u25cf');
        oldpasswordTF.setOpaque(false);
        oldpasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oldpasswordTFActionPerformed(evt);
            }
        });
        oldpasswordTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldpasswordTFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                oldpasswordTFKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                oldpasswordTFKeyTyped(evt);
            }
        });
        pnlMAIN.add(oldpasswordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 278, -1));

        pincodeLABEL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        pincodeLABEL.setForeground(new java.awt.Color(102, 102, 102));
        pincodeLABEL.setText("pin code");
        pnlMAIN.add(pincodeLABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, -1, -1));

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
        pnlMAIN.add(showpincodeCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 218, -1, -1));

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
        pnlMAIN.add(pincodeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 278, -1));

        txt_emp.setForeground(new java.awt.Color(253, 253, 253));
        txt_emp.setText("emp");
        pnlMAIN.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 240, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMAIN, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(730, 527));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    int xy, xx; //<-- for pnlTop
    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        try {
            LoginFrame mainLogin= new LoginFrame();
            mainLogin.setVisible(true);
            this.dispose();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_backBTNActionPerformed

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
            System.exit(0);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void minimizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeButtonActionPerformed
        // Minimize Button code:
        resetColor2(exitButton);
        setColor2(minimizeButton);
        
        ResetPasswordFrame.this.setState(JFrame.ICONIFIED);
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

    private void newpasswordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newpasswordTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            confirmnewpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_newpasswordTFKeyPressed

    private void newpasswordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newpasswordTFKeyReleased

    }//GEN-LAST:event_newpasswordTFKeyReleased

    private void resetpasswordBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetpasswordBTNActionPerformed
        String fullnameString = fullnameTF.getText();
        String oldPasswordString = oldpasswordTF.getText();
        String pincodeString = pincodeTF.getText();
        showpasswordcharOLD_ClickLogin();
        showpasswordcharNEW_ClickLogin();
        showpasswordcharNEWCONFIRM_ClickLogin();
        showpinchar_ClickLogin();
        
        //RESET PASSWORD BUTTON CODE
        clicked++;
        if(clicked<=3) {
            
        try {
            //check user old password
            Statement st1 = conn.createStatement();
            ResultSet rspasscheck = st1.executeQuery("SELECT password FROM Users WHERE password like '"+oldPasswordString+"'");

            //find fullname
            Statement st2 = conn.createStatement();
            ResultSet rsfullname = st2.executeQuery("select fullname from Users where fullname like '"+'%'+fullnameString+'%'+"'");      

            //check user pincode
            Statement st3 = conn.createStatement();
            ResultSet rspincodecheck = st3.executeQuery("SELECT pincode FROM Users WHERE pincode like '"+pincodeString+"'");
            
            //check if the field is empty
            if (fullnameTF.getText().isEmpty()| oldpasswordTF.getText().isEmpty() | newpasswordTF.getText().isEmpty() | confirmnewpasswordTF.getText().isEmpty()) {
                getToolkit().beep();
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>One of the required field is empty. <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                fullnameTF.requestFocusInWindow();
            
            //check the password length up to 8 characters only.
            } else if(!(newpasswordTF.getText().length()>=8)) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>Invalid, Password must have 8 characters exactly! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                newpasswordTF.requestFocusInWindow();

            //check the pincode length up to 4 characters.
            } else if(!(pincodeTF.getText().length()>=4)) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>Invalid, Pincode must have 4 characters exactly! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                pincodeTF.requestFocusInWindow();
                
            //check the user fullname    
            } else if(!(rsfullname.next())) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>"+fullnameString+" account is not found! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                fullnameTF.requestFocusInWindow();
                st2.close();
                rsfullname.close();
                
            //check the password    
            } else if(!(rspasscheck.next())) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>Wrong old password! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                oldpasswordTF.requestFocusInWindow();
                st1.close();
                rspasscheck.close();
 
            //check the pincode  
            } else if(!(rspincodecheck.next())) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>Wrong Pincode! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                pincodeTF.requestFocusInWindow();
                st3.close();
                rspincodecheck.close();
                
            //check if the old and new password is same
            } else if(newpasswordTF.getText().equals(oldpasswordTF.getText())) {
                registerpanelloadingcheck();
                JOptionPane.showMessageDialog(null, "<html><center>Same old and new password is not allowed! <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                oldpasswordTF.requestFocusInWindow();
            
            //change the password
            } else {
                String fullname = fullnameTF.getText();
                String value1 = oldpasswordTF.getText();
                String value2 = newpasswordTF.getText();
                String value3 = confirmnewpasswordTF.getText();
                String value4 = pincodeTF.getText();
                
                if(value2.equals(value3)) {
                    String query = "UPDATE Users SET password='"+value2+"' WHERE password='"+value1+"' AND fullname='"+fullname+"'";
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.execute();
                        pst.close();
                    }
                    audit();
                    registerpanelloading();
                    resetsuccess();
                
                //check the newpassword and confirmpassword if match from IF statement above.
                } else {
                    registerpanelloadingcheck();
                    JOptionPane.showMessageDialog(null,"<html><center>Password not match! <br>Please re-check your password. <br>Attempt: "+clicked+""+attemptover+"</center></html>", mainErrorString,JOptionPane.INFORMATION_MESSAGE,null);
                    newpasswordTF.requestFocusInWindow();
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            

            //if the attempts is equal to 3/3
            

            //if the attempts is equal to 3/3
            } else if (clicked>=3) {
            try {
                getToolkit().beep();
                shuttingdown(); //<-- shutdown jdialog void
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }//GEN-LAST:event_resetpasswordBTNActionPerformed

    private void showpasswordCBnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCBnewActionPerformed
        if(showpasswordCBnew.isSelected()) {
            newpasswordTF.setEchoChar((char)0);
            newpasswordTF.requestFocusInWindow();
        } else {
            showpasswordcharNEW();
            newpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCBnewActionPerformed

    private void newpasswordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newpasswordTFActionPerformed
    }//GEN-LAST:event_newpasswordTFActionPerformed

    private void confirmnewpasswordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmnewpasswordTFActionPerformed
    }//GEN-LAST:event_confirmnewpasswordTFActionPerformed

    private void confirmnewpasswordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmnewpasswordTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            showpasswordcharOLD_ClickLogin();
            showpasswordcharNEW_ClickLogin();
            showpasswordcharNEWCONFIRM_ClickLogin();
            showpinchar_ClickLogin();
            resetpasswordBTN.requestFocusInWindow();
            resetpasswordBTN.doClick();
        }
    }//GEN-LAST:event_confirmnewpasswordTFKeyPressed

    private void confirmnewpasswordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmnewpasswordTFKeyReleased
    }//GEN-LAST:event_confirmnewpasswordTFKeyReleased

    private void showpasswordCBconfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCBconfirmActionPerformed
        if(showpasswordCBconfirm.isSelected()) {
            confirmnewpasswordTF.setEchoChar((char)0);
            confirmnewpasswordTF.requestFocusInWindow();
        } else {
            showpasswordcharNEWCONFIRM();
            confirmnewpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCBconfirmActionPerformed

    private void fullnameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameTFActionPerformed

    private void fullnameTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullnameTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            pincodeTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_fullnameTFKeyPressed

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

    private void clearBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBTNMousePressed
        clearText();
    }//GEN-LAST:event_clearBTNMousePressed

    private void newpasswordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newpasswordTFKeyTyped

    }//GEN-LAST:event_newpasswordTFKeyTyped

    private void confirmnewpasswordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmnewpasswordTFKeyTyped

    }//GEN-LAST:event_confirmnewpasswordTFKeyTyped

    private void oldpasswordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oldpasswordTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oldpasswordTFActionPerformed

    private void oldpasswordTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldpasswordTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            newpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_oldpasswordTFKeyPressed

    private void oldpasswordTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldpasswordTFKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_oldpasswordTFKeyReleased

    private void oldpasswordTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldpasswordTFKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_oldpasswordTFKeyTyped

    private void showpasswordCBoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordCBoldActionPerformed
        if(showpasswordCBold.isSelected()) {
            oldpasswordTF.setEchoChar((char)0);
            oldpasswordTF.requestFocusInWindow();
        } else {
            showpasswordcharOLD();
            oldpasswordTF.requestFocusInWindow();
        }
    }//GEN-LAST:event_showpasswordCBoldActionPerformed

    private void pincodeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pincodeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pincodeTFActionPerformed

    private void pincodeTFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pincodeTFKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            oldpasswordTF.requestFocusInWindow();
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

    private void resetpasswordBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetpasswordBTNMousePressed

    }//GEN-LAST:event_resetpasswordBTNMousePressed

    private void guiTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiTitleMouseClicked
        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuBack2Login = new JMenuItem("Back To Login Menu");
        final JMenuItem menuClose = new JMenuItem("Close Permanently");
        final JMenuItem menuRestore = new JMenuItem("Restore to Default Location");
        
        JLabel ShortcutsLBL = new JLabel();
        ShortcutsLBL.setText(" Shortcuts");
        ShortcutsLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); 
       
        if(SwingUtilities.isRightMouseButton(evt)) {

        //------------------------------------------//
        //restore
        ActionListener restoreActionListener =
            (ActionEvent e) -> {
                this.setLocationRelativeTo(null);
        };
        menuRestore.addActionListener(restoreActionListener);
            
        //------------------------------------------//
        //minimize
        ActionListener minimizeActionListener =
            (ActionEvent e) -> {
                ResetPasswordFrame.this.setState(Frame.ICONIFIED);
        };
        menuMinimize.addActionListener(minimizeActionListener);
        
        //------------------------------------------//
        //close taskbar
        ActionListener closeActionListener =
            (ActionEvent e) -> {
                System.exit(0);
        };
        menuClose.addActionListener(closeActionListener);        
 
        //------------------------------------------//
        //back2login taskbar
        ActionListener loginActionListener =
            (ActionEvent e) -> {
                LoginFrame login;
            try {
                login = new LoginFrame();
                login.setVisible(true);
                this.dispose();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        menuBack2Login.addActionListener(loginActionListener);
        
        //----------------------------------------------------------//
        //set jmenus cursor
        menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBack2Login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        //set popup background
        popupRightClick.setBackground(new Color(249, 250, 253));
        
        //add popup on click
        popupRightClick.removeAll();
        popupRightClick.add(menuRestore);
        popupRightClick.add(menuBack2Login);
        popupRightClick.addSeparator();
        popupRightClick.add(ShortcutsLBL);
        popupRightClick.add(menuMinimize);
        popupRightClick.add(menuClose);
        popupRightClick.updateUI();
        
        //show popup on right click.
        //popupRightClick.show(pnlTop, evt.getXOnScreen(), evt.getYOnScreen()/3);
        popupRightClick.show(guiTitle, evt.getX(), evt.getY()/3);
        }
    }//GEN-LAST:event_guiTitleMouseClicked

    private void pnlTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopMouseClicked
        //for SHORTCUTS
        final JPopupMenu popupRightClick = new JPopupMenu();
        final JMenuItem menuMinimize = new JMenuItem("Minimize");
        final JMenuItem menuBack2Login = new JMenuItem("Back To Login Menu");
        final JMenuItem menuClose = new JMenuItem("Close Permanently");
        final JMenuItem menuRestore = new JMenuItem("Restore to Default Location");
        
        JLabel ShortcutsLBL = new JLabel();
        ShortcutsLBL.setText(" Shortcuts");
        ShortcutsLBL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); 
       
        if(SwingUtilities.isRightMouseButton(evt)) {

        //------------------------------------------//
        //restore
        ActionListener restoreActionListener =
            (ActionEvent e) -> {
                this.setLocationRelativeTo(null);
        };
        menuRestore.addActionListener(restoreActionListener);
            
        //------------------------------------------//
        //minimize
        ActionListener minimizeActionListener =
            (ActionEvent e) -> {
                ResetPasswordFrame.this.setState(Frame.ICONIFIED);
        };
        menuMinimize.addActionListener(minimizeActionListener);
        
        //------------------------------------------//
        //close taskbar
        ActionListener closeActionListener =
            (ActionEvent e) -> {
                System.exit(0);
        };
        menuClose.addActionListener(closeActionListener);        
 
        //------------------------------------------//
        //back2login taskbar
        ActionListener loginActionListener =
            (ActionEvent e) -> {
                LoginFrame login;
            try {
                login = new LoginFrame();
                login.setVisible(true);
                this.dispose();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        menuBack2Login.addActionListener(loginActionListener);
        
        //----------------------------------------------------------//
        //set jmenus cursor
        menuRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBack2Login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        //set popup background
        popupRightClick.setBackground(new Color(249, 250, 253));
        
        //add popup on click
        popupRightClick.removeAll();
        popupRightClick.add(menuRestore);
        popupRightClick.add(menuBack2Login);
        popupRightClick.addSeparator();
        popupRightClick.add(ShortcutsLBL);
        popupRightClick.add(menuMinimize);
        popupRightClick.add(menuClose);
        popupRightClick.updateUI();
        
        //show popup on right click.
        //popupRightClick.show(pnlTop, evt.getXOnScreen(), evt.getYOnScreen()/3);
        popupRightClick.show(pnlTop, evt.getX(), evt.getY()/3);
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
                new ResetPasswordFrame().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(ResetPasswordFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle backBTN;
    private javax.swing.JLabel clearBTN;
    private javax.swing.JPasswordField confirmnewpasswordTF;
    private javax.swing.JLabel confirmpasswordLABEL;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel fullnameLABEL;
    public javax.swing.JTextField fullnameTF;
    private javax.swing.JLabel guiTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JButton minimizeButton;
    private javax.swing.JLabel newpasswordLABEL;
    private javax.swing.JPasswordField newpasswordTF;
    private javax.swing.JLabel oldpasswordLABEL;
    private javax.swing.JPasswordField oldpasswordTF;
    private javax.swing.JLabel pincodeLABEL;
    private javax.swing.JPasswordField pincodeTF;
    private javax.swing.JPanel pnlMAIN;
    private javax.swing.JPanel pnlTop;
    private rojerusan.RSMaterialButtonRectangle resetpasswordBTN;
    private javax.swing.JCheckBox showpasswordCBconfirm;
    private javax.swing.JCheckBox showpasswordCBnew;
    private javax.swing.JCheckBox showpasswordCBold;
    private javax.swing.JCheckBox showpincodeCB;
    private javax.swing.JLabel txt_emp;
    // End of variables declaration//GEN-END:variables
}
// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //