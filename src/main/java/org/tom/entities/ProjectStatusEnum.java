package org.tom.entities;

public enum ProjectStatusEnum {

	UNSTRUCTURED("U"),
	STRUCTURED("S");
	
	private String id;
	
	private ProjectStatusEnum(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

}
