package renderEngine;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import entities.Entity;
import models.TexturedModel;
import shaders.StaticShader;

public class EntityRenderer {
	
	private StaticShader shader;

	public EntityRenderer(StaticShader shader) {
		this.shader = shader;
	}
		
	public void init(Matrix4f projMat){
		shader.start();
		shader.setProjectionMatrix(projMat);
		shader.stop();
	}
		
	public void render(Map<TexturedModel, List<Entity>> entities){
		for(TexturedModel model: entities.keySet()){
			prepareModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity: batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}			
			unbindModel(model);
		}
	}
	
	private void prepareModel(TexturedModel model){
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
				
		shader.setSpecular(model.getShineDamper(), model.getReflectivity());
		model.getTexture().bind(GL13.GL_TEXTURE0);
		if (model.getTexture().getHasTransparency()){
			MasterRenderer.disableCulling();
		}

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getVBID());
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
				
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getTBID());
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, model.getNBID());
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getIBID());
	}
	
	private void prepareInstance(Entity entity){
		shader.setTransformationMatrix(entity.getTranformationMatrix());		
	}
	
	private void unbindModel(TexturedModel model){
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		MasterRenderer.enableCulling();
	}

	
}
