// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_EmployeeManager;

import SystemDB.DBconnection;
import MainPackage.ToastManager;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class UpdateSalaryGUI extends javax.swing.JDialog {
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
    
    //for money currency
    String pesoSignString; ///this string will get DATA from db
    
    //for TOAST
    final String printwait = "Testing...";
    final Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    final int widthvarToast = this.getSize().width;
    final int heightvatToast = this.getSize().height;
    final int xPosToast = (dimToast.width-widthvarToast)/2;
    final int yPosToast = (dimToast.width-heightvatToast)/2;
    final ToastManager printToast = new ToastManager(printwait, xPosToast, yPosToast);
    /**
     * Creates new form AddEmployeeGUI
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public UpdateSalaryGUI() throws SQLException, IOException {
        initComponents();
        //connection to database
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, 
        size.height/2 - getHeight()/2);
        
        this.getRootPane().setBorder(new LineBorder(new Color(0,102,204)));
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        this.setModal(true); //this.setAlwaysOnTop(true);
        txt_search.setDocument(new JTextFieldLimitAPI(10));
        txt_emp.setText(String.valueOf(Emp.empId));
        
        noCopyPaste();
        GUINaming_DATA();
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
    
    public void clearall() {
        txt_amountinsert.setText("");
        txt_dept.setText("");
        txt_dob.setText("");
        txt_emp.setText("");
        txt_empid.setText("");
        txt_firstname.setText("");
        txt_percentage.setText("");
        txt_basic_salary.setText("");
        txt_surname.setText("");
        txt_search.setText("");
        txt_search.requestFocusInWindow();
        
        r_amount.setSelected(false);
        r_amount.setEnabled(false);
        txt_amountinsert.setEnabled(false);
        
        r_percentage.setSelected(false);
        r_percentage.setEnabled(false);
        txt_percentage.setEnabled(false);
        
        updateBTN.setEnabled(false);
        calculateBTN.setEnabled(false);
        
        lbl_computed_total.setText("0.00");
        lbl_computed_salary.setText("0.00");
        
        txt_percentage.setText("0");
        txt_amountinsert.setText("0");
    }
    
    public void noCopyPaste() {
        txt_percentage.setTransferHandler(null);
        txt_amountinsert.setTransferHandler(null);
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
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        searchempBTN = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txt_empid = new javax.swing.JTextField();
        txt_dob = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_basic_salary = new javax.swing.JTextField();
        txt_dept = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_firstname = new javax.swing.JTextField();
        r_percentage = new javax.swing.JRadioButton();
        r_amount = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txt_percentage = new javax.swing.JTextField();
        txt_amountinsert = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        updateBTN = new javax.swing.JButton();
        txt_emp = new javax.swing.JLabel();
        lbl_computed_salary = new javax.swing.JLabel();
        lbl_computed_total = new javax.swing.JLabel();
        calculateBTN = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
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
        lblTitle.setText("PAYROLL SYSTEM | SALARY");
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

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, -1));

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPDATE EMPLOYEE SALARY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setPreferredSize(new java.awt.Dimension(560, 340));
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(249, 250, 253));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setText("Search Employee ID:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, 20));

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
        jPanel4.add(searchempBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 20, 25, 20));

        txt_search.setBackground(new java.awt.Color(249, 250, 253));
        txt_search.setOpaque(false);
        txt_search.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                txt_searchComponentRemoved(evt);
            }
        });
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_searchKeyTyped(evt);
            }
        });
        jPanel4.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 390, -1));

        viewpanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_empid.setEditable(false);
        txt_empid.setBackground(new java.awt.Color(249, 250, 253));
        txt_empid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_empidActionPerformed(evt);
            }
        });
        jPanel1.add(txt_empid, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 19, 160, -1));

        txt_dob.setEditable(false);
        txt_dob.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 19, 160, -1));

        jLabel3.setText("Date of Birth :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 19, -1, -1));

        jLabel2.setText("Surname :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, -1, -1));

        jLabel1.setText("First name :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 46, -1, -1));

        jLabel5.setText("Employee id :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 22, -1, -1));

        jLabel12.setText("Basic Salary :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 48, -1, -1));

        txt_basic_salary.setEditable(false);
        txt_basic_salary.setBackground(new java.awt.Color(249, 250, 253));
        txt_basic_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_basic_salaryActionPerformed(evt);
            }
        });
        jPanel1.add(txt_basic_salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 45, 160, -1));

        txt_dept.setEditable(false);
        txt_dept.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.add(txt_dept, new org.netbeans.lib.awtextra.AbsoluteConstraints(351, 71, 161, -1));

        txt_surname.setEditable(false);
        txt_surname.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.add(txt_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 69, 160, -1));

        jLabel9.setText("Department :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 74, -1, -1));

        txt_firstname.setEditable(false);
        txt_firstname.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.add(txt_firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 43, 160, -1));

        r_percentage.setBackground(new java.awt.Color(249, 250, 253));
        r_percentage.setText("Percentage (%)");
        r_percentage.setEnabled(false);
        r_percentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_percentageActionPerformed(evt);
            }
        });
        jPanel1.add(r_percentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 109, -1, -1));

        r_amount.setBackground(new java.awt.Color(249, 250, 253));
        r_amount.setText("Amount");
        r_amount.setEnabled(false);
        r_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_amountActionPerformed(evt);
            }
        });
        jPanel1.add(r_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 109, -1, -1));

        jLabel4.setText("Update Salary by :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 113, -1, -1));

        txt_percentage.setEditable(false);
        txt_percentage.setBackground(new java.awt.Color(249, 250, 253));
        txt_percentage.setText("0");
        txt_percentage.setEnabled(false);
        txt_percentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_percentageActionPerformed(evt);
            }
        });
        txt_percentage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_percentageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_percentageKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_percentageKeyTyped(evt);
            }
        });
        jPanel1.add(txt_percentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 95, -1));

        txt_amountinsert.setEditable(false);
        txt_amountinsert.setBackground(new java.awt.Color(249, 250, 253));
        txt_amountinsert.setText("0");
        txt_amountinsert.setEnabled(false);
        txt_amountinsert.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_amountinsertKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_amountinsertKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_amountinsertKeyTyped(evt);
            }
        });
        jPanel1.add(txt_amountinsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 95, -1));

        jLabel6.setText("Percentage :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 20));

        jLabel7.setText("Amount:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 50, 20));

        updateBTN.setBackground(new java.awt.Color(249, 250, 253));
        updateBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateBTN.setText("Update");
        updateBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBTN.setDefaultCapable(false);
        updateBTN.setEnabled(false);
        updateBTN.setFocusPainted(false);
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });
        jPanel1.add(updateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 80, 30));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setText("emp");
        jPanel1.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 80, -1));

        lbl_computed_salary.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_computed_salary.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_computed_salary.setText("0.00");
        jPanel1.add(lbl_computed_salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 100, 20));

        lbl_computed_total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_computed_total.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_computed_total.setText("0.00");
        jPanel1.add(lbl_computed_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 100, 20));

        calculateBTN.setBackground(new java.awt.Color(249, 250, 253));
        calculateBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        calculateBTN.setText("Calculate");
        calculateBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        calculateBTN.setEnabled(false);
        calculateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBTNActionPerformed(evt);
            }
        });
        jPanel1.add(calculateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 90, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Salary after Update:  ₱");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Total Basic Salary:     ₱");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, 20));

        clearBTN.setBackground(new java.awt.Color(249, 250, 253));
        clearBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBTN.setText("Clear");
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        jPanel1.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 70, 30));

        viewpanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 580, 210));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 580, 260));

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 600, 310));

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

    private void txt_searchComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_searchComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchComponentRemoved

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased

    }//GEN-LAST:event_txt_searchKeyReleased

    private void txt_basic_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_basic_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_basic_salaryActionPerformed

    private void r_percentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_percentageActionPerformed
        r_percentage.setSelected(true);
        r_amount.setSelected(false);
        //r_amount.setEnabled(false);
        txt_amountinsert.setEnabled(false);
        txt_percentage.setEditable(true);
        txt_percentage.setEnabled(true);
        txt_percentage.setText("");
        txt_percentage.requestFocusInWindow();
        txt_amountinsert.setText("0");
        
        calculateBTN.setEnabled(true);
        updateBTN.setEnabled(false);
        
        lbl_computed_salary.setText("0.00");
    }//GEN-LAST:event_r_percentageActionPerformed

    private void r_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_amountActionPerformed
        r_amount.setSelected(true);
        r_percentage.setSelected(false);
        //r_percentage.setEnabled(false);
        txt_percentage.setEnabled(false);
        txt_amountinsert.setEditable(true);
        txt_amountinsert.setEnabled(true);
        txt_amountinsert.setText("");
        txt_amountinsert.requestFocusInWindow();
        txt_percentage.setText("0");
        
        calculateBTN.setEnabled(true);
        updateBTN.setEnabled(false);
        
        lbl_computed_salary.setText("0.00");
    }//GEN-LAST:event_r_amountActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        int currentsalary = Integer.parseInt(txt_basic_salary.getText());
        int currenttotal = Integer.parseInt(lbl_computed_salary.getText());
        String currentsalaryString = String.valueOf(currentsalary);
        String currenttotalString = String.valueOf(currenttotal);
        
        if(currentsalaryString.equals(currenttotalString)) {
            JOptionPane.showMessageDialog(null, "<html><center>Same numeric data on the total of salary and salary after update is not allowed. <br>Please Try Again!</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            if(r_amount.isSelected()==true) {
                txt_amountinsert.requestFocusInWindow();
            }
            if(r_percentage.isSelected()==true) {
                txt_percentage.requestFocusInWindow();
            }
            
        } else {
        
        int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to update salary?", mainnameString,JOptionPane.YES_NO_OPTION);
        if(p==0){
            int salary = Integer.parseInt(txt_basic_salary.getText());

            if(r_percentage.isSelected()== true){
                int getPercentage = Integer.parseInt(txt_percentage.getText());
                int calcPercentage = salary /100 * getPercentage + salary;
                String xP = String.valueOf(calcPercentage);
                lbl_computed_salary.setText(xP);
            }

            else if(r_amount.isSelected()==true){
                int getAmt = Integer.parseInt(txt_amountinsert.getText());
                int calcAmount = salary + getAmt;
                String xA = String.valueOf(calcAmount);
                lbl_computed_salary.setText(xA);
            }
            try{
                Date currentDate = GregorianCalendar.getInstance().getTime();
                DateFormat df = DateFormat.getDateInstance();
                String dateString = df.format(currentDate);

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
                String timeString = sdf.format(d);

                String value0 = timeString;
                String values = dateString;
                String val = txt_emp.getText();

                String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+values+"','Salary of Employee #"+txt_empid+" is Updated by: "+val+"')";
                pst=conn.prepareStatement(reg);
                pst.execute();
            }
            catch (SQLException e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
            try{

                String value1 = txt_empid.getText();
                String value2 = lbl_computed_salary.getText();

                String sql= "update EmployeesRecord set id='"+value1+"',Salary='"+value2+"' where id='"+value1+"'";

                pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null,"Record Updated", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);    
                //clearall();

            }catch(HeadlessException | SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
            finally {

                try{
                    rs.close();
                    pst.close();
                    clearall();
                }
                catch(SQLException e){

                }
            }
        }
        }

    }//GEN-LAST:event_updateBTNActionPerformed

    private void searchempBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchempBTNActionPerformed
        try { 

            if(txt_search.getText().isEmpty()) {
                clearall();
                //JOptionPane.showMessageDialog(null,"Search field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            }
                
            String sql ="select * from EmployeesRecord where id=? ";
                
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_search.getText());
            rs=pst.executeQuery();
            
                try {
                    if (rs.next()) {

            String add1 =rs.getString("id");
            txt_empid.setText(add1);

            String add2 =rs.getString("first_name");
            txt_firstname.setText(add2);

            String add3 =rs.getString("surname");
            txt_surname.setText(add3);

            String add4 =rs.getString("Dob");
            txt_dob.setText(add4);

            String add5 =rs.getString("Salary");
            txt_basic_salary.setText(add5);
            lbl_computed_total.setText(add5);

            String add8 =rs.getString("Department");
            txt_dept.setText(add8);

            rs.close();
            pst.close();

            calculateBTN.setEnabled(false);
            r_amount.setEnabled(true);
            r_percentage.setEnabled(true);
                        
            } else {
                JOptionPane.showMessageDialog(null,"Employee not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                clearall();
                        
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
            //resetTable();
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void calculateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBTNActionPerformed
        // TODO add your handling code here:

        if(txt_amountinsert.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Amount field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            txt_amountinsert.requestFocusInWindow();

        } else if(txt_percentage.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Percentage field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            txt_percentage.requestFocusInWindow();

        } else {
            int salary = Integer.parseInt(txt_basic_salary.getText());

            if(r_percentage.isSelected()== true) {
                int currentsalary1 = Integer.parseInt(txt_basic_salary.getText());
                int getPercentage = Integer.parseInt(txt_percentage.getText());
                int calcPercentage = salary /100 * getPercentage + salary;
                String amount1 = String.valueOf(calcPercentage);
                
                lbl_computed_total.setText(String.valueOf(currentsalary1));
                lbl_computed_salary.setText(amount1);
                updateBTN.setEnabled(true);
            }
            
            if(r_amount.isSelected()==true) {
                int currentsalary2 = Integer.parseInt(txt_basic_salary.getText());
                int getAmt = Integer.parseInt(txt_amountinsert.getText());
                int calcAmount = salary + getAmt;
                String amount2 = String.valueOf(calcAmount);
                
                lbl_computed_total.setText(String.valueOf(currentsalary2));
                lbl_computed_salary.setText(amount2);
                updateBTN.setEnabled(true);
                
            }
        }
    }//GEN-LAST:event_calculateBTNActionPerformed

    private void txt_percentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_percentageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_percentageActionPerformed

    private void txt_percentageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_percentageKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            calculateBTN.requestFocusInWindow();
            calculateBTN.doClick();
            txt_percentage.requestFocusInWindow();
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_computed_salary.setText("0.00");
            updateBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_percentageKeyPressed

    private void txt_amountinsertKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_amountinsertKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            calculateBTN.requestFocusInWindow();
            calculateBTN.doClick();
            txt_amountinsert.requestFocusInWindow();
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_computed_salary.setText("0.00");
            updateBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_amountinsertKeyPressed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        clearall();
    }//GEN-LAST:event_clearBTNActionPerformed

    private void txt_percentageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_percentageKeyTyped
        try {
            //numbers only.
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
                //JOptionPane.showMessageDialog(null, "<html><center>You must enter numeric values (01-100) only!</html></center>", mainErrorString, JOptionPane.ERROR_MESSAGE);
                
            // if on the text field the numbers are bigger than 100, consumes the last number typed    
            } else if (Integer.parseInt(txt_percentage.getText() + c) > 100) {
                evt.consume();
                txt_percentage.requestFocusInWindow();
            }
        } catch (NumberFormatException e) {
        }
       
        //limit the number length
        String l = txt_percentage.getText();
            if (!(l.length()<3))
            {
            evt.consume();
        }
    }//GEN-LAST:event_txt_percentageKeyTyped

    private void txt_amountinsertKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_amountinsertKeyTyped
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
       
        //limit the number length
        String l = txt_amountinsert.getText();
            if (!(l.length()<6)) {
                evt.consume();
        }
    }//GEN-LAST:event_txt_amountinsertKeyTyped

    private void txt_amountinsertKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_amountinsertKeyReleased
        if (txt_amountinsert.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_amountinsertKeyReleased

    private void txt_percentageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_percentageKeyReleased
        if (txt_percentage.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_percentageKeyReleased

    private void txt_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyTyped
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
    }//GEN-LAST:event_txt_searchKeyTyped

    private void txt_empidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empidActionPerformed

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
                new UpdateSalaryGUI().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(UpdateSalaryGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lbl_computed_salary;
    private javax.swing.JLabel lbl_computed_total;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JRadioButton r_amount;
    private javax.swing.JRadioButton r_percentage;
    private javax.swing.JButton searchempBTN;
    private javax.swing.JTextField txt_amountinsert;
    private javax.swing.JTextField txt_basic_salary;
    private javax.swing.JTextField txt_dept;
    private javax.swing.JTextField txt_dob;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JTextField txt_empid;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_percentage;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JButton updateBTN;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

}
