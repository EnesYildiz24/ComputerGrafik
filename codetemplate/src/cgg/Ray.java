
package cgg;

import cgtools.*;

public class Ray {
    private Point x; // Ursprung des Strahls
    private Direction d; // Richtung des Strahls
    private double tMin; // Minimale Entfernung entlang des Strahls
    private double tMax; // Maximale Entfernung entlang des Strahls

    // Konstruktor zur Initialisierung eines Strahls mit Richtung, maximaler Entfernung, minimaler Entfernung und Ursprung
    public Ray(Direction d, double tMax, double tMin, Point x) {
        this.d = d;
        this.tMin = tMin;
        this.tMax = tMax;
        this.x = x;
    }

    // Getter-Methode für den Ursprung des Strahls
    public Point getOrigin() {
        return x;
    }

    public double tMin(){
        return tMin;
    }

    public double tMax(){
        return tMax;
    }

    // Methode zur Berechnung eines Punktes entlang des Strahls bei einem bestimmten Parameter t
    public Point pointAt(double t) {
        Point point = new Point(
            x.x() + t * d.x(),
            x.y() + t * d.y(),
            x.z() + t * d.z());
        return point;
    }

    // Getter-Methode für die Richtung des Strahls
    public Direction getDirection() {
        return d;
    }

    // Methode zur Überprüfung, ob ein bestimmter Parameter t innerhalb der gültigen Reichweite des Strahls liegt
    public boolean isValid(double t) {
        boolean valid = tMin <= t && t <= tMax;
        return valid;
    }
}
