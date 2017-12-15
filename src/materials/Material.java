package materials;

import org.joml.Vector3f;


public class Material {
	
	private Vector3f diffuseColor;
	private Vector3f specularColor;
	
	public Material() {
		this.diffuseColor = new Vector3f(1, 1, 1);
		this.specularColor = new Vector3f(1, 1, 1);
	}
		
	public Material(Vector3f diffuseColor, Vector3f specularColor) {
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
	}

	public Vector3f getDiffuseColor() {
		return diffuseColor;
	}
	
	public void setDiffuseColor(Vector3f diffuseColor) {
		this.diffuseColor = diffuseColor;
	}
	public Vector3f getSpecularColor() {
		return specularColor;
	}
	
	public void setSpecularColor(Vector3f specularColor) {
		this.specularColor = specularColor;
	}

}
