/** @author henrik.tramberend@bht-berlin.de */
package cgg.a03;

import cgtools.*;

import static cgtools.Vector.color;

import cgg.*;

public class Main {

  public static void main(String[] args) {
    final int width = 512; // 512;
    final int height = 256; // 256;

    Image image = new Image(width, height);
    Lochkamera a32 = new Lochkamera(Math.PI / 2, 10, 10);
    Lochkamera kamara = new Lochkamera(Math.PI / 2, height, width);
    // wegen aufgabenteil 4 auch ein hintergrund erstellen sonst(Exception in thread
    // "main" java.lang.NullPointerException: Cannot invoke
    // "cgg.Shape.intersect(cgg.Ray)" because "this.background" is null)
    Shape background = new Background(color(0, 0, 0));

    // Raytracer mit Hintergrund erstellen
    Raytracer raytracer = new Raytracer(kamara, background);
    raytracer.addShape(new Sphere(new Point(0, 0, -7), 2, color(1, 1, 1)));
    raytracer.addShape(new Sphere(new Point(2, 0, -6), 0.5, color(0, 1, 0.2)));
    raytracer.addShape(new Sphere(new Point(-2, 0, -6), 0.5, color(0.2, 0.1, 1)));
    raytracer.addShape(new Sphere(new Point(0, -1, -4), 0.75, color(0, 1, 1)));
    raytracer.addShape(new Sphere(new Point(0, 2, -7), 1.5, color(0, 1, 2)));

    image.superSample(raytracer);

    Ray ray1 = a32.generateRay(0, 0);
    Ray ray2 = a32.generateRay(5, 5);
    Ray ray3 = a32.generateRay(10, 10);

    System.out.println(printVector(ray1.getDirection()));
    System.out.println(printVector(ray2.getDirection()));
    System.out.println(printVector(ray3.getDirection()));

    final String filename = "doc/a03-spheres.png";
    image.imageWrite(filename);
    System.out.println("Wrote image: " + filename);
  }

  // @Author chatgpt. davor hat er nur den verzeichnis geprintet
  private static String printVector(Direction v) {
    return String.format("(%f, %f, %f)", v.x(), v.y(), v.z());
  }
}
