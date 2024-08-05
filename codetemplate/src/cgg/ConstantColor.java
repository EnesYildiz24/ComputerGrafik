package cgg;

import cgtools.*;

public class ConstantColor implements Sampler {
    private Color color;

    public ConstantColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(double u, double v) {
        return color;
    }
}
