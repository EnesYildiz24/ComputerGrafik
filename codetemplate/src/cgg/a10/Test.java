/** @author chatGPT mit den Kommentare */

package cgg.a10;

import cgg.*;
import cgtools.*;

public class Test {
        public static void main(String[] args) {
                // Kugel mit Mittelpunkt (0,0,-2) und Radius 1
                Shape sphere = new Sphere(Vector.point(0, 0, -2), 1, new GlasMaterial(Vector.color(1, 1, 1), 1.5));

                // Test 1: Ursprung (0,0,0), Richtung (0,0,-1)
                Ray ray1 = new Ray(Vector.direction(0, 0, -1), Double.POSITIVE_INFINITY, 0, Vector.point(0, 0, 0));
                Hit hit1 = sphere.intersect(ray1);
                System.out.println("Test 1:");
                if (hit1 != null) {
                        System.out.println("Trefferpunkt: " + hit1.x() + ", Normale: " + hit1.n());
                } else {
                        System.out.println("Kein Treffer");
                }

                // Test 2: Ursprung (0,0,-2), Richtung (0,0,-1)
                Ray ray2 = new Ray(Vector.direction(0, 0, -1), Double.POSITIVE_INFINITY, 0, Vector.point(0, 0, -2));
                Hit hit2 = sphere.intersect(ray2);
                System.out.println("Test 2:");
                if (hit2 != null) {
                        System.out.println("Trefferpunkt: " + hit2.x() + ", Normale: " + hit2.n());
                } else {
                        System.out.println("Kein Treffer");
                }

                // Test 3: Ursprung (0,0,-4), Richtung (0,0,-1)
                Ray ray3 = new Ray(Vector.direction(0, 0, -1), Double.POSITIVE_INFINITY, 0, Vector.point(0, 0, -4));
                Hit hit3 = sphere.intersect(ray3);
                System.out.println("Test 3:");
                if (hit3 != null) {
                        System.out.println("Trefferpunkt: " + hit3.x() + ", Normale: " + hit3.n());
                } else {
                        System.out.println("Kein Treffer");
                }

                // Additional Tests for reflect, schlick, and refract methods

                GlasMaterial material = new GlasMaterial(Vector.color(1, 1, 1), 1.5);

                // Reflect tests
                System.out.println("Reflect Tests:");
                Direction reflectResult1 = material.reflect(Vector.direction(0.000, 0.000, 0.000),
                                Vector.direction(0.000, 1.000, 0.000));
                System.out.println("reflect() Test 1: " + reflectResult1);

                Direction reflectResult2 = material.reflect(Vector.direction(0.707, -0.707, 0.000),
                                Vector.direction(0.000, 1.000, 0.000));
                System.out.println("reflect() Test 2: " + reflectResult2);

                Direction reflectResult3 = material.reflect(Vector.direction(0.707, 0.707, 0.000),
                                Vector.direction(0.000, 1.000, 0.000));
                System.out.println("reflect() Test 3: " + reflectResult3);

                // Schlick tests
                System.out.println("Schlick Tests:");

                Direction incoming1 = Vector.direction(0.707, 0.707, 0.0);
                Direction normal = Vector.direction(0.0, 1.0, 0.0);

                double schlickResult1 = material.schlick(incoming1, normal, 1.0, 1.5);
                System.out.println("schlick() Test 1: " + schlickResult1);                                          

                double schlickResult2 = material.schlick(incoming1, normal, 1.5, 1.0);
                System.out.println("schlick() Test 2: " + schlickResult2); 

                Direction incoming2 = Vector.direction(0.995, -0.100, 0.0);
                double schlickResult3 = material.schlick(incoming2, normal, 1.0, 1.5);
                System.out.println("schlick() Test 3: " + schlickResult3);

                double schlickResult4 = material.schlick(incoming2, normal, 1.5, 1.0);
                System.out.println("schlick() Test 4: " + schlickResult4);

                // Refract tests
                System.out.println("Refract Tests:");
                Direction refractResult1 = material.refract(Vector.direction(0.707, 0.707, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1, 1.5);
                System.out.println("refract() Test 1: " + refractResult1);

                Direction refractResult2 = material.refract(Vector.direction(0.707, 0.707, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1.5, 1);
                System.out.println("refract() Test 2: " + refractResult2);

                Direction refractResult3 = material.refract(Vector.direction(0.995, -0.100, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1, 1.5);
                System.out.println("refract() Test 4: " + refractResult3);

                Direction refractResult4 = material.refract(Vector.direction(0.995, -0.100, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1.5, 1);
                System.out.println("refract() Test 3: " + refractResult4);

                Direction refractResult5 = material.refract(Vector.direction(0.100, -0.995, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1, 1.5);
                System.out.println("refract() Test 6: " + refractResult5);

                Direction refractResult6 = material.refract(Vector.direction(0.100, -0.995, 0.000),
                                Vector.direction(0.000, 1.000, 0.000), 1.5, 1);
                System.out.println("refract() Test 5: " + refractResult6);

        }
}
