/** @author henrik.tramberend@bht-berlin.de */
package cgtools;

// For details on the record data type, see
// https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Record.html
public record Color(double r, double g, double b) {

  @Override
  public String toString() {
    return String.format("(Col: %.2f %.2f %.2f)", r, g, b);
  }
}
