// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package MainPackage;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class StringFormatter {
    public static String capitalizeWord(String str) {
        String words [] = str.split("\\s");
        String capitalizeWord = "";
        
        for(String w:words) {
            String first = w.substring(0,1);
            String afterfirst = w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        
        return capitalizeWord.trim();
    }
}