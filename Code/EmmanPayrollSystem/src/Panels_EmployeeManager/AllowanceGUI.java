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
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class AllowanceGUI extends javax.swing.JDialog {
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

    String OvertimeRateFromDB;
    
    String RPHRateFromDB;
    
    //row checker int
    int rowchecker;
    
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
    public AllowanceGUI() throws SQLException, IOException {
        initComponents();
        //connection to database
        DBconnection c=new DBconnection();
        conn= c.getconnection();
        
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, 
        size.height/2 - getHeight()/2);
        //txt_emp.setText(main.useronlineTF.getText());
        txt_emp.setText(String.valueOf(Emp.empId));
        
        this.getRootPane().setBorder(new LineBorder(new Color(0,102,204)));
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        this.setModal(true); //this.setAlwaysOnTop(true);
        txt_search.setDocument(new JTextFieldLimitAPI(10));
        txt_overtime.setDocument(new JTextFieldLimitAPI(6));
        txt_med.setDocument(new JTextFieldLimitAPI(6));
        txt_bonus.setDocument(new JTextFieldLimitAPI(6));
        txt_other.setDocument(new JTextFieldLimitAPI(6));
        
        //Update_table();
        clearall();
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
                jLabel10.setText("Total Amount:  "+pesoSignString);
                
                //set the Default Normal Popups Title Message
                mainnameString = rsGNaming.getString("PopupNormal");
                
                //set the Default Error Popups Title Message
                mainErrorString = rsGNaming.getString("PopupError");
                
                OvertimeRateFromDB = rsGNaming.getString("OvertimeRate");
                //overtimerateTF.setText(OvertimeRateFromDB);
                
                RPHRateFromDB = rsGNaming.getString("RPHRate");
                //rphrateTF.setText(RPHRateFromDB);
                
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

    private void Update_table() { 
        try {
            String sql ="select * from allowance";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            allowanceTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch(SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    }  
        finally {
            try{
                rs.close();
                pst.close();
            }
            catch(SQLException e){
                
            }
        }
    }

    public void clearall() {
        txt_empid.setText("");
        txt_firstname.setText("");
        txt_surname.setText("");
        txt_salary.setText("");
        txt_dob.setText("");
        txt_dept.setText("");
        txt_med.setText("0");
        txt_bonus.setText("0");
        txt_other.setText("0");
        txt_overtime.setText("0");
        txt_rph.setText("0");
        txt_total_overtime.setText("0");
        lbl_total.setText("0.00");
        
        resetTable();
        
        refreshtableBTN.setEnabled(false);
        saveBTN.setEnabled(false);
        calculateBTN.setEnabled(false);
        
        viewScroll.getViewport().setViewPosition(new Point(0,0));
        tableScroll.getViewport().setViewPosition(new Point(0,0));
        
        txt_search.setText("");
        txt_search.requestFocusInWindow();
        
        removerowBTN.setEnabled(false);
        
        txt_overtime.setEditable(false);
        txt_med.setEditable(false);
        txt_bonus.setEditable(false);
        txt_other.setEditable(false);
    }
    
    public void setCellsAlignmentToCenter() {
        
        allowanceTable.setDefaultEditor(Object.class, null); //<-- Disable editing/inserting data in JTable 
        
        DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment(JLabel.CENTER);

        allowanceTable.getColumnModel().getColumn(0).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(1).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(2).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(3).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(4).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(5).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(6).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(7).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(8).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(9).setCellRenderer(rendar);
        allowanceTable.getColumnModel().getColumn(10).setCellRenderer(rendar);
    }
    
    public void defaultTable() {
        resetTable();
        setCellsAlignmentToCenter();
        allowanceTable.removeAll();
    }
    
    public void resetTable() {
        allowanceTable.setModel(new DefaultTableModel(null, new String []{"EMP ID", "ALLOWANCE COUNT#", "FIRST NAME", "SURNAME", "BASIC SALARY", "RATE PER HOUR", "ALLOWANCES", "OVERTIME", "BONUS", "MEDICAL", "OTHERS"}));
        allowanceTable.getColumn("EMP ID").setPreferredWidth(70);
        allowanceTable.getColumn("ALLOWANCE COUNT#").setPreferredWidth(150);
        allowanceTable.getColumn("FIRST NAME").setPreferredWidth(150);
        allowanceTable.getColumn("SURNAME").setPreferredWidth(150);
        allowanceTable.getColumn("BASIC SALARY").setPreferredWidth(150);
        allowanceTable.getColumn("RATE PER HOUR").setPreferredWidth(150);
        allowanceTable.getColumn("ALLOWANCES").setPreferredWidth(180);
        allowanceTable.getColumn("OVERTIME").setPreferredWidth(100);
        allowanceTable.getColumn("BONUS").setPreferredWidth(100);
        allowanceTable.getColumn("MEDICAL").setPreferredWidth(100);
        allowanceTable.getColumn("OTHERS").setPreferredWidth(100);
    }
    
    public void refreshemployeededucTable() {
        setCellsAlignmentToCenter();
        resetTable();
        //autorefresh();
        try {
            DefaultTableModel tb = (DefaultTableModel)allowanceTable.getModel();
            
            ResultSet rtable;
            try (Statement sttable = conn.createStatement()) {
                rtable = sttable.executeQuery("select * from Allowance where emp_id like '"+'%'+txt_empid.getText()+'%'+"'");
                while(rtable.next()) {
                    
                    Vector v=new Vector();
                    
                    String A =(rtable.getString("emp_id"));
                    String A1 =(rtable.getString("id"));
                    String B =(rtable.getString("firstname"));
                    String C =(rtable.getString("surname"));
                    String D =(rtable.getString("salary"));
                    String E =(rtable.getString("rate"));
                    String F =(rtable.getString("total_allowance"));
                    String G =(rtable.getString("overtime"));
                    String H =(rtable.getString("bonus"));
                    String I =(rtable.getString("medical"));
                    String J =(rtable.getString("other"));
                    
                    v.add(A);
                    v.add(A1);
                    v.add(B);
                    v.add(C);
                    v.add(D);
                    v.add(E);
                    v.add(F);
                    v.add(G);
                    v.add(H);
                    v.add(I);
                    v.add(J);
                    
                    tb.addRow(v);
                    
                    sttable.close();
                }
            }
            rtable.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"<html><center>TABLE ERROR 404<br>ERR: "+e+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);            
            
        }
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
        String val = txt_emp.getText();
        try {
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Allowance row count #"+rowchecker+" of Employee #"+txt_empid.getText()+" is Deleted by: "+val+"')";
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
        viewpanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_med = new javax.swing.JTextField();
        txt_bonus = new javax.swing.JTextField();
        txt_other = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_overtime = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_total_overtime = new javax.swing.JTextField();
        txt_rph = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        txt_empid = new javax.swing.JTextField();
        txt_dob = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_salary = new javax.swing.JTextField();
        txt_dept = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_firstname = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        searchempBTN = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        tableScroll = new javax.swing.JScrollPane();
        allowanceTable = new javax.swing.JTable();
        calculateBTN = new javax.swing.JButton();
        refreshtableBTN = new javax.swing.JButton();
        txt_emp = new javax.swing.JLabel();
        clearBTN = new javax.swing.JButton();
        lbl_total = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        saveBTN = new javax.swing.JButton();
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
        lblTitle.setText("PAYROLL SYSTEM | ALLOWANCE");
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
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANAGE EMPLOYEE ALLOWANCE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewScroll.setBackground(new java.awt.Color(249, 250, 253));
        viewScroll.setBorder(null);
        viewScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_med.setEditable(false);
        txt_med.setBackground(new java.awt.Color(249, 250, 253));
        txt_med.setText("0");
        txt_med.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_medActionPerformed(evt);
            }
        });
        txt_med.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_medKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_medKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_medKeyTyped(evt);
            }
        });
        jPanel3.add(txt_med, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 80, 126, -1));

        txt_bonus.setEditable(false);
        txt_bonus.setBackground(new java.awt.Color(249, 250, 253));
        txt_bonus.setText("0");
        txt_bonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bonusKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bonusKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bonusKeyTyped(evt);
            }
        });
        jPanel3.add(txt_bonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 111, 126, -1));

        txt_other.setEditable(false);
        txt_other.setBackground(new java.awt.Color(249, 250, 253));
        txt_other.setText("0");
        txt_other.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_otherKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_otherKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_otherKeyTyped(evt);
            }
        });
        jPanel3.add(txt_other, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 137, 126, -1));

        jLabel6.setText("Medical :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 83, -1, -1));

        jLabel7.setText("Bonus :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 111, -1, -1));

        jLabel8.setText("Other :");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Please enter the amount");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 31, -1, -1));

        txt_overtime.setEditable(false);
        txt_overtime.setBackground(new java.awt.Color(249, 250, 253));
        txt_overtime.setText("0");
        txt_overtime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_overtimeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_overtimeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_overtimeKeyTyped(evt);
            }
        });
        jPanel3.add(txt_overtime, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 54, 126, -1));

        jLabel4.setText("Overtime :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 57, -1, -1));

        txt_total_overtime.setEditable(false);
        txt_total_overtime.setBackground(new java.awt.Color(249, 250, 253));
        txt_total_overtime.setText("0");
        txt_total_overtime.setEnabled(false);
        txt_total_overtime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_overtimeActionPerformed(evt);
            }
        });
        jPanel3.add(txt_total_overtime, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 54, 150, -1));

        txt_rph.setEditable(false);
        txt_rph.setBackground(new java.awt.Color(249, 250, 253));
        txt_rph.setText("0");
        txt_rph.setEnabled(false);
        jPanel3.add(txt_rph, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 85, 150, -1));

        jLabel15.setText("Total Overtime Rate:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 57, -1, -1));

        jLabel11.setText("RPH Rate:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 88, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 10, 120));

        viewpanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, 180));

        jPanel4.setBackground(new java.awt.Color(249, 250, 253));

        txt_empid.setEditable(false);
        txt_empid.setBackground(new java.awt.Color(249, 250, 253));
        txt_empid.setEnabled(false);
        txt_empid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_empidActionPerformed(evt);
            }
        });

        txt_dob.setEditable(false);
        txt_dob.setBackground(new java.awt.Color(249, 250, 253));
        txt_dob.setEnabled(false);

        jLabel3.setText("Date of Birth :");

        jLabel2.setText("Surname :");

        jLabel1.setText("First name :");

        jLabel5.setText("Employee id :");

        jLabel12.setText("Basic Salary :");

        txt_salary.setEditable(false);
        txt_salary.setBackground(new java.awt.Color(249, 250, 253));
        txt_salary.setEnabled(false);
        txt_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_salaryActionPerformed(evt);
            }
        });

        txt_dept.setEditable(false);
        txt_dept.setBackground(new java.awt.Color(249, 250, 253));
        txt_dept.setEnabled(false);

        txt_surname.setEditable(false);
        txt_surname.setBackground(new java.awt.Color(249, 250, 253));
        txt_surname.setEnabled(false);

        jLabel9.setText("Department :");

        txt_firstname.setEditable(false);
        txt_firstname.setBackground(new java.awt.Color(249, 250, 253));
        txt_firstname.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_dept, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_firstname)
                            .addComponent(txt_empid)
                            .addComponent(txt_surname)
                            .addComponent(txt_dob, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_empid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txt_dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_dept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewpanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 190));

        jPanel5.setBackground(new java.awt.Color(249, 250, 253));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setText("Search Employee ID:");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 20));

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
        jPanel5.add(searchempBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 30, 20));

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
        jPanel5.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 165, -1));

        viewpanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 17, 776, 30));

        tableScroll.setBackground(new java.awt.Color(249, 250, 253));
        tableScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        allowanceTable.setBackground(new java.awt.Color(249, 250, 253));
        allowanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EMP ID", "ALLOWANCE COUNT#", "FIRST NAME", "SURNAME", "BASIC SALARY", "RATE PER HOUR", "ALLOWANCES", "OVERTIME", "BONUS", "MEDICAL", "OTHERS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        allowanceTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        allowanceTable.setOpaque(false);
        allowanceTable.setSelectionForeground(new java.awt.Color(249, 250, 253));
        allowanceTable.getTableHeader().setResizingAllowed(false);
        allowanceTable.getTableHeader().setReorderingAllowed(false);
        tableScroll.setViewportView(allowanceTable);
        if (allowanceTable.getColumnModel().getColumnCount() > 0) {
            allowanceTable.getColumnModel().getColumn(0).setResizable(false);
            allowanceTable.getColumnModel().getColumn(0).setPreferredWidth(70);
            allowanceTable.getColumnModel().getColumn(1).setResizable(false);
            allowanceTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            allowanceTable.getColumnModel().getColumn(2).setResizable(false);
            allowanceTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            allowanceTable.getColumnModel().getColumn(3).setResizable(false);
            allowanceTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            allowanceTable.getColumnModel().getColumn(4).setResizable(false);
            allowanceTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            allowanceTable.getColumnModel().getColumn(5).setResizable(false);
            allowanceTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            allowanceTable.getColumnModel().getColumn(6).setResizable(false);
            allowanceTable.getColumnModel().getColumn(6).setPreferredWidth(180);
            allowanceTable.getColumnModel().getColumn(7).setResizable(false);
            allowanceTable.getColumnModel().getColumn(7).setPreferredWidth(100);
            allowanceTable.getColumnModel().getColumn(8).setResizable(false);
            allowanceTable.getColumnModel().getColumn(8).setPreferredWidth(100);
            allowanceTable.getColumnModel().getColumn(9).setResizable(false);
            allowanceTable.getColumnModel().getColumn(9).setPreferredWidth(100);
            allowanceTable.getColumnModel().getColumn(10).setResizable(false);
            allowanceTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        }

        viewpanel.add(tableScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 840, 234));

        calculateBTN.setBackground(new java.awt.Color(249, 250, 253));
        calculateBTN.setText("Calculate");
        calculateBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        calculateBTN.setDefaultCapable(false);
        calculateBTN.setEnabled(false);
        calculateBTN.setFocusPainted(false);
        calculateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBTNActionPerformed(evt);
            }
        });
        viewpanel.add(calculateBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 126, 31));

        refreshtableBTN.setBackground(new java.awt.Color(249, 250, 253));
        refreshtableBTN.setText("Refresh Table");
        refreshtableBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshtableBTN.setDefaultCapable(false);
        refreshtableBTN.setFocusPainted(false);
        refreshtableBTN.setOpaque(false);
        refreshtableBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshtableBTNActionPerformed(evt);
            }
        });
        viewpanel.add(refreshtableBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 530, 126, 31));

        txt_emp.setForeground(new java.awt.Color(253, 253, 253));
        txt_emp.setText("emp");
        viewpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 150, 30));

        clearBTN.setBackground(new java.awt.Color(249, 250, 253));
        clearBTN.setText("Clear");
        clearBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearBTN.setDefaultCapable(false);
        clearBTN.setFocusPainted(false);
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        viewpanel.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, 126, 31));

        lbl_total.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbl_total.setText("0.00");
        lbl_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lbl_totalKeyTyped(evt);
            }
        });
        viewpanel.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 231, 117, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Total Amount:  ₱");
        viewpanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, -1, 20));

        saveBTN.setBackground(new java.awt.Color(249, 250, 253));
        saveBTN.setText("Save");
        saveBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBTN.setDefaultCapable(false);
        saveBTN.setEnabled(false);
        saveBTN.setFocusPainted(false);
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTNActionPerformed(evt);
            }
        });
        viewpanel.add(saveBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 250, 126, 31));

        removerowBTN.setBackground(new java.awt.Color(249, 250, 253));
        removerowBTN.setText("Remove this Row");
        removerowBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removerowBTN.setDefaultCapable(false);
        removerowBTN.setEnabled(false);
        removerowBTN.setFocusPainted(false);
        removerowBTN.setOpaque(false);
        removerowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerowBTNActionPerformed(evt);
            }
        });
        viewpanel.add(removerowBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 530, 126, 31));

        viewScroll.setViewportView(viewpanel);

        mainpanel.add(viewScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 880, 480));

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

    private void txt_medActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_medActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_medActionPerformed

    private void txt_total_overtimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_overtimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_overtimeActionPerformed

    private void txt_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_salaryActionPerformed

    private void txt_searchComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_searchComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchComponentRemoved

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased

    }//GEN-LAST:event_txt_searchKeyReleased

    private void saveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTNActionPerformed
        // TODO add your handling code here:
        
        if (txt_empid.getText().isEmpty() | txt_firstname.getText().isEmpty() | txt_surname.getText().isEmpty() | txt_dob.getText().isEmpty() | txt_salary.getText().isEmpty()  | txt_salary.getText().isEmpty()) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null,"One of the required field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);    
            if(txt_overtime.getText().length() <0) {
                txt_overtime.setText("0");
            }
            if(txt_med.getText().length() <0) {
                txt_med.setText("0");
            }  
            if(txt_bonus.getText().length() <0) {
                txt_bonus.setText("0");
            }
            if(txt_other.getText().length() <0) {
                txt_other.setText("0");
            }
        } else if(lbl_total.getText().equals("0.0") | lbl_total.getText().equals("0.00")) {
            JOptionPane.showMessageDialog(null,"0 data is invalid. Please try again!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);    
            
        } else {
            calculateBTN.doClick();
            int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to save record?", mainnameString,JOptionPane.YES_NO_OPTION);
            if(p==0){
                try {

                    String value = Emp.empId;
                    String value1 =  txt_salary.getText();
                    String value2 =  txt_bonus.getText();
                    String value3 =  txt_med.getText();
                    String value4 =  txt_other.getText();
                    String value5 =  txt_rph.getText();
                    String value6 =  txt_overtime.getText();
                    String value7 =  lbl_total.getText();
                    String value8 =  txt_empid.getText();
                    String value9 =  txt_firstname.getText();
                    String value10 = txt_surname.getText();

                    String sql= "insert into Allowance (created_by,emp_id,overtime,medical,bonus,other,salary,rate,total_allowance,firstname,surname) values ('"+value+"','"+value8+"','"+value6+"','"+value3+"','"+value2+"','"+value4+"','"+value1+"','"+value5+"','"+value7+"','"+value9+"','"+value10+"')";

                    pst=conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null,"Allowance added!", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);    
                    txt_search.requestFocusInWindow();
                    
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
                }
                try {
                    Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
                    String timeString = sdf.format(d);

                    String value0 = timeString;
                    String values = dateString;
                    String val = txt_emp.getText();

                    String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+values+"','Allowance of Employee #"+txt_empid.getText()+" is Updated by: "+val+"')";
                    pst=conn.prepareStatement(reg);
                    pst.execute();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
                } finally {
                    try{
                        rs.close();
                        pst.close();
                        //clearall();
                        
                    } catch(SQLException e){

                    }
                }
            }
        }
        refreshtableBTN.doClick();
    }//GEN-LAST:event_saveBTNActionPerformed

    private void lbl_totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_totalKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_lbl_totalKeyTyped

    private void calculateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBTNActionPerformed

        if (txt_overtime.getText().isEmpty() | txt_med.getText().isEmpty() | txt_bonus.getText().isEmpty() | txt_other.getText().isEmpty() | txt_overtime.getText().isEmpty() | txt_rph.getText().isEmpty()) {
            //getToolkit().beep();
            //JOptionPane.showMessageDialog(null,"Allowance field is empty!", mainErrorString,JOptionPane.ERROR_MESSAGE,null);    
            
            if(txt_overtime.getText().length() <0) {
                txt_overtime.setText("0");
            }
            if(txt_med.getText().length() <0) {
                txt_med.setText("0");
            }  
            if(txt_bonus.getText().length() <0) {
                txt_bonus.setText("0");
            }
            if(txt_other.getText().length() <0) {
                txt_other.setText("0");
            }
        } else {
            
            saveBTN.setEnabled(true);
            int salary = Integer.parseInt(txt_salary.getText());
            int overtime = Integer.parseInt(txt_overtime.getText());

            double eight = 8;
            double days = 25;
            double dbop = Double.parseDouble(RPHRateFromDB); //rph rate
            double overtimeRate = Double.parseDouble(OvertimeRateFromDB); //overtime rate

            //calculate the total hours of overtime
            double Total_Overtime = overtime * overtimeRate;
            String x = String.valueOf(Total_Overtime);
            txt_total_overtime.setText(x);

            //calculate overall overtime
            dbop = salary /days/eight;
            String s = String.valueOf(dbop);
            txt_rph.setText(s);

            int med = Integer.parseInt(txt_med.getText());
            int bonus = Integer.parseInt(txt_bonus.getText());
            int other = Integer.parseInt(txt_other.getText());
            int f = med+bonus+other;
            double calc = Total_Overtime * dbop+f;
            String c = String.valueOf(calc);
            lbl_total.setText(c);
        }
    }//GEN-LAST:event_calculateBTNActionPerformed

    private void refreshtableBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshtableBTNActionPerformed
        refreshemployeededucTable();
        setCellsAlignmentToCenter();
        tableScroll.getViewport().setViewPosition(new Point(0,0));
    }//GEN-LAST:event_refreshtableBTNActionPerformed

    private void searchempBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchempBTNActionPerformed
       
        try {
            if(txt_search.getText().isEmpty()) {
                calculateBTN.setEnabled(false);
                defaultTable();
                setCellsAlignmentToCenter();
                refreshtableBTN.setEnabled(false);
                removerowBTN.setEnabled(false);
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
            txt_salary.setText(add5);

            String add6 =rs.getString("Department");
            txt_dept.setText(add6);
            
            rs.close();
            pst.close();
            
            txt_overtime.requestFocusInWindow();
            calculateBTN.setEnabled(true);
            calculateBTN.doClick();
            refreshemployeededucTable();
            setCellsAlignmentToCenter();
            refreshtableBTN.setEnabled(true);
            removerowBTN.setEnabled(true);
            refreshtableBTN.doClick();
            
            txt_overtime.setEditable(true);
            txt_med.setEditable(true);
            txt_bonus.setEditable(true);
            txt_other.setEditable(true);
            
            } else {
                
                JOptionPane.showMessageDialog(null,"Employee not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
                clearall();
                defaultTable();
                resetTable();
                setCellsAlignmentToCenter();
                refreshtableBTN.setEnabled(false);
                txt_search.setText("");
                txt_search.requestFocusInWindow();
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
            resetTable();
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        clearall();
    }//GEN-LAST:event_clearBTNActionPerformed

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
         //row checker int
        rowchecker = allowanceTable.getSelectedRow();
        
        //row count is greater than 1;
        if(allowanceTable.getModel().getRowCount()>=1) {
            removerowBTN.setEnabled(true);
            
            //no row is selected
            if(rowchecker == -1) {
                JOptionPane.showMessageDialog(null, "<html><center>No row is selected!</center></html>",mainErrorString, JOptionPane.ERROR_MESSAGE);
                
            //row is selected
            } else {
                int rowRemove = allowanceTable.getSelectedRow();
                String cell = allowanceTable.getModel().getValueAt(rowRemove, 1).toString();
                String sql = "DELETE FROM Allowance where id = "+cell+"";
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
        } else if (allowanceTable.getModel().getRowCount()<=0) {
            JOptionPane.showMessageDialog(null, "<html><center>Table is Empty.</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
        
        }
    }//GEN-LAST:event_removerowBTNActionPerformed

    private void txt_overtimeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_overtimeKeyTyped
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
       /*
        //limit the number length
        String l = txt_overtime.getText();
            if (!(l.length()<6)) {
                evt.consume();
        }*/
    }//GEN-LAST:event_txt_overtimeKeyTyped

    private void txt_medKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_medKeyTyped
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
    }//GEN-LAST:event_txt_medKeyTyped

    private void txt_bonusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bonusKeyTyped
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
    }//GEN-LAST:event_txt_bonusKeyTyped

    private void txt_otherKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyTyped
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
    }//GEN-LAST:event_txt_otherKeyTyped

    private void txt_overtimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_overtimeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            if(txt_overtime.getText().length() <1) {
                txt_overtime.setText("0");
            }
            txt_med.requestFocusInWindow();
            
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_total.setText("0.0");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_overtimeKeyPressed

    private void txt_overtimeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_overtimeKeyReleased
        if (txt_overtime.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_overtimeKeyReleased

    private void txt_medKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_medKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            if(txt_med.getText().length() <1) {
                txt_med.setText("0");
            }
            txt_bonus.requestFocusInWindow();
            
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_total.setText("0.0");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_medKeyPressed

    private void txt_bonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bonusKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            if(txt_bonus.getText().length() <1) {
                txt_bonus.setText("0");
            }
            txt_other.requestFocusInWindow();
            
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_total.setText("0.0");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_bonusKeyPressed

    private void txt_otherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            if(txt_other.getText().length() <1) {
                txt_other.setText("0");
            }
            calculateBTN.requestFocusInWindow();
            calculateBTN.doClick();
            
        } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE ||  evt.getKeyCode() == KeyEvent.VK_DELETE) {
            lbl_total.setText("0.0");
            saveBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_otherKeyPressed

    private void txt_medKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_medKeyReleased
        if (txt_overtime.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_medKeyReleased

    private void txt_bonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bonusKeyReleased
        if (txt_overtime.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_bonusKeyReleased

    private void txt_otherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherKeyReleased
        if (txt_overtime.getText().length() >0) {
            calculateBTN.doClick();
        }
    }//GEN-LAST:event_txt_otherKeyReleased

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
                new AllowanceGUI().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AllowanceGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable allowanceTable;
    private javax.swing.JButton calculateBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JButton refreshtableBTN;
    private javax.swing.JButton removerowBTN;
    private javax.swing.JButton saveBTN;
    private javax.swing.JButton searchempBTN;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JTextField txt_bonus;
    private javax.swing.JTextField txt_dept;
    private javax.swing.JTextField txt_dob;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JTextField txt_empid;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_med;
    private javax.swing.JTextField txt_other;
    private javax.swing.JTextField txt_overtime;
    private javax.swing.JTextField txt_rph;
    private javax.swing.JTextField txt_salary;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_total_overtime;
    private javax.swing.JScrollPane viewScroll;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

}
