
package cgg;

import cgtools.*;

/** @author chatGPT */
public class MetalMaterial implements Material {
    private Color reflectionCoefficient; // Farbreflexionskoeffizient
    private double roughness; // Rauheit der Oberfläche

    public MetalMaterial(Color reflectionCoefficient, double roughness) {
        this.reflectionCoefficient = reflectionCoefficient;
        this.roughness = roughness;
    }

    @Override
    public Color albedo(double u, double v) {
        return reflectionCoefficient; // Verwenden Sie den Reflexionskoeffizienten als Albedo
    }

    @Override
    public Color emission(double u, double v) {
        return Vector.black; // Keine Emission
    }

    @Override
    public Ray reflection(Ray ray, Hit hit) {
        Direction idealReflected = Vector.reflect(ray.getDirection(), hit.n());
        Direction roughReflected = addRoughness(idealReflected, hit.n(), roughness);

        return new Ray(roughReflected, Double.POSITIVE_INFINITY, 0.00001, hit.x());
    }

    public Color reflectedColor(Color incomingColor) {
        return Vector.multiply(reflectionCoefficient, incomingColor);
    }

    private Direction addRoughness(Direction idealReflected, Direction normal, double roughness) {
        if (roughness == 0) {
            return idealReflected; // Keine Rauheit, perfekte Reflexion
        }
        Direction randomComponent = RandomUtils.unitSphere();
        Direction roughComponent = Vector.multiply(randomComponent, roughness);
        Direction combinedDirection = Vector.add(idealReflected, roughComponent);
        if (Vector.dotProduct(combinedDirection, normal) < 0) {
            combinedDirection = Vector.negate(combinedDirection); // Umkehren, falls unterhalb der Oberfläche
        }
        return Vector.normalize(combinedDirection);
    }
}
