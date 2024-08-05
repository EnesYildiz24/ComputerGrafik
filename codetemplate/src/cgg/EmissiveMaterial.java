package cgg;

import cgtools.*;

public class EmissiveMaterial implements Material {
    private Sampler texture;

    public EmissiveMaterial(Sampler texture) {
        this.texture = texture;
    }

    @Override
    public Color emission(double u, double v) {
        return texture.getColor(u, v); // Nutze die Textur für Emission
    }

    @Override
    public Color albedo(double u, double v) {
        return new Color(0, 0, 0); // Keine Reflexion
    }

    @Override
    public Ray reflection(Ray ray, Hit hit) {
        return null; // Keine Reflexion
    }
}

