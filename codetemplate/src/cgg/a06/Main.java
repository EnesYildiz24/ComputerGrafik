package cgg.a06;

import cgtools.*;

import cgg.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;

        // Kameraeinstellungen
        Matrix transform = Matrix.translation(-3, 1, -20).multiply(Matrix.rotation(0, 1, 0, 180));
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

        // Materialdefinitionen
        Material dGreen = new DiffusedMaterial(Vector.green);
        Material dPink = new DiffusedMaterial(Vector.color(1, 0.7, 0.8));
        Material darkGreen = new DiffusedMaterial(Vector.color(0, 0.5, 0.1));
        Material dBlue = new DiffusedMaterial(Vector.blue);
        Material dRed = new DiffusedMaterial(Vector.red);
        Material dYellow = new DiffusedMaterial(Vector.yellow);
        Material dWhite = new DiffusedMaterial(Vector.white);
        Material dGray = new DiffusedMaterial(Vector.gray);
        Material dBlack = new DiffusedMaterial(Vector.color(0.1, 0.1, 0.1));

        // Hintergrund und Boden
        Shape background = new Background(new BackgroundMaterial(Vector.color(0.5, 0.7, 1)));
        Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0, -1, 0), dGreen);

        // Objekte
        Group objects = createParkScene(dRed, dBlue, dYellow, dWhite, dGray, dBlack, darkGreen, dPink);

        // Szene zusammenstellen
        Shape[] sceneShapes = { background, ground, objects };
        Group scene = new Group(sceneShapes);

        // Raytracer-Instanz
        Raytracer rt = new Raytracer(camera, scene);

        // Bild rendern
        Image image = new Image(width, height);
        image.superSample(rt, 5);

        final String filename = "doc/a06-camera1.png";
        image.imageWrite(filename);
        System.out.println("Wrote image: " + filename);
    }

    // @Author Chatgpt
    private static Group createParkScene(Material dRed, Material dBlue, Material dYellow, Material dWhite,
            Material dGray, Material dBlack, Material dPink, Material darkGreen) {
        Shape[] objects = new Shape[69]; // Definieren des Arrays mit der festen Länge von 50
        int index = 0;

        // Bäume (Zylinder und Kugeln als Kronen)
        for (int i = -5; i <= 5; i += 2) {
            for (int j = -5; j <= 5; j += 2) {
                double x = i * 2.0;
                double z = j * 2.0;
                Material trunkMaterial = (i + j) % 4 == 0 ? dGray : dBlack;
                // Sicherstellen, dass der Index innerhalb der Array-Grenzen ist
                if (index < objects.length)
                    objects[index++] = new Cylinder(Vector.point(x, 0, z), 0.2, 0.2, 2, trunkMaterial);
                Material leafMaterial = (i + j) % 3 == 0 ? dPink : darkGreen;
                if (index < objects.length)
                    objects[index++] = new Sphere(Vector.point(x, 2.5, z), 1, leafMaterial);
            }
        }

        return new Group(objects);
    }

}
