package loader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;

import models.RawModel;
import models.TexturedModel;
import textures.Texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Loader {

	private List<Integer> b_ids = new ArrayList<Integer>();
	private List<Integer> tex_ids = new ArrayList<Integer>();

	public RawModel loadModel(float[] vertices, float[] normals, int[] indices) {
		int vb_id = bindArrayBuffer(vertices);
		int nb_id = bindArrayBuffer(normals);
		int ib_id = bindIndices(indices);
		unbind();
		return new RawModel(vb_id, nb_id, ib_id, indices.length);
	}
	
	public TexturedModel loadOBJModel(String name){
		ModelData data = OBJFileLoader.loadOBJ(name);
		return loadTexturedModel(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
	}
	
	public TexturedModel loadTexturedModel(float[] vertices, float[] texCoords, float[] normals, int[] indices){
		int vb_id = bindArrayBuffer(vertices);
		int tb_id = bindArrayBuffer(texCoords);
		int nb_id = bindArrayBuffer(normals);
		int ib_id = bindIndices(indices);
		unbind();
		return new TexturedModel(vb_id, tb_id, nb_id, ib_id, indices.length);
	}
	
	public Texture loadTexture(String fileName){
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("res/" + fileName));
			int width = bi.getWidth();
			int height = bi.getHeight();
			
			int[] pixel_raw = new int[width * height * 4];
			pixel_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++){
					// 0xAARRGGBB
					int pixel = pixel_raw[i * width + j];
					pixels.put((byte) ((pixel >> 16 ) & 0xFF)); // RED
					pixels.put((byte) ((pixel >> 8) & 0xFF));	// GREEN
					pixels.put((byte) ((pixel) & 0xFF));	// BLUE
					pixels.put((byte) ((pixel >> 24) & 0xFF));	// ALPHA
				}
			}
			
			pixels.flip();
			
			int id = GL11.glGenTextures();
			tex_ids.add(id);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1f);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
			
			return new Texture(id, width, height);
		} catch (IOException e) {
			System.err.println("Could not load texture: " + fileName);
			e.printStackTrace();
			System.exit(-1);
		}
		
		return null;
	}

	private int bindArrayBuffer(float[] vertices) {
		int b_id = GL15.glGenBuffers();
		b_ids.add(b_id);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, b_id);
		FloatBuffer buffer = toFloatBuffer(vertices);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		return b_id;
	}

	private int bindIndices(int[] indices) {
		int b_id = GL15.glGenBuffers();
		b_ids.add(b_id);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, b_id);
		IntBuffer buffer = toIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		return b_id;
	}

	private static IntBuffer toIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private void unbind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void cleanUp() {
		for (int b_id : b_ids) {
			GL15.glDeleteBuffers(b_id);
		}
		for (int tex_id : tex_ids){
			GL11.glDeleteTextures(tex_id);
		}
	}
}
