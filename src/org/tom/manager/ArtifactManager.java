package org.tom.manager;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.Artifact;


public class ArtifactManager extends JPABaseDAO<Artifact>{
	
	public void artifactPersist(String name, String type, String path, String version) throws ModeloException{
		Artifact artifact = new Artifact(name);
		artifact.setType(type);
		artifact.setPath(path);
		artifact.setVersion(version);
		artifact.setDateTime();
		
		persist(artifact);
		JPAUtil.commitTransaction();
	}	
	

	public Artifact getArtifact(String name){
		Artifact artifact = new Artifact();
		try {
			artifact = createQueryUnique("Select a from Artifact a where a.name=:name", new Object[]{"name",name});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return artifact;
	}
	
	public List<Artifact> getArtifactsByType(String type){
		List<Artifact> artifacts = new ArrayList<Artifact>();
		try {
			artifacts = createQueryList("Select a from Artifact a where a.type=:type", new Object[]{"type",type});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return artifacts;
	}

}
