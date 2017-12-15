package shaders;

import org.joml.Matrix4f;

import entities.Camera;
import entities.Light;
import renderEngine.Fog;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";
	
	private int transMatLoc;
	private int projMatLoc;
	private int viewMatLoc;
	private int samplrLoc;
	private int lightPosLoc;
	private int lightColorLoc;
	private int shineDamperLoc;
	private int reflectivityLoc;
	private int cameraPosLoc;
	private int useFakeLightingLoc;
	private int skyColorLoc;
	private int fogDensityLoc;
	private int fogGradientLoc;;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textures");
		bindAttribute(2, "normals");
	}

	public void setSampler(int value) {
		setUniformi(samplrLoc, value);		
	}
	
	public void setTransformationMatrix(Matrix4f mat4x4){
		setUniformMat(transMatLoc, mat4x4);
	}
	
	public void setProjectionMatrix(Matrix4f mat4x4){
		setUniformMat(projMatLoc, mat4x4);
	}
	
	public void setViewMatrix(Camera cam){
		setUniformMat(viewMatLoc, cam.getViewMatrix());
		setUniformVectorf(cameraPosLoc, cam.getTransform().getPosition());
	}
	
	public void setLight(Light light){
		setUniformVectorf(lightPosLoc, light.getPosition());
		setUniformVectorf(lightColorLoc, light.getColor());
	}
	
	public void setSpecular(float shineDamper, float reflectivity){
		setUniformf(shineDamperLoc, shineDamper);
		setUniformf(reflectivityLoc, reflectivity);
	}
	
	public void setFakeLighting(boolean value){
		setUniformBoolean(useFakeLightingLoc, value);
	}
	
	public void setFog(Fog fog){
		setUniformf(fogDensityLoc, fog.getDensity());
		setUniformf(fogGradientLoc, fog.getGradient());
		setUniformVectorf(skyColorLoc, fog.getColor());
	}

	@Override
	protected void getAllUniformLocation() {
		samplrLoc = getUniformLocation("samplr");
		transMatLoc = getUniformLocation("transformationMatrix");
		projMatLoc = getUniformLocation("projectionMatrix");
		viewMatLoc = getUniformLocation("viewMatrix");
		lightPosLoc = getUniformLocation("lightPos");
		lightColorLoc = getUniformLocation("lightColor");
		shineDamperLoc = getUniformLocation("shineDamper");
		reflectivityLoc = getUniformLocation("reflectivity");
		cameraPosLoc = getUniformLocation("cameraPos");
		useFakeLightingLoc = getUniformLocation("useFakeLighting");
		skyColorLoc = getUniformLocation("skyColor");
		fogDensityLoc = getUniformLocation("fogDensity");
		fogGradientLoc = getUniformLocation("fogGradient");
	}

}
