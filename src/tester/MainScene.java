package tester;

import org.joml.Vector3f;

import entities.Entity;
import entities.Light;
import entities.Player;
import game.Scene;
import models.TexturedModel;
import renderEngine.Fog;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.TerrainTexturePack;

public class MainScene extends Scene {

	private Player player;
	
	@Override
	public void init() {
		light = new Light(new Vector3f(0, 1000, 0), new Vector3f(1, 1, 1));
		fog = new Fog(0.0008f, 2f, new Vector3f(0.5f, 0.7f, 0.8f));
		
		TerrainTexturePack textures = new TerrainTexturePack();
		textures.setBackgroundTexture(loader.loadTexture("grass.png"));
		textures.setrTexture(loader.loadTexture("mud.png"));
		textures.setbTexture(loader.loadTexture("path.png"));
		textures.setgTexture(loader.loadTexture("dirt.png"));
		textures.setBlendMapTexture(loader.loadTexture("blendMap.png"));
		
		Terrain terrain = new Terrain(-1, -1, loader, textures, "heightMap");
		terrain.getModel().setReflectivity(0.1f);
		terrain.getModel().setShineDamper(10);
		terrains.add(terrain);
							
		TexturedModel sphere = loader.loadOBJModel("sphere");
		sphere.setTexture(loader.loadTexture("sphere.png"));
		sphere.setShineDamper(50);
		sphere.setReflectivity(0.1f);	
				
		player = new Player(sphere, new Vector3f(-1000, 20, -1000), new Vector3f(0, 0, 0), 20f);		
		entities.add(player);
		camera.setPlayer(player);
	}

	@Override
	public void update(float deltaTime) {
		player.Move(deltaTime);
		camera.Move(deltaTime);
	}
	
	@Override
	public void render(MasterRenderer renderer){
		for(Terrain terrain: terrains)
			renderer.processTerrain(terrain);
		
		for(Entity entity: entities)
			renderer.processEntity(entity);
		
		renderer.render();
	}

	@Override
	public void exit() {
		loader.cleanUp();		
	}

}
