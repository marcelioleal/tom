package org.tom.config;

public enum VisionGroupConfig {

	ACTORID(1),
	ACTORNAME(2);
	
	int group;
	
	VisionGroupConfig(int group){
		this.group = group;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
}
