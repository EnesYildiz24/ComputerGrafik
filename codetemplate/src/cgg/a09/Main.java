package cgg.a09;

import java.util.ArrayList;
import java.util.List;
import cgg.*;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1920;
        final int height = 1080;

        // Kameraeinstellungen
        Matrix transform = Matrix.translation(-3, -0.5, -25).multiply(Matrix.rotation(0, 1, 0, 193));
        Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);

        // Lade eine Panoramatextur für den Hintergrund
        //Quelle https://www.freepik.com/free-photo/mountain-forest-beautiful-night-sky-with-stars_26955794.htm#fromView=search&page=1&position=3&uuid=d40c2b37-91ec-489e-8f17-fc36d9021ef0
        //https://polyhaven.com/textures/rock/outdoor
        ImageTexture panoramaTexture = new ImageTexture("/Users/enesy/Documents/CGG/codetemplate/doc/mountain-forest-beautiful-night-sky-with-stars.jpg");
        Sampler woodTextureSampler = new TextureTransform(new ImageTexture("/Users/enesy/Documents/CGG/codetemplate/doc/rosewood_veneer1_diff_4k.jpg"), Matrix.scaling(0.1, 0.1, 1));
        Material panoramaMaterial = new EmissiveMaterial(panoramaTexture);
        Shape panorama = new Sphere(Vector.point(0, 0, 0), 500, panoramaMaterial);
        // Materialien und Texturen
        ImageTexture woodTexture = new ImageTexture("doc/rosewood_veneer1_diff_4k.jpg");
        Material woodMaterial = new TexturedMaterial(new TextureTransform(woodTexture, Matrix.scaling(0.1, 0.1, 1)), true);
        
        ImageTexture moonTexture = new ImageTexture("/Users/enesy/Documents/CGG/codetemplate/doc/rock_tile_floor_diff_4k.jpg");
        Material moonMaterial = new TexturedMaterial(new TextureTransform(moonTexture, Matrix.scaling(0.1, 0.1, 1)), true);
        
        Sampler backgroundSampler = new ConstantColor(new Color(0, 0, 0));
        Sampler polkaDots = new PolkaDotsTexture(0.4, woodTextureSampler, backgroundSampler, 20);
        Material polkaDotMaterial = new TexturedMaterial(polkaDots, true);
        
        ImageTexture sandTexture = new ImageTexture("doc/sand_03_diff_4k.jpg");
        Material sandMaterial = new TexturedMaterial(new TextureTransform(sandTexture, Matrix.scaling(1, 1, 1)), true);
                
        // Erstellen der Szene
        List<Shape> objects = new ArrayList<>();
        objects.add(panorama); // Add the panorama sphere to the main scene objects.
        objects.add(new Sphere(Vector.point(-3, 0, -10), 2, woodMaterial));
        objects.add(new Sphere(Vector.point(3, 0, -12), 2, polkaDotMaterial));
        objects.add(new Sphere(Vector.point(-70, 40, 200), 10, moonMaterial));
        objects.add(new Ebene(new Direction(0, 1, 0), new Point(0, -1, 0), sandMaterial,0.01,0.01));
        Group scene = new Group(objects.toArray(new Shape[0])); // Combine all into one group.

        // Raytracer-Instanz
        Raytracer rt = new Raytracer(camera, scene);

        // Erzeuge das Bild
        Image image = new Image(width, height);
        image.superSample(rt, 5);
        image.imageWrite("doc/a09-textures.png");

        System.out.println("Wrote image: doc/a09-textures.png");

    }
}
//randinfo: wir mussten paar bilder komprimieren sodass es für die max abgabe 50mb noch durchgeht.