// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package JFileChooser_Locations_API;
import java.util.prefs.Preferences;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public enum FileLastLocation1 {
  FileLocation;
  private static final Preferences prefs1 = Preferences.userRoot()
                                        .node(FileLastLocation1.class.getName());

  public String get(String defaultValue1) {
      return prefs1.get(this.name(), defaultValue1);
  }

  public void put(String value) {
      prefs1.put(this.name(), value);
  }
}