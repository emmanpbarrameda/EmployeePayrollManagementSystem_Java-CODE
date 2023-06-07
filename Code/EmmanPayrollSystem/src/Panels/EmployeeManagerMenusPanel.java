// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels;

import SystemDB.DBconnection;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.sql.Connection;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.KeyStroke;
import Panels_EmployeeManager.*;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class EmployeeManagerMenusPanel extends javax.swing.JPanel {
    //for Database Connection Variable
    Connection conn;
    
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
    
    /**
     * Creates new form Search
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
      
    public EmployeeManagerMenusPanel() throws IOException, SQLException {
        initComponents();
        
        DBconnection c=new DBconnection();
        conn= c.getconnection();

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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addemployeeBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome2 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        searchemployeeBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        employeeallowanceBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome7 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        updatesalaryBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome5 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        deductionBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome4 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        paymentBTN = new rojerusan.RSButtonPane();
        totalmembersTFhome6 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(249, 250, 253));
        setPreferredSize(new java.awt.Dimension(1313, 571));

        jPanel1.setBackground(new java.awt.Color(249, 250, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee Manager");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1310, -1));

        addemployeeBTN.setBackground(new java.awt.Color(51, 153, 255));
        addemployeeBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addemployeeBTN.setToolTipText("");
        addemployeeBTN.setColorHover(new java.awt.Color(90, 172, 254));
        addemployeeBTN.setColorNormal(new java.awt.Color(51, 153, 255));
        addemployeeBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addemployeeBTNMousePressed(evt);
            }
        });

        totalmembersTFhome2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome2.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome2.setText("<html><center> ADD NEW\n<br> \nEMPLOYEE\n</br></center></html>");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel31.setText("Click this button to add new employee");
        jLabel31.setIconTextGap(1);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/new_employee_64px.png"))); // NOI18N

        javax.swing.GroupLayout addemployeeBTNLayout = new javax.swing.GroupLayout(addemployeeBTN);
        addemployeeBTN.setLayout(addemployeeBTNLayout);
        addemployeeBTNLayout.setHorizontalGroup(
            addemployeeBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(addemployeeBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addemployeeBTNLayout.setVerticalGroup(
            addemployeeBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addemployeeBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(addemployeeBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 270, 180));

        searchemployeeBTN.setBackground(new java.awt.Color(0, 153, 51));
        searchemployeeBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        searchemployeeBTN.setToolTipText("");
        searchemployeeBTN.setColorHover(new java.awt.Color(1, 173, 58));
        searchemployeeBTN.setColorNormal(new java.awt.Color(0, 153, 51));
        searchemployeeBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                searchemployeeBTNMousePressed(evt);
            }
        });

        totalmembersTFhome3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome3.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome3.setText("<html><center> SEARCH\n<br> \nEMPLOYEE\n</br></center></html>");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel32.setText("<html><center>Click this button to search and delete employee</center></html>");
        jLabel32.setIconTextGap(1);

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_employee_64px.png"))); // NOI18N

        javax.swing.GroupLayout searchemployeeBTNLayout = new javax.swing.GroupLayout(searchemployeeBTN);
        searchemployeeBTN.setLayout(searchemployeeBTNLayout);
        searchemployeeBTNLayout.setHorizontalGroup(
            searchemployeeBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(totalmembersTFhome3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(searchemployeeBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel35)
                .addContainerGap(102, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchemployeeBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );
        searchemployeeBTNLayout.setVerticalGroup(
            searchemployeeBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchemployeeBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(searchemployeeBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 270, 180));

        employeeallowanceBTN.setBackground(new java.awt.Color(255, 102, 0));
        employeeallowanceBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        employeeallowanceBTN.setToolTipText("");
        employeeallowanceBTN.setColorHover(new java.awt.Color(254, 131, 49));
        employeeallowanceBTN.setColorNormal(new java.awt.Color(255, 102, 0));
        employeeallowanceBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                employeeallowanceBTNMousePressed(evt);
            }
        });

        totalmembersTFhome7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome7.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome7.setText("<html><center> MANAGE EMPLOYEE\n<br> \nALLOWANCE\n</br></center></html>");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel43.setText("Click this button to manage employee allowance");
        jLabel43.setIconTextGap(1);

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/allowance_cash_64px.png"))); // NOI18N

        javax.swing.GroupLayout employeeallowanceBTNLayout = new javax.swing.GroupLayout(employeeallowanceBTN);
        employeeallowanceBTN.setLayout(employeeallowanceBTNLayout);
        employeeallowanceBTNLayout.setHorizontalGroup(
            employeeallowanceBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome7)
            .addGroup(employeeallowanceBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        employeeallowanceBTNLayout.setVerticalGroup(
            employeeallowanceBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeallowanceBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(employeeallowanceBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 270, 180));

        updatesalaryBTN.setBackground(new java.awt.Color(63, 97, 113));
        updatesalaryBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        updatesalaryBTN.setToolTipText("");
        updatesalaryBTN.setColorHover(new java.awt.Color(65, 116, 141));
        updatesalaryBTN.setColorNormal(new java.awt.Color(63, 97, 113));
        updatesalaryBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                updatesalaryBTNMousePressed(evt);
            }
        });

        totalmembersTFhome5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome5.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome5.setText("<html><center> UPDATE\n<br> \nEMPLOYEE SALARY\n</br></center></html>");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel38.setText("Click this button to update the employee salary");
        jLabel38.setIconTextGap(1);

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update_employees_64px.png"))); // NOI18N

        javax.swing.GroupLayout updatesalaryBTNLayout = new javax.swing.GroupLayout(updatesalaryBTN);
        updatesalaryBTN.setLayout(updatesalaryBTNLayout);
        updatesalaryBTNLayout.setHorizontalGroup(
            updatesalaryBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(updatesalaryBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        updatesalaryBTNLayout.setVerticalGroup(
            updatesalaryBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatesalaryBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalmembersTFhome5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(updatesalaryBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 270, 180));

        deductionBTN.setBackground(new java.awt.Color(255, 102, 102));
        deductionBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        deductionBTN.setToolTipText("");
        deductionBTN.setColorHover(new java.awt.Color(255, 136, 136));
        deductionBTN.setColorNormal(new java.awt.Color(255, 102, 102));
        deductionBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                deductionBTNMousePressed(evt);
            }
        });

        totalmembersTFhome4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome4.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome4.setText("<html><center> EMPLOYEE\n<br> \nDEDUCTION\n</br></center></html>");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel36.setText("Click this button to update employee deduction");
        jLabel36.setIconTextGap(1);

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deduction_employee_64px.png"))); // NOI18N

        javax.swing.GroupLayout deductionBTNLayout = new javax.swing.GroupLayout(deductionBTN);
        deductionBTN.setLayout(deductionBTNLayout);
        deductionBTNLayout.setHorizontalGroup(
            deductionBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(deductionBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        deductionBTNLayout.setVerticalGroup(
            deductionBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deductionBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(deductionBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 270, 180));

        paymentBTN.setBackground(new java.awt.Color(204, 0, 255));
        paymentBTN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        paymentBTN.setToolTipText("");
        paymentBTN.setColorHover(new java.awt.Color(216, 62, 254));
        paymentBTN.setColorNormal(new java.awt.Color(204, 0, 255));
        paymentBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                paymentBTNMousePressed(evt);
            }
        });

        totalmembersTFhome6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalmembersTFhome6.setForeground(new java.awt.Color(255, 255, 255));
        totalmembersTFhome6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalmembersTFhome6.setText("<html><center> PRINT EMPLOYEE\n<br> \nPAYMENT RECEIPT\n</br></center></html>");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cursor_16px.png"))); // NOI18N
        jLabel41.setText("Click this button to print the emp. receipt");
        jLabel41.setIconTextGap(1);

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/payment_employee_64px.png"))); // NOI18N

        javax.swing.GroupLayout paymentBTNLayout = new javax.swing.GroupLayout(paymentBTN);
        paymentBTN.setLayout(paymentBTNLayout);
        paymentBTNLayout.setHorizontalGroup(
            paymentBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(totalmembersTFhome6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(paymentBTNLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paymentBTNLayout.setVerticalGroup(
            paymentBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalmembersTFhome6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(paymentBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 290, 270, 180));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1313, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addemployeeBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addemployeeBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    addemployeeBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                addemployeeBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                AddEmployeeGUI panel = new AddEmployeeGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_addemployeeBTNMousePressed

    private void searchemployeeBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchemployeeBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    searchemployeeBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                searchemployeeBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                SearchEmployeeGUI panel = new SearchEmployeeGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_searchemployeeBTNMousePressed

    private void employeeallowanceBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeallowanceBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    employeeallowanceBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                employeeallowanceBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                AllowanceGUI panel = new AllowanceGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_employeeallowanceBTNMousePressed

    private void updatesalaryBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatesalaryBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    updatesalaryBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                updatesalaryBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                UpdateSalaryGUI panel = new UpdateSalaryGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_updatesalaryBTNMousePressed

    private void deductionBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deductionBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    deductionBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                deductionBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                EmployeeDeductionGUI panel = new EmployeeDeductionGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_deductionBTNMousePressed

    private void paymentBTNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentBTNMousePressed
        new Thread(() -> {
            try {
                try {
                    //open code
                    paymentBTN.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //cursor loading
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                //close code
                paymentBTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //cursor default
                SalarySlipGUI panel = new SalarySlipGUI();
                panel.setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EmployeeManagerMenusPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_paymentBTNMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSButtonPane addemployeeBTN;
    private rojerusan.RSButtonPane deductionBTN;
    private rojerusan.RSButtonPane employeeallowanceBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JPanel jPanel1;
    private rojerusan.RSButtonPane paymentBTN;
    private rojerusan.RSButtonPane searchemployeeBTN;
    private javax.swing.JLabel totalmembersTFhome2;
    private javax.swing.JLabel totalmembersTFhome3;
    private javax.swing.JLabel totalmembersTFhome4;
    private javax.swing.JLabel totalmembersTFhome5;
    private javax.swing.JLabel totalmembersTFhome6;
    private javax.swing.JLabel totalmembersTFhome7;
    private rojerusan.RSButtonPane updatesalaryBTN;
    // End of variables declaration//GEN-END:variables
}