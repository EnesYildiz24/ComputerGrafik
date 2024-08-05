
package cgg.a02;

import static cgtools.Vector.color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import cgg.Disc;
import cgtools.*;

public class ColoredDisc implements Sampler {

    private List<Disc> discs;

    // Konstruktor zum Initialisieren der Liste von Scheiben
    ColoredDisc() {
        this.discs = new ArrayList<>();
    }
    // Methode zum Hinzufügen einer Scheibe zur Liste
    public void addDisc(Disc disc) {
        discs.add(disc);
        // @author chatpgt kurz gesagt sortiert Kreise von klein (vorne) nach groß
        // (hinzen) per radius
        discs.sort(Comparator.comparingDouble(Disc::getRadius));
    }

    // Methode zur Berechnung der Farbe an den Koordinaten (x, y)
    public Color getColor(double x, double y) {
        // Überprüft jede Scheibe in der Liste, ob der Punkt (x, y) innerhalb der Scheibe liegt
        for (Disc disc : discs) {
            if (disc.isPointInDisc(x, y)) {
                return disc.getColor(x, y);
            }
        }
        // Gibt Schwarz zurück, wenn der Punkt in keiner Scheibe liegt
        return color(0, 0, 0);
    }

    // Methode zum Erstellen einer zufälligen Sammlung von Scheiben
    public static ColoredDisc createRandomDiscs(int numDisc) {
        Random random = new Random();
        ColoredDisc coloredDisc = new ColoredDisc();
        for (int i = 0; i < numDisc; i++) {
            double x = random.nextDouble() * 512; // Zufällige x-Koordinate
            double y = random.nextDouble() * 256; // Zufällige y-Koordinate
            double radius = 10 + random.nextDouble() * 90; // Zufälliger Radius zwischen 10 und 100
            Color color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble()); // Zufällige Farbe
            coloredDisc.addDisc(new Disc(x, y, radius, color));
        }
        return coloredDisc;
    }

}

