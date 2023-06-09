// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package MainPackage;

import SystemDB.DBconnection;
import Panels_EmployeeManager.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
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
import javax.swing.KeyStroke;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class AboutSystem extends javax.swing.JDialog {
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
    public AboutSystem() throws SQLException, IOException {
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
            String reg= "insert into Audit (emp_id, date, status) values ('"+val+"','"+value0+" / "+value1+"','Name is Edited by "+txt_emp.getText()+"')";
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
        pnlTitle = new javax.swing.JPanel();
        lblTitleIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        mainpanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewpanel = new javax.swing.JPanel();
        sourceLBL = new javax.swing.JLabel();
        aboutLBL = new javax.swing.JLabel();
        rSPanelImage1 = new rojerusan.RSPanelImage();
        txt_emp = new javax.swing.JLabel();

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
        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnlTop.add(pnlActions, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, -1, 30));

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

        pnlTop.add(pnlTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        exitButton.setBackground(new java.awt.Color(0, 102, 204));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_multiply_18px_1.png"))); // NOI18N
        exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.setDefaultCapable(false);
        exitButton.setFocusPainted(false);
        exitButton.setFocusable(false);
        exitButton.setIconTextGap(0);
        exitButton.setOpaque(false);
        exitButton.setPreferredSize(new java.awt.Dimension(10, 18));
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exitButtonMousePressed(evt);
            }
        });
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        pnlTop.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 20, 30));

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, -1));

        mainpanel.setBackground(new java.awt.Color(249, 250, 253));
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ABOUT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sourceLBL.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        sourceLBL.setForeground(new java.awt.Color(0, 102, 255));
        sourceLBL.setText("Source: Jobadder.com");
        viewpanel.add(sourceLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, -1, -1));

        aboutLBL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        aboutLBL.setForeground(new java.awt.Color(0, 102, 204));
        aboutLBL.setText("<html><center>Employee Payroll Management System, made by ECoders is used to manage and streamline the process of making payments to employees.<br><br>\n\nOnce an employee’s standard payment information is entered into the payroll system, i.e. their wage, the number of hours they work per week and their bank account details, minimal maintenance is required.<br><br>\n\nThe advantage of using Payroll System is that it relieves accounting personnel of a lot of the administrative tasks associated with making payments to employees, that would otherwise be very time-consuming. It also reduces the instance of mistakes caused by human error.</center></html>");
        viewpanel.add(aboutLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 540, 330));

        rSPanelImage1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/Images/about_128px.png"))); // NOI18N
        viewpanel.add(rSPanelImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 120, 120));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 540, 480));

        txt_emp.setForeground(new java.awt.Color(249, 250, 253));
        txt_emp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_emp.setText("emp");
        mainpanel.add(txt_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 90, -1));

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 560, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void exitButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseMoved
    }//GEN-LAST:event_exitButtonMouseMoved

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
    }//GEN-LAST:event_exitButtonMouseEntered

    private void exitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseExited
    }//GEN-LAST:event_exitButtonMouseExited

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // Exit/Close Button code:
        AboutSystem.this.setVisible(false);
        AboutSystem.this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void exitButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMousePressed
        exitButton.doClick();
    }//GEN-LAST:event_exitButtonMousePressed

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
                new AboutSystem().setVisible(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AboutSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aboutLBL;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private rojerusan.RSPanelImage rSPanelImage1;
    private javax.swing.JLabel sourceLBL;
    private javax.swing.JLabel txt_emp;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

    private final ImageIcon format =null;
    //strin filename
    String filename = null;
    byte[] person_image = null;
    
    private String gender;
}
