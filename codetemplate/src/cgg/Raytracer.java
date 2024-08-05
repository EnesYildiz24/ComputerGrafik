
package cgg;

import cgtools.*;

public class Raytracer implements Sampler {
    private Lochkamera camera;
    private World world;
    private int maxDepth;

    public Raytracer(Lochkamera camera, World world, int maxDepth) {
        
        this.camera = camera;
        this.world = world;
        this.maxDepth = maxDepth;
    }

    @Override
    public Color getColor(double x, double y) {
        Ray ray = camera.generateRay(x, y);
        return calculateRadiance(ray, 0);
    }

    private Color calculateRadiance(Ray ray, int depth) {
        
        if (depth >= maxDepth) {
            return Vector.black; // Keine weiteren Rekursionen, schwarze Farbe als Basis
        }

        Hit hit = world.scene.intersect(ray);
        if (hit == null) {
            return Vector.black; // Kein Treffer, Hintergrundfarbe
        }

        Material mat = hit.getMaterial();
        Color emitted = mat.emission(hit.u, hit.v); // Emission des Materials
        Ray scattered = mat.reflection(ray, hit);

        if (scattered == null) {
            return emitted; // Keine Reflektion, nur emittiertes Licht
        }

        // Rekursiver Aufruf zur Berechnung der Reflektion
        Color reflectedColor = calculateRadiance(scattered, depth + 1);
        Color albedo = mat.albedo(hit.u, hit.v);

        // Hinzufügen der von Lichtquellen emittierten Intensität, falls vorhanden
        Color directLight = addDirectLight(hit, world);

        return Vector.add(emitted, Vector.add(Vector.multiply(albedo, reflectedColor), directLight));
    }
    // Berechnet direktes Licht von allen Lichtquellen
    private Color addDirectLight(Hit hit, World world) {
        Color directLight = Vector.black;
        for (Light light : world.lights) {
            Color lightIntensity = light.incomingIntensity(hit.x(), hit.n(), world.scene);
            if (lightIntensity != null) {
                directLight = Vector.add(directLight, lightIntensity);
            }
        }
        return directLight;
    }
    
}
