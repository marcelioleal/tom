package org.tom.entities.structuredDB;

public enum LinkTypeEnum {

	DEPENDENCY(1),
	SATISFACTION(2),
	ABSTRACTION(3),
	CONTRIBUTION(4),
	BELONGS(5),
	EVOLUTION(6);
	
	private int id;
	
	private LinkTypeEnum(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
}
