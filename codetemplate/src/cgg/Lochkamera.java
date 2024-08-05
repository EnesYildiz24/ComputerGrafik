/** @author chatGPT mit den Kommentare */

package cgg;

import cgtools.*;

public class Lochkamera {
    private double winkel; // Sichtwinkel der Kamera
    private double height; // Höhe des Bildes
    private double width; // Breite des Bildes
    private Matrix transformation; // Transformationsmatrix für die Kameraposition und -orientierung


    // Konstruktor zur Initialisierung der Kamera mit Winkel, Höhe und Breite
    public Lochkamera(double winkel, double height, double width, Matrix transformation) {
        this.winkel = winkel;
        this.height = height;
        this.width = width;
        this.transformation = transformation;
    }

    // Methode zur Generierung eines Strahls (Ray) basierend auf Bildkoordinaten
    // (xp, yp)
    public Ray generateRay(double xp, double yp) {
        // Berechnung der Richtung des Strahls basierend auf den Bildkoordinaten
        Direction direction = Vector.direction(
                xp - width / 2,
                -(yp - height / 2),
                -width / (2 * Math.tan(winkel / 2)));
        // Normalisierung der berechneten Richtung
        Direction normDirection = Vector.normalize(direction);

        // Ursprung des Strahls ist der Koordinatenursprung (0, 0, 0)
        Point transformedOrigin = Matrix.multiply(transformation, new Point(0, 0, 0));
        Direction transformedDirection = Matrix.multiply(transformation, Vector.normalize(normDirection));
        // Rückgabe des generierten Strahls mit der berechneten Richtung und dem
        // Ursprung
        return new Ray(transformedDirection, Double.POSITIVE_INFINITY, Util.EPSILON, transformedOrigin);
    }
}
