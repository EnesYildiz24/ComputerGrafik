package cgg;

import cgtools.Direction;
import java.util.Random;

public class RandomUtils {

    private static final Random rand = new Random();
    // Gibt eine zufällig gleichmäßig verteilte Richtung auf der Einheitssphäre zurück
    public static Direction unitSphere() {
        double theta = 2 * Math.PI * rand.nextDouble(); // Winkel um die z-Achse
        double phi = Math.acos(2 * rand.nextDouble() - 1); // Winkel von der z-Achse weg
        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = Math.cos(phi);
        return new Direction(x, y, z);
    }
}
