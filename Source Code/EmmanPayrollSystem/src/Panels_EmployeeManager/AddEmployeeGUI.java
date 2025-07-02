// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_EmployeeManager;

import ImageBrowse_JFileChooser_API.ImageBrowsePreview_CLASS_API;
import JFileChooser_Locations_API.FileLastLocation1;
import SystemDB.DBconnection;
import MainPackage.ToastManager;
import UppercaseTypeFilterPackage.UppercaseDocumentFilter_API;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class AddEmployeeGUI extends javax.swing.JDialog {
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
    
    //filter for Uppercase on first letter of the Sentence.
    DocumentFilter typefilter = new UppercaseDocumentFilter_API();
    
    //Button Group for GENDER
    ButtonGroup genderBTNgroup;
    
    /**
     * Creates new form AddEmployeeGUI
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public AddEmployeeGUI() throws SQLException, IOException, ClassNotFoundException {
        initComponents();
        //connection to database
        DBconnection c=new DBconnection();
        conn= c.getconnection();

        
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        txt_emp.setText(String.valueOf(Emp.empId));
        this.setModal(true); //this.setAlwaysOnTop(true);
        this.getRootPane().setBorder(new LineBorder(new Color(0,102,204)));
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/TASKBAR_ICON.png")).getImage());
        employeeid();
        GUINaming_DATA();
        txt_firstname.requestFocusInWindow();
        //clearall(); //<-- clear all void not allowed because of clearing selection of button group.
        
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
        ((AbstractDocument) txt_firstname.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_surname.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_dob.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_address.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_add2.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_apt.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_dep.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_desig.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_status.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_doj.getDocument()).setDocumentFilter(typefilter);
        ((AbstractDocument) txt_job.getDocument()).setDocumentFilter(typefilter);
        
        //JTextField Limit
        txt_pc.setDocument(new JTextFieldLimitAPI(4));
        txt_tel.setDocument(new JTextFieldLimitAPI(11));
        txt_salary.setDocument(new JTextFieldLimitAPI(12));
        
        //Button Group for GENDER
        genderBTNgroup = new ButtonGroup();
        genderBTNgroup.add(r_male); //male radio button
        genderBTNgroup.add(r_female); //female radio button
        genderBTNgroup.getElements();
    }

    //-------------------- START VOID CODES HERE --------------------//
    
    //GUINaming from Database
    public void GUINaming_DATA() {
        System.out.println("[INFO] Loading GUI naming data from 'guinames' table...");

        String query = "SELECT * FROM guinames";

        try (Statement stGNaming = conn.createStatement();
            ResultSet rsGNaming = stGNaming.executeQuery(query)) {

            if (rsGNaming.next()) {
                // GUI Title
                mainAppNameFromDB = rsGNaming.getString("MainAppName");
                System.out.println("[INFO] MainAppName: " + mainAppNameFromDB);

                // Company Name
                companyNameFromDB = rsGNaming.getString("MainCompanyName");
                System.out.println("[INFO] CompanyName: " + companyNameFromDB);

                // Currency Symbol
                pesoSignString = rsGNaming.getString("CurrencySign");
                System.out.println("[INFO] CurrencySign: " + pesoSignString);

                // Popup Titles
                mainnameString = rsGNaming.getString("PopupNormal");
                System.out.println("[INFO] PopupNormal: " + mainnameString);

                mainErrorString = rsGNaming.getString("PopupError");
                System.out.println("[INFO] PopupError: " + mainErrorString);

                // Optional: assign to external/global variables
                // mainPopupTitleNormalGUI = mainnameString;
                // mainPopupTitleErrorGUI = mainErrorString;
                // mainAppNameString = mainAppNameFromDB;

            } else {
                System.err.println("[WARN] No data found in 'guinames' table.");
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to load GUI naming data.");
            e.printStackTrace();
        }
    }
    
    //GENERATE EMPLOYEE ID VOID
    public void employeeid() {
            
    try {
        Statement stCount = conn.createStatement();
        ResultSet rsCount = stCount.executeQuery("select max(id) from employeesrecord");
        rsCount.next();
                
        rsCount.getString("max(id)");
                
        if(rsCount.getString("max(id)")== null) {
            txt_id.setText("1");
                    
        } else {
            long id = Long.parseLong(rsCount.getString("max(id)"));
            id++;
            txt_id.setText(String.format("%01d", id));
            stCount.close();
            rsCount.close();
            }
                
        } catch (SQLException ex) {
            //Logger.getLogger(AddEmployeeGUI.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "Something went wrong! ERR: "+ex, mainErrorString, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearall() {
        txt_id.setText("");
        txt_firstname.setText("");
        txt_surname.setText("");
        txt_tel.setText("");
        txt_dob.setText("");
        txt_email.setText("");
        txt_address.setText("");
        txt_dep.setText("");
        txt_status.setText("");
        txt_salary.setText("");
        txt_add2.setText("");
        txt_pc.setText("");
        txt_desig.setText("");
        txt_job.setText("");
        txt_apt.setText("");
        txt_doj.setText("");
        lbl_img.setIcon(null);
        r_male.setSelected(false);
        r_female.setSelected(false);
        
        txt_firstname.requestFocusInWindow();
        
        //for gender
        r_male.setEnabled(true);
        r_male.setSelected(false);
        
        r_female.setEnabled(true);
        r_female.setSelected(false);
        
        //clear selection of gender
        genderBTNgroup.clearSelection();
        
        //employee count void
        employeeid();
    }
    
    //audit added new employee
    public void auditAddEmp() {
        try {
            Date currentDate = GregorianCalendar.getInstance().getTime();
            DateFormat df = DateFormat.getDateInstance();
            String dateString = df.format(currentDate);

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            String timeString = sdf.format(d);

            String value0 = timeString;
            String value1 = dateString;
            String val = txt_emp.getText();
            String fullname = ""+txt_firstname.getText()+" "+txt_surname.getText()+"";
                
            String reg= "insert into audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Added New Employee #"+txt_id.getText()+"-("+fullname+") by Admin: "+val+"')";
            try (PreparedStatement pstADD = conn.prepareStatement(reg)) {
                pstADD.executeUpdate();
                pstADD.close();
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    //proceed to step two - manage employee allowance
    public void steptwo_allowance() throws SQLException, IOException, ClassNotFoundException {
        int step = JOptionPane.showConfirmDialog(null, "<html><center>Do you want to proceed on:<br>Step Two (Manage Employee Allowance)?</center></html>", mainnameString, JOptionPane.YES_NO_OPTION);
        if (step == 0) {
            stepexitBTN.doClick();
            conn.close();
            AllowanceGUI_STEP2 step2 = new AllowanceGUI_STEP2();
            String empid_step2_string = txt_id.getText();
            step2.empid_step2_Void(empid_step2_string); //set the emp-id from here to 2nd step
            step2.setVisible(true);
            this.dispose();
        } else {
            clearall();
            employeeid();
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
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_dob = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        txt_firstname = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        txt_dep = new javax.swing.JTextField();
        txt_desig = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        txt_doj = new javax.swing.JTextField();
        txt_salary = new javax.swing.JTextField();
        txt_job = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_img = new javax.swing.JLabel();
        addrecordBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        r_female = new javax.swing.JRadioButton();
        r_male = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_pc = new javax.swing.JTextField();
        txt_apt = new javax.swing.JTextField();
        txt_add2 = new javax.swing.JTextField();
        txt_address = new javax.swing.JTextField();
        txt_tel = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_emp = new javax.swing.JLabel();
        insertpictureBTN = new javax.swing.JButton();
        stepexitBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(249, 250, 253));
        setName("add_step1"); // NOI18N
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
        lblTitle.setText("PAYROLL SYSTEM | ADD EMPLOYEE");
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
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADD EMPLOYEE | STEP 1 of 3", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Employee id :");
        viewpanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, 20));

        jLabel1.setText("First name :");
        viewpanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, -1, -1));

        jLabel2.setText("Surname :");
        viewpanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 68, -1, -1));

        jLabel3.setText("Date of Birth :");
        viewpanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, -1, -1));

        txt_dob.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dobKeyPressed(evt);
            }
        });
        viewpanel.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 94, 168, -1));

        txt_surname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_surnameKeyPressed(evt);
            }
        });
        viewpanel.add(txt_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 68, 168, -1));

        txt_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_firstnameActionPerformed(evt);
            }
        });
        txt_firstname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_firstnameKeyPressed(evt);
            }
        });
        viewpanel.add(txt_firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 42, 168, -1));

        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(249, 250, 253));
        txt_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_idKeyTyped(evt);
            }
        });
        viewpanel.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 15, 168, -1));

        txt_dep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_depKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_depKeyTyped(evt);
            }
        });
        viewpanel.add(txt_dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 11, 160, -1));

        txt_desig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_desigKeyPressed(evt);
            }
        });
        viewpanel.add(txt_desig, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 37, 160, -1));

        txt_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_statusKeyPressed(evt);
            }
        });
        viewpanel.add(txt_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 68, 160, -1));

        txt_doj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dojKeyPressed(evt);
            }
        });
        viewpanel.add(txt_doj, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 94, 160, -1));

        txt_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_salaryActionPerformed(evt);
            }
        });
        txt_salary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_salaryKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_salaryKeyTyped(evt);
            }
        });
        viewpanel.add(txt_salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 125, 160, -1));

        txt_job.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jobActionPerformed(evt);
            }
        });
        txt_job.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_jobKeyPressed(evt);
            }
        });
        viewpanel.add(txt_job, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 151, 160, -1));

        jDesktopPane1.setLayer(lbl_img, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_img, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_img, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        viewpanel.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 11, -1, 200));

        addrecordBTN.setBackground(new java.awt.Color(249, 250, 253));
        addrecordBTN.setText("Add Record");
        addrecordBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrecordBTNActionPerformed(evt);
            }
        });
        addrecordBTN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addrecordBTNKeyPressed(evt);
            }
        });
        viewpanel.add(addrecordBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 321, 134, 44));

        clearBTN.setBackground(new java.awt.Color(249, 250, 253));
        clearBTN.setText("Clear");
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTNActionPerformed(evt);
            }
        });
        viewpanel.add(clearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(709, 321, 134, 44));

        jLabel20.setText("Job Title :");
        viewpanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 154, -1, -1));

        jLabel12.setText("Basic Salary :");
        viewpanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 128, -1, -1));

        jLabel18.setText("Date Hired :");
        viewpanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 97, -1, -1));

        jLabel17.setText("Status :");
        viewpanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 71, -1, -1));

        jLabel13.setText("Designation :");
        viewpanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 40, -1, -1));

        jLabel9.setText("Department :");
        viewpanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 14, -1, -1));

        r_female.setBackground(new java.awt.Color(249, 250, 253));
        r_female.setText("Female");
        r_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_femaleActionPerformed(evt);
            }
        });
        r_female.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                r_femaleKeyPressed(evt);
            }
        });
        viewpanel.add(r_female, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 124, -1, -1));

        r_male.setBackground(new java.awt.Color(249, 250, 253));
        r_male.setText("Male");
        r_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_maleActionPerformed(evt);
            }
        });
        r_male.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                r_maleKeyPressed(evt);
            }
        });
        viewpanel.add(r_male, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 124, -1, -1));

        jLabel11.setText("Gender:");
        viewpanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 128, -1, -1));

        jLabel6.setText("Email :");
        viewpanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 154, -1, -1));

        jLabel7.setText("Contact :");
        viewpanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jLabel8.setText("Address Line 1 :");
        viewpanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 206, -1, -1));

        jLabel14.setText("Address Line 2 :");
        viewpanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 232, -1, -1));

        jLabel15.setText("Apt./House No :");
        viewpanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 263, -1, -1));

        jLabel16.setText("Postal Code :");
        viewpanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 294, -1, -1));

        txt_pc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pcKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_pcKeyTyped(evt);
            }
        });
        viewpanel.add(txt_pc, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 291, 168, -1));

        txt_apt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_aptActionPerformed(evt);
            }
        });
        txt_apt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_aptKeyPressed(evt);
            }
        });
        viewpanel.add(txt_apt, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 260, 168, -1));

        txt_add2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_add2KeyPressed(evt);
            }
        });
        viewpanel.add(txt_add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 229, 168, -1));

        txt_address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_addressKeyPressed(evt);
            }
        });
        viewpanel.add(txt_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 203, 168, -1));

        txt_tel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_telKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telKeyTyped(evt);
            }
        });
        viewpanel.add(txt_tel, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 177, 168, -1));

        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });
        viewpanel.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 151, 168, -1));

        txt_emp.setForeground(new java.awt.Color(253, 253, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        viewpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 330, 20));

        insertpictureBTN.setBackground(new java.awt.Color(249, 250, 253));
        insertpictureBTN.setText("Insert Picture");
        insertpictureBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertpictureBTNActionPerformed(evt);
            }
        });
        viewpanel.add(insertpictureBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, -1, -1));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 880, 480));

        stepexitBTN.setBorderPainted(false);
        stepexitBTN.setContentAreaFilled(false);
        stepexitBTN.setDefaultCapable(false);
        stepexitBTN.setFocusPainted(false);
        stepexitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepexitBTNActionPerformed(evt);
            }
        });
        mainpanel.add(stepexitBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, 50, 10));

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
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

    private void txt_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_firstnameActionPerformed

    private void txt_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_salaryActionPerformed

    private void txt_jobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jobActionPerformed

    private void addrecordBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addrecordBTNActionPerformed

        try {
            String fullname = txt_firstname.getText() + " " + txt_surname.getText();

            // Check for empty fields
            if (isAnyFieldEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "One of the required fields is empty!", mainErrorString, JOptionPane.ERROR_MESSAGE);
                txt_firstname.requestFocusInWindow();
                return;
            }

            // Check if record already exists
            String query = "SELECT fullname FROM employeesrecord WHERE fullname LIKE ?";
            try (PreparedStatement psCheck = conn.prepareStatement(query)) {
                psCheck.setString(1, "%" + fullname + "%");
                try (ResultSet rsCheck = psCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Employee already exists in the database.", mainErrorString, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Check gender selected
            if (genderBTNgroup.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Gender is not selected!", mainErrorString, JOptionPane.ERROR_MESSAGE);
                r_male.requestFocusInWindow();
                return;
            }

            // Check image is selected
            if (person_image == null) {
                JOptionPane.showMessageDialog(null, "You cannot save data without an employee image!", mainErrorString, JOptionPane.ERROR_MESSAGE);
                insertpictureBTN.requestFocusInWindow();
                return;
            }

            // Confirm add
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to add the record?", mainnameString, JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "INSERT INTO employeesrecord (first_name, surname, fullname, Dob, Email, Telephone, Address, Department, Image, Salary, Gender, Address2, Post_code, Designation, Status, job_title, Apartment, Date_hired) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, txt_firstname.getText());
                    pst.setString(2, txt_surname.getText());
                    pst.setString(3, fullname);
                    pst.setString(4, txt_dob.getText());
                    pst.setString(5, txt_email.getText());
                    pst.setString(6, txt_tel.getText());
                    pst.setString(7, txt_address.getText());
                    pst.setString(8, txt_dep.getText());
                    pst.setBytes(9, person_image);
                    pst.setString(10, txt_salary.getText());
                    pst.setString(11, gender);
                    pst.setString(12, txt_add2.getText());
                    pst.setString(13, txt_pc.getText());
                    pst.setString(14, txt_desig.getText());
                    pst.setString(15, txt_status.getText());
                    pst.setString(16, txt_job.getText());
                    pst.setString(17, txt_apt.getText());
                    pst.setString(18, txt_doj.getText());
                    pst.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Data of Employee #" + txt_id.getText() + " is saved successfully!", mainnameString, JOptionPane.INFORMATION_MESSAGE);
                auditAddEmp();
                steptwo_allowance();
                clearall();
            }
        } catch (HeadlessException | IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), mainErrorString, JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_addrecordBTNActionPerformed

    private void clearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTNActionPerformed
        clearall();
    }//GEN-LAST:event_clearBTNActionPerformed

    private void r_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_femaleActionPerformed
        // TODO add your handling code here:
        gender ="Female";
        r_female.setSelected(true);
        r_male.setSelected(false);

    }//GEN-LAST:event_r_femaleActionPerformed

    private void r_maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_maleActionPerformed
        // TODO add your handling code here:
        gender ="Male";
        r_male.setSelected(true);
        r_female.setSelected(false);
    }//GEN-LAST:event_r_maleActionPerformed

    private void txt_aptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_aptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_aptActionPerformed

    private void insertpictureBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertpictureBTNActionPerformed
        String locationFilePIC = FileLastLocation1.FileLocation.get(System.getProperty("user.home"));
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(locationFilePIC));
        chooser.setPreferredSize(new Dimension(600, 550)); //width and height
        //chooser.showOpenDialog(pnlManage); //<-- set JFileChooser icon like the main panel icon
        chooser.setDialogTitle("Select an image");
	chooser.setAcceptAllFileFilterUsed(false);
	//FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPEG images", "png", "jpeg");
        //FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
	chooser.addChoosableFileFilter(imageFilter); //<-- show png and jpg files only
        chooser.setAccessory(new ImageBrowsePreview_CLASS_API(chooser)); //<-- Add IMAGE PREVIEW from ImageBrowsePreview_API class
        int photo = chooser.showDialog(this, "Select");
        if (photo==JFileChooser.APPROVE_OPTION) {
        File f = chooser.getSelectedFile();
        FileLastLocation1.FileLocation.put(f.getParentFile().getAbsolutePath());
        filename =f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(), Image.SCALE_DEFAULT));
        lbl_img.setIcon(imageIcon);
        
      try {

            File image = new File(filename);
            FileInputStream fis = new FileInputStream (image);
            ByteArrayOutputStream bos= new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for(int readNum; (readNum=fis.read(buf))!=-1; ){

                bos.write(buf,0,readNum);
            }
            person_image=bos.toByteArray();
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(null,e);

        }
        }
    }//GEN-LAST:event_insertpictureBTNActionPerformed

    private void txt_idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idKeyTyped
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
    }//GEN-LAST:event_txt_idKeyTyped

    private void txt_firstnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_firstnameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_surname.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_firstnameKeyPressed

    private void txt_surnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_surnameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_dob.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_surnameKeyPressed

    private void txt_dobKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dobKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            r_male.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_dobKeyPressed

    private void r_maleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_r_maleKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            r_female.requestFocusInWindow();
        }
    }//GEN-LAST:event_r_maleKeyPressed

    private void r_femaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_r_femaleKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_email.requestFocusInWindow();
        }
    }//GEN-LAST:event_r_femaleKeyPressed

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_tel.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_telKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_address.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_telKeyPressed

    private void txt_addressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_addressKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_add2.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_addressKeyPressed

    private void txt_add2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_add2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_apt.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_add2KeyPressed

    private void txt_aptKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_aptKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_pc.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_aptKeyPressed

    private void txt_pcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pcKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_dep.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_pcKeyPressed

    private void txt_depKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_desig.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_depKeyPressed

    private void txt_desigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_desigKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_status.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_desigKeyPressed

    private void txt_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_statusKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_doj.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_statusKeyPressed

    private void txt_dojKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dojKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_salary.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_dojKeyPressed

    private void txt_salaryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_salaryKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_job.requestFocusInWindow();
        }
    }//GEN-LAST:event_txt_salaryKeyPressed

    private void txt_jobKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jobKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            addrecordBTN.requestFocusInWindow();
            addrecordBTN.doClick();
        }
    }//GEN-LAST:event_txt_jobKeyPressed

    private void addrecordBTNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addrecordBTNKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ||  evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_firstname.requestFocusInWindow();
        }
    }//GEN-LAST:event_addrecordBTNKeyPressed

    private void stepexitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepexitBTNActionPerformed
        try {
            pst.clearBatch();
            pst.close();
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AddEmployeeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_stepexitBTNActionPerformed

    private void txt_pcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pcKeyTyped
        char c=evt.getKeyChar();
        if (!((c >= '0') && (c <='9') ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
            evt.consume();
        } else if(c==KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_pcKeyTyped

    private void txt_salaryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_salaryKeyTyped
        char c=evt.getKeyChar();
        if (!((c >= '0') && (c <='9') ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
            evt.consume();
        } else if(c==KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_salaryKeyTyped

    private void txt_telKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telKeyTyped
        char c=evt.getKeyChar();
        if (!((c >= '0') && (c <='9') ||
                (c==KeyEvent.VK_BACK_SPACE) ||
                (c==KeyEvent.VK_DELETE) ||
                (c==KeyEvent.VK_ENTER) ||
                (c==KeyEvent.VK_TAB))) {
            evt.consume();
        } else if(c==KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_telKeyTyped

    private void txt_depKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depKeyTyped
        
    }//GEN-LAST:event_txt_depKeyTyped

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
                new AddEmployeeGUI().setVisible(true);
            } catch (SQLException | IOException | ClassNotFoundException ex) {
                Logger.getLogger(AddEmployeeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addrecordBTN;
    private javax.swing.JButton clearBTN;
    private javax.swing.JButton insertpictureBTN;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JLabel lbl_img;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JRadioButton r_female;
    private javax.swing.JRadioButton r_male;
    private javax.swing.JButton stepexitBTN;
    private javax.swing.JTextField txt_add2;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_apt;
    private javax.swing.JTextField txt_dep;
    private javax.swing.JTextField txt_desig;
    private javax.swing.JTextField txt_dob;
    private javax.swing.JTextField txt_doj;
    private javax.swing.JTextField txt_email;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_job;
    private javax.swing.JTextField txt_pc;
    private javax.swing.JTextField txt_salary;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_surname;
    private javax.swing.JTextField txt_tel;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

    private final ImageIcon format =null;
    //strin filename
    String filename = null;
    byte[] person_image = null;
    
    private String gender;
    
    private boolean isAnyFieldEmpty() {
        return txt_firstname.getText().isEmpty()
                || txt_surname.getText().isEmpty()
                || txt_dob.getText().isEmpty()
                || txt_email.getText().isEmpty()
                || txt_tel.getText().isEmpty()
                || txt_address.getText().isEmpty()
                || txt_dep.getText().isEmpty()
                || txt_salary.getText().isEmpty()
                || txt_apt.getText().isEmpty()
                || txt_pc.getText().isEmpty()
                || txt_desig.getText().isEmpty()
                || txt_status.getText().isEmpty()
                || txt_doj.getText().isEmpty()
                || txt_job.getText().isEmpty()
                || txt_add2.getText().isEmpty();
    }
    
}
