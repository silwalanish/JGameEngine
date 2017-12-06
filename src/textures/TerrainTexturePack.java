package textures;

import org.lwjgl.opengl.GL13;

public class TerrainTexturePack {
	
	private Texture backgroundTexture;
	private Texture rTexture;
	private Texture gTexture;
	private Texture bTexture;
	private Texture blendMapTexture;
	
	public TerrainTexturePack(){
		
	}
	
	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, Texture gTexture, Texture bTexture,
			Texture blendMapTexture) {
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
		this.blendMapTexture = blendMapTexture;
	}
	
	public void bind(){
		this.backgroundTexture.bind(GL13.GL_TEXTURE0);
		this.rTexture.bind(GL13.GL_TEXTURE1);
		this.gTexture.bind(GL13.GL_TEXTURE2);
		this.bTexture.bind(GL13.GL_TEXTURE3);
		this.blendMapTexture.bind(GL13.GL_TEXTURE4);
	}
	
	public void setBackgroundTexture(Texture backgroundTexture) {
		this.backgroundTexture = backgroundTexture;
	}

	public void setrTexture(Texture rTexture) {
		this.rTexture = rTexture;
	}

	public void setgTexture(Texture gTexture) {
		this.gTexture = gTexture;
	}

	public void setbTexture(Texture bTexture) {
		this.bTexture = bTexture;
	}

	public void setBlendMapTexture(Texture blendMapTexture) {
		this.blendMapTexture = blendMapTexture;
	}

	public Texture getBackgroundTexture() {
		return backgroundTexture;
	}

	public Texture getrTexture() {
		return rTexture;
	}

	public Texture getgTexture() {
		return gTexture;
	}

	public Texture getbTexture() {
		return bTexture;
	}

	public Texture getBlendMapTexture() {
		return blendMapTexture;
	}
	
}
