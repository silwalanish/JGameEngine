package components;

import org.joml.Vector3f;

import controller.Keyboard;
import controller.Keys;
import entities.Entity;
import entities.Transform;

public class Movement extends Component {
	
	private float sensitivity;
	private float speed;
	
	private float currentSpeed = 0;
	private float currentTurn = 0;
	
	public Movement() {
		sensitivity = 20;
		speed = 50;
	}
	public Movement(Entity parent) {
		super(parent);
		sensitivity = 20;
		speed = 50;
	}
	
	public Movement(float sensitivity, float speed) {
		this.sensitivity = sensitivity;
		this.speed = speed;
	}
	
	public Movement(Entity parent, float sensitivity, float speed) {
		super(parent);
		this.sensitivity = sensitivity;
		this.speed = speed;
	}
	
	@Override
	public void update(float deltaTime) {
		checkInput();
		Transform transform = parent.getTransform();
		transform.getRotation().add(new Vector3f(0, currentTurn * deltaTime, 0));
		float dist = currentSpeed * deltaTime;
		float x_dist = (float) Math.sin(Math.toRadians(transform.getRotation().y)) * dist;
		float z_dist = (float) Math.cos(Math.toRadians(transform.getRotation().y)) * dist;
		transform.getPosition().add(new Vector3f(x_dist, 0, z_dist));
	}
	
	private void checkInput(){
		if (Keyboard.getKey(Keys.KEY_W)){
			currentSpeed = -speed;
		}else if (Keyboard.getKey(Keys.KEY_S)){
			currentSpeed = speed;
		}else{
			currentSpeed = 0;
		}
		
		if (Keyboard.getKey(Keys.KEY_A)){
			currentTurn = sensitivity;
		}else if (Keyboard.getKey(Keys.KEY_D)){
			currentTurn = -sensitivity;
		}else{
			currentTurn = 0;
		}
	}
	
	public float getSensitivity() {
		return sensitivity;
	}
	
	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}	
}
