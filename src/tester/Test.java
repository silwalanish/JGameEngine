package tester;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Test {
	
	public static void main(String[] args) {
		new Test().run();
	} 
	
	public void run() {
		Matrix4f mat = new Matrix4f();
		mat.identity();
		mat.translation(new Vector3f(1, 0, -25));
		mat.rotate((float) Math.toRadians(90), new Vector3f(1, 0, 0));
		mat.rotate((float) Math.toRadians(90), new Vector3f(0, 1, 0));
		mat.rotate((float) Math.toRadians(90), new Vector3f(0, 0, 1));
		mat.scale(5);
		displayMatrix(mat);
	}
	
	public void displayMatrix(Matrix4f mat){
		for(int i = 0; i < 4; i++){
			Vector4f dest = new Vector4f();
			mat.getRow(i, dest);
			System.out.printf("%f\t%f\t%f\t%f\n", dest.x, dest.y, dest.z, dest.w);			
		}
	}
	
}
