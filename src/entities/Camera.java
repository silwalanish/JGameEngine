package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import controller.Keyboard;
import controller.Keys;
import controller.Mouse;
import renderEngine.Window;

public class Camera {

	private float fov = 70;
	private float near_plane = 0.1f;
	private float far_plane = 1000f;
	private Matrix4f projMat;

	private Vector3f position;
	private Vector3f rotation;
	private Player player;
	
	private float distanceFromPlayer = 200;
	private float angleAroundPlayer = 180;

	public Camera(float fov, float near_plane, float far_plane, Vector3f position, Vector3f rotation) {
		this.fov = fov;
		this.near_plane = near_plane;
		this.far_plane = far_plane;
		this.position = position;
		this.rotation = rotation;
		createProjectionMatrix();
	}

	public Camera(float fov, float near_plane, float far_plane) {
		this(fov, near_plane, far_plane, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	}
	
	public void move(){
			calculateZoom();
			calculatePitch();
			calculateAroundPlayer();
		if (player != null){
			float yOffset = distanceFromPlayer * (float) Math.sin(Math.toRadians(rotation.x));
			float hOffset = distanceFromPlayer * (float) Math.cos(Math.toRadians(rotation.x));
			
			float theta = angleAroundPlayer + player.getRotation().y;
			float xOffset = hOffset * (float) Math.sin(Math.toRadians(theta));
			float zOffset = hOffset * (float) Math.cos(Math.toRadians(theta));
			rotation.y = 180 - theta;
					
			position.x = player.getPosition().x - xOffset;
			position.z = player.getPosition().z - zOffset;
			position.y = player.getPosition().y + yOffset;
		}
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void calculateZoom(){
		if(Keyboard.getKey(Keys.KEY_Z)){
			distanceFromPlayer -= 5.0f;
		}else if(Keyboard.getKey(Keys.KEY_X)){
			distanceFromPlayer += 5.0f;
		}
	}
	
	public void calculatePitch(){
		if(Mouse.getMouseButton(Keys.MOUSE_LEFT) && rotation.x >= 1.0f && rotation.x < 76.0f){
			float pitch = Mouse.getMouseDY() * 0.1f;
			rotation.x -= pitch;
			if(rotation.x < 1.0f){
				rotation.x = 1.0f;
			}else if(rotation.x >= 76.0f){
				rotation.x = 75.0f;
			}
		}
	}
	
	public void calculateAroundPlayer(){
		if(Mouse.getMouseButton(Keys.MOUSE_RIGHT)){
			float angle = Mouse.getMouseDX() * 0.1f;
			angleAroundPlayer += angle;
		}
	}

	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();

		viewMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		viewMatrix.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));

		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		viewMatrix.translate(negativeCameraPos);

		return viewMatrix;
	}

	public void createProjectionMatrix() {
		projMat = new Matrix4f();
		projMat.identity();
		float aspectRatio = (float) Window.GetWidth() / (float) Window.GetHeight();
		float y_scale = (float) ((1.0f / Math.tan(Math.toRadians(fov / 2.0f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;

		projMat = new Matrix4f();
		projMat.m00(x_scale);
		projMat.m11(y_scale);
		projMat.m22(-((far_plane + near_plane) / frustum_length));
		projMat.m23(-1);
		projMat.m32(-((2 * near_plane * far_plane) / frustum_length));
		projMat.m33(0);

	}

	public Matrix4f getProjectionMatrix() {
		return projMat;
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

	public Player getPlayer() {
		return player;
	}

}
