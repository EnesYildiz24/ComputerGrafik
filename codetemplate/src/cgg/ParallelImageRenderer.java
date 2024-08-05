/** @author chatGPT mit den Kommentare */

package cgg;

import cgtools.*;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Ein Renderer, der die Bildgenerierung auf mehrere Threads verteilt,
 * um die Berechnung von Bildsegmenten parallel durchzuführen.
 */
public class ParallelImageRenderer {
    private final int width; // Die Breite des zu rendernden Bildes.
    private final int height; // Die Höhe des zu rendernden Bildes.
    private final Sampler sampler; // Sampler-Interface für die Farbberechnung jedes Pixels.
    private final int nThreads; // Anzahl der Threads, die für das Rendering verwendet werden.
    private final int sampleRate; // Anzahl der Samples pro Pixel, beeinflusst die Bildqualität und Renderzeit.

    public ParallelImageRenderer(int width, int height, Sampler sampler, int nThreads, int sampleRate) {
        this.width = width;
        this.height = height;
        this.sampler = sampler;
        this.nThreads = nThreads;
        this.sampleRate = sampleRate;
    }

    // teilweise chatgpt
    public Image render() {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);// Executor-Service zur Thread-Verwaltung.
        List<Future<Image>> futures = new ArrayList<>();// Future-Liste für die asynchronen Render-Aufgaben.
        int segmentHeight = height / nThreads; // Berechnet die Höhe jedes Segments, das von einem Thread gerendert
                                               // wird.

        // Erzeugt Render-Aufgaben für jeden Thread.
        for (int i = 0; i < nThreads; i++) {
            int yStart = i * segmentHeight; // Startpunkt des Segments auf der y-Achse.
            int yEnd = (i == nThreads - 1) ? height : yStart + segmentHeight; // Endpunkt des Segments, berücksichtigt
                                                                              // das letzte Segment.
            futures.add(executor.submit(new RenderTask(sampler, width, yStart, yEnd, sampleRate)));
            // Submit der RenderTask zur Ausführung.
        }

        Image finalImage = new Image(width, height);

        /** @author chatGPT */
        try {
            // Mergen der gerenderten Segmente in das finale Bild.
            for (int i = 0; i < futures.size(); i++) {
                Image segment = futures.get(i).get(); // Wartet auf das Ergebnis des Future und holt das Segment.
                mergeSegment(finalImage, segment, i * segmentHeight);// Integriert das Segment in das finale Bild.
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();// Exception Handling für unterbrochene oder fehlerhafte Tasks.
        } finally {
            executor.shutdown();// Schließt den ExecutorService, um Systemressourcen freizugeben.
        }

        return finalImage;
    }

    // Fügt ein gerendertes Bildsegment in das Gesamtbild ein.
    private void mergeSegment(Image finalImage, Image segment, int yOffset) {
        int width = finalImage.getWidth(); // Holt die Breite des finalen Bildes.

        // Schleife durch alle Pixel des Segments und setzt die Pixelwerte im finalen
        // Bild.
        for (int y = 0; y < segment.getHeight(); y++) {
            for (int x = 0; x < width; x++) {
                Color color = segment.getColor(x, y); // Holt die Farbe des aktuellen Pixels im Segment.
                finalImage.setPixel(x, y + yOffset, color); // Setzt die Farbe im finalen Bild.
            }
        }
    }
}
