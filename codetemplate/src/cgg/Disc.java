/** @author chatGPT mit den Kommentare */
package cgg;

import cgtools.*;

public class Disc implements Shape {
    private double x; // Koordinaten des Mittelpunkts der Scheibe
    private double y;
    private double radius; // Radius der Scheibe
    private Material material; // Farbe der Scheibe
    private Point center; // Zentrum der Scheibe
    private Direction normal; // Normalenvektor der Scheibe

    public Disc(Point center, Direction normal, Material material) {
        this.center = center;
        this.normal = Vector.normalize(normal); // Normalisierung des Normalenvektors
        this.material = material;
    }

    // Konstruktor zum Initialisieren der Scheibe mit Mittelpunkt, Radius und Farbe
    public Disc(double x, double y, double radius, Material material) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.material = material;
    }

    // Getter-Methode für den Radius der Scheibe
    public double getRadius() {
        return this.radius;
    }

    // Methode zur Überprüfung, ob ein Punkt (x1, y1) innerhalb der Scheibe liegt
    public boolean isPointInDisc(double x1, double y1) {
        // Berechnung der horizontalen und vertikalen Distanz vom Punkt zum Mittelpunkt
        // der Scheibe
        double längeX = x1 - x;
        double längeY = y1 - y;
        // Anwendung des Satzes des Pythagoras zur Bestimmung, ob der Punkt innerhalb
        // des Radius liegt
        return längeX * längeX + längeY * längeY <= radius * radius;
    }

    // Methode zur Bestimmung des Schnittpunkts der Scheibe mit einem Strahl
    @Override
    public Hit intersect(Ray ray) {
        double denominator = Vector.dotProduct(normal, ray.getDirection());
        // Vermeidung von Division durch Null
        if (denominator == 0) {
            return null; // Strahl ist parallel zur Scheibe und schneidet sie daher nicht
        }
        // Berechnung des Strahlparameters t zum Schnittpunkt
        double t = Vector.dotProduct(Vector.subtract(center, ray.getOrigin()), normal) / denominator;
        // Überprüfung, ob t innerhalb der gültigen Reichweite des Strahls liegt
        if (!ray.isValid(t)) {
            return null; // t liegt außerhalb der gültigen Reichweite
        }
        // Berechnung des Schnittpunkts
        Point hitPoint = ray.pointAt(t);
        double dx = hitPoint.x() - center.x();
        double dy = hitPoint.y() - center.y();
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist > radius) {
            return null; // Schnittpunkt liegt außerhalb des Scheibenradius
        }
        // Berechnung von Texturkoordinaten u und v
        double u = 0.5 + Math.atan2(dy, dx) / (2 * Math.PI);
        double v = dist / radius;

        // Rückgabe eines neuen Hit-Objekts
        return new Hit(t, hitPoint, normal, material, u, v);

    }

    public Material material() {
        return material;
    }

}
