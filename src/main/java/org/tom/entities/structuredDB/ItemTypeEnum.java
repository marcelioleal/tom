package org.tom.entities.structuredDB;

public enum ItemTypeEnum {
	
	NEED(1),
	FEATURE(2),
	ACTOR(3),
	USECASE(4),
	BUSINESSRULE(5),
	TESTCASE(6),
	CODE(7),
	REQUIREMENT(8),
	SUBSYSTEM(9);
	
	private ItemTypeEnum(int id){
		this.id = id;
	}
	
	private int id;
	
	public int getId(){
		return id;
	}
	
	public String getDesc(){
		if(getId() == 1){
			return "NEED";
		}else if(getId() == 2){
			return "FEATURE";
		}else if(getId() == 3){
			return "ACTOR";
		}else if(getId() == 4){
			return "USECASE";
		}else if(getId() == 5){
			return "BUSINESSRULE";
		}else if(getId() == 6){
			return "TESTCASE";
		}else if(getId() == 7){
			return "CODE";
		}else if(getId() == 8){
			return "REQUIREMENT";
		}else if(getId() == 9){
			return "SUBSYSTEM";
		}else{
			return "NOT FINDED";
		}
	}
	
}
