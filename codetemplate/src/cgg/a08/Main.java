package cgg.a08;

import cgtools.*;
import cgg.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;
        final int sampleRate = 3;

        // Kameraeinstellungen
        Matrix transform = Matrix.translation(-3, 0, -20).multiply(Matrix.rotation(0, 1, 0, 180));
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);
        final int maxThreads = Runtime.getRuntime().availableProcessors(); // Anzahl der verfügbaren CPU-Kerne

        // Materialdefinitionen
        Material dPink = new DiffusedMaterial(Vector.color(1, 0.7, 0.8));
        Material darkGreen = new DiffusedMaterial(Vector.color(0, 0.5, 0.1));
        Material dBlue = new DiffusedMaterial(Vector.blue);
        Material dRed = new DiffusedMaterial(Vector.red);
        Material dYellow = new DiffusedMaterial(Vector.yellow);
        Material dWhite = new DiffusedMaterial(Vector.white);
        Material dGray = new DiffusedMaterial(Vector.gray);
        Material dBlack = new DiffusedMaterial(Vector.color(0.1, 0.1, 0.1));

        // Hintergrund und Boden
        Shape background = new Background(new BackgroundMaterial(Vector.color(1, 0.7, 1)));
        Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0, -1, 0), dBlack);

        // Objekte
        Group objects = createParkScene(dRed, dBlue, dYellow, dWhite, dGray, dBlack, darkGreen, dPink);

        // Szene zusammenstellen
        Shape[] sceneShapes = { background, ground, objects };
        Group scene = new Group(sceneShapes);

        // Raytracer-Instanz
        Raytracer rt = new Raytracer(camera, scene);
        
        benchmark(width, height, rt, sampleRate, maxThreads);

    }

    //vom aufgabe 7
    private static Group createParkScene(Material dRed, Material dBlue, Material dYellow, Material dWhite,
            Material dGray, Material dBlack, Material darkGreen, Material dPink) {
        List<Shape> objects = new ArrayList<>();

        final int MAX_SHAPES = 50000; // Maximale Anzahl von Shapes, die hinzugefügt werden dürfen
        int currentShapeCount = 0; // Aktueller Zähler für die Anzahl der hinzugefügten Shapes

        // Schneemänner, positioniert in einer Linie
        List<Shape> snowmen = new ArrayList<>();
        double minX = -40.0, maxX = 40.0; // Bereich für die x-Koordinate
        double minZ = -20.0, maxZ = 20.0; // Bereich für die z-Koordinate
        int snowmanCount = 1000; // Anzahl der zu erstellenden Schneemänner

        for (int i = 0; i < snowmanCount; i++) {
            double x = minX + Math.random() * (maxX - minX);
            double z = minZ + Math.random() * (maxZ - minZ);

            snowmen.add(new Sphere(Vector.point(x, -0.3, z), 0.4, dWhite));
            snowmen.add(new Sphere(Vector.point(x, 0.1, z), 0.25, dWhite));
            snowmen.add(new Sphere(Vector.point(x + 0.12, 0.14, z - 0.25), 0.03, dBlack));
            snowmen.add(new Sphere(Vector.point(x - 0.12, 0.14, z - 0.25), 0.03, dBlack));
            snowmen.add(new Cylinder(Vector.point(x, 0.05, z - 0.06), 0.2, 0.2, 0.02, dBlack));
            currentShapeCount += 5; // Jedes Mal fünf Shapes hinzufügen

            if (currentShapeCount >= MAX_SHAPES)
                break; // Prüfen, ob das Maximum erreicht ist
        }

        Matrix snowmenPlacement = Matrix.translation(0, -0.5, 0); // Anpassung der zentralen Höhe
        if (!snowmen.isEmpty()) {
            objects.add(new Group(snowmen.toArray(new Shape[0]), snowmenPlacement));
        }
        return new Group(objects.toArray(new Shape[0]));
    }

    //author chatgpt
    private static void benchmark(int width, int height, Raytracer rt, int sampleRate, int maxThreads) {
        try (FileWriter writer = new FileWriter("benchmark_results.txt")) {
            System.out.println("Starting benchmark...");
            for (int nThreads = 1; nThreads <= maxThreads + 1; nThreads++) {
                long startTime = System.currentTimeMillis();  // Millisekunden seit Mitternacht, 1. Januar 1970 UTC.
    
                ParallelImageRenderer renderer = new ParallelImageRenderer(width, height, rt, nThreads, sampleRate);
                Image finalImage = renderer.render();
    
                long endTime = System.currentTimeMillis();
                double duration = (endTime - startTime) / 1000.0; // Berechnen der Dauer in Sekunden und umwandeln in double für Dezimalstelle.
    
                System.out.printf("Threads: %d, Time: %.1f s%n", nThreads, duration); // Ausgabe mit einer Dezimalstelle.
                writer.write(String.format("Threads:%d,Time:%.1f\n", nThreads, duration)); // Schreiben mit einer Dezimalstelle.
    
                // Optional: Speichern Sie das Bild für ausgewählte Thread-Anzahlen
                if (nThreads == 1) {
                    String filename = "doc/a08-benchmark-scene.png";
                    finalImage.imageWrite(filename);
                    System.out.println("Saved image for " + nThreads + " threads.");
                }
            }
            System.out.println("Benchmark completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

