/** @author henrik.tramberend@bht-berlin.de */
package cgg.a01;

import static cgtools.Vector.*;
import cgg.*;

public class Main {

  public static void main(String[] args) {
    final int width = 512;
    final int height = 256;

    // This class instance defines the contents of the image.
    ConstantColor content = new ConstantColor(red);
    Circle cDisc = new Circle(red, width, height, 50);
    PolkaDots many = new PolkaDots(height, width, 10.0, 10, blue);

    // Creates an image and iterates over all pixel positions inside the image.
    Image image = new Image(width, height);
    for (int x = 0; x != width; x++) {
      for (int y = 0; y != height; y++) {
        // Sets the color for one particular pixel.
        image.setPixel(x, y, content.getColor(x, y));
      }
    }

    // Write the image to disk.
    final String filename = "doc/a01-image.png";
    image.imageWrite(filename);
    System.out.println("Wrote image: " + filename);

    Image image1 = new Image(width, height);
    for (int x = 0; x != width; x++) {
      for (int y = 0; y != height; y++) {
        // Sets the color for one particular pixel.
        image1.setPixel(x, y, cDisc.getColor(x, y));
      }
    }

    // Write the image to disk.
    final String filename2 = "doc/a01-image.png";
    image1.imageWrite(filename2);
    System.out.println("Wrote image: " + filename2);

    Image image2 = new Image(width, height);
    for (int x = 0; x != width; x++) {
      for (int y = 0; y != height; y++) {
        // Sets the color for one particular pixel.
        image2.setPixel(x, y, many.getColor(x, y));
      }
    }

    // Write the image to disk.
    final String filename3 = "doc/a01-polka-dots.png";
    image2.imageWrite(filename3);
    System.out.println("Wrote image: " + filename3);

  }

}
