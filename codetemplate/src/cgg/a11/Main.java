package cgg.a11;

import cgg.*;
import cgtools.*;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;

        // Kameraeinstellungen
        Matrix transform = Matrix.translation(0, 0, -5).multiply(Matrix.rotation(0, 2, 0, 90));
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

        // Materialien
        Material polishedMetal = new MetalMaterial(new Color(0.8, 0.8, 0.8), 0);
        Material roughMetal = new MetalMaterial(new Color(0.8, 0.8, 0.8), 0.1);
        Material glassMaterial = new GlasMaterial(new Color(0.8, 0.8, 0.8), 1.5);
        Material diffusedMaterialRed = new DiffusedMaterial(new Color(0.8, 0.2, 0.2));
        Material diffusedMaterialGreen = new DiffusedMaterial(new Color(0.2, 0.8, 0.2));
        Material diffusedMaterialBlue = new DiffusedMaterial(new Color(0.2, 0.2, 0.8));

        // Objekte
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(new Ebene(new Direction(0, 1, 0), new Point(0.0, -0.5, 0.0), new DiffusedMaterial(Vector.gray)));
        shapes.add(new Sphere(new Point(0.4, -0.25, -1.5), 0.25, polishedMetal));
        shapes.add(new Sphere(new Point(-0.4, -0.25, -1.5), 0.25, roughMetal));
        shapes.add(new Sphere(new Point(0, -0.25, -2.0), 0.25, glassMaterial));
        shapes.add(new Sphere(new Point(0.8, -0.25, -1.5), 0.25, diffusedMaterialRed));
        shapes.add(new Sphere(new Point(-0.8, -0.25, -1.5), 0.25, diffusedMaterialGreen));
        shapes.add(new Sphere(new Point(0, -0.25, -1.0), 0.25, diffusedMaterialBlue));

        Group scene = new Group(shapes.toArray(new Shape[0]));

        // Lichtquellen
        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new PointLight(new Point(0, 2, -1), new Color(1, 0, 0), 5)); // Rotes Punktlicht
        lights.add(new PointLight(new Point(-2, 2, -3), new Color(0, 1, 0), 5)); // Grünes Punktlicht
        lights.add(new DirectionalLight(new Direction(1, -1, -1), new Color(0, 0, 1), 5)); // Blaues Richtungslicht

        // Weltkonstruktion
        World world = new World(scene, lights.toArray(new Light[0]));

        // Raytracer-Instanzierung
        Raytracer rt = new Raytracer(camera, world, 5);

        // Bild rendern
        Image image = new Image(width, height);
        image.superSample(rt, 2);

        // Bild speichern
        final String filename = "doc/a11-directillum1.png";
        image.imageWrite(filename);
        System.out.println("Wrote image: " + filename);
    }
}
