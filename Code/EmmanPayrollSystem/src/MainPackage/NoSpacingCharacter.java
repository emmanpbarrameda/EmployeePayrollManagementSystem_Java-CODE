// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package MainPackage;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class NoSpacingCharacter extends DocumentFilter {

@Override
public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,AttributeSet attr) throws BadLocationException {
    fb.insertString(offset, text.substring(0, 1).trim() + text.substring(1), attr);
  }

@Override
  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,AttributeSet attrs) throws BadLocationException {
    fb.replace(offset, length, text.substring(0, 1).trim() + text.substring(1), attrs);
  }

}