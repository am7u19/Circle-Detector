/**
 * This class is used to the decide which pixel is the centre of a circle.
 */
public class Voting {

  /**
   * Takes a certain pixel of the original image and gives a vote to every pixe
   * contained in a certain radius around it.
   * This is done for every radius from 20 pixels to the maximum possible radius.
   *
   * @param y y-coordinate of current pixel
   * @param x x-coordinate of current pixel
   * @param voteArray Array keeping track of votes
   * @param imageHeight Height of input image
   * @param imageWidth Width of input image
   */
  public static void voting(int y, int x, int[][][] voteArray, int imageHeight, int imageWidth) {

    int maxDimension = (int) Math.sqrt(imageHeight * imageHeight + imageWidth * imageWidth);

    for (int radius = 20; radius < maxDimension; radius++) {
      for (int theta = 0; theta < 360; theta++) {

        int yPossibleCenter = (int) (y - radius * Math.sin(theta * Math.PI / 180));
        int xPossibleCenter = (int) (x - radius * Math.cos(theta * Math.PI / 180));

        if (xPossibleCenter < imageWidth && yPossibleCenter < imageHeight) {
          if (xPossibleCenter >= 0 && yPossibleCenter >= 0) {
            voteArray[xPossibleCenter][yPossibleCenter][radius]++;
          }
        }
      }
    }
  }
}
