package renderEngine;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import models.TexturedModel;
import shaders.TerrainShader;
import terrains.Terrain;

public class TerrainRenderer {

	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader){
		this.shader = shader;
	}
		
	public void init(Matrix4f projMat){
		shader.start();
		shader.setProjectionMatrix(projMat);
		shader.setTexturePack();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains){
		for(Terrain terrain: terrains){
			prepareTerrain(terrain.getModel());
			prepareInstance(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTerrain(terrain.getModel());
		}
	}
	
	private void prepareTerrain(TexturedModel model){
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
				
		shader.setSpecular(model.getShineDamper(), model.getReflectivity());

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getVBID());
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
				
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getTBID());
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getNBID());
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getIBID());
	}
	
	private void prepareInstance(Terrain terrain){
		terrain.getTexturePack().bind();
		shader.setTransformationMatrix(terrain.getTranformationMatrix());		
	}
	
	private void unbindTerrain(TexturedModel model){
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
	}
		
}
