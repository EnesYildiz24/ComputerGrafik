
package cgg;

import cgtools.*;

public interface Material {

    // In welche Richtung wird der Strahl gestreut?
    Ray reflection(Ray ray, Hit hit);

   // Wie stark wird die Strahldichte entlang des gestreuten Strahls abgeschwächt
    Color albedo(double u, double v);

    // Welchen Wert hat die emittierte Strahldichte? Nimmt nun Texturkoordinaten an.
    Color emission(double u, double v);
}
