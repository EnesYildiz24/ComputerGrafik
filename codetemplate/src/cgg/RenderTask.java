/** @author chatGPT mit den Kommentare */

package cgg;

import cgtools.*;

import java.util.concurrent.Callable;

/**
 * Die RenderTask-Klasse implementiert Callable<Image>, um ein Bildsegment in einem separaten Thread zu rendern.
 */
//idee von chat mit Callable
public class RenderTask implements Callable<Image> {
    private final Sampler sampler; // Der Sampler bestimmt die Farbe an jedem Punkt der Szene.
    private final int width; // Die Breite des zu rendernden Bildsegments.
    private final int yStart; // Startpunkt der Y-Koordinate für das zu rendernde Segment.
    private final int yEnd; // Endpunkt der Y-Koordinate für das zu rendernde Segment.
    private final int sampleRate; // Anzahl der Samples pro Pixel, beeinflusst die Bildqualität.

    public RenderTask(Sampler sampler, int width, int yStart, int yEnd, int sampleRate) {
        this.sampler = sampler;
        this.width = width;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.sampleRate = sampleRate;
    }

    @Override
    public Image call() {
        Image segment = new Image(width, yEnd - yStart);
        for (int x = 0; x < width; x++) {
            for (int y = yStart; y < yEnd; y++) {
                Color vorFarbe = new Color(0, 0, 0); // Initialfarbe
                for (int xi = 0; xi < sampleRate; xi++) {
                    for (int yi = 0; yi < sampleRate; yi++) {
                        double rx = Random.random(); // Zufällige Verschiebung in x-Richtung
                        double ry = Random.random(); // Zufällige Verschiebung in y-Richtung
                        double xs = x + (xi + rx) / sampleRate; // Subpixel x-Koordinate
                        double ys = y + (yi + ry) / sampleRate; // Subpixel y-Koordinate
                        Color nebenColor = sampler.getColor(xs, ys); // Farbe des Subpixels
                        // Mittelwertbildung der Subpixel-Farben
                        vorFarbe = new Color(
                                vorFarbe.r() + nebenColor.r() / (sampleRate * sampleRate),
                                vorFarbe.g() + nebenColor.g() / (sampleRate * sampleRate),
                                vorFarbe.b() + nebenColor.b() / (sampleRate * sampleRate));
                    }
                }
                // Anwendung der Gamma-Korrektur auf die gemittelte Farbe
                Color nachColor = new Color(
                        gammakorrektion(vorFarbe.r()),
                        gammakorrektion(vorFarbe.g()),
                        gammakorrektion(vorFarbe.b()));
                // Setzen der korrigierten Farbe für das Pixel
                segment.setPixel(x, y - yStart, nachColor);
            }
        }
        return segment;
    }

    private double gammakorrektion(double i) {
        return Math.pow(i, 1 / 2.2); // Gamma-Korrektur mit dem Standardwert 2.2
    }
}
