/** @author chatGPT mit den Kommentare */
package cgg;

import cgtools.Color;

public class PolkaDots {
    int height; // Höhe des Bildes
    int width; // Breite des Bildes
    double radius; // Radius der Punkte
    double abstand; // Abstand zwischen den Punkten
    Color farbe; // Farbe der Punkte
    Color backgroundColor = new Color(0, 0, 0); // Hintergrundfarbe des Bildes (schwarz in diesem Fall)

    // Konstruktor zur Initialisierung der Höhe, Breite, Radius, Abstand und Farbe
    // der Punkte
    public PolkaDots(int height, int width, double radius, double abstand, Color farbe) {
        this.height = height;
        this.width = width;
        this.radius = radius;
        this.abstand = abstand;
        this.farbe = farbe;
    }

    // Methode zur Berechnung der Farbe eines Pixels basierend auf seinen
    // Koordinaten (x, y)
    // Wenn das Pixel innerhalb eines Punkts liegt, wird die Punktfarbe
    // zurückgegeben
    // Andernfalls wird die Hintergrundfarbe zurückgegeben
    /** @author chatGPT */
    public Color getColor(double x, double y) {
        // Berechnen der nächstgelegenen Rasterpunkte
        double nearestX = Math.round(x / (abstand + 2 * radius)) * (abstand + 2 * radius);
        double nearesty = Math.round(y / (abstand + 2 * radius)) * (abstand + 2 * radius);

        // Überprüfung, ob der Punkt (x, y) innerhalb eines Punkts liegt
        if (Math.pow(x - nearestX, 2) + Math.pow(y - nearesty, 2) <= Math.pow(radius, 2)) {
            // Rückgabe der Punktfarbe
            return farbe;
        }
        // Rückgabe der Hintergrundfarbe
        return backgroundColor;
    }
}
