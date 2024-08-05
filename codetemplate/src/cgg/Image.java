package cgg;

import cgtools.*;

public class Image {

    // Array zur Speicherung der Bilddaten (RGB-Werte für jeden Pixel)
    private double[] daten;
    // Breite des Bildes
    private int width;
    // Höhe des Bildes
    private int height;
    // Gammawert für die Gamma-Korrektur (Standardwert für Monitore ist 2.2)
    private double gamma = 2.2;

    // Konstruktor zur Initialisierung der Bildbreite, -höhe und des Datenarrays
    public Image(int width, int height,double gamma) {
        this.width = width;
        this.height = height;
        this.daten = new double[width * height * 3];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Image(int width, int height) {
        this(width, height, 2.2);
    }

    public void setPixel(int x, int y, Color color) {
        int index = (y * width + x) * 3;
        daten[index] = gammakorrektion(color.r());
        daten[index + 1] = gammakorrektion(color.g());
        daten[index + 2] = gammakorrektion(color.b());
    }

    public Color getColor(int x, int y) {
        int index = (y * width + x) * 3;
        return new Color(
            linearize(daten[index]),
            linearize(daten[index + 1]),
            linearize(daten[index + 2])
        );
    }

    // Berechnet die Gamma-Korrektur für einen gegebenen Wert
    private double gammakorrektion(double value) {
        return Math.pow(value, 1 / gamma);
    }

    // Linearisiert einen gamma-korrigierten Wert
    private double linearize(double value) {
        return Math.pow(value, gamma);
    }

    // Führt Super-Sampling-Anti-Aliasing durch, ohne Gamma-Korrektur bei der
    // Mittelwertbildung
    public void superSample(Sampler source, int sampler) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color accumulatedColor = new Color(0, 0, 0); // Initialfarbe
                for (int xi = 0; xi < sampler; xi++) {
                    for (int yi = 0; yi < sampler; yi++) {
                        double rx = Random.random(); // Zufällige Verschiebung in x-Richtung
                        double ry = Random.random(); // Zufällige Verschiebung in y-Richtung
                        double xs = x + (xi + rx) / sampler; // Subpixel x-Koordinate
                        double ys = y + (yi + ry) / sampler; // Subpixel y-Koordinate
                        Color subPixelColor = source.getColor(xs, ys); // Farbe des Subpixels
                        accumulatedColor = Vector.add(accumulatedColor,
                                Vector.divide(subPixelColor, sampler * sampler));
                    }
                }
                // Setzen der korrigierten Farbe für das Pixel
                setPixel(x, y, accumulatedColor);
            }
        }
    }

    // Schreibt das Bild in eine Datei
    public void imageWrite(String filename) {
        ImageWriter.write(filename, daten, width, height);
    }
}
