// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package MainPackage;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
class ToastDriverSample extends JFrame implements ActionListener { 
  
    // create a frame 
    static JFrame f; 
  
    // textfield 
    static JTextField tf; 
  
    public static void main(String args[]) 
    { 
        // create the frame 
        f = new JFrame("toast"); 
  
        ToastDriverSample d = new ToastDriverSample(); 
  
        // textfield 
        tf = new JTextField(16); 
  
        // button 
        Button b = new Button("create"); 
  
        // add action listener 
        b.addActionListener(d); 
  
        // create a panel 
        JPanel p = new JPanel(); 
  
        p.add(tf); 
        p.add(b); 
  
        // add panel 
        f.add(p); 
  
        // setSize 
        f.setSize(500, 500); 
  
        f.show(); 
    } 
  
    // if button is pressed 
    @Override
    public void actionPerformed(ActionEvent e) 
    { 
    Dimension dimToast = Toolkit.getDefaultToolkit().getScreenSize();
    int widthvarToast = this.getSize().width;
    int heightvatToast = this.getSize().height;
    int xPosToast = (dimToast.width-widthvarToast)/2;
    int yPosToast = (dimToast.width-heightvatToast)/2;  
        // create a ToastManager message 
        
        String testString = "Hello";
        
        ToastManager t = new ToastManager(tf.getText(), xPosToast, yPosToast); 
        
        //ToastManager t1 = new ToastManager(testString, x, y);
  
        // call the method 
        t.showToast(); 
        //t1.showtoast(); 
    } 
} 