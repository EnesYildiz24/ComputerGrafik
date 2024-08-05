package cgg;

import cgtools.*;

public class TexturedMaterial implements Material {
    private Sampler texture;
    private boolean shouldEmitLight;  // Zustandsflag für Emission

    // Konstruktor mit Emissionsflag
    public TexturedMaterial(Sampler texture, boolean shouldEmitLight) {
        this.texture = texture;
        this.shouldEmitLight = shouldEmitLight;
    }

    @Override
    public Color albedo(double u, double v) {
        return texture.getColor(u, v);
    }

    @Override
    public Color emission(double u, double v) {
        if (shouldEmitLight) {
            return texture.getColor(u, v);  // Verwenden der Texturfarbe als Emissionsfarbe
        }
        return Vector.black; // Keine Emission, wenn das Material nicht leuchten soll
    }
    @Override
    public Ray reflection(Ray incomingRay, Hit hit) {
        Direction scatterDirection = RandomUtils.unitSphere();
        scatterDirection = Vector.add(hit.n(), scatterDirection);
        Direction normalizedDirection = Vector.normalize(scatterDirection);

        return new Ray(normalizedDirection, Double.POSITIVE_INFINITY, 0.0001,hit.x());
    }
}
