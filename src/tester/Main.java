package tester;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import loader.Loader;
import models.TexturedModel;
import renderEngine.MasterRenderer;
import renderEngine.Window;
import terrains.Terrain;
import textures.TerrainTexturePack;

public class Main {

	public static void main(String[] args) {
		Window.CreateWindow(800, 600, "Game");

		Loader loader = new Loader();
		Camera cam = new Camera(70, 0.1f, 5000f, new Vector3f(-1000, 300, -1000), new Vector3f(30, 0, 0));
				
		TerrainTexturePack pack = new TerrainTexturePack();
		pack.setBackgroundTexture(loader.loadTexture("res/grass.png"));
		pack.setrTexture(loader.loadTexture("res/mud.png"));
		pack.setgTexture(loader.loadTexture("res/dirt.png"));
		pack.setbTexture(loader.loadTexture("res/path.png"));
		pack.setBlendMapTexture(loader.loadTexture("res/blendMap.png"));
		
		Terrain terrain = new Terrain(-1, -1, loader, pack, "heightmap");
			
		TexturedModel tree_model = loader.loadOBJModel("tree");
		tree_model.setTexture(loader.loadTexture("res/tree.png"));
		tree_model.setShineDamper(10);
		tree_model.setReflectivity(0.001f);	
		
		TexturedModel bush_model = loader.loadOBJModel("grass");
		bush_model.setTexture(loader.loadTexture("res/grassTexture.png"));
		bush_model.getTexture().setHasTransparency(true);
		bush_model.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Player player = new Player(tree_model, new Vector3f(-1000, 400, -1000), new Vector3f(0, 0, 0), 20f);
		entities.add(player);

		cam.setPlayer(player);
		
		for (int i = 0; i < 800; i++) {
			float x_pos = -(float) (Math.random() * 1800) - 10;
			float z_pos = -(float) (Math.random() * 1800) - 10;
			float y_pos = terrain.getTerrainHeight(x_pos, z_pos);
			Entity bush = new Entity(bush_model, new Vector3f(x_pos, y_pos, z_pos), new Vector3f(0, 0, 0), (float) Math.random() * 10f + 1f);
			entities.add(bush);
		}
										
		Light light = new Light(new Vector3f(-2500, 1000, -2500), new Vector3f(1, 1, 1));
		
		MasterRenderer renderer = new MasterRenderer(cam);
		float lastTime = 0;
		float deltaTime = 0;
		
		float startTime;
		while (!Window.isCloseRequested()) {
			startTime = (float) GLFW.glfwGetTime();
		
			player.Move(deltaTime, terrain);
			cam.move();
			Window.PollEvents();
		
			renderer.processTerrain(terrain);
			
			for(Entity entity: entities)
				renderer.processEntity(entity);
			
			renderer.render(light, new Vector3f(0.5f, 0.7f, 0.8f));
			
			Window.UpdateWindow();
			
			lastTime = (float) GLFW.glfwGetTime();
			deltaTime = lastTime - startTime;
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		Window.CloseWindow();

	}

}
