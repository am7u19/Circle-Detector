import java.awt.image.Raster;

/**
 * This class is used to detect edges on a grayscale image.
 */
public class Convolution {

  /**
   * This method applies the Sobel operator on a 3x3 sub-matrix of a
   * grayscale image's pixel matrix using convolution.
   *
   * @param yCenter y-coordinate of center of 3x3 sub-matrix
   * @param xCenter x-coordinate of center of 3x3 sub-matrix
   * @param grayRaster Represents a 3x3 sub-matrix of the grayscale image's pixel matrix
   * @return Image with highlighted edges
   */
  public static int convolution(int yCenter, int xCenter, Raster grayRaster) {

    int[][] xSobel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    int[][] ySobel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

    int xValue = 0;
    int yValue = 0;

    for (int i = yCenter - 1; i <= yCenter + 1; i++) {
      for (int j = xCenter - 1; j <= xCenter + 1; j++) {
        xValue += grayRaster.getSample(j, i, 0) * xSobel[i - (yCenter - 1)][j - (xCenter - 1)];
        yValue += grayRaster.getSample(j, i, 0) * ySobel[i - (yCenter - 1)][j - (xCenter - 1)];
      }
    }

    return (int) Math.min(255, Math.max(0, Math.sqrt(xValue * xValue + yValue * yValue)));
  }
}
