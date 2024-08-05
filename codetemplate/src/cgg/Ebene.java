package cgg;

import cgtools.*;
import static cgtools.Vector.*;

public class Ebene implements Shape {
    private Direction normal;
    private Point anker;
    private Material material;
    private double scaleU, scaleV;

    public Ebene(Direction normal, Point anker, Material material, double scaleU, double scaleV) {
        this.normal = normalize(normal);
        this.anker = anker;
        this.material = material;
        this.scaleU = scaleU;
        this.scaleV = scaleV;
    }


    public Ebene(Direction normal, Point anker, Material material) {
        this.normal = normalize(normal);
        this.anker = anker;
        this.material = material;

    }
    //chatgpt hilfe
    @Override
    public Hit intersect(Ray ray) {
        double dotProduct = Vector.dotProduct(normal, ray.getDirection());

        if (Math.abs(dotProduct) < 1e-5) {
            return null; // Kein Schnitt möglich, wenn Strahl und Normalen orthogonal sind
        }

        double t = Vector.dotProduct(normal, Vector.subtract(anker, ray.getOrigin())) / dotProduct;
        if (!ray.isValid(t)) {
            return null;
        }

        Point hitPoint = ray.pointAt(t);
        Direction uDir = normalize(new Direction(normal.y(), -normal.x(), 0));
        Direction vDir = crossProduct(normal, uDir);

        double u = mod(Vector.dotProduct(subtract(hitPoint, anker), uDir) * scaleU, 1.0);
        double v = mod(Vector.dotProduct(subtract(hitPoint, anker), vDir) * scaleV, 1.0);

        return new Hit(t, hitPoint, normal, material, u, v);
    }
    
    private double mod(double value, double modulus) {
        double result = value % modulus;
        return result < 0 ? result + modulus : result;
    }


    @Override
    public Material material() {
        return material;
    }
}
