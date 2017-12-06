package models;

import textures.Texture;

public class TexturedModel extends RawModel {

	private Texture texture = null;
	private int tb_id;
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	public TexturedModel(int vbid, int tbid, int nbid, int ibid, int vc) {
		super(vbid, nbid, ibid, vc);
		this.tb_id = tbid;
	}
	
	public void setTexture(Texture tex){
		this.texture = tex;
	}

	public Texture getTexture() {
		return texture;
	}	
	
	public int getTBID(){
		return tb_id;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}
	
}


