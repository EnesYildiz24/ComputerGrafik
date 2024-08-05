/** @author chatGPT mit den Kommentare */

package cgg;

import cgtools.*;

public class GlasMaterial implements Material {
    private Color color; // Farbe des Glases
    private double opticalIndex; // Optischer Index (Brechungsindex)

    public GlasMaterial(Color color, double opticalIndex) {
        this.color = color;
        this.opticalIndex = opticalIndex;
    }

    @Override
    public Color albedo(double u, double v) {
        return color; // Verwenden Sie die Farbe des Glases als Albedo
    }

    @Override
    public Color emission(double u, double v) {
        return Vector.black; // Keine Emission
    }
    
    /** @author chatGPT  */

    @Override
    public Ray reflection(Ray ray, Hit hit) {
        Direction normal = hit.n();
        double cosThetaI = Vector.dotProduct(ray.getDirection(), normal);

        // Überprüfen, ob der Strahl von innen oder außen kommt
        double n1 = 1.0; // Brechungsindex des ursprünglichen Mediums (angenommen: Luft)
        double n2 = opticalIndex; // Brechungsindex des Materials der Oberfläche

        // Wenn der Strahl innerhalb des Materials ist, negiere die Normale und
        // vertausche die Indizes
        if (cosThetaI > 0) {
            normal = Vector.negate(normal);
            cosThetaI = -cosThetaI;
            n1 = opticalIndex;
            n2 = 1.0;
        }

        double ratio = n1 / n2;
        double sinThetaTSquared = ratio * ratio * (1.0 - cosThetaI * cosThetaI);

        // Totalreflexion oder Schlick-Approximation für Reflexion berechnen
        if (sinThetaTSquared > 1.0) {
            Direction reflectedDirection = reflect(ray.getDirection(), normal);
            return new Ray(reflectedDirection, Double.POSITIVE_INFINITY, 0.001, hit.x());
        } else {
            double reflectance = schlick(ray.getDirection(), normal, n1, n2);
            if (Random.random() < reflectance) {
                // Reflektieren des Strahls
                Direction reflectedDirection = reflect(ray.getDirection(), normal);
                return new Ray(reflectedDirection, Double.POSITIVE_INFINITY, 0.001, hit.x());
            } else {
                // Brechung des Strahls
                Direction refractedDirection = refract(ray.getDirection(), normal, n1, n2);
                if (refractedDirection != null && !Double.isNaN(refractedDirection.x())
                        && !Double.isNaN(refractedDirection.y()) && !Double.isNaN(refractedDirection.z())) {
                    return new Ray(refractedDirection, Double.POSITIVE_INFINITY, 0.001, hit.x());
                } else {
                    // Falls Brechung zu NaN führt, als Reflexion behandeln
                    Direction reflectedDirection = reflect(ray.getDirection(), normal);
                    return new Ray(reflectedDirection, Double.POSITIVE_INFINITY, 0.001, hit.x());
                }
            }
        }
    }
    /** @author chatGPT  */

    public Direction reflect(Direction incoming, Direction normal) {
        double dot = Vector.dotProduct(incoming, normal);
        return Vector.subtract(incoming, Vector.multiply(2 * dot, normal));
    }

    public Direction refract(Direction incoming, Direction normal, double n1, double n2) {
        double cosThetaI = -Vector.dotProduct(normal, Vector.normalize(incoming));
        double sinThetaI2 = 1.0 - cosThetaI * cosThetaI;
        double sinThetaT2 = (n1 / n2) * (n1 / n2) * sinThetaI2;

        if (sinThetaT2 > 1.0)
            return null;

        double cosThetaT = Math.sqrt(1.0 - sinThetaT2);
        Direction rParallel = Vector.multiply((n1 / n2), Vector.add(incoming, Vector.multiply(cosThetaI, normal)));
        Direction rPerpendicular = Vector.multiply(-cosThetaT, normal);
        return Vector.normalize(Vector.add(rParallel, rPerpendicular));
    }

    public double schlick(Direction incoming, Direction normal, double n1, double n2) {
        incoming = Vector.normalize(incoming);
        normal = Vector.normalize(normal);
    
        double cosThetaI = Math.abs(Vector.dotProduct(incoming, normal));
        if (cosThetaI > 1) cosThetaI = 1;
    
        if (Vector.dotProduct(incoming, normal) > 0) {
            double temp = n1;
            n1 = n2;
            n2 = temp;
        }
    
        double r0 = Math.pow((n1 - n2) / (n1 + n2), 2);
        double schlickApproximation = r0 + (1 - r0) * Math.pow(1 - cosThetaI, 5);
    
        return schlickApproximation;
    }
    
    
    
    
    
    

    
    
    
    
    

}
