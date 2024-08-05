package cgg;

import java.util.ArrayList;
import java.util.List;

public class World {
    public final List<Light> lights;
    public final Group scene;

    public World(Group scene, Light... lights) {
        this.scene = scene;
        this.lights = new ArrayList<>(); 
        for (Light l : lights) {
            this.lights.add(l);
        }
    }
}
