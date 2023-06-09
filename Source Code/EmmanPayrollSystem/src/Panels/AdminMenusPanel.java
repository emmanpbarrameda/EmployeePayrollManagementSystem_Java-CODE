// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels;

import JFileChooser_Locations_API.FileLastLocation3;
import SystemDB.DBconnection;
import MainPackage.ToastManager;
import Panels_Administrator.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.sql.Connection;
import java.io.*;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class AdminMenusPanel extends javax.swing.JPanel {
    //for Database Connection Variable
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
    final Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    final int widthvarToast = this.getSize().width;
    final int heightvatToast = this.getSize().height;
    final int xPosToast = (dimToast.width-widthvarToast)/2;
    final int yPosToast = (dimToast.width-heightvatToast)/2;
    
    final String loadingwait = "Please Wait...";
    final ToastManager loadingwaitToast = new ToastManager(loadingwait, xPosToast, yPosToast);

    final String printingwait = "Opening your file..."; 
    final ToastManager printingwaitToast = new ToastManager(printingwait, xPosToast, yPosToast);
    
    //for opening files and folders
    static int clicked1 = 0;
    static int clicked2 = 0;
    static int clicked3 = 0;
    static int clicked4 = 0;
    static int clicked5 = 0;
    static int clicked6 = 0;
    
    /**
     * Creates new form Search
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
      
    public AdminMenusPanel() throws IOException, SQLException {
        initComponents();
        
        DBconnection c=new DBconnection();
        conn= c.getconnection();
                
        GUINaming_DATA();
        
        //txt_emp.setText(String.valueOf(Emp.empId));
        
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
                //this.setTitle(mainAppNameFromDB);
                
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
 
    /*
    public void plswaitLoading() {
            new Thread(() -> {
                try {
                    for (int loading = 1; loading <=100; loading++) {
                        Thread.sleep(25);
                        main.consoleLoading.setValue(loading);
                        main.consoleLoading.setStringPainted(true);
                        main.consoleLoading.setMinimum(1);
                        main.consoleLoading.setMaximum(100);
                        main.consoleLoading.setVisible(true);
                        main.consoleLoading.setIndeterminate(true);
                        main.consoleLoading.setStringPainted(false);
                        main.consoleText.setVisible(true);
                        main.consoleText.setText("<html>Please wait...</html>");
                    }
                    //dialog2.dispose();
                    main.consoleLoading.setValue(0);
                    main.consoleLoading.setVisible(false);
                    main.consoleLoading.setIndeterminate(false);
                    main.consoleLoading.setStringPainted(true);
                    main.consoleText.setText("");
                    main.consoleText.setVisible(false);
                }   catch (InterruptedException ex) {
                    Logger.getLogger(AdminMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
    }*/
    
    //setText the username from Auth 
    public void usernameVoidAdminM(String usernameStringAdminM) {
        txt_emp.setText(usernameStringAdminM);
    }
    
    public void auditPrintAll() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','All Employees Data is Printed by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void auditPrintAllowance() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Employees Allowance Data is Printed by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
  
    public void auditPrintDeduction() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Employees Deduction Data is Printed by: "+txt_emp.getText()+"')";
            try (PreparedStatement pstAudit = conn.prepareStatement(reg)) {
                pstAudit.execute();
                pstAudit.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
  
    public void auditOpenDB() {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Database storage folder is opened by: "+txt_emp.getText()+"')";
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        printallBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome2 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        printempallowanceBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        printempdeductionBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome7 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        managedbBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome6 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        registerAdminBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome8 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        manageprintedBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome4 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        auditactivitiesBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome5 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txt_emp = new javax.swing.JLabel();
        manageadminBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome9 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(249, 250, 253));
        setPreferredSize(new java.awt.Dimension(1313, 571));

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.setMinimumSize(new java.awt.Dimension(1310, 700));
        jPanel1.setPreferredSize(new java.awt.Dimension(1310, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Administrator Panel");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1310, -1));

        printallBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        printallBTN.setToolTipText("");
        printallBTN.setColorHover(new java.awt.Color(65, 116, 141));
        printallBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        printallBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                printallBTNMousePressed(evt);
            }
        });

        totalmembersTFhome2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome2.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome2.setText("<html><center> PRINT ALL <br> \nEMPLOYEES DATA\n</br></center></html>");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel31.setText("Click this button to print employees data to excel");
        jLabel31.setIconTextGap(0);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/excel1_64px.png"))); // NOI18N

        javax.swing.GroupLayout printallBTNLayout = new javax.swing.GroupLayout(printallBTN);
        printallBTN.setLayout(printallBTNLayout);
        printallBTNLayout.setHorizontalGroup(
            printallBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(printallBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        printallBTNLayout.setVerticalGroup(
            printallBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printallBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(printallBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 270, 180));

        printempallowanceBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        printempallowanceBTN.setToolTipText("");
        printempallowanceBTN.setColorHover(new java.awt.Color(65, 116, 141));
        printempallowanceBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        printempallowanceBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                printempallowanceBTNMousePressed(evt);
            }
        });

        totalmembersTFhome3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome3.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome3.setText("<html><center>PRINT  EMPLOYEES <br>  ALLOWANCE LIST </br></center></html>");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel32.setText("<html><center>Click this button to print employees allowance list to PDF</center></html>");
        jLabel32.setIconTextGap(1);

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/liturgy_64px.png"))); // NOI18N

        javax.swing.GroupLayout printempallowanceBTNLayout = new javax.swing.GroupLayout(printempallowanceBTN);
        printempallowanceBTN.setLayout(printempallowanceBTNLayout);
        printempallowanceBTNLayout.setHorizontalGroup(
            printempallowanceBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalmembersTFhome3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(printempallowanceBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel35)
                .addContainerGap(102, Short.MAX_VALUE))
            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        printempallowanceBTNLayout.setVerticalGroup(
            printempallowanceBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printempallowanceBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(printempallowanceBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 270, 180));

        printempdeductionBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        printempdeductionBTN.setToolTipText("");
        printempdeductionBTN.setColorHover(new java.awt.Color(65, 116, 141));
        printempdeductionBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        printempdeductionBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                printempdeductionBTNMousePressed(evt);
            }
        });

        totalmembersTFhome7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome7.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome7.setText("<html><center>PRINT  EMPLOYEES\n<br> DEDUCTION LIST\n</br></center></html>");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel43.setText("<html><center>Click this button to print employees deduction list to PDF</center></html>");
        jLabel43.setIconTextGap(1);

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/liturgy_64px.png"))); // NOI18N

        javax.swing.GroupLayout printempdeductionBTNLayout = new javax.swing.GroupLayout(printempdeductionBTN);
        printempdeductionBTN.setLayout(printempdeductionBTNLayout);
        printempdeductionBTNLayout.setHorizontalGroup(
            printempdeductionBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome7)
            .addGroup(printempdeductionBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        printempdeductionBTNLayout.setVerticalGroup(
            printempdeductionBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printempdeductionBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(printempdeductionBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 270, 180));

        managedbBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        managedbBTN.setToolTipText("");
        managedbBTN.setColorHover(new java.awt.Color(65, 116, 141));
        managedbBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        managedbBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                managedbBTNMousePressed(evt);
            }
        });

        totalmembersTFhome6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome6.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome6.setText("<html><center> MANAGE\n<br> \nDATABASE\n</br></center></html>");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel41.setText("Click this button to open the database folder");
        jLabel41.setIconTextGap(1);

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sql_64px.png"))); // NOI18N

        registerAdminBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        registerAdminBTN.setToolTipText("");
        registerAdminBTN.setColorHover(new java.awt.Color(65, 116, 141));
        registerAdminBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        registerAdminBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                registerAdminBTNMousePressed(evt);
            }
        });

        totalmembersTFhome8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome8.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome8.setText("<html><center> REGISTER\n<br> \nNEW ADMINISTRATOR\n</br></center></html>");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel39.setText("<html><center>Click this button to open the <br>Admin Registration form</center></html>");
        jLabel39.setIconTextGap(1);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/register_admin_64px.png"))); // NOI18N

        javax.swing.GroupLayout registerAdminBTNLayout = new javax.swing.GroupLayout(registerAdminBTN);
        registerAdminBTN.setLayout(registerAdminBTNLayout);
        registerAdminBTNLayout.setHorizontalGroup(
            registerAdminBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39)
            .addComponent(totalmembersTFhome8, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addGroup(registerAdminBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        registerAdminBTNLayout.setVerticalGroup(
            registerAdminBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerAdminBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout managedbBTNLayout = new javax.swing.GroupLayout(managedbBTN);
        managedbBTN.setLayout(managedbBTNLayout);
        managedbBTNLayout.setHorizontalGroup(
            managedbBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(managedbBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(managedbBTNLayout.createSequentialGroup()
                .addComponent(registerAdminBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        managedbBTNLayout.setVerticalGroup(
            managedbBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managedbBTNLayout.createSequentialGroup()
                .addComponent(registerAdminBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(managedbBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 290, 270, 180));

        manageprintedBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageprintedBTN.setToolTipText("");
        manageprintedBTN.setColorHover(new java.awt.Color(65, 116, 141));
        manageprintedBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        manageprintedBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                manageprintedBTNMousePressed(evt);
            }
        });

        totalmembersTFhome4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome4.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome4.setText("<html><center> CONFIGURE\n<br> \nGUI OPTIONS\n</br></center></html>");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel36.setText("Click this button to edit the gui naming label");
        jLabel36.setIconTextGap(1);

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/manage_gui_64px.png"))); // NOI18N

        javax.swing.GroupLayout manageprintedBTNLayout = new javax.swing.GroupLayout(manageprintedBTN);
        manageprintedBTN.setLayout(manageprintedBTNLayout);
        manageprintedBTNLayout.setHorizontalGroup(
            manageprintedBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(manageprintedBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manageprintedBTNLayout.setVerticalGroup(
            manageprintedBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageprintedBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(manageprintedBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 270, 180));

        auditactivitiesBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        auditactivitiesBTN.setToolTipText("");
        auditactivitiesBTN.setColorHover(new java.awt.Color(65, 116, 141));
        auditactivitiesBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        auditactivitiesBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                auditactivitiesBTNMousePressed(evt);
            }
        });

        totalmembersTFhome5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome5.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome5.setText("<html><center> AUDIT\n<br> \nSYSTEM ACTIVITIES\n</br></center></html>");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel38.setText("Click this button to view the Admins Activity logs");
        jLabel38.setIconTextGap(1);

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/audit_activities_64px.png"))); // NOI18N

        javax.swing.GroupLayout auditactivitiesBTNLayout = new javax.swing.GroupLayout(auditactivitiesBTN);
        auditactivitiesBTN.setLayout(auditactivitiesBTNLayout);
        auditactivitiesBTNLayout.setHorizontalGroup(
            auditactivitiesBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(auditactivitiesBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        auditactivitiesBTNLayout.setVerticalGroup(
            auditactivitiesBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(auditactivitiesBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalmembersTFhome5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(auditactivitiesBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 270, 180));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        jPanel1.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 20));

        manageadminBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageadminBTN.setToolTipText("");
        manageadminBTN.setColorHover(new java.awt.Color(65, 116, 141));
        manageadminBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        manageadminBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                manageadminBTNMousePressed(evt);
            }
        });

        totalmembersTFhome9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome9.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome9.setText("<html><center> MANAGE\n<br> \nADMIN ACCOUNT\n</br></center></html>");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel46.setText("<html><center>Click this button to manage the <br>Admin Account</center></html>");
        jLabel46.setIconTextGap(1);

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/manage_admin_64px.png"))); // NOI18N

        javax.swing.GroupLayout manageadminBTNLayout = new javax.swing.GroupLayout(manageadminBTN);
        manageadminBTN.setLayout(manageadminBTNLayout);
        manageadminBTNLayout.setHorizontalGroup(
            manageadminBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome9)
            .addGroup(manageadminBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel47)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manageadminBTNLayout.setVerticalGroup(
            manageadminBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageadminBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(manageadminBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, 270, 180));

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1313, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1313, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void printallBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printallBTNMousePressed
        new Thread(() -> {
            try {
                //open code
                printallBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                loadingwaitToast.showToast();
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            printallBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            String exactdatetimefile = "(of "+(sdf.format(d))+")";       
        
            //choose location
            JFileChooser dialog = new JFileChooser();
            String locationPrintEXCEL = FileLastLocation3.FileLocation.get(System.getProperty("user.home"));
            dialog.setCurrentDirectory(new File(locationPrintEXCEL));
            //dialog = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
            dialog.setPreferredSize(new Dimension(600, 550)); //width and height
            dialog.setSelectedFile(new File("All Employees Main Data "+exactdatetimefile+".xls"));
            dialog.setDragEnabled(false);
            dialog.setDialogTitle(mainnameString); //title
            //dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //<-- Show folders only
        
            int saveHere = dialog.showDialog(this, "Save File Here"); //icon and button
            if (saveHere==JFileChooser.APPROVE_OPTION) {
                String filePathExcelPrint = dialog.getSelectedFile().getPath();
                File selectedFilePrintEXCEL = dialog.getSelectedFile();
                FileLastLocation3.FileLocation.put(selectedFilePrintEXCEL.getParentFile().getAbsolutePath());
                
                try {
                    try (Statement stmtPrintEX = conn.createStatement()) {

                    FileOutputStream fileOut;
                    fileOut = new FileOutputStream(filePathExcelPrint);
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet worksheet = workbook.createSheet("Sheet 0");
                    worksheet.getPrintSetup().setLandscape(true);
                    worksheet.getPrintSetup().setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
                    // Creating Header
                    CellStyle styleheader = workbook.createCellStyle();
                    Header header = worksheet.getHeader();
                    header.setCenter("Employees Data");
                    org.apache.poi.ss.usermodel.Font fontheader = workbook.createFont();
                    fontheader.setFontHeightInPoints((short)20);
                    fontheader.setFontName("Segoe UI");
                    fontheader.setBold(true);
                    styleheader.setFont(fontheader);
                    Row row1 = worksheet.createRow((short)0);
                    CellStyle stylerow1 = workbook.createCellStyle();
                    row1.createCell(0).setCellValue("COUNT ID");
                    row1.createCell(1).setCellValue("FIRST NAME");
                    row1.createCell(2).setCellValue("SURNAME");
                    row1.createCell(3).setCellValue("FULLNAME");
                    row1.createCell(4).setCellValue("BDAY");
                    row1.createCell(5).setCellValue("GENDER");
                    row1.createCell(6).setCellValue("EMAIL");
                    row1.createCell(7).setCellValue("CONTACT #");
                    row1.createCell(8).setCellValue("APT/HOUSE #");
                    row1.createCell(9).setCellValue("ADDRESS 1");
                    row1.createCell(10).setCellValue("ADDRESS 2");
                    row1.createCell(11).setCellValue("POSTAL CODE");
                    row1.createCell(12).setCellValue("SALARY");
                    row1.createCell(13).setCellValue("JOB TITLE");
                    row1.createCell(14).setCellValue("DATE HIRED");
                    row1.createCell(15).setCellValue("DEPARTMENT");
                    row1.createCell(16).setCellValue("DESIGNATION");
                    row1.createCell(17).setCellValue("WORK STATUS");
                    
                    org.apache.poi.ss.usermodel.Font fontrow1 = workbook.createFont();
                    fontrow1.setFontHeightInPoints((short)12);
                    fontrow1.setFontName("Segoe UI");
                    fontrow1.setBold(true);
                    fontrow1.setStrikeout(true);
                    
                    // Applying font to the style
                    stylerow1.setFont(fontrow1);
                    
                    Row row4;
                    try (ResultSet rsPrintEX = stmtPrintEX.executeQuery("SELECT id,first_name,surname,fullname,Dob,Gender,Email,Telephone,Apartment,Address,Address2,Post_code,Salary,job_title,Date_hired,Department,Designation,Status FROM EmployeesRecord")) {
                        while(rsPrintEX.next()) {
                            int a = rsPrintEX.getRow();
                            row4 = worksheet.createRow((short)a);
                            row4.createCell(0).setCellValue(rsPrintEX.getString(1));
                            row4.createCell(1).setCellValue(rsPrintEX.getString(2));
                            row4.createCell(2).setCellValue(rsPrintEX.getString(3));
                            row4.createCell(3).setCellValue(rsPrintEX.getString(4));
                            row4.createCell(4).setCellValue(rsPrintEX.getString(5));
                            row4.createCell(5).setCellValue(rsPrintEX.getString(6));
                            row4.createCell(6).setCellValue(rsPrintEX.getString(7));
                            row4.createCell(7).setCellValue(rsPrintEX.getString(8));
                            row4.createCell(8).setCellValue(rsPrintEX.getString(9));
                            row4.createCell(9).setCellValue(rsPrintEX.getString(10));
                            row4.createCell(10).setCellValue(rsPrintEX.getString(11));
                            row4.createCell(11).setCellValue(rsPrintEX.getString(12));
                            row4.createCell(12).setCellValue(rsPrintEX.getString(13));
                            row4.createCell(13).setCellValue(rsPrintEX.getString(14));
                            row4.createCell(14).setCellValue(rsPrintEX.getString(15));
                            row4.createCell(15).setCellValue(rsPrintEX.getString(16));
                            row4.createCell(16).setCellValue(rsPrintEX.getString(17));
                            row4.createCell(17).setCellValue(rsPrintEX.getString(18));
                        }
                        workbook.write(fileOut);
                        fileOut.flush();
                        fileOut.close();
                    }
                    stmtPrintEX.close();
                    JOptionPane.showMessageDialog(null, "<html><center>Employees Main Data is Successfuly Printed to Excel Document! <br>File Path: "+filePathExcelPrint+"</center></html>",mainnameString, JOptionPane.INFORMATION_MESSAGE);
                    auditPrintAll();
                    //System.out.println("Export Success");
                    }
                } catch(SQLException | IOException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null,"ERROR 404 EXPrint. ERR: "+ex, mainErrorString,JOptionPane.ERROR_MESSAGE,null);            
                }
                
                
                int opendata = JOptionPane.showConfirmDialog(null, "Do You Want To Open the Main Employees Data?", mainnameString, JOptionPane.YES_NO_OPTION);
                if (opendata == 0) { 
                    try {   
                        printingwaitToast.showToast(); 
                        File file = new File(filePathExcelPrint+"");
                        if(!Desktop.isDesktopSupported()) { //check if Desktop is supported by Platform or not  
                            //System.out.println("not supported");
                            JOptionPane.showMessageDialog(null, "Opening of employees data not supported!","ERROR", JOptionPane.ERROR_MESSAGE);
                            return;  
                        }
                        Desktop desktop = Desktop.getDesktop();  
                        if(file.exists()) //checks file exists or not  
                        desktop.open(file); //opens the specified file 
                        
                    } catch(HeadlessException | IOException e) {   
                        JOptionPane.showMessageDialog(null, "Something went wrong! ERR: "+e, mainErrorString, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_printallBTNMousePressed

    private void printempallowanceBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printempallowanceBTNMousePressed
        new Thread(() -> {
            try {
                //open code
                printempallowanceBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                loadingwaitToast.showToast();
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            printempallowanceBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            String exactdatetimefile = "(of "+(sdf.format(d))+")";       
        
            //choose location
            JFileChooser dialog = new JFileChooser();
            String locationPrintEXCEL = FileLastLocation3.FileLocation.get(System.getProperty("user.home"));
            dialog.setCurrentDirectory(new File(locationPrintEXCEL));
            //dialog = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
            dialog.setPreferredSize(new Dimension(600, 550)); //width and height
            dialog.setSelectedFile(new File("Employee Allowance Report "+exactdatetimefile+""+".pdf"));
            dialog.setDragEnabled(false);
            dialog.setDialogTitle(mainnameString); //title
            //dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //<-- Show folders only
        
            int saveHere = dialog.showDialog(this, "Save File Here"); //icon and button
            if (saveHere==JFileChooser.APPROVE_OPTION) {
                String filePath = dialog.getSelectedFile().getPath();
                File selectedFile = dialog.getSelectedFile();
                FileLastLocation3.FileLocation.put(selectedFile.getParentFile().getAbsolutePath());

            try {       
                String sql ="select * from Allowance";
            
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
           
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(11);
                myDocument.open();
           
                float[] columnWidths = new float[] {3,6,10,10,5,6,5,5,7,6,8};
                table.setWidths(columnWidths);

                table.setWidthPercentage(100); //set table width to 100%
           
                myDocument.add(new Paragraph("Employees Allowance List",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD )));
                myDocument.add(new Paragraph(new Date().toString()));
                myDocument.add(new Paragraph(" "));
                table.addCell(new PdfPCell(new Paragraph("ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Emp ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Firstname",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Surname",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("O.T",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Medical",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Bonus",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Other",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Salary",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Rate",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Allowance",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));

                while(rs.next()) {
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(10),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(11),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(9),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                }
           
                myDocument.add(table);
                //myDocument.add(new Paragraph("--------------------------------------------------------------------------------------------"));
                myDocument.addAuthor(companyNameFromDB);
                myDocument.addCreationDate();
                myDocument.addDocListener(myWriter);
                myDocument.close();  
                JOptionPane.showMessageDialog(null, "<html><center>Report was successfully generated! <br>File Path: "+filePath+"</center></html>",mainnameString, JOptionPane.INFORMATION_MESSAGE);
                auditPrintAllowance();
                
                int opendata = JOptionPane.showConfirmDialog(null, "Do You Want To Open the Employees Allowance Report?", mainnameString, JOptionPane.YES_NO_OPTION);
                if (opendata == 0) {
                    try {  
                        loadingwaitToast.showToast();
                        File file = new File(filePath+"");
                        if(!Desktop.isDesktopSupported()) { //check if Desktop is supported by Platform or not   
                            JOptionPane.showMessageDialog(null, "Opening of employees data is not supported!", mainErrorString, JOptionPane.ERROR_MESSAGE);
                            return;
                        }  
                            Desktop desktop = Desktop.getDesktop();  
                            if(file.exists())         //checks file exists or not  
                            desktop.open(file);              //opens the specified file  
                        } catch(HeadlessException | IOException e) {   
                            JOptionPane.showMessageDialog(null, "Something went wrong! ERR: "+e, mainErrorString, JOptionPane.ERROR_MESSAGE);
                        }
                }
                } catch(DocumentException | HeadlessException | FileNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
         
                } finally {
                try {
                    rs.close();
                    pst.close();
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            }
        }).start();
    }//GEN-LAST:event_printempallowanceBTNMousePressed

    private void printempdeductionBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printempdeductionBTNMousePressed

        new Thread(() -> {
            try {
                //open code
                printempdeductionBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                loadingwaitToast.showToast();
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            printempdeductionBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            String exactdatetimefile = "(of "+(sdf.format(d))+")";       
        
            //choose location
            JFileChooser dialog = new JFileChooser();
            String locationPrintEXCEL = FileLastLocation3.FileLocation.get(System.getProperty("user.home"));
            dialog.setCurrentDirectory(new File(locationPrintEXCEL));
            //dialog = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
            dialog.setPreferredSize(new Dimension(600, 550)); //width and height
            dialog.setSelectedFile(new File("Employee Deduction Report "+exactdatetimefile+""+".pdf"));
            dialog.setDragEnabled(false);
            dialog.setDialogTitle(mainnameString); //title
            //dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //<-- Show folders only
        
            int saveHere = dialog.showDialog(this, "Save File Here"); //icon and button
            if (saveHere==JFileChooser.APPROVE_OPTION) {
                String filePath = dialog.getSelectedFile().getPath();
                File selectedFile = dialog.getSelectedFile();
                FileLastLocation3.FileLocation.put(selectedFile.getParentFile().getAbsolutePath());
            
            try {
                String sql ="select * from Deductions";

                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();

                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(8);
                myDocument.open();
                
                float[] columnWidths = new float[] {3,7,10,10,6,6,9,8};
                table.setWidths(columnWidths);

                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Employees Deduction List",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD )));
                myDocument.add(new Paragraph(new Date().toString()));
                myDocument.add(new Paragraph(" "));
                table.addCell(new PdfPCell(new Paragraph("ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Emp ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("First Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Surname",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Salary",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Deduc. Amount",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Deduc. Reason",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Created By",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));

                while(rs.next()) {
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.PLAIN))));
                }
                
                myDocument.add(table);
                //myDocument.add(new Paragraph("--------------------------------------------------------------------------------------------"));
                myDocument.addAuthor(companyNameFromDB);
                myDocument.addCreationDate();
                myDocument.addDocListener(myWriter);
                myDocument.close();  
                JOptionPane.showMessageDialog(null, "<html><center>Report was successfully generated! <br>File Path: "+filePath+"</center></html>",mainnameString, JOptionPane.INFORMATION_MESSAGE);
                auditPrintDeduction();
                
                int opendata = JOptionPane.showConfirmDialog(null, "Do You Want To Open the Employees Deduction Report?", mainnameString, JOptionPane.YES_NO_OPTION);
                if (opendata == 0) {
                    try {  
                        loadingwaitToast.showToast();
                        File file = new File(filePath+"");
                        if(!Desktop.isDesktopSupported()) { //check if Desktop is supported by Platform or not   
                            JOptionPane.showMessageDialog(null, "Opening of employees data is not supported!", mainErrorString, JOptionPane.ERROR_MESSAGE);
                            return;
                        }  
                            Desktop desktop = Desktop.getDesktop();  
                            if(file.exists())         //checks file exists or not  
                            desktop.open(file);              //opens the specified file  
                        } catch(HeadlessException | IOException e) {   
                            JOptionPane.showMessageDialog(null, "Something went wrong! ERR: "+e, mainErrorString, JOptionPane.ERROR_MESSAGE);
                        }
                }
            } catch(DocumentException | HeadlessException | FileNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null,e);

            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            }
        }).start();
    }//GEN-LAST:event_printempdeductionBTNMousePressed

    private void managedbBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managedbBTNMousePressed
    String FolderType = "Database";
    final String Path="C:/Payroll Management System/database"; //<-- path of the file/folder
        clicked3++;
        new Thread(() -> {
            try {
                if(clicked3<2) {
                    for (int loading = 1; loading <=100; loading++) {
                        Thread.sleep(3);
                        /*
                        main.consoleLoading.setValue(loading);
                        main.consoleLoading.setStringPainted(true);
                        main.consoleLoading.setIndeterminate(false);
                        //consoleLoadingPercent.setText("" +Integer.toString(loading)+"sec.");
                        main.consoleLoading.setMinimum(1);
                        main.consoleLoading.setMaximum(100);
                        main.consoleLoading.setVisible(true);
                        main.consoleText.setVisible(true);
                        main.consoleText.setText("<html>Finding the "+FolderType+" Folder</html>");
                        */
                        managedbBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    }
                    //dialog2.dispose();
                    //main.consoleLoading.setValue(0);
                    //main.consoleLoading.setVisible(false);
                    //main.consoleLoading.setIndeterminate(true);
                    //main.consoleText.setText("");
                    loadingwaitToast.showToast();
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Path);
                    auditOpenDB();
                    //plswaitLoading(); //pleasewait void
                    managedbBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                    
                } else if (clicked3>2) {
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "<html><center>Do not click this button twice.</center></html>", mainErrorString, JOptionPane.ERROR_MESSAGE);
                    clicked3 = 0;
                    managedbBTN.setFocusable(false); //unfocus mouse cursor
                }
            } catch (InterruptedException | IOException ex) {
                JOptionPane.showMessageDialog(null, "Something went wrong while opening "+Path+" ERR: "+ex, mainErrorString, JOptionPane.ERROR_MESSAGE);
            }
    }).start();
    }//GEN-LAST:event_managedbBTNMousePressed

    private void manageprintedBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageprintedBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    manageprintedBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                manageprintedBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                EditGUIOptions panelGuiEdit = new EditGUIOptions();
                panelGuiEdit.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AdminMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_manageprintedBTNMousePressed

    private void auditactivitiesBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_auditactivitiesBTNMousePressed
        new Thread(() -> {
            try {
                //open code
                auditactivitiesBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            auditactivitiesBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
            AuditSystemGUI panel;
            try {
                panel = new AuditSystemGUI();
                panel.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(AdminMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_auditactivitiesBTNMousePressed

    private void registerAdminBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerAdminBTNMousePressed
        new Thread(() -> {
            try {
                //open code
                registerAdminBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            registerAdminBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
            RegisterFrame panel;
            try {
                panel = new RegisterFrame();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AdminMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_registerAdminBTNMousePressed

    private void manageadminBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageadminBTNMousePressed
        new Thread(() -> {
            try {
                //open code
                manageadminBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            //close code
            manageadminBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
            ManageAdministrators panel;
            try {
                panel = new ManageAdministrators();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AdminMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_manageadminBTNMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSButtonPane auditactivitiesBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSButtonPane manageadminBTN;
    private rojerusan.RSButtonPane managedbBTN;
    private rojerusan.RSButtonPane manageprintedBTN;
    private rojerusan.RSButtonPane printallBTN;
    private rojerusan.RSButtonPane printempallowanceBTN;
    private rojerusan.RSButtonPane printempdeductionBTN;
    private rojerusan.RSButtonPane registerAdminBTN;
    private javax.swing.JLabel totalmembersTFhome2;
    private javax.swing.JLabel totalmembersTFhome3;
    private javax.swing.JLabel totalmembersTFhome4;
    private javax.swing.JLabel totalmembersTFhome5;
    private javax.swing.JLabel totalmembersTFhome6;
    private javax.swing.JLabel totalmembersTFhome7;
    private javax.swing.JLabel totalmembersTFhome8;
    private javax.swing.JLabel totalmembersTFhome9;
    private javax.swing.JLabel txt_emp;
    // End of variables declaration//GEN-END:variables
}
