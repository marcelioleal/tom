package org.tom.config;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="artifact")
public class ArtifactConfiguration {
	@Element(name="ini")
	private String ini;
	
	@Element(name="type")
	private String type;
	
	@ElementList(name="ae", inline=true)
	List<AE> aes = new ArrayList<AE>();
	
	
	public String getIni() {
		return ini;
	}
	public void setIni(String ini) {
		this.ini = ini;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<AE> getAes() {
		return aes;
	}
	public void setAes(List<AE> aes) {
		this.aes = aes;
	}
	
	
}
