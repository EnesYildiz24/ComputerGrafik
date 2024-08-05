package cgg;

import cgtools.*;

public class DiffusedMaterial implements Material {

    private final Color albedo;

    public DiffusedMaterial(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public Ray reflection(Ray incomingRay, Hit hit ) {
        Direction scatterDirection; 
        while(true){
            double x = -1 + Random.random() * 2;
            double y = -1 + Random.random() * 2;
            double z = -1 + Random.random() * 2;
            if((( x * x) + ( y * y) + ( z * z)) <= 1) {
                scatterDirection = Vector.direction(x, y, z);
                break;
            }

        }
        Direction d = Vector.normalize(Vector.add(hit.n(), scatterDirection));

        return new Ray ( d, Double.POSITIVE_INFINITY,0.00001, hit.x());
    }

    @Override
    public Color albedo(double u, double v) {
        return albedo; 
    }

    @Override
    public Color emission(double u, double v) {
        return Vector.black; 
    }

}
