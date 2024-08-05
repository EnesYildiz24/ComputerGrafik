/** @author chatGPT mit den Kommentare */
package cgg;

import cgtools.*;

public class Cylinder implements Shape {

    private Point center; // Mittelpunkt der Basis des Zylinders
    private double radiusX, radiusZ; // Radien des Zylinders in X- und Z-Richtung
    private double height; // Höhe des Zylinders
    private Material material; // Material, aus dem der Zylinder besteht

    // Konstruktor für die Initialisierung eines Zylinders
    public Cylinder(Point center, double radiusX, double radiusZ, double height, Material material) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.height = height;
        this.material = material;
    }

    @Override
    public Hit intersect(Ray ray) {
        // Verschiebung des Strahlursprungs relativ zum Zylinderzentrum
        Direction originPoint = Vector.subtract(ray.getOrigin(), center);

        // Berechnung der Koeffizienten der quadratischen Gleichung (a, b, c) für die
        // Zylinderintersektion
        double a = (ray.getDirection().x() * ray.getDirection().x()) / (radiusX * radiusX) +
                (ray.getDirection().z() * ray.getDirection().z()) / (radiusZ * radiusZ);
        double b = 2 * ((originPoint.x() * ray.getDirection().x()) / (radiusX * radiusX) +
                (originPoint.z() * ray.getDirection().z()) / (radiusZ * radiusZ));
        double c = (originPoint.x() * originPoint.x()) / (radiusX * radiusX) +
                (originPoint.z() * originPoint.z()) / (radiusZ * radiusZ) - 1;

        // Diskriminante der quadratischen Formel bestimmen
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null; // Kein Schnittpunkt, wenn die Diskriminante negativ ist
        }

        // Schnittpunkte des Strahls mit dem unendlichen Zylinder berechnen
        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double minT = Double.POSITIVE_INFINITY;
        Hit finalHit = null;

        // @Chatgpt korrektur
        // Überprüfung der Gültigkeit beider potentieller Schnittpunkte
        double[] ts = { t1, t2 };
        for (double t : ts) {
            if (t >= ray.tMin() && t <= ray.tMax()) {
                Point p = ray.pointAt(t);
                double yPos = p.y() - center.y();
                // Überprüfen, ob der Punkt im gültigen Höhenbereich des Zylinders liegt
                if (yPos >= 0 && yPos <= height) {
                    if (t < minT) {
                        // Berechnung der normalen Vektoren an den Schnittpunkten
                        double nx = 2 * (p.x() - center.x()) / (radiusX * radiusX);
                        double nz = 2 * (p.z() - center.z()) / (radiusZ * radiusZ);
                        Direction normal = Vector.normalize(new Direction(nx, 0, nz));
                        double u = Math.atan2(p.z(), p.x()) / (2 * Math.PI) + 0.5; // u-Koordinate entlang des Umfangs
                        double v = yPos / height; // v-Koordinate entlang der Höhe
                        finalHit = new Hit(t, p, normal, material, u, v);
                        minT = t;
                    }
                }
            }
        }

        // @Chatgpt korrektur
        // Prüfen auf Schnittpunkte mit der Decke und dem Boden des Zylinders
        if (ray.getDirection().y() != 0) { // Vermeiden der Division durch Null
            double[] heights = { 0, height }; // Höhen des Bodens und der Decke des Zylinders
            for (double h : heights) {
                double t = (h + center.y() - ray.getOrigin().y()) / ray.getDirection().y();
                if (t >= ray.tMin() && t <= ray.tMax()) {
                    Point p = ray.pointAt(t);
                    double dx = p.x() - center.x();
                    double dz = p.z() - center.z();
                    // Überprüfung, ob der Punkt auf der Decke/Boden innerhalb des Zylinderradius
                    // liegt
                    if (dx * dx / (radiusX * radiusX) + dz * dz / (radiusZ * radiusZ) <= 1) {
                        if (t < minT) {
                            Direction normal = new Direction(0, h == 0 ? -1 : 1, 0); // Normale nach oben oder unten
                            double u = (p.x() - center.x()) / (2 * radiusX) + 0.5;
                            double v = (p.z() - center.z()) / (2 * radiusZ) + 0.5;
                            finalHit = new Hit(t, p, normal, material,u,v);
                            minT = t;
                        }
                    }
                }
            }
        }

        return finalHit;
    }

    @Override
    public Material material() {
        return material; // Rückgabe des Materials des Zylinders
    }

}
