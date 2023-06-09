// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_EmployeeManager;

import SystemDB.DBconnection;
import MainPackage.ToastManager;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class EmployeeDeductionGUI extends javax.swing.JDialog {
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
    
    //row checker int
    int rowchecker;
    
    /**
     * Creates new form AddEmployeeGUI
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public EmployeeDeductionGUI() throws SQLException, IOException {
        initComponents();
        //connection to database
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, 
        size.height/2 - getHeight()/2);
        
        lbl_emp.setText(String.valueOf(Emp.empId));
        
        this.getRootPane().setBorder(new LineBorder(new Color(0,102,204)));
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        this.setModal(true); //this.setAlwaysOnTop(true);
        txt_search.setDocument(new JTextFieldLimitAPI(10));
        GUINaming_DATA();
        noCopyPaste();
        //no icon in taskbar
        //this.setType(javax.swing.JFrame.Type.UTILITY);
        
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

        setCellsAlignmentToCenter();
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
                jLabel5.setText("Total Deduction: "+pesoSignString);
                jLabel2.setText("Salary after deduction: "+pesoSignString);
                        
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
        txt_id.setText("");
        txt_firstname.setText("");
        txt_surname.setText("");
        txt_dob.setText("");
        txt_dep.setText("");
        txt_status.setText("");
        txt_salary.setText("");
        txt_desig.setText("");
        txt_job.setText("");
        txt_doj.setText("");
        lbl_total.setText("0.00");
        txt_percentage.setText("0");
        txt_amountinsert.setText("0");
        txt_reason.setText("");
        txt_search.setText("");
        lbl_salary_after.setText("0.00");
               
        r_amount.setSelected(false);
        r_amount.setEnabled(false);
        txt_amountinsert.setEnabled(false);
        
        r_percentage.setSelected(false);
        r_percentage.setEnabled(false);
        txt_percentage.setEnabled(false);
        
        txt_search.requestFocusInWindow();
        
        resetTable();
        
        refreshtableBTN.setEnabled(false);
        saveBTN.setEnabled(false);
        calculateBTN.setEnabled(false);
        
        viewScroll.getViewport().setViewPosition(new Point(0,0));
        tableScroll.getViewport().setViewPosition(new Point(0,0));
        
        removerowBTN.setEnabled(false);
    }
  
    public void noCopyPaste() {
        txt_percentage.setTransferHandler(null);
        txt_amountinsert.setTransferHandler(null);
    }
    
    public void setCellsAlignmentToCenter() {
        
        empdeducionTable.setDefaultEditor(Object.class, null); //<-- Disable editing/inserting data in JTable 
        
        DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment(JLabel.CENTER);

        empdeducionTable.getColumnModel().getColumn(0).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(1).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(2).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(3).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(4).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(5).setCellRenderer(rendar);
        empdeducionTable.getColumnModel().getColumn(6).setCellRenderer(rendar);
    }
    
    public void resetTable() {
        empdeducionTable.setModel(new DefaultTableModel(null, new String []{"EMP ID#", "COUNT", "FIRST NAME", "SURNAME", "BASIC SALARY", "DEDUCTION AMOUNT", "DEDUCTION REASON"}));
        empdeducionTable.getColumn("EMP ID#").setPreferredWidth(70);
        empdeducionTable.getColumn("COUNT").setPreferredWidth(70);
        empdeducionTable.getColumn("FIRST NAME").setPreferredWidth(150);
        empdeducionTable.getColumn("SURNAME").setPreferredWidth(150);
        empdeducionTable.getColumn("BASIC SALARY").setPreferredWidth(150);
        empdeducionTable.getColumn("DEDUCTION AMOUNT").setPreferredWidth(200);
        empdeducionTable.getColumn("DEDUCTION REASON").setPreferredWidth(250);
    }
    
    public void refreshemployeededucTable() {
        setCellsAlignmentToCenter();
        resetTable();
        //autorefresh();
        try {
            DefaultTableModel tb = (DefaultTableModel)empdeducionTable.getModel();
            
            ResultSet rtable;
            try (Statement sttable = conn.createStatement()) {
                rtable = sttable.executeQuery("select * from Deductions where emp_id like '"+'%'+txt_id.getText()+'%'+"'");
                while(rtable.next()) {
                    
                    Vector v=new Vector();
                    
                    String EID1 =(rtable.getString("emp_id"));
                    String ID1 =(rtable.getString("id"));
                    String FIRSTNAME1 =(rtable.getString("firstname"));
                    String SURNAME1 =(rtable.getString("surname"));
                    String SALARY1 =(rtable.getString("salary"));
                    String DEDUCTIONAMOUNT1 =(rtable.getString("deduction_amount"));
                    String DEDUCTIONREASON1 =(rtable.getString("deduction_reason"));
                    
                    v.add(EID1);
                    v.add(ID1);
                    v.add(FIRSTNAME1);
                    v.add(SURNAME1);
                    v.add(SALARY1);
                    v.add(DEDUCTIONAMOUNT1);
                    v.add(DEDUCTIONREASON1);
                    
                    tb.addRow(v);
                    
                    sttable.close();
                }
            }
            rtable.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"<html><center>TABLE ERROR 404<br>ERR: "+e+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);            
            
        }
        
    }
    
    public void defaultTable() {
        resetTable();
        setCellsAlignmentToCenter();
        empdeducionTable.removeAll();
    }
    
    public void auditdeletedataFromTable() {
        Date currentDate = GregorianCalendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance();
        String dateString = df.format(currentDate);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        String timeString = sdf.format(d);

        String value0 = timeString;
        String value1 = dateString;
        String val = lbl_emp.getText();
        try {
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Deduction row count #"+rowchecker+" of Employee #"+txt_id.getText()+" is Deleted by: "+val+"')";
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

        pnlTop = new javax.swing.JPanel();
        pnlActions = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        pnlTitle = new javax.swing.JPanel();
        lblTitleIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        viewScroll = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_firstname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_surname = new javax.swing.JTextField();
        txt_dob = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_dep = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_desig = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_doj = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_job = new javax.swing.JTextField();
        txt_salary = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        r_percentage = new javax.swing.JRadioButton();
        r_amount = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        lbl_salary_after = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_amountinsert = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_percentage = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_reason = new javax.swing.JTextField();
        calculateBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();
        saveBTN = new javax.swing.JButton();
        lbl_emp = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        searchempBTN = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        tableScroll = new javax.swing.JScrollPane();
        empdeducionTable = new javax.swing.JTable();
        refreshtableBTN = new javax.swing.JButton();
        removerowBTN = new javax.swing.JButton();

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
        lblTitle.setText("PAYROLL SYSTEM | MANAGE DEDUCTION");
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

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, -1));

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANAGE EMPLOYEE DEDUCTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewScroll.setBackground(new java.awt.Color(249, 250, 253));
        viewScroll.setBorder(null);
        viewScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Employee id :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 19, -1, 20));

        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 19, 177, -1));

        txt_firstname.setEditable(false);
        txt_firstname.setBackground(new java.awt.Color(249, 250, 253));
        txt_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_firstnameActionPerformed(evt);
            }
        });
        jPanel3.add(txt_firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 46, 177, -1));

        jLabel4.setText("First name :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 49, -1, -1));

        jLabel7.setText("Surname :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 79, -1, -1));

        txt_surname.setEditable(false);
        txt_surname.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 76, 177, -1));

        txt_dob.setEditable(false);
        txt_dob.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 109, 177, -1));

        jLabel8.setText("Date of Birth :");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 112, -1, -1));

        jLabel9.setText("Department :");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 143, -1, -1));

        txt_dep.setEditable(false);
        txt_dep.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 140, 177, -1));

        jLabel17.setText("Designation :");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 22, -1, -1));

        txt_desig.setEditable(false);
        txt_desig.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_desig, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 19, 177, -1));

        txt_status.setEditable(false);
        txt_status.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 50, 177, -1));

        jLabel18.setText("Status :");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 53, -1, -1));

        jLabel19.setText("Date Hired :");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 79, -1, -1));

        txt_doj.setEditable(false);
        txt_doj.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_doj, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 76, 177, -1));

        jLabel20.setText("Job Title :");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 112, -1, -1));

        txt_job.setEditable(false);
        txt_job.setBackground(new java.awt.Color(249, 250, 253));
        txt_job.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jobActionPerformed(evt);
            }
        });
        jPanel3.add(txt_job, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 109, 177, -1));

        txt_salary.setEditable(false);
        txt_salary.setBackground(new java.awt.Color(249, 250, 253));
        txt_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_salaryActionPerformed(evt);
            }
        });
        jPanel3.add(txt_salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 140, 177, -1));

        jLabel13.setText("Basic Salary :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 143, -1, -1));

        jLabel12.setText("Deduct Salary by :");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 182, -1, -1));

        r_percentage.setBackground(new java.awt.Color(249, 250, 253));
        r_percentage.setText("Percentage (%)");
        r_percentage.setEnabled(false);
        r_percentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_percentageActionPerformed(evt);
            }
        });
        jPanel3.add(r_percentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 178, -1, -1));

        r_amount.setBackground(new java.awt.Color(249, 250, 253));
        r_amount.setText("Amount");
        r_amount.setEnabled(false);
        r_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_amountActionPerformed(evt);
            }
        });
        jPanel3.add(r_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 178, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Total Deduction: ₱");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 150, -1));

        lbl_total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_total.setText("0.00");
        jPanel3.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 220, 110, -1));

        lbl_salary_after.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_salary_after.setText("0.00");
        jPanel3.add(lbl_salary_after, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 250, 110, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Salary after deduction: ₱");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 160, -1));

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
        jPanel3.add(txt_amountinsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 219, 95, -1));

        jLabel15.setText("Amount :");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 222, 50, -1));

        txt_percentage.setEditable(false);
        txt_percentage.setBackground(new java.awt.Color(249, 250, 253));
        txt_percentage.setText("0");
        txt_percentage.setEnabled(false);
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
        jPanel3.add(txt_percentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 219, 95, -1));

        jLabel14.setText("Percentage :");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 222, -1, -1));

        jLabel1.setText("Reason:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, -1, -1));

        txt_reason.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.add(txt_reason, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 257, 263, -1));

        calculateBTN.setBackground(new java.awt.Color(249, 250, 253));
        calculateBTN.setText("Calculate");
        calculateBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        calculateBTN.setEnabled(false);
        calculateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBTNActionPerformed(evt);
            }
        });
        jPanel3.add(calculateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 166, 36));

        clearBTN.setBackground(new java.awt.Color(249, 250, 253));
        clearBTN.setText("Clear");
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        jPanel3.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, 168, 36));

        saveBTN.setBackground(new java.awt.Color(249, 250, 253));
        saveBTN.setText("Save");
        saveBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBTN.setEnabled(false);
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTNActionPerformed(evt);
            }
        });
        jPanel3.add(saveBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 166, 36));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 600, -1));

        lbl_emp.setForeground(new java.awt.Color(253, 253, 253));
        lbl_emp.setText("emp");
        jPanel1.add(lbl_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 790, 150, 20));

        jLabel11.setText("Search Employee ID :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

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
        jPanel1.add(searchempBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 25, 20));

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
        jPanel1.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 140, -1));

        tableScroll.setBackground(new java.awt.Color(249, 250, 253));

        empdeducionTable.setBackground(new java.awt.Color(249, 250, 253));
        empdeducionTable.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        empdeducionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EMP ID#", "COUNT", "FIRST NAME", "SURNAME", "BASIC SALARY", "DEDUCTION AMOUNT", "DEDUCTION REASON"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        empdeducionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        empdeducionTable.setSelectionForeground(new java.awt.Color(249, 250, 253));
        empdeducionTable.getTableHeader().setResizingAllowed(false);
        empdeducionTable.getTableHeader().setReorderingAllowed(false);
        tableScroll.setViewportView(empdeducionTable);
        if (empdeducionTable.getColumnModel().getColumnCount() > 0) {
            empdeducionTable.getColumnModel().getColumn(0).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(0).setPreferredWidth(70);
            empdeducionTable.getColumnModel().getColumn(1).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            empdeducionTable.getColumnModel().getColumn(2).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            empdeducionTable.getColumnModel().getColumn(3).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            empdeducionTable.getColumnModel().getColumn(4).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            empdeducionTable.getColumnModel().getColumn(5).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(5).setPreferredWidth(200);
            empdeducionTable.getColumnModel().getColumn(6).setResizable(false);
            empdeducionTable.getColumnModel().getColumn(6).setPreferredWidth(250);
        }

        jPanel1.add(tableScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 610, 290));

        refreshtableBTN.setBackground(new java.awt.Color(249, 250, 253));
        refreshtableBTN.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        refreshtableBTN.setText("Refresh Table");
        refreshtableBTN.setEnabled(false);
        refreshtableBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshtableBTNActionPerformed(evt);
            }
        });
        jPanel1.add(refreshtableBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 740, -1, -1));

        removerowBTN.setBackground(new java.awt.Color(249, 250, 253));
        removerowBTN.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        removerowBTN.setText("Remove this Row");
        removerowBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removerowBTN.setDefaultCapable(false);
        removerowBTN.setEnabled(false);
        removerowBTN.setFocusPainted(false);
        removerowBTN.setOpaque(false);
        removerowBTN.setPreferredSize(new java.awt.Dimension(105, 25));
        removerowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerowBTNActionPerformed(evt);
            }
        });
        jPanel1.add(removerowBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 740, 130, -1));

        viewScroll.setViewportView(jPanel1);

        mainpanel.add(viewScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 610, 480));

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 630, 530));

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

    private void txt_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_firstnameActionPerformed

    private void txt_jobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jobActionPerformed

    private void txt_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_salaryActionPerformed

    private void r_percentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_percentageActionPerformed
        // TODO add your handling code here:
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
        saveBTN.setEnabled(false);
    }//GEN-LAST:event_r_percentageActionPerformed

    private void r_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_amountActionPerformed
        // TODO add your handling code here:
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
        saveBTN.setEnabled(false);
    }//GEN-LAST:event_r_amountActionPerformed

    private void saveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTNActionPerformed
        // TODO add your handling code here:

        int currentsalary = Integer.parseInt(lbl_salary_after.getText());
        int currenttotal = Integer.parseInt(lbl_total.getText());
        String currentsalaryString = String.valueOf(currentsalary);
        String currenttotalString = String.valueOf(currenttotal);
        
        if (txt_id.getText().isEmpty() | txt_firstname.getText().isEmpty()  | txt_surname.getText().isEmpty() | txt_dob.getText().isEmpty() | txt_dep.getText().isEmpty()  | txt_desig.getText().isEmpty() | txt_status.getText().isEmpty() | txt_doj.getText().isEmpty()  | txt_job.getText().isEmpty() | txt_salary.getText().isEmpty()) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null,"One of the required field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null); 
            
        } else if(txt_reason.getText().isEmpty()) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null,"Reason field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null); 
            txt_reason.requestFocusInWindow();
            
        } else if(currenttotalString.equals("0") | (currenttotalString.equals("00") | (currenttotalString.equals("000") | (currenttotalString.equals("0000"))))) {
            JOptionPane.showMessageDialog(null, "<html><center>'0' data is not allowed. <br>Please Try Again!</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            if(r_amount.isSelected()==true) {
                txt_amountinsert.requestFocusInWindow();
            }
            if(r_percentage.isSelected()==true) {
                txt_percentage.requestFocusInWindow();
            }
            
            
        /*} else if(r_percentage.isSelected()==true) {
            if(txt_percentage.getText().isEmpty() | txt_percentage.getText().equals("0")) {
                JOptionPane.showMessageDialog(null,"Percentage field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null); 
                txt_percentage.requestFocusInWindow(); 

        } else if(r_amount2.isSelected()==true) {
            if(txt_amount_deduction.getText().isEmpty() | txt_amount_deduction.getText().equals("0")) {
                JOptionPane.showMessageDialog(null,"Amount field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null); 
                txt_amount_deduction.requestFocusInWindow(); 
            }*/
            
        } else {
        
        calculateBTN.doClick();
        int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to add record?", mainnameString,JOptionPane.YES_NO_OPTION);
        if(p==0){

            String value3 = lbl_emp.getText();
            try {

                String sql ="insert into Deductions (firstname,surname,salary,deduction_amount,deduction_reason,emp_id,made_by) values (?,?,?,?,?,?,'"+value3+"')";
                pst=conn.prepareStatement(sql);
                pst.setString(1,txt_firstname.getText());
                pst.setString(2,txt_surname.getText());
                pst.setString(3,txt_salary.getText());
                pst.setString(4,lbl_total.getText());
                pst.setString(5,txt_reason.getText());
                pst.setString(6,txt_id.getText());

                pst.execute();
                JOptionPane.showMessageDialog(null,"Data is saved successfully", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);    

            }
            catch (HeadlessException | SQLException e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
            try{
                Date currentDate = GregorianCalendar.getInstance().getTime();
                DateFormat df = DateFormat.getDateInstance();
                String dateString = df.format(currentDate);

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                String timeString = sdf.format(d);

                String value0 = timeString;
                String values = dateString;
                String val = lbl_emp.getText();

                String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+values+"','Deduction of Employee #"+txt_id.getText()+" is Updated by: "+val+"')";
                pst=conn.prepareStatement(reg);
                pst.execute();
                refreshtableBTN.doClick();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,e);
            } finally {
                try{
                    rs.close();
                    pst.close();
                    //clear();
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        }
        }
    }//GEN-LAST:event_saveBTNActionPerformed

    private void calculateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBTNActionPerformed
        // TODO add your handling code here:

        if(txt_amountinsert.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Amount field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);    
            txt_amountinsert.requestFocusInWindow();
            
        } else if(txt_percentage.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Percentage field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);    
            txt_percentage.requestFocusInWindow();
            
        } else {
        
        int salary = Integer.parseInt(txt_salary.getText());
        if(r_percentage.isSelected()== true){
            int percentage = Integer.parseInt(txt_percentage.getText());
            //calculate the total hours of overtime
            int total_percentage_deduction = salary /100 * percentage;
            String x = String.valueOf(total_percentage_deduction);
            int sal = salary - total_percentage_deduction;
            lbl_total.setText(x);
            lbl_salary_after.setText(String.valueOf(sal));
            saveBTN.setEnabled(true);

        }

        if(r_amount.isSelected()== true){
            int deduction = Integer.parseInt(txt_amountinsert.getText());
            //calculate the total hours of overtime
            int total_amount_deduction =  salary - deduction;
            String s = String.valueOf(total_amount_deduction);

            lbl_salary_after.setText(s);
            lbl_total.setText(String.valueOf(deduction));
            saveBTN.setEnabled(true);
        }
        }
    }//GEN-LAST:event_calculateBTNActionPerformed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        clearall();
    }//GEN-LAST:event_clearBTNActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchempBTN.doClick();
            
        } else if(txt_search.getText().isEmpty()|| evt.getKeyCode() == KeyEvent.VK_BACK_SPACE|| evt.getKeyCode() == KeyEvent.VK_DELETE) {
            clearall();
            resetTable();
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void searchempBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchempBTNActionPerformed
    
        try { 

            if(txt_search.getText().isEmpty()) {
                calculateBTN.setEnabled(false);
                defaultTable();
                setCellsAlignmentToCenter();
                refreshtableBTN.setEnabled(false);
            }
                
            String sql ="select * from EmployeesRecord where id=? ";
                
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_search.getText());
            rs=pst.executeQuery();
            
                try {
                    if (rs.next()) {
                        
                        String add1 =rs.getString("id");
                        txt_id.setText(add1);
                        
                        String add2 =rs.getString("first_name");
                        txt_firstname.setText(add2);
                        
                        String add3 =rs.getString("surname");
                        txt_surname.setText(add3);
                        
                        String add4 =rs.getString("Dob");
                        txt_dob.setText(add4);
                        
                        String add5 =rs.getString("Department");
                        txt_dep.setText(add5);
                        
                        String add7 =rs.getString("Salary");
                        txt_salary.setText(add7);
                        
                        String add8 =rs.getString("Status");
                        txt_status.setText(add8);
                        
                        String add9 =rs.getString("Date_hired");
                        txt_doj.setText(add9);
                        
                        String add10 =rs.getString("job_title");
                        txt_job.setText(add10);
                        
                        String add17 =rs.getString("Designation");
                        txt_desig.setText(add17);
                        
                        rs.close();
                        pst.close();

                        refreshemployeededucTable();
                        refreshtableBTN.doClick();
                        setCellsAlignmentToCenter();
                        refreshtableBTN.setEnabled(true);
                        r_amount.setEnabled(true);
                        r_percentage.setEnabled(true);
                        removerowBTN.setEnabled(true);
                        
                    } else {
                        JOptionPane.showMessageDialog(null,"Employee not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                        clearall();
                        defaultTable();
                        setCellsAlignmentToCenter();
                        refreshtableBTN.setEnabled(false);
                        
                    }   } catch (SQLException ex) {
                        Logger.getLogger(EmployeeDeductionGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDeductionGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_searchempBTNActionPerformed

    private void refreshtableBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshtableBTNActionPerformed
        refreshemployeededucTable();
        setCellsAlignmentToCenter();
        tableScroll.getViewport().setViewPosition(new Point(0,0));
    }//GEN-LAST:event_refreshtableBTNActionPerformed

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

    private void txt_amountinsertKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_amountinsertKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            calculateBTN.requestFocusInWindow();
            calculateBTN.doClick();
            txt_amountinsert.requestFocusInWindow();
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_salary_after.setText("0.00");
            lbl_total.setText("0.00");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_amountinsertKeyPressed

    private void txt_percentageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_percentageKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            calculateBTN.requestFocusInWindow();
            calculateBTN.doClick();
            txt_percentage.requestFocusInWindow();
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_salary_after.setText("0.00");
            lbl_total.setText("0.00");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_percentageKeyPressed

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

    private void removerowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerowBTNActionPerformed

        rowchecker = empdeducionTable.getSelectedRow();
        
        //row count is greater than 1;
        if(empdeducionTable.getModel().getRowCount()>=1) {
            removerowBTN.setEnabled(true);

            //no row is selected
            if(rowchecker == -1) {
                JOptionPane.showMessageDialog(null, "<html><center>No row is selected!</center></html>",mainErrorString, JOptionPane.ERROR_MESSAGE);

                //row is selected
            } else {
                int rowRemove = empdeducionTable.getSelectedRow();
                String cell = empdeducionTable.getModel().getValueAt(rowRemove, 1).toString();
                String sql = "DELETE FROM Deductions where id = "+cell+"";
                PreparedStatement pstRemoveRow = null;
                //ResultSet rsRemoveRow =null;
                try {
                    pstRemoveRow = conn.prepareStatement(sql);
                    pstRemoveRow.execute();
                    auditdeletedataFromTable();
                    JOptionPane.showMessageDialog(null,"Removed Succesfully!", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);
                    refreshtableBTN.doClick();
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    try {
                        pstRemoveRow.close();
                    } catch (SQLException er) {
                        System.out.println(er);
                    }
                }
            }

            //row count is lessthan 0
        } else if (empdeducionTable.getModel().getRowCount()<=0) {
            JOptionPane.showMessageDialog(null, "<html><center>Table is Empty.</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_removerowBTNActionPerformed

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
                new EmployeeDeductionGUI().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeDeductionGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JTable empdeducionTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lbl_emp;
    private javax.swing.JLabel lbl_salary_after;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JRadioButton r_amount;
    private javax.swing.JRadioButton r_percentage;
    private javax.swing.JButton refreshtableBTN;
    private javax.swing.JButton removerowBTN;
    private javax.swing.JButton saveBTN;
    private javax.swing.JButton searchempBTN;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JTextField txt_amountinsert;
    private javax.swing.JTextField txt_dep;
    private javax.swing.JTextField txt_desig;
    private javax.swing.JTextField txt_dob;
    private javax.swing.JTextField txt_doj;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_job;
    private javax.swing.JTextField txt_percentage;
    private javax.swing.JTextField txt_reason;
    private javax.swing.JTextField txt_salary;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JScrollPane viewScroll;
    // End of variables declaration//GEN-END:variables

}
