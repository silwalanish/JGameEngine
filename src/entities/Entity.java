package entities;

import java.util.ArrayList;
import java.util.List;

import components.Component;
import materials.Material;
import models.TexturedModel;

public class Entity {

	protected TexturedModel model;
	protected Transform transform;
	protected Material material;
	protected List<Component> components = new ArrayList<Component>();
	
	public Entity(TexturedModel model){
		this.model = model;
		this.transform = new Transform();
		this.material = new Material();
	}

	public Entity(TexturedModel model, Transform transform) {
		this.model = model;
		this.transform = transform;
		this.material = new Material();
	}
	
	public Entity(TexturedModel model, Transform transform, Material material) {
		this.model = model;
		this.transform = transform;
		this.material = material;
	}
	
	public void AddComponent(Component component){
		component.setParent(this);
		components.add(component);
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void update(float deltaTime) {
		for (Component component: components){
			component.update(deltaTime);
		}
	}

}
