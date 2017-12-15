# JGameEngine
3D Game Engine written in JAVA using opengl 2.1 and LWJGL 3

# How to use? 
1. Download the project
2. Import project in eclipse
3. Setup as follow
# Main.java
```
public class Main {

  public static void main(String[] args) {
    Engine engine = new Engine(800, 600, "Game");
    MainScene main = new MainScene();
    engine.setScene(main);
    engine.start();
  }

}
```
# MainScene.java
```
public class MainScene extends Scene {

	private Entity player;
	
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
				
		player = new Entity(sphere, 
				new Transform(new Vector3f(-1000, 200, -1000), new Vector3f(0, 0, 0), 20f));
		player.AddComponent(new Movement());
		entities.add(player);
		camera.setFollow(player);
	}

	@Override
	public void update(float deltaTime) {
		camera.Move(deltaTime);
		for(Entity entity: entities){
			entity.update(deltaTime);
		}
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
```

> You can also find the example in tester package.
