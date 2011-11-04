package org.tom.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ae")
public class AE {
	
	@Element(name="vision")
	private String vision;
	
	@Element(name="desc")
	private String desc;

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	
}
