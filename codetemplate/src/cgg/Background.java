package cgg;
import static cgtools.Vector.normalize;

import cgtools.*;


public class Background implements Shape {
    private Material material;

    public Background(Material material) {
        this.material = material;
    }
    @Override
    public Hit intersect(Ray ray) {
        // Der Hintergrund ist immer getroffen, aber unendlich weit weg
        double t = Double.POSITIVE_INFINITY;
        Point hitPoint = ray.pointAt(t);

        // Berechnung der sphärischen Koordinaten für die Textur
        Direction d = normalize(ray.getDirection());
        double u = 0.5 + Math.atan2(d.z(), d.x()) / (2 * Math.PI); // Azimutwinkel
        double v = 0.5 - Math.asin(d.y()) / Math.PI; // Höhenwinkel

        // Modifikation der u-Koordinate, um das nahtlose Umwickeln der Textur zu ermöglichen
        u = u % 1.0;
        if (u < 0) u += 1.0;

        return new Hit(t, hitPoint, d, material, u, v);
    }

    @Override
    public Material material() {
        return material;
    }
}

