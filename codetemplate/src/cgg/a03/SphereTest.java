
package cgg.a03;

import static cgtools.Vector.color;

import cgg.Hit;
import cgg.Sphere;
import cgg.Ray;
import cgtools.*;

public class SphereTest {
    public static void main(String[] args) {
        SphereTest test = new SphereTest();
        test.runtest();
    }

    // Methode zum Ausführen der Tests
    public void runtest() {
        try {
            // Test 1: Strahl trifft die Kugel
            testChecker(new Point(0, 0, -2), 1, new Point(0, 0, 0), new Direction(0, 0, -1), 0,
                    Double.POSITIVE_INFINITY, new Point(0, 0, -1), new Direction(0, 0, 1));
        } catch (AssertionError e) {
            System.out.println("Test 1 failed: " + e.getMessage());
        }

        try {
            // Test 2: Strahl verfehlt die Kugel
            testChecker(new Point(0, 0, -2), 1, new Point(0, 0, 0), new Direction(0, 1, -1), 0,
                    Double.POSITIVE_INFINITY, null, null);
        } catch (AssertionError e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        try {
            // Test 3: Strahl trifft die Kugel an einem anderen Punkt
            testChecker(new Point(0, -1, -2), 1, new Point(0, 0, 0), new Direction(0, 0, -1), 0,
                    Double.POSITIVE_INFINITY, new Point(0, 0, -2), new Direction(0, 0, 1));
        } catch (AssertionError e) {
            System.out.println("Test 3 failed: " + e.getMessage());
        }

        try {
            // Test 4: Strahl startet innerhalb der Kugel, verfehlt aber den zweiten Punkt
            testChecker(new Point(0, 0, -2), 1, new Point(0, 0, -4), new Direction(0, 0, -1), 0,
                    Double.POSITIVE_INFINITY, null, null);
        } catch (AssertionError e) {
            System.out.println("Test 4 failed: " + e.getMessage());
        }

        try {
            // Test 5: Strahl startet innerhalb der Kugel und hat eine beschränkte Reichweite
            testChecker(new Point(0, 0, -4), 1, new Point(0, 0, 0), new Direction(0, 0, -1), 0, 2, null, null);
        } catch (AssertionError e) {
            System.out.println("Test 5 failed: " + e.getMessage());
        }
    }

    // Methode zur Überprüfung eines Tests
    public void testChecker(Point center, double radius, Point x, Direction d, double tMin, double tMax,
            Point treffPoint, Direction normal) {
        // Erstellen einer neuen Kugel
        Sphere kugel = new Sphere(center, radius, color(tMin));
        // Erstellen eines neuen Strahls
        Ray ray = new Ray(d, tMax, tMin, x);
        // Bestimmen des Schnittpunkts zwischen Strahl und Kugel
        Hit hit = kugel.intersect(ray);

        if (hit == null) {
            if (treffPoint != null) {
                throw new AssertionError("Expected a hit at " + treffPoint + " but got none.");
            } else {
                System.out.println("Passed: No hit as expected.");
            }
        } else {
            Point hitPoint = hit.x();
            Direction normalDirection = hit.n();

            if (!hitPoint.equals(treffPoint)) {
                throw new AssertionError("Failed: Hit at " + hitPoint + " with normal " + normalDirection +
                        ", but expected hit at " + treffPoint + " with normal " + normal);
            } else {
                System.out.println("Passed: Hit at " + hitPoint + " with normal " + normalDirection + " as expected.");
            }
        }
    }
}
