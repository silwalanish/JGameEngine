package controller;

import org.lwjgl.glfw.GLFW;

import renderEngine.Window;

public class Keyboard {
		
	public static boolean getKey(int keyCode){
		return (GLFW.glfwGetKey(Window.GetWindow(), keyCode) == 1);
	}
		
}
