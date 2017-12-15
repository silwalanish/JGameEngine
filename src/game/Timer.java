package game;

import org.lwjgl.glfw.GLFW;

public class Timer {

	private static float lastTime = 0;
	private static float deltaTime = 0;

	public static void CalculateDelta() {
		float currentTime = (float) GLFW.glfwGetTime();
		deltaTime = currentTime - lastTime;
		lastTime = currentTime;
	}

	public static float GetDelta() {
		return deltaTime;
	}

}
