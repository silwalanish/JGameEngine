package game;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import loader.Loader;
import renderEngine.Fog;
import renderEngine.MasterRenderer;
import terrains.Terrain;

public abstract class Scene {
	
	protected Camera camera = new Camera(70, 0.1f, 2000f, new Vector3f(0, 300, 0), new Vector3f(30, 0, 0));
	protected Loader loader = new Loader();
	protected Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
	protected Fog fog = new Fog(0.001f, 1.5f, new Vector3f());
	protected List<Terrain> terrains = new ArrayList<Terrain>();
	protected List<Entity> entities = new ArrayList<Entity>();
	
	public Scene(){
		
	}
			
	public Scene(Camera camera, Loader loader, Light light, Fog fog) {
		this.camera = camera;
		this.loader = loader;
		this.light = light;
		this.fog = fog;
	}

	public abstract void init();
	
	public abstract void update(float deltaTime);
	
	public abstract void render(MasterRenderer renderer);
	
	public abstract void exit();

	public Fog getFog() {
		return fog;
	}

	public void setFog(Fog fog) {
		this.fog = fog;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
