package cgg;

import cgtools.Color;
import cgtools.Sampler;

public class PolkaDotsTexture implements Sampler {
    private double radius;
    private Sampler foreground;
    private Sampler background;
    private int repetitions; // Anzahl der Wiederholungen der Textur auf der Sphäre

    public PolkaDotsTexture(double radius, Sampler foreground, Sampler background, int repetitions) {
        this.radius = radius;
        this.foreground = foreground;
        this.background = background;
        this.repetitions = repetitions;
    }
    //chatgpt hilfe
    @Override
    public Color getColor(double u, double v) {
        // Anpassung von u und v für die Wiederholung, ohne den Bereich zu verlassen
        u = (u * repetitions) % 1.0;
        v = (v * repetitions) % 1.0;
        if (u < 0) u += 1.0;  // Korrektur für negative Werte nach Modulo
        if (v < 0) v += 1.0;
    
        // Berechnung des Abstands zum nächsten Punkt
        double adjustedU = u - Math.floor(u);
        double adjustedV = v - Math.floor(v);
        double distance = Math.sqrt(Math.pow(adjustedU - 0.5, 2) + Math.pow(adjustedV - 0.5, 2));
    
        // Ausgabe der Farbe basierend auf dem Abstand
        if (distance < radius) {
            return foreground.getColor(adjustedU, adjustedV);
        } else {
            return background.getColor(adjustedU, adjustedV);
        }
    }
    
}
