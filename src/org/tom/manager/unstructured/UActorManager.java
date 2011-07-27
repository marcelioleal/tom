package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UActor;
import org.tom.util.FileManipulation;


public class UActorManager extends JPABaseDAO<UActor>{
	
	public void actorPersist(String actorId,String name, String artifact, String version, String rule, String description, String goals) throws ModeloException{
		UActor actor = new UActor();
		actor.setId(actorId);
		actor.setName(FileManipulation.cleanActorName(name));
		actor.setArtifactName(artifact);
		actor.setVersion(version);
		actor.setRule(rule);
		actor.setDescription(description);
		actor.setGoals(goals);
		actor.setType();
		actor.setDateTime();
		persist(actor);
		JPAUtil.commitTransaction();
	}
	
	public void actorPersist(String actorId,String name, String artifact, String rule) throws ModeloException{
		UActor actor = new UActor();		
		actor.setId(actorId);
		actor.setName(name);
		actor.setArtifactName(artifact);
		actor.setRule(rule);
		actor.setType();
		actor.setDateTime();
		persist(actor);
		JPAUtil.commitTransaction();
	}
	

	public UActor getActor(String actorId){
		UActor actor = new UActor();
		try {
			actor = createQueryUnique("Select a from UActor a where a.id=:id", new Object[]{"id",actorId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return actor;
	}
	
	public List<UActor> getUActorByArtifact(String artifactName){
		List<UActor> actors  = new ArrayList<UActor>();
		try {
			actors =  createQueryList("Select a from UActor a where a.artifactName=:artifactName", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return actors;
	}
	
	public List<UActor> getAllActor(){
		List<UActor> actors  = new ArrayList<UActor>();
		try {
			actors =  createQueryList("Select a from UActor a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return actors;
	}



}
