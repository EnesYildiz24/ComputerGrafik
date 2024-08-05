package cgg;

import cgtools.*;

public class PointLight implements Light {
	private Point position;
	private Color color;
	private double intensity;

	public PointLight(Point position, Color color, double intensity) {
		this.position = position;
		this.color = color;
		this.intensity = intensity;
	}

	// chatgpt korrektur

	@Override
	public Color incomingIntensity(Point x, Direction n, Shape s) {
		Direction lightDir = Vector.subtract(position, x);
		double distance = Vector.length(lightDir);
		lightDir = Vector.normalize(lightDir);

		double cosTheta = Vector.dotProduct(lightDir, n);
		if (cosTheta > 0) {
			Point shadowRayOrigin = Vector.add(x, Vector.multiply(0.0001, n));
			Ray shadowRay = new Ray(lightDir, distance, 0.0001, shadowRayOrigin);
			Hit hit = s.intersect(shadowRay);
			if (hit == null) {
				double attenuation = 1.0 / (distance * distance);
				return Vector.multiply(color, intensity * cosTheta * attenuation);
			}
		}
		return new Color(0, 0, 0);
	}
}
