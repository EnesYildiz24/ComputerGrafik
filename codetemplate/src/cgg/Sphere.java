
package cgg;

import cgtools.*;

public class Sphere implements Shape {

    private Point center; // Zentrum der Kugel
    private double radius; // Radius der Kugel
    private Material material; // material der Kugel

    // Konstruktor zur Initialisierung der Kugel mit Zentrum, Radius und Farbe
    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Hit intersect(Ray r) {
        Direction OC = Vector.subtract(r.getOrigin(), center);
        double b = Vector.dotProduct(OC, r.getDirection());
        double c = Vector.dotProduct(OC, OC) - radius * radius;
        double discriminant = b*b - c;
    
        if (discriminant < 0) return null;
    
        double sqrtD = Math.sqrt(discriminant);
        double t1 = -b - sqrtD;
        double t2 = -b + sqrtD;
    
        double t = (t1 > 0) ? t1 : ((t2 > 0) ? t2 : -1);
        if (t < 0) return null;
    
        Point hitPoint = r.pointAt(t);
        Direction normal = Vector.normalize(Vector.subtract(hitPoint, center));
        double phi = Math.acos(normal.y()); // Elevation basierend auf Y
        double theta = Math.atan2(normal.x(), normal.z()) + Math.PI; // Azimut basierend auf X und Z
        double u = theta / (2 * Math.PI);
        double v = phi / Math.PI;
    
        return new Hit(t, hitPoint, normal, material, u, v);
    }
    

    @Override
    public Material material() {
        return material;
    }
}
