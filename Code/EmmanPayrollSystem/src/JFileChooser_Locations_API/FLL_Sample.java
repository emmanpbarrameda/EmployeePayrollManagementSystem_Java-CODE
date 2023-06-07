// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package JFileChooser_Locations_API;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class FLL_Sample {
    public static void main(String[] args) {
        JFrame frame = createFrame();
        JLabel fileLabel = new JLabel();

        JButton openFileBtn = new JButton("Open File");
        openFileBtn.addActionListener(ae -> chooseFile(fileLabel, frame));

        JPanel panel = new JPanel();
        panel.add(openFileBtn);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(fileLabel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void chooseFile(JLabel fileLabel, JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        String location = FileLastLocation1.FileLocation.get(System.getProperty("user.documents"));
        fileChooser.setCurrentDirectory(new File(location));

        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText(selectedFile.getAbsolutePath());
            FileLastLocation1.FileLocation.put(selectedFile.getParentFile().getAbsolutePath());
        }
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("JTree Remembering File Chooser location via Java Prefs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        return frame;
    }
}
