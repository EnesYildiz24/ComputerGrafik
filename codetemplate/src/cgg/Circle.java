/** @author henrik.tramberend@bht-berlin.de */
/** @author chatGPT mit den Kommentare */
package cgg;

import cgtools.*;

// Represents the contents of an image.
// Provides the same color for all pixels.
public class Circle {
    Color backgroundColor = new Color(0, 0, 0); // Hintergrundfarbe für den Kreis (Schwarz in diesem Fall)
    Color color; // Farbe des Kreises
    int width; // Breite des Bildes
    int height; // Höhe des Bildes
    double radius; // Radius des Kreises

    // Konstruktor, der die Farbe, Breite, Höhe und den Radius des Kreises setzt
    public Circle(Color color, int width, int height, double radius) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    // Methode zur Berechnung der Farbe eines Pixels basierend auf seinen
    // Koordinaten (x, y)
    // Wenn das Pixel innerhalb des Kreises liegt, wird die Kreisfarbe zurückgegeben
    // Andernfalls wird die Hintergrundfarbe zurückgegeben
    public Color getColor(double x, double y) {
        // Mittelpunkt des Kreises (cx, cy)
        double cx = width / 2;
        double cy = height / 2;
        // Berechnung des quadratischen Abstands vom Punkt (x, y) zum Mittelpunkt (cx,
        // cy)
        double cdDistance = Math.pow(x - cx, 2) + Math.pow(y - cy, 2);

        // Wenn der Abstand kleiner als der Radius des Kreises ist, liegt der Punkt im
        // Kreis
        if (cdDistance < Math.pow(radius, 2)) {
            // Rückgabe der Kreisfarbe
            return color;
        }
        // Andernfalls Rückgabe der Hintergrundfarbe
        return backgroundColor;
    }
}
