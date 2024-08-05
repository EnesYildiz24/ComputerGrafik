package cgg.a10;

import cgtools.*;
import cgg.*;

public class Main {
  public static void main(String[] args) {
    final int width = 1920;
    final int height = 1080;

    // Kameraeinstellungen
    Matrix transform = Matrix.translation(0, 0, -3).multiply(Matrix.rotation(0, 1, 0, 180));
    Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

    // Definiere neue Materialien
    Material polishedMetal = new MetalMaterial(new Color(0.8, 0.8, 0.8), 0); // Poliertes Metall ohne Rauheit
    Material roughMetal = new MetalMaterial(new Color(0.8, 0.8, 0.8), 0.1); // Angeschliffenes Metall mit Rauheit
    Material GlasMaterial = new MetalMaterial(new Color(0.8, 0.8, 0.8),0); // glas


    // Bestehende Materialien
    Material dGray = new DiffusedMaterial(Vector.gray);
    Material dViolet = new DiffusedMaterial(Vector.violet);
    Material dYellow = new DiffusedMaterial(Vector.yellow);
    Material bgWhite = new BackgroundMaterial(Vector.white);

    // Szeneaufbau
    Shape background = new Background(bgWhite);
    Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0.0, -0.5, 0.0), dGray);

    // Metallkugeln zu den bestehenden hinzu
    Shape globe1 = new Sphere(Vector.point(0.4, -0.25, -1.5), 0.25, polishedMetal);
    Shape globe2 = new Sphere(Vector.point(-0.4, -0.25, -1.5), 0.25, roughMetal);
    Shape globe5 = new Sphere(Vector.point(0, -0.2, -0.5), 0.25, GlasMaterial);
    // Bestehende Kugeln
    Shape globe3 = new Sphere(Vector.point(0.3, -0.25, 0), 0.2, dViolet);
    Shape globe4 = new Sphere(Vector.point(-0.3, -0.25, 0), 0.2, dYellow);

    Shape[] groupShapes = {background, ground, globe1, globe2, globe3, globe4,globe5};
    Shape group = new Group(groupShapes);

    // Raytracer-Instanz
    Raytracer rt = new Raytracer(camera, group);

    // Bild erzeugen
    Image image = new Image(width, height);
    image.superSample(rt, 20);

    // Bild speichern
    final String filename = "doc/a10-refraction.png";
    image.imageWrite(filename);
    System.out.println("Wrote image: " + filename);
  }
}
