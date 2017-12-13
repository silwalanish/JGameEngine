package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private TerrainShader terrainShader = new TerrainShader();
	private EntityRenderer renderer;
	private TerrainRenderer terrainRenderer;
	private Camera camera;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(Camera cam) {
		camera = cam;
		renderer = new EntityRenderer(shader, camera.getProjectionMatrix());
		terrainRenderer = new TerrainRenderer(terrainShader, cam.getProjectionMatrix());
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void perpare(Vector3f skyColor) {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1);
	}
	
	public void render(Light light, Vector3f skyColor){
		perpare(skyColor);
		shader.start();
		shader.setLight(light);
		shader.setViewMatrix(camera);
		shader.setFog(0.000f, 1.5f, skyColor);
		renderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.setLight(light);
		terrainShader.setViewMatrix(camera);
		terrainShader.setFog(0.000f, 1.5f, skyColor);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity){
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
	}
}
