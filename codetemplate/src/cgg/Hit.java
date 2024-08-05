package cgg;

import cgtools.*;

public class Hit {
    private double t; // Strahlparameter
    private Point x; // die Position des Trefferpunkts
    private Direction n; // Normalenvektor
    private Material material;
    double u, v; // Texturkoordinaten


    public Hit(double t, Point x, Direction n, Material material, double u, double v) {
        this.t = t;
        this.x = x;
        this.n = n;
        this.material = material;
        this.u = u;
        this.v = v;
    }

    public double getT() {
        return t;
    }

    public Point x() {
        return x;
    }

    public Direction n() {
        return n;
    }

    public Material getMaterial(){
        return material;
    }

    public double u(){
        return u;
    }

    public double v(){
        return v;
    }


}
