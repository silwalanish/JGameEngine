package game;

import renderEngine.MasterRenderer;
import renderEngine.Window;

public class Engine {

	private Scene scene;
	protected MasterRenderer renderer;

	public Engine(int width, int height, String title) {
		Window.CreateWindow(width, height, title);
		renderer = new MasterRenderer();
	}

	public void start() {
		scene.init();
		renderer.setScene(scene);
		gameloop();
	}

	public void gameloop() {
		while (!Window.isCloseRequested()) {
			Timer.CalculateDelta();
			Window.PollEvents();

			scene.update(Timer.GetDelta());

			scene.render(renderer);

			Window.UpdateWindow();
		}
		exit();
	}

	public void exit() {
		scene.exit();
		renderer.cleanUp();
		Window.CloseWindow();
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		renderer.setScene(scene);
	}

}
