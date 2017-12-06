package controller;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import renderEngine.Window;

public class Mouse implements GLFWCursorPosCallbackI {
	
	private static Vector2f mouseLastPos = null;
	private static Vector2f mouseDelta;

	public static boolean getMouseButton(int buttonCode){
		return (GLFW.glfwGetMouseButton(Window.GetWindow(), buttonCode) == 1);
	}
	
	public static float getMouseDX(){
		return mouseDelta.x;
	}
	
	public static float getMouseDY(){
		return mouseDelta.y;
	}

	@Override
	public void invoke(long window, double xpos, double ypos) {
		Vector2f currentPos = new Vector2f((float) xpos, (float) ypos);
		if(mouseLastPos == null){
			mouseLastPos = currentPos;
		}
		mouseDelta = mouseLastPos.sub(currentPos);
		mouseLastPos = currentPos;
	}
	
	
}
