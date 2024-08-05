/** @author henrik.tramberend@bht-berlin.de */
package cgg.a01;

import cgtools.*;

// Represents the contents of an image.
// Provides the same color for all pixels.
public class ConstantColor {
  Color color;

  ConstantColor(Color color) {
    this.color = color;
  }

  // Returns the color for the given position.
  public Color getColor(double x, double y) {
    return color;
  }
}