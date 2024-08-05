package cgg;

import cgtools.*;

public interface Light {

	public Color incomingIntensity(Point x, Direction n, Shape s);

}