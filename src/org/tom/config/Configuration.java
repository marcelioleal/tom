package org.tom.config;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="configuration")
public class Configuration {
	//#TODO Fazer cmo que essa classe leia o XML de configuracao correto 
	
	@ElementList(name="artifact", inline=true)
	List<ArtifactConfiguration> artifacts = new ArrayList<ArtifactConfiguration>();
	
//	@ElementList(name="baddir", inline=false)
//	List<String> badDirectories = new ArrayList<String>();

	@Element(name="badDir")
	String badDirectory;
	
	//@ElementList(name="formats", inline=true)
	//List<Format> formats = new ArrayList<Format>();


	public List<ArtifactConfiguration> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<ArtifactConfiguration> artifacts) {
		this.artifacts = artifacts;
	}


	public String getBadDirectory() {
		return badDirectory;
	}

	public void setBadDirectory(String badDirectory) {
		this.badDirectory = badDirectory;
	}
/*
	public List<Format> getFormats() {
		return formats;
	}

	public void setFormats(List<Format> formats) {
		this.formats = formats;
	}
	
	*/
}
