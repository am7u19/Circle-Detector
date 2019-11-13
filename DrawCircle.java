import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class contains a method that highlights a circle on an image.
 */
public class DrawCircle {

  /**
   * Highlights a circle with given center and radius.
   * Highlight colour changes depending on circle colour.
   *
   * @param image Input image
   * @param xCenter x-coordinate of center of circle
   * @param yCenter y-coordinate of center of circle
   * @param radius Radius of circle
   */
  public static void drawCircle(BufferedImage image, int xCenter, int yCenter, int radius) {
    Graphics imageGraphics = image.getGraphics();

    for (int theta = 0; theta < 360; theta++) {
      int y = (int) (yCenter - radius * Math.sin(theta * Math.PI / 180));
      int x = (int) (xCenter - radius * Math.cos(theta * Math.PI / 180));

      if (x < image.getWidth() && y < image.getHeight()) {
        if (x >= 0 && y >= 0) {
          Color pixelColor = new Color(image.getRGB(xCenter, yCenter));
          if (pixelColor.getRed() < 100) {
            imageGraphics.setColor(Color.RED);
          } else if (pixelColor.getBlue() < 100) {
            imageGraphics.setColor(Color.BLUE);
          } else if (pixelColor.getGreen() < 100) {
            imageGraphics.setColor(Color.GREEN);
          } else {
            imageGraphics.setColor(Color.MAGENTA);
          }
          imageGraphics.drawRect(x, y, 1, 1);
        }
      }
    }
  }
}
