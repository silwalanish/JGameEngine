package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
	
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Transform(){
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = 1;
	}
	
	public Transform(Vector3f position, Vector3f rotation, float scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Matrix4f getTranformationMatrix() {
		Matrix4f transMat = new Matrix4f();
		transMat.identity();
		transMat.translation(position);

		transMat.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
		transMat.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		transMat.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));

		transMat.scale(scale);

		return transMat;
	}
		
}
