package tester;

import game.Engine;

public class Main {

	public static void main(String[] args) {
		Engine engine = new Engine(800, 600, "Game");
		MainScene main = new MainScene();
		engine.setScene(main);
		engine.start();
	}

}
