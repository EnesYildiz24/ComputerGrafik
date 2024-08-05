package cgg;

import cgtools.*;

public class ProceduralTexture implements Sampler {
    @Override
    public Color getColor(double u, double v) {
        int n = 8;  // Beispielhafte Skalierungsvariable, um die Größe der Kacheln zu bestimmen
        int ui = (int)((u % 1) * n);
        int vi = (int)((v % 1) * n);
        if ((ui + vi) % 2 == 0)
            return new Color(1, 1, 1);  // Weiße Farbe für eine Zelle
        else
            return new Color(0, 0, 0);  // Schwarze Farbe für die alternative Zelle
    }
    
}
