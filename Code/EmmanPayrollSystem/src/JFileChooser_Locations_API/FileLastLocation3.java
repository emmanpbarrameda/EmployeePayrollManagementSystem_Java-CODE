// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package JFileChooser_Locations_API;
import java.util.prefs.Preferences;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public enum FileLastLocation3 {
  FileLocation;
  private static final Preferences prefs3 = Preferences.userRoot()
                                        .node(FileLastLocation3.class.getName());

  public String get(String defaultValue) {
      return prefs3.get(this.name(), defaultValue);
  }

  public void put(String value) {
      prefs3.put(this.name(), value);
  }
}