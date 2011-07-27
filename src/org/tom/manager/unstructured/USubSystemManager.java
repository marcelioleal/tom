package org.tom.manager.unstructured;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.URequerimentEntity;
import org.tom.entities.unstructuredDB.USubSystemEntity;


public class USubSystemManager extends JPABaseDAO<USubSystemEntity>{
	
	public void subSystemPersist(String name, String id, String artifact, String version, String rule, String description, String reqs) throws ModeloException{
		USubSystemEntity sSystem = new USubSystemEntity();		
		sSystem.setId(id);
		sSystem.setName(name);
		sSystem.setArtifact(artifact);
		sSystem.setVersion(version);
		sSystem.setRule(rule);
		sSystem.setDescription(description);
		sSystem.setRequeriments(reqs);
		persist(sSystem);
		JPAUtil.commitTransaction();
	}	

}
