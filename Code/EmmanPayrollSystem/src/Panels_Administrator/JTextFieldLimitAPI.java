// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package Panels_Administrator;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class JTextFieldLimitAPI extends PlainDocument {
   final public int limit;
   JTextFieldLimitAPI(int limit) {
      super();
      this.limit = limit;
   }
   JTextFieldLimitAPI(int limit, boolean upper) {
      super();
      this.limit = limit;
   }
   @Override
   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
       if (str == null)
         return;
      if ((getLength() + str.length()) <= limit) {
         super.insertString(offset, str, attr);
      }
   }
}