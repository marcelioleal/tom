package org.tom.entities;

public enum ArtifactTypeEnum {
	
	VISION("VD"),
	USECASESPEC("UCS"),
	TESTCASESPEC("TCS"),
	CODE("CD"),
	BUSINESSRULES("BR");
	
	String type;
	
	ArtifactTypeEnum(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
