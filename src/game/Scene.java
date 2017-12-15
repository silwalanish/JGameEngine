package game;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Transform;
import loader.Loader;
import renderEngine.Fog;
import renderEngine.MasterRenderer;
import terrains.Terrain;

public abstract class Scene {

	protected Camera camera;
	protected Loader loader;
	protected Light light;
	protected Fog fog;
	protected List<Terrain> terrains = new ArrayList<Terrain>();
	protected List<Entity> entities = new ArrayList<Entity>();

	public Scene() {
		this.loader = new Loader();
		this.light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		this.camera = new Camera(70, 0.1f, 2000f, new Transform());
		this.fog = new Fog(0.001f, 1.5f, new Vector3f());
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
