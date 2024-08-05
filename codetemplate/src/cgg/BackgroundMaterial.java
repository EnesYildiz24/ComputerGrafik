package cgg;

import cgtools.*;

public class BackgroundMaterial implements Material {
    private Color emission;

    public BackgroundMaterial(Color emission) {
        this.emission = emission;
    }
    @Override
    public Color emission(double u, double v) {
        return emission;  // gibt die emittierte Farbe zurück, ignoriert die Koordinaten
    }

    @Override
    public Color albedo(double u, double v) {
        return new Color(0, 0, 0);  // keine Reflexion, ignoriert die Koordinaten
    }

    @Override
    public Ray reflection( Ray ray, Hit hit) {
        return null;  // keine Streuung, keine Richtung
    }

}
