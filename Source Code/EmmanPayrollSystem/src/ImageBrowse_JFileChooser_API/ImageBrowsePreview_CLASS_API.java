// ©  M A D E		B Y		E M M A N		B A R R A M E D A  © //

package ImageBrowse_JFileChooser_API;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 *
 * @author EMMANUEL PEÑAFLORIDA BARRAMEDA
 */
public class ImageBrowsePreview_CLASS_API extends JComponent implements PropertyChangeListener {
   // Dimensions of image preview's preferred size.

   final static int WIDTH = 150;
   final static int HEIGHT = 100;

   // Reference to ImageIcon whose image is displayed in accessory area. If
   // null reference, nothing is displayed in accessory area.

   public ImageIcon icon;

   // Create ImageBrowsePreview_API component to serve as a file chooser accessory.

   public ImageBrowsePreview_CLASS_API(JFileChooser fc)
   {
      // Register a property change listener with the file chooser so that
      // the ImageBrowsePreview_API component is made aware of file chooser events (such
      // as a user selecting a file).

      fc.addPropertyChangeListener(this);

      // Set the ImageBrowsePreview_API's dimensions to accommodate image thumbnails.
      // The specified values determine the overall size of the file chooser.

      setPreferredSize(new Dimension(WIDTH, HEIGHT));
   }

   // Paint the ImageBrowsePreview_API component in response to a repaint() method call.

   @Override
   protected void paintComponent(Graphics g)
   {
      // When this method is called, the background has already been painted.
      // If icon is null, do nothing. This action causes the current image
      // thumbnail to disappear when the user selects a directory, for example.

      if (icon != null)
      {
         // Paint a white background behind the pixels so that a GIF image's
         // transparent pixel causes white (instead of gray or whatever color
         // is appropriate for this look and feel) to show through.

         Graphics2D g2d = (Graphics2D) g;
         Rectangle bounds = new Rectangle(0, 0, icon.getIconWidth(),
                                          icon.getIconHeight());
         g.setColor(Color.white);
         g2d.fill(bounds);

         // Paint the image -- (0, 0) is the image's upper-left corner, and
         // the upper-left corner of the accessory area.

         icon.paintIcon(this, g, 0, 0);
      }
   }

   // Respond to property change events sent to this ImageBrowsePreview_API component by
   // the file chooser.

   @Override
   public void propertyChange(PropertyChangeEvent e)
   {                
      // Extract property name from event object.

      String propName = e.getPropertyName();

      // Erase any displayed image if user moves up the directory hierarchy.
                      
      if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(propName))
      {
         icon = null;
         repaint();
         return;
      }                  

      // Display selected file. If a directory is selected, erase any
      // displayed image.

      if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(propName))
      {
         // Extract selected file's File object.

         File file = (File) e.getNewValue();

         // If file is null, a directory was selected -- the user is moving
         // between directories. In response, any displayed image in the
         // accessory area must be erased.

         if (file == null)
         {
            icon = null;
            repaint();
            return;
         }

         // Obtain the selected file's icon.

         icon = new ImageIcon(file.getPath());

         // The ImageIcon constructor invokes a Toolkit getImage() method to
         // obtain the image identified by file.getPath(). The image is read
         // from the file unless the image (together with file path/name
         // information) has been cached (for performance reasons). Suppose
         // the user has specified the name of a file and that file does not
         // exist. Toolkit's getImage() method will return an Image with the
         // width and height each set to -1. The "image" associated with this
         // Image will be cached. Suppose the user activates the open file
         // chooser and selects the file to which the image was saved. The
         // previous ImageIcon() constructor will execute, but the image
         // will not be read from the file -- it will be read from the cache
         // (with -1 as the width and as the height). No image will appear in 
         // the preview window; the user will be confused. The solution to
         // this problem is to test the Image's width for -1. If this value
         // is present, Image's flush() method is called on the Image, and a
         // new ImageIcon is created. Internally, Toolkit's getImage() method
         // will read the image from the file -- not from the cache.

         if (icon.getIconWidth() == -1)
         {
            icon.getImage().flush();
            icon = new ImageIcon(file.getPath());
         }

         // Scale icon to fit accessory area if icon too big.

         if (icon.getIconWidth() > WIDTH)
            icon = new ImageIcon(icon.getImage().getScaledInstance (WIDTH, -1,
                                                          Image.SCALE_DEFAULT));

         // Display image.

         repaint();
      }
   }
}
