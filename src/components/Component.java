package components;

import entities.Entity;

public abstract class Component {
	
	protected Entity parent;	
	
	public Component(){
		
	}
	
	public Component(Entity parent) {
		this.parent = parent;
	}
		
	public abstract void update(float deltaTime);
		
	public Entity getParent(){
		return parent;
	}
	
	public void setParent(Entity entity){
		this.parent = entity;
	}
	
}
