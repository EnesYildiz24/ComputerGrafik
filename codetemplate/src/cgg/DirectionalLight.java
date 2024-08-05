package cgg;

import cgtools.*;

public class DirectionalLight implements Light {
    private Direction direction;
    private Color color;
    private double intensity;

    public DirectionalLight(Direction direction, Color color, double intensity) {
        this.direction = Vector.normalize(direction);
        this.color = color;
        this.intensity = intensity;
    }

    //chatgpt korrektur
    @Override
    public Color incomingIntensity(Point x, Direction n, Shape s) {
        double cosTheta = Vector.dotProduct(Vector.negate(this.direction), n);
        if (cosTheta > 0) {
            Point shadowRayOrigin = Vector.add(x, Vector.multiply(0.0001, n)); 
            Direction shadowRayDirection = Vector.negate(this.direction);
            Ray shadowRay = new Ray(shadowRayDirection,  Double.POSITIVE_INFINITY,0.0001,shadowRayOrigin);
            Hit hit = s.intersect(shadowRay);
            if (hit == null) {
                return Vector.multiply(color, intensity * cosTheta);
            }
        }
        return new Color(0, 0, 0); 
    }
}
