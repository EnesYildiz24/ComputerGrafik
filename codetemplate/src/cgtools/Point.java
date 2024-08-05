/** @author henrik.tramberend@bht-berlin.de */
package cgtools;

import static cgtools.Vector.*;

// For details on the record data type, see
// https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Record.html
public record Point(double x, double y, double z) {

  @Override
  public String toString() {
    return String.format("(Pnt: %.2f %.2f %.2f)", x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Point))
      return false;
    if (o == this)
      return true;
    Point v = (Point) o;
    return Util.isZero(length(subtract(this, v)));
  }
}
