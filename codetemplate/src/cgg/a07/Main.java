package cgg.a07;

import cgtools.*;

import java.util.ArrayList;
import java.util.List;

import cgg.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;

        // Kameraeinstellungen
        Matrix transform = Matrix.translation(-3, 0, -10).multiply(Matrix.rotation(0, 1, 0, 180));
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

        // Materialdefinitionen
        Material dPink = new DiffusedMaterial(Vector.color(1,0.7,0.8));
        Material darkGreen = new DiffusedMaterial(Vector.color(0,0.5,0.1));
        Material dBlue = new DiffusedMaterial(Vector.blue);
        Material dRed = new DiffusedMaterial(Vector.red);
        Material dYellow = new DiffusedMaterial(Vector.yellow);
        Material dWhite = new DiffusedMaterial(Vector.white);
        Material dGray = new DiffusedMaterial(Vector.gray);
        Material dBlack = new DiffusedMaterial(Vector.color(0.1, 0.1, 0.1));

        // Hintergrund und Boden
        Shape background = new Background(new BackgroundMaterial(Vector.color(0.5, 0.7, 1)));
        Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0, -1, 0), dWhite);

        // Objekte
        Group objects = createParkScene(dRed, dBlue, dYellow, dWhite, dGray, dBlack, darkGreen,dPink);


        // Szene zusammenstellen
        Shape[] sceneShapes = { background, ground, objects };
        Group scene = new Group(sceneShapes);

        // Raytracer-Instanz
        Raytracer rt = new Raytracer(camera, scene);

        // Bild rendern
        Image image = new Image(width, height);
        image.superSample(rt, 3);

        final String filename = "doc/a07-instances2.png";
        image.imageWrite(filename);
        System.out.println("Wrote image: " + filename);
    }
    //@Author Chatgpt
private static Group createParkScene(Material dRed, Material dBlue, Material dYellow, Material dWhite, Material dGray, Material dBlack, Material dPink, Material darkGreen) {
    List<Shape> objects = new ArrayList<>();

    final int MAX_SHAPES = 100;  // Maximale Anzahl von Shapes, die hinzugefügt werden dürfen
    int currentShapeCount = 0;  // Aktueller Zähler für die Anzahl der hinzugefügten Shapes

    // Bäume mit einer spezifischen räumlichen Anordnung
    Matrix treePlacement = Matrix.translation(-5, 0, -5);
    List<Shape> trees = new ArrayList<>();
    for (int i = 0; i <=  10; i += 2) {
        if (currentShapeCount >= MAX_SHAPES) break;
        for (int j = 0; j <= 10; j += 2) {
            if (currentShapeCount >= MAX_SHAPES) break;
            double x = i * 1.0;
            double z = j * 1.0;
            Material trunkMaterial = ((i + j) % 4 == 0) ? dGray : dBlack;
            trees.add(new Cylinder(Vector.point(x, -2, z), 0.2, 0.2, 2, trunkMaterial));
            Material leafMaterial = (i + j) % 3 == 0 ? dWhite : darkGreen;
            trees.add(new Sphere(Vector.point(x, 0.7, z), 0.8, leafMaterial));
            currentShapeCount += 2;  // Jedes Mal zwei Shapes hinzufügen
            if (currentShapeCount >= MAX_SHAPES) break;
        }
    }
    if (!trees.isEmpty()) {
        objects.add(new Group(trees.toArray(new Shape[0]), treePlacement));
    }

    // Schneemänner, positioniert in einer Linie
    List<Shape> snowmen = new ArrayList<>();
    double minX = -4.0, maxX = 4.0;  // Bereich für die x-Koordinate
    double minZ = -2.0, maxZ = 2.0;   // Bereich für die z-Koordinate
    int snowmanCount = 12;            // Anzahl der zu erstellenden Schneemänner
    
    for (int i = 0; i < snowmanCount; i++) {
        double x = minX + Math.random() * (maxX - minX);
        double z = minZ + Math.random() * (maxZ - minZ);
    
        snowmen.add(new Sphere(Vector.point(x, -0.3, z), 0.4, dWhite));
        snowmen.add(new Sphere(Vector.point(x, 0.1, z), 0.25, dWhite));
        snowmen.add(new Sphere(Vector.point(x + 0.12, 0.14, z - 0.25), 0.03, dBlack));
        snowmen.add(new Sphere(Vector.point(x - 0.12, 0.14, z - 0.25), 0.03, dBlack));
        snowmen.add(new Cylinder(Vector.point(x, 0.05, z - 0.06), 0.2, 0.2, 0.02, dBlack));
        currentShapeCount += 5;  // Jedes Mal fünf Shapes hinzufügen
    
        if (currentShapeCount >= MAX_SHAPES) break;  // Prüfen, ob das Maximum erreicht ist
    }
    
    Matrix snowmenPlacement = Matrix.translation(0, -0.5, 0); // Anpassung der zentralen Höhe
    if (!snowmen.isEmpty()) {
        objects.add(new Group(snowmen.toArray(new Shape[0]), snowmenPlacement));
    }
    return new Group(objects.toArray(new Shape[0]));
}

    
    
}
