package org.tom.entities.unstructuredDB;

public enum UItemTypeEnum {

	NEED("ND"),
	FEATURE("FT"),
	ACTOR("AC"),
	USECASE("UC"),
	TESTCASE("TC"),
	REQUIREMENT("RE"),
	SUBSYSTEM("SS"),
	BUSINESSRULE("BR"),
	CODE("CD");
	
	String type;
	
	UItemTypeEnum(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
