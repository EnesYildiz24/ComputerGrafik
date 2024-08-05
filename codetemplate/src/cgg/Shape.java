package cgg;

public interface Shape {
    Hit intersect(Ray ray);
    Material material();
}

