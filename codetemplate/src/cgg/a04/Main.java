package cgg.a04;

import cgtools.*;

import cgg.*;

public class Main {
    public static void main(String[] args) {
        final int width = 512;
        final int height = 512;
        Matrix transform = Matrix.translation(-3, 0, -10).multiply(Matrix.rotation(0, 1, 0, 180));

        // Erstellen der Kamera
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

        Material red = new BackgroundMaterial(Vector.red);

        Material green = new BackgroundMaterial(Vector.green);

        Material blue = new BackgroundMaterial(Vector.blue);

        Material dGray = new BackgroundMaterial(Vector.gray);

        Material bgWhite = new BackgroundMaterial(Vector.white);
        Shape background = new Background(bgWhite);

        Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0.0, -0.5, 0.0), dGray);
        Shape globe1 = new Sphere(Vector.point(-0.6, -0.25, -1.5), 0.15, red);
        Shape globe2 = new Sphere(Vector.point(-0.2, -0.25, -1.5), 0.15, green);
        Shape globe3 = new Sphere(Vector.point(0.2, -0.25, -1.5), 0.15, blue);

        // Erstellen der Gruppe
        Shape[] groupShapes = new Shape[] { background, ground, globe1, globe2, globe3 };
        Shape group = new Group(groupShapes);

        // Raytracer und Bild erstellen
        Raytracer tracer = new Raytracer(camera, group);

        Image image = new Image(width, height);
        image.superSample(tracer, 10);

        // Bild speichern
        final String filename = "doc/a04-scene.png";
        image.imageWrite(filename);
        System.out.println("Wrote image: " + filename);
    }
}
