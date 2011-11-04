package org.tom.config;

import java.io.File;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.tom.entities.ArtifactTypeEnum;

public class MapArtifacts {

	private Configuration conf;
	
	public MapArtifacts(){
		try {
			Serializer serializer = new Persister();
			conf = serializer.read(Configuration.class, new File("config/config.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean canConvert(String artifact){
		//#TODO Restricao de Doc. Adicionar na configuracao.
		boolean can = false;
		if(artifact.endsWith(".doc")){
			for (ArtifactConfiguration a : this.conf.getArtifacts()) {
				if((a.getIni().length() < artifact.length()) && (artifact.substring(0,a.getIni().length()).equals(a.getIni()))){
						can = true;
						break;
					}
			}
		}
		return can;
	}
	
	public boolean canAnalyseCode(String artifact){
		//#TODO Restricao de Code. Adicionar na configuracao.
		boolean can = false;
		if(artifact.endsWith(".php")|| artifact.endsWith(".java") || artifact.endsWith(".aspx") || artifact.endsWith(".htm"))
			can = true;
		return can;
	}
		
	public boolean canAnalyseDirectory(String dirName){
		//for(String badDir : this.conf.getBadDirectories()){
		String badDir = this.conf.getBadDirectory(); 
		if(badDir.trim().equalsIgnoreCase(dirName.trim()))
			return false;
		//}
		return true;
	}
	
	public List<AE> getAEs(String artifact){
		for (ArtifactConfiguration a : this.conf.getArtifacts()) 
			if((a.getIni().length() < artifact.length()) && (artifact.substring(0,a.getIni().length()).equals(a.getIni())))
					return a.getAes();		
		//implementado errado ver com o Daniel
		return null;
	}
	
	public boolean canAnalyse(String artifact){
		for (ArtifactConfiguration a : this.conf.getArtifacts()) 
			if((a.getIni().length() < artifact.length()) && (artifact.substring(0,a.getIni().length()).equals(a.getIni())))
					return true;		
		//implementado errado ver com o Daniel
		return false;
	}
	
	public ArtifactTypeEnum getArtifactType(String artifact){
		for (ArtifactConfiguration a : this.conf.getArtifacts()) 
			if((a.getIni().length() < artifact.length()) && (artifact.substring(0,a.getIni().length()).equals(a.getIni()))){
				if(a.getType().equalsIgnoreCase("Vision"))
					return ArtifactTypeEnum.VISION;
				else if(a.getType().equalsIgnoreCase("UseCaseSpecification"))
					return ArtifactTypeEnum.USECASESPEC;
				else if(a.getType().equalsIgnoreCase("BusinessRuleSpecification"))
					return ArtifactTypeEnum.BUSINESSRULES;
				else if(a.getType().equalsIgnoreCase("TestCaseSpecification"))
					return ArtifactTypeEnum.TESTCASESPEC;
				else if(a.getType().equalsIgnoreCase("Code"))
					return ArtifactTypeEnum.CODE;
			}
		return ArtifactTypeEnum.CODE;
	}
	
}
