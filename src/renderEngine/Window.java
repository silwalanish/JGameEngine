package renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import controller.Mouse;

import org.lwjgl.glfw.GLFWVidMode;

public class Window {

	private static long WINDOW = 0;
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private static String TITLE = "JGameEngine";
	
	public static void CreateWindow(){
		CreateWindow(WIDTH, HEIGHT, TITLE);
	}

	public static void CreateWindow(int width, int height) {
		CreateWindow(width, height, TITLE);
	}

	public static void CreateWindow(int width, int height, String title) {
		if (!GLFW.glfwInit()) {
			System.err.println("Could not init glfw!");
			System.exit(-1);
		}

		WIDTH = width;
		HEIGHT = height;
		TITLE = title;

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		WINDOW = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);

		if (Window.WINDOW == 0) {
			System.err.println("Could not create window!");
			System.exit(-1);
		}

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(WINDOW, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
		
		GLFW.glfwSetCursorPosCallback(WINDOW, new Mouse());

		GLFW.glfwShowWindow(WINDOW);

		GLFW.glfwMakeContextCurrent(WINDOW);

		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static boolean isCloseRequested() {
		return GLFW.glfwWindowShouldClose(WINDOW);
	}

	public static void PollEvents() {
		GLFW.glfwPollEvents();
	}

	public static void UpdateWindow() {
		GLFW.glfwSwapBuffers(WINDOW);
	}

	public static void CloseWindow() {
		GLFW.glfwTerminate();
	}

	public static long GetWindow() {
		return WINDOW;
	}
	
	public static int GetWidth(){
		return WIDTH;
	}
	
	public static int GetHeight(){
		return HEIGHT;
	}

}
