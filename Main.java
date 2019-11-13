import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

/**
 * This program finds the most prominent circle in an image named "input.png".
 * Files should be put in a folder named "src" along with the input image.
 * A new image name "output.png" will be created which highlights the most prominent circle.
 * For a circle to be found, it must have a radius of at least 20 pixels.
 */
public class Main {

  public static void main(String[] args) {

    try {
      File inputFile = new File("src//input.png");
      BufferedImage inputImage = ImageIO.read(inputFile);

      int imageHeight = inputImage.getHeight();
      int imageWidth = inputImage.getWidth();
      BufferedImage grayImage = new BufferedImage(imageWidth, imageHeight,
          BufferedImage.TYPE_BYTE_GRAY); //creates grayscale image

      Graphics grayGraphics = grayImage.getGraphics();
      grayGraphics.drawImage(inputImage, 0, 0, null);

      /*
        Creates image that highlights edges using convolution with the Sobel operator
       */
      BufferedImage edgeImage = new BufferedImage(imageWidth, imageHeight,
          BufferedImage.TYPE_BYTE_GRAY);

      Raster grayRaster = grayImage.getData();
      int[][] edgeArray = new int[imageHeight][imageWidth];
     for (int y = 1; y < imageHeight - 1; y++) {
        for (int x = 1; x < imageWidth - 1; x++) {
          edgeArray[y][x] = Convolution.convolution(y, x, grayRaster);
          int a = edgeArray[y][x];
          Color grayColor = new Color(a, a, a);
          edgeImage.setRGB(x, y, grayColor.getRGB());
        }
      }

     int threshold = 200; // Sets threshold for a point being on an edge

      /*
        Every pixel on an edge finds possible centers of circles
       */
     int maxDimension = (int) Math.sqrt(imageHeight * imageHeight + imageWidth * imageWidth);

     int[][][] voteArray = new int[imageWidth + 1][imageHeight + 1][maxDimension + 1];

      for (int y = 1; y < imageHeight - 1; y++) {
        for (int x = 1; x < imageWidth - 1; x++) {
          if (edgeArray[y][x] > threshold) {
            Voting.voting(y, x, voteArray, imageHeight, imageWidth);
          }
        }
      }

      /*
        Finds most voted pixel; this is the center of the most prominent circle
       */
      int maximum = 0;
      int xMax = 0;
      int yMax = 0;
      int radiusMax = 0;

      for (int x = 1; x < imageWidth; x++) {
        for (int y = 1; y < imageHeight; y++) {
          for (int radius = maxDimension; radius > 20; radius--) {
            if (voteArray[x][y][radius] > maximum) {
              maximum = voteArray[x][y][radius];
              xMax = x;
              yMax = y;
              radiusMax = radius;
            }
          }
        }
      }

      /*
        Highlights circle on input image and outputs the resulting image
       */
      DrawCircle.drawCircle(inputImage, xMax, yMax, radiusMax);
      File outputFile = new File("src//output.png");
      ImageIO.write(inputImage, "png", outputFile);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
