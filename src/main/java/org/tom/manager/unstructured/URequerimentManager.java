package org.tom.manager.unstructured;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.URequerimentEntity;


public class URequerimentManager extends JPABaseDAO<URequerimentEntity>{
	
	public void requerimentPersist(String name, String id, String artifact, String version, String rule, String description, String useCases) throws ModeloException{
		URequerimentEntity req = new URequerimentEntity();		
		req.setId(id);
		req.setName(name);
		req.setArtifact(artifact);
		req.setVersion(version);
		req.setRule(rule);
		req.setDescription(description);
		req.setUseCases(useCases);
		persist(req);
		JPAUtil.commitTransaction();
	}	
	

	public URequerimentEntity getActor(String id){
		URequerimentEntity requeriment = new URequerimentEntity();
		try {
			requeriment = createQueryUnique("Select a from RequerimentEntity a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return requeriment;
	}


}
