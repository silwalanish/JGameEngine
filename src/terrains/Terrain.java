package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import loader.Loader;
import models.TexturedModel;
import textures.TerrainTexturePack;

public class Terrain {

	private float x;
	private float z;
	private TexturedModel model;
	private TerrainTexturePack textures;
	private float[][] heights;
	
	public static final float SIZE = 1800;
	public static final float MAX_HEIGHT = 400;
	public static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack textures, String heightMap) {
		this.model = generateTerrain(loader, heightMap);
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.textures = textures;
	}

	public float getTerrainHeight(float worldX, float worldZ) {
		float terrainX = worldX - x;
		float terrainZ = worldZ - z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= (heights.length - 1) || gridZ >= (heights.length - 1) ||
				gridX < 0 || gridZ < 0) {
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1-zCoord)) {
			answer = barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), 
						new Vector3f(1,	heights[gridX + 1][gridZ], 0),
						new Vector3f(0, heights[gridX][gridZ + 1], 1),
						new Vector2f(xCoord, zCoord));
		} else {
			answer = barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0),
						new Vector3f(1,	heights[gridX + 1][gridZ + 1], 1),
						new Vector3f(0,	heights[gridX][gridZ + 1], 1),
						new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	private TexturedModel generateTerrain(Loader loader, String heightMap) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				float height = getHeight(i, j, image);
				heights[j][i] = height;
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;				
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(i, j, image);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadTexturedModel(vertices, textureCoords, normals, indices);
	}

	private float getHeight(int x, int y, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || y < 0 || y >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COLOR / 2f;
		height /= MAX_PIXEL_COLOR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}

	private Vector3f calculateNormal(int x, int y, BufferedImage image) {
		float heightL = getHeight(x - 1, y, image);
		float heightR = getHeight(x + 1, y, image);
		float heightU = getHeight(x, y - 1, image);
		float heightD = getHeight(x, y + 1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU).normalize();
		return normal;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public TexturedModel getModel() {
		return model;
	}

	public TerrainTexturePack getTexturePack() {
		return textures;
	}

	public Matrix4f getTranformationMatrix() {
		Matrix4f transMat = new Matrix4f();
		transMat.identity();
		transMat.translation(new Vector3f(x, 0, z));

		return transMat;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

}
