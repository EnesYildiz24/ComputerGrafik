package cgg.a05;

import cgtools.*;
import cgg.*;

public class Main {
  public static void main(String[] args) {
    final int width = 1920;
    final int height = 1080;

    Matrix transform = Matrix.translation(0, 2, 0).multiply(Matrix.rotation(1, 0, 0, -30));

    Lochkamera camera = new Lochkamera(Math.PI / 3, height, width, transform);


    Material dRed = new DiffusedMaterial(Vector.red);
    Material dBlue = new DiffusedMaterial(Vector.blue);
    Material dGray = new DiffusedMaterial(Vector.gray);
    Material dViolet = new DiffusedMaterial(Vector.violet);
    Material dYellow = new DiffusedMaterial(Vector.yellow);
    Material bgWhite = new BackgroundMaterial(Vector.white);

    Shape background = new Background(bgWhite);

    Shape ground = new Ebene(new Direction(0, 1, 0), new Point(0.0, -0.5, 0.0), dGray);

    //LEVEL 1
    Shape globe1 = new Sphere(Vector.point(-0.6, -0.25, -1.5), 0.15, dRed);
    Shape globe2 = new Sphere(Vector.point(-0.2, -0.25, -1.5), 0.15, dBlue);
    Shape globe3 = new Sphere(Vector.point(0.2, -0.25, -1.5), 0.15, dViolet);
    Shape globe4 = new Sphere(Vector.point(0.6, -0.25, -1.5), 0.15, dYellow);

    //LEVEL 2
    Shape globe5 = new Sphere(Vector.point(-1, 0, -2.5), 0.47, dBlue);
    Shape globe6 = new Sphere(Vector.point(0, 0, -2.5), 0.47, dYellow);
    Shape globe7 = new Sphere(Vector.point(1, 0, -2.5), 0.47, dRed);

    Shape[] groupShapes = new Shape[]{background, ground, globe1,globe2, globe3, globe4, globe5, globe6, globe7};
    Shape group = new Group(groupShapes);

    Raytracer rt = new Raytracer(camera, group);

    Image image = new Image(width, height);
    image.superSample(rt, 10
    );

    final String filename = "doc/a05-diffuse-spheres.png";
    image.imageWrite(filename);
    System.out.println("Wrote image: " + filename);
    

  }
}