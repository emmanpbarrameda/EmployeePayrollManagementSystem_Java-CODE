// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package UppercaseTypeFilterPackage;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class UppercaseDocumentFilter_API extends DocumentFilter {

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (fb.getDocument().getLength() == 0) {
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            //text += Character.toUpperCase(sb.charAt(0)) + sb.substring(1) + " "; 
            text = sb.toString();
        } 
        fb.insertString(offset, text, attr);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() == 0) {
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            //text += Character.toUpperCase(sb.charAt(0)) + sb.substring(1) + " "; 
            text = sb.toString();
        }
        fb.replace(offset, length, text, attrs);
    }
}