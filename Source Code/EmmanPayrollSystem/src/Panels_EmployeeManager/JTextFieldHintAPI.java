// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_EmployeeManager;
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.event.FocusEvent;  
import java.awt.event.FocusListener;  
import javax.swing.plaf.basic.BasicTextFieldUI;  
import javax.swing.text.JTextComponent;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class JTextFieldHintAPI extends BasicTextFieldUI implements FocusListener {  
    public final String hint;
    public final Color  hintColor;

    public JTextFieldHintAPI(String hint, Color hintColor) {
        this.hint = hint;
        this.hintColor = hintColor;
    }

    public void repaint() {
        if (getComponent() != null) {
            getComponent().repaint();
        }
    }

    @Override
    protected void paintSafely(Graphics g) {
        // Render the default text field UI
        super.paintSafely(g);
        // Render the hint text
        JTextComponent component = getComponent();
        if (component.getText().length() == 0 && !component.hasFocus()) {
            g.setColor(hintColor);
            int padding = (component.getHeight() - component.getFont().getSize()) / 2;
            int inset = 3;
            g.drawString(hint, inset, component.getHeight() - padding - inset);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        repaint();
    }

    @Override
    public void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }

    @Override
    public void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}