package renderEngine;

import org.joml.Vector3f;

public class Fog {

	public float density;
	public float gradient;
	public Vector3f color;
	
	public Fog() {
		this.density = 0.001f;
	}
	
	public Fog(float density, float gradient, Vector3f color) {
		this.density = density;
		this.gradient = gradient;
		this.color = color;
	}

	public float getDensity() {
		return density;
	}
	
	public void setDensity(float density) {
		this.density = density;
	}
	
	public float getGradient() {
		return gradient;
	}
	
	public void setGradient(float gradient) {
		this.gradient = gradient;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
}
