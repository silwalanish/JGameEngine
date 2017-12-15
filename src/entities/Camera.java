package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import controller.Keyboard;
import controller.Keys;
import controller.Mouse;
import renderEngine.Window;

public class Camera extends Entity {

	private float fov = 70;
	private float near_plane = 0.1f;
	private float far_plane = 1000f;
	private Matrix4f projMat;

	private Entity entityToFollow;
	
	private float distanceFromEntity = 200;
	private float angleAroundEntity = 180;
	private float sensitivity = 5;	

	public Camera(float fov, float near_plane, float far_plane, Transform transform) {
		super(null, transform);
		this.fov = fov;
		this.near_plane = near_plane;
		this.far_plane = far_plane;
		createProjectionMatrix();
	}

	public Camera(float fov, float near_plane, float far_plane) {
		this(fov, near_plane, far_plane, new Transform());
	}
	
	public void Move(float deltaTime){
			calculateZoom(deltaTime);
			calculatePitch(deltaTime);
			calculateAroundPlayer(deltaTime);
		if (entityToFollow != null){
			float yOffset = distanceFromEntity * (float) Math.sin(Math.toRadians(transform.getRotation().x));
			float hOffset = distanceFromEntity * (float) Math.cos(Math.toRadians(transform.getRotation().x));
						
			float theta = angleAroundEntity + entityToFollow.getTransform().getRotation().y;
			float xOffset = hOffset * (float) Math.sin(Math.toRadians(theta));
			float zOffset = hOffset * (float) Math.cos(Math.toRadians(theta));
			transform.getRotation().y = 180 - theta;
					
			transform.getPosition().x = entityToFollow.getTransform().getPosition().x - xOffset;
			transform.getPosition().z = entityToFollow.getTransform().getPosition().z - zOffset;
			transform.getPosition().y = entityToFollow.getTransform().getPosition().y + yOffset;
		}
	}
	
	public void setFollow(Entity entity){
		this.entityToFollow = entity;
	}

	public Entity getFollowing() {
		return entityToFollow;
	}
	
	public void calculateZoom(float delta){
		if(Keyboard.getKey(Keys.KEY_Z)){
			distanceFromEntity -= 50.0f * delta;
		}else if(Keyboard.getKey(Keys.KEY_X)){
			distanceFromEntity += 50.0f * delta;
		}
	}
	
	public void calculatePitch(float delta){
		if(Mouse.getMouseButton(Keys.MOUSE_LEFT)){
			float pitch = Mouse.getMouseDY() * sensitivity;
			transform.getRotation().x -= pitch * delta;
		}
	}
	
	public void calculateAroundPlayer(float delta){
		if(Mouse.getMouseButton(Keys.MOUSE_RIGHT)){
			float angle = Mouse.getMouseDX() * sensitivity;
			angleAroundEntity += angle * delta;
		}
	}

	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();

		viewMatrix.rotate((float) Math.toRadians(transform.getRotation().x), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(transform.getRotation().y), new Vector3f(0, 1, 0));
		viewMatrix.rotate((float) Math.toRadians(transform.getRotation().z), new Vector3f(0, 0, 1));

		Vector3f negativeCameraPos = new Vector3f(-transform.getPosition().x, -transform.getPosition().y, -transform.getPosition().z);
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
	
}
