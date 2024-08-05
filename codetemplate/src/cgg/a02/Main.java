/** @author henrik.tramberend@bht-berlin.de */
package cgg.a02;

import cgg.*;

public class Main {

  public static void main(String[] args) {
    final int width = 512;
    final int height = 256;

    Image image = new Image(width, height);
    ColoredDisc discs = ColoredDisc.createRandomDiscs(30);

    image.superSample(discs);

    final String filename = "doc/a02-discs-supersampling.png";
    image.imageWrite(filename);
    System.out.println("Wrote image: " + filename);
  }
}
