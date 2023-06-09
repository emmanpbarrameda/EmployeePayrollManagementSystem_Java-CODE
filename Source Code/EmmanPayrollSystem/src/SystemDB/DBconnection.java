// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package SystemDB;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.sqlite.*;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class DBconnection {
    Connection conn;
    
    public Connection getconnection() throws IOException {
        try {
             
            //embed db to this java system
            Class.forName("org.sqlite.JDBC");            
            
            SQLiteDataSource urlsql = new SQLiteDataSource();
            urlsql.setUrl("jdbc:sqlite::resource:"+DBconnection.class.getResource("/SystemDB/database.sqlite").toString());
            conn = urlsql.getConnection();
            
            //get db from system
            //String url = "jdbc:sqlite:C:\\Payroll Management System\\database\\dbpayroll.sqlite";
            //conn=DriverManager.getConnection(url);
            
            //JOptionPane.showOptionDialog(null, "Hello","Empty?", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            //ImageIcon iconofJOP = new ImageIcon("");
            /*final JOptionPane optionPane = new JOptionPane("<html><center>Connecting to the Server...<br/>Please wait PATIENTLY!</center><html>", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            final JDialog dialog = new JDialog();
            dialog.setTitle("VMIS | Enrollment System");
            dialog.setModal(true);
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.pack();
            Image imageloadingtop;
            imageloadingtop = ImageIO.read(getClass().getResource("/projecticon/wait_16px.png"));
            dialog.setIconImage(imageloadingtop); //set icon to dialog
            dialog.setLocationRelativeTo(null); //center the dialog
            //dialog.setSize(200,200);
            //setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);
    
            // Set a timer
            new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            dialog.dispose();
            }
            }).start();
            //dialog.setVisible(true);
            
            *///JOptionPane.showMessageDialog(null,"Connected Successfully!","VMIS | Enrollment System",JOptionPane.INFORMATION_MESSAGE,null);
       
            } catch (ClassNotFoundException | SQLException e) {
                final JProgressBar progressBar1 = new JProgressBar();
                progressBar1.setIndeterminate(true);
                final JOptionPane optionPane1 = new JOptionPane("<html><center>Can't connect to the database!<br>ERR: "+e+"<br>Exiting now...</center></html>", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                final JDialog dialog1 = new JDialog();
                optionPane1.add(progressBar1, BorderLayout.CENTER);
                progressBar1.setIndeterminate(true);
                dialog1.setTitle("SYSTEM ERROR | ECoders");
                dialog1.setAlwaysOnTop(true);
                dialog1.setModal(true);
                dialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                dialog1.setContentPane(optionPane1);
                dialog1.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog1.pack();
                Image imageloadingtop1;
                imageloadingtop1 = ImageIO.read(getClass().getResource("/Images/wait_16px.png"));
                dialog1.setIconImage(imageloadingtop1); //set icon to dialog
                dialog1.setLocationRelativeTo(null); //center the dialog
    
                // Set a timer
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException err) {
                    }
                    dialog1.dispose();
                    System.exit(0);
                }).start();
                dialog1.setVisible(true);
            }
        return conn;
    }
}
