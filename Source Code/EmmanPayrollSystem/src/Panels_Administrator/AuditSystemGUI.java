// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_Administrator;

import SystemDB.DBconnection;
import MainPackage.ToastManager;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public final class AuditSystemGUI extends javax.swing.JDialog {
    Connection conn;
    ResultSet rs=null;
    PreparedStatement pst=null;

    //main name of popups
    final String mainnameString = "Payroll System";
    //main name of popups errors
    final String mainErrorString = "Payroll System | ERROR";
    
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
     * @throws java.io.IOException
     */
    public AuditSystemGUI() throws IOException {
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
        
        //no icon in taskbar
        //this.setType(javax.swing.JFrame.Type.UTILITY);
        
        //automatic_refresh();
        //Update_table3();

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

    
    private void Update_table3() {  
        try {
            String sql ="select * from Audit";
        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            auditadminactTable.setModel(DbUtils.resultSetToTableModel(rs)); 
        }
        
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            try {
                rs.close();
                pst.close();
                
            }
            catch(SQLException e){
                
            }
        }
    }
    
    private void automatic_refresh() {
        refreshactivitiesBTN.doClick();
        Update_table3();
    }
    
    public void setCellsAlignmentToCenter() {
        
        auditadminactTable.setDefaultEditor(Object.class, null); //<-- Disable editing/inserting data in JTable 
        
        DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment(JLabel.CENTER);

        auditadminactTable.getColumnModel().getColumn(0).setCellRenderer(rendar);
        auditadminactTable.getColumnModel().getColumn(1).setCellRenderer(rendar);
        auditadminactTable.getColumnModel().getColumn(2).setCellRenderer(rendar);
        auditadminactTable.getColumnModel().getColumn(3).setCellRenderer(rendar);
    }
    
    public void defaultTable() {
        auditadminactTable.removeAll();
    }
    
    public void resetTable() {
        auditadminactTable.setModel(new DefaultTableModel(null, new String []{"ADMIN NAME", "ACTIVITY #", "DATE", "ACTIVITY STATUS"}));
        auditadminactTable.getColumn("ADMIN NAME").setPreferredWidth(100);
        auditadminactTable.getColumn("ACTIVITY #").setPreferredWidth(73);
        auditadminactTable.getColumn("DATE").setPreferredWidth(250);
        auditadminactTable.getColumn("ACTIVITY STATUS").setPreferredWidth(600);
    }
    
    public void RefreshAdminAuditTable() {
        
        if(txt_search.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Search field is empty.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            removerowBTN.setEnabled(false);
            refreshactivitiesBTN.setEnabled(false);
            txt_search.requestFocusInWindow();

        } else if(txt_search.getText().equals(" ")) {
            JOptionPane.showMessageDialog(null,"Admin not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            removerowBTN.setEnabled(false);
            refreshactivitiesBTN.setEnabled(false);
            txt_search.requestFocusInWindow();
            
        } else {
        setCellsAlignmentToCenter();
        resetTable();
        //autorefresh();
        try {
            DefaultTableModel tb = (DefaultTableModel)auditadminactTable.getModel();
            
            ResultSet rtable;
            try (Statement sttable = conn.createStatement()) {
                rtable = sttable.executeQuery("select * from Audit where emp_id like '"+'%'+txt_search.getText()+'%'+"'");
                while(rtable.next()) {
                    
                    Vector v=new Vector();
                    
                    String ADMINID =(rtable.getString("emp_id"));
                    String ACTIVITYNUM =(rtable.getString("audit_id"));
                    String DATE =(rtable.getString("date"));
                    String ACTIVITYSTATUS =(rtable.getString("status"));
                    
                    v.add(ADMINID);
                    v.add(ACTIVITYNUM);
                    v.add(DATE);
                    v.add(ACTIVITYSTATUS);
                    
                    tb.addRow(v);
                }
            }
            rtable.close();
            
            removerowBTN.setEnabled(true);
            refreshactivitiesBTN.setEnabled(true);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"<html><center>TABLE ERROR 404<br>ERR: "+e+"</center></html>", mainErrorString,JOptionPane.ERROR_MESSAGE,null);            
            
        }
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        auditadminactTable = new javax.swing.JTable();
        refreshactivitiesBTN = new javax.swing.JButton();
        removerowBTN = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchempBTN = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();

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
        lblTitle.setText("PAYROLL SYSTEM | AUDIT ACTIVITIES");
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
        mainpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADMINISTRATORS ACTIVITIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 102, 204))); // NOI18N
        mainpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(249, 250, 253));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        viewpanel.setBackground(new java.awt.Color(249, 250, 253));
        viewpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(249, 250, 253));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Audit Admin Activities", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 14))); // NOI18N

        auditadminactTable.setBackground(new java.awt.Color(249, 250, 253));
        auditadminactTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADMIN NAME", "ACTIVITY #", "DATE", "ACTIVITY STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        auditadminactTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        auditadminactTable.setOpaque(false);
        auditadminactTable.setSelectionForeground(new java.awt.Color(249, 250, 253));
        auditadminactTable.getTableHeader().setResizingAllowed(false);
        auditadminactTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(auditadminactTable);
        if (auditadminactTable.getColumnModel().getColumnCount() > 0) {
            auditadminactTable.getColumnModel().getColumn(0).setResizable(false);
            auditadminactTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            auditadminactTable.getColumnModel().getColumn(1).setResizable(false);
            auditadminactTable.getColumnModel().getColumn(1).setPreferredWidth(73);
            auditadminactTable.getColumnModel().getColumn(2).setResizable(false);
            auditadminactTable.getColumnModel().getColumn(2).setPreferredWidth(250);
            auditadminactTable.getColumnModel().getColumn(3).setResizable(false);
            auditadminactTable.getColumnModel().getColumn(3).setPreferredWidth(600);
        }

        refreshactivitiesBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refreshactivitiesBTN.setText("Refresh");
        refreshactivitiesBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshactivitiesBTN.setEnabled(false);
        refreshactivitiesBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshactivitiesBTNActionPerformed(evt);
            }
        });

        removerowBTN.setBackground(new java.awt.Color(249, 250, 253));
        removerowBTN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(removerowBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshactivitiesBTN)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(refreshactivitiesBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removerowBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewpanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 880, 440));

        jPanel4.setBackground(new java.awt.Color(249, 250, 253));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("   Admin:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 20));

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
        jPanel4.add(searchempBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 20, 20));

        txt_search.setBackground(new java.awt.Color(249, 250, 253));
        txt_search.setOpaque(false);
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
        jPanel4.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 170, -1));

        viewpanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jScrollPane1.setViewportView(viewpanel);

        mainpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 880, 480));

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

    private void refreshactivitiesBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshactivitiesBTNActionPerformed
        searchempBTN.doClick();
        txt_search.requestFocusInWindow();
        setCellsAlignmentToCenter();
    }//GEN-LAST:event_refreshactivitiesBTNActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchKeyReleased

    private void searchempBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchempBTNActionPerformed
        RefreshAdminAuditTable();
        /*try {

            String sql ="select * from Audit where emp_id=? ";

            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_search.getText());
            rs=pst.executeQuery();
            auditadminactTable.setModel(DbUtils.resultSetToTableModel(rs));

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Admin not found.", mainErrorString,JOptionPane.ERROR_MESSAGE,null);
            txt_search.setText("");
            txt_search.requestFocusInWindow();
        }
        finally {

            try{

                rs.close();
                pst.close();

            }
            catch(SQLException e){

            }
        }*/
    }//GEN-LAST:event_searchempBTNActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchempBTN.doClick();
            setCellsAlignmentToCenter();
            
        } else if(txt_search.getText().isEmpty()|| evt.getKeyCode() == KeyEvent.VK_BACK_SPACE|| evt.getKeyCode() == KeyEvent.VK_DELETE) {
            defaultTable();
            resetTable();
            refreshactivitiesBTN.setEnabled(false);
            removerowBTN.setEnabled(false);
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

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

    private void removerowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerowBTNActionPerformed
        //row checker int
        int rowchecker = auditadminactTable.getSelectedRow();

        //row count is greater than 1;
        if(auditadminactTable.getModel().getRowCount()>=1) {
            removerowBTN.setEnabled(true);

            //no row is selected
            if(rowchecker == -1) {
                JOptionPane.showMessageDialog(null, "<html><center>No row is selected!</center></html>",mainErrorString, JOptionPane.ERROR_MESSAGE);

                //row is selected
            } else {
                int rowRemove = auditadminactTable.getSelectedRow();
                String cell = auditadminactTable.getModel().getValueAt(rowRemove, 1).toString();
                String sql = "DELETE FROM Audit where audit_id = "+cell+"";
                PreparedStatement pstRemoveRow = null;
                //ResultSet rsRemoveRow =null;
                try {
                    pstRemoveRow = conn.prepareStatement(sql);
                    pstRemoveRow.execute();
                    JOptionPane.showMessageDialog(null,"Removed Succesfully!", mainnameString,JOptionPane.INFORMATION_MESSAGE,null);
                    refreshactivitiesBTN.doClick();
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
        } else if (auditadminactTable.getModel().getRowCount()<=0) {
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
                new AuditSystemGUI().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(AuditSystemGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable auditadminactTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleIcon;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JButton refreshactivitiesBTN;
    private javax.swing.JButton removerowBTN;
    private javax.swing.JButton searchempBTN;
    private javax.swing.JTextField txt_search;
    private javax.swing.JPanel viewpanel;
    // End of variables declaration//GEN-END:variables

}
