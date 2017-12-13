package entities;

import org.joml.Vector3f;

import controller.Keyboard;
import controller.Keys;
import models.TexturedModel;
import terrains.Terrain;

public class Player extends Entity {
	
	private static final float GRAVITY = -50;
	private float move_speed;
	private float rotation_sensitivity;
	private float jump_force;
	
	private float currentSpeed = 0;
	private float currentTurn = 0;
	private float upSpeed = 0;
	private boolean isInAir = true; 
	
	
	public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		super(model, position, rotation, scale);
		this.move_speed = 20;
		this.rotation_sensitivity = 90;
		this.jump_force = 30;
	}
	
	public Player(TexturedModel model, Vector3f position, Vector3f rotation, float scale,
			float move_speed, float rotation_sensitivity, float jump_force) {
		super(model, position, rotation, scale);
		this.move_speed = move_speed;
		this.rotation_sensitivity = rotation_sensitivity;
		this.jump_force = jump_force;
	}	

	public void Move(float deltaTime, Terrain terrain){
		checkInput();
		increaseRotation(new Vector3f(0, currentTurn * deltaTime, 0));
		float dist = currentSpeed * deltaTime;
		float x_dist = (float) Math.sin(Math.toRadians(rotation.y)) * dist;
		float z_dist = (float) Math.cos(Math.toRadians(rotation.y)) * dist;
		increasePosition(new Vector3f(x_dist, 0, z_dist));
		upSpeed += GRAVITY * deltaTime;
		increasePosition(new Vector3f(0, upSpeed * deltaTime, 0));
		float terrainHeight = terrain.getTerrainHeight(position.x, position.z);
		if(position.y < terrainHeight){
			upSpeed = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	private void checkInput(){
		if(Keyboard.getKey(Keys.KEY_W)){
			this.currentSpeed = -move_speed;
		}else if(Keyboard.getKey(Keys.KEY_S)){
			this.currentSpeed = move_speed;
		}else{
			this.currentSpeed = 0;
		}
		
		if(Keyboard.getKey(Keys.KEY_A)){
			this.currentTurn = rotation_sensitivity;
		}else if(Keyboard.getKey(Keys.KEY_D)){
			this.currentTurn = -rotation_sensitivity;
		}else{
			this.currentTurn = 0;
		}
		
		if(Keyboard.getKey(Keys.KEY_SPACE) && !isInAir){
			this.upSpeed = jump_force;
			isInAir = true;
		}
		
	}

	public float getMoveSpeed() {
		return move_speed;
	}

	public void setMoveSpeed(float move_speed) {
		this.move_speed = move_speed;
	}

	public float getRotationSensitivity() {
		return rotation_sensitivity;
	}

	public void setRotationSensitivity(float rotation_sensitivity) {
		this.rotation_sensitivity = rotation_sensitivity;
	}

	public boolean getInAir() {
		return isInAir;
	}
	
}
