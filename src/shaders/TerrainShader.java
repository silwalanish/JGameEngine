package shaders;

import org.joml.Matrix4f;
import entities.Camera;
import entities.Light;
import renderEngine.Fog;

public class TerrainShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.glsl";
	
	private int transMatLoc;
	private int projMatLoc;
	private int viewMatLoc;
	private int cameraPosLoc;
	
	private int backgroundTextureLoc;
	private int rTextureLoc;
	private int gTextureLoc;
	private int bTextureLoc;
	private int blendMapLoc;
	
	private int lightPosLoc;
	private int lightColorLoc;
	private int shineDamperLoc;
	private int reflectivityLoc;
		
	private int skyColorLoc;
	private int fogDensityLoc;
	private int fogGradientLoc;

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textures");
		bindAttribute(2, "normals");
	}

	public void setTexturePack() {
		setUniformi(backgroundTextureLoc, 0);
		setUniformi(rTextureLoc, 1);
		setUniformi(gTextureLoc, 2);
		setUniformi(bTextureLoc, 3);
		setUniformi(blendMapLoc, 4);
	}
	
	public void setTransformationMatrix(Matrix4f mat4x4){
		setUniformMat(transMatLoc, mat4x4);
	}
	
	public void setProjectionMatrix(Matrix4f mat4x4){
		setUniformMat(projMatLoc, mat4x4);
	}
	
	public void setViewMatrix(Camera cam){
		setUniformMat(viewMatLoc, cam.getViewMatrix());
		setUniformVectorf(cameraPosLoc, cam.getPosition());
	}
	
	public void setLight(Light light){
		setUniformVectorf(lightPosLoc, light.getPosition());
		setUniformVectorf(lightColorLoc, light.getColor());
	}
	
	public void setSpecular(float shineDamper, float reflectivity){
		setUniformf(shineDamperLoc, shineDamper);
		setUniformf(reflectivityLoc, reflectivity);
	}
	
	public void setFog(Fog fog){
		setUniformf(fogDensityLoc, fog.getDensity());
		setUniformf(fogGradientLoc, fog.getGradient());
		setUniformVectorf(skyColorLoc, fog.getColor());
	}

	@Override
	protected void getAllUniformLocation() {
		backgroundTextureLoc = getUniformLocation("backgroundTexture");
		rTextureLoc = getUniformLocation("rTexture");
		gTextureLoc = getUniformLocation("gTexture");
		bTextureLoc = getUniformLocation("bTexture");
		blendMapLoc = getUniformLocation("blendMap");
		
		transMatLoc = getUniformLocation("transformationMatrix");
		projMatLoc = getUniformLocation("projectionMatrix");
		viewMatLoc = getUniformLocation("viewMatrix");
		
		lightPosLoc = getUniformLocation("lightPos");
		lightColorLoc = getUniformLocation("lightColor");
		shineDamperLoc = getUniformLocation("shineDamper");
		reflectivityLoc = getUniformLocation("reflectivity");
		
		cameraPosLoc = getUniformLocation("cameraPos");
		
		skyColorLoc = getUniformLocation("skyColor");
		fogDensityLoc = getUniformLocation("fogDensity");
		fogGradientLoc = getUniformLocation("fogGradient");
	}

}
