package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UNeed;
import org.tom.util.FileManipulation;


public class UNeedManager extends JPABaseDAO<UNeed>{
	
	public void needPersist(String id, String name,String description,String benefit, String artifact, String version, String rule, String features) throws ModeloException{
		UNeed need = new UNeed();		
		need.setId(id);
		need.setName(FileManipulation.cleanNeedName(name));
		need.setDescription(description);
		need.setBenefit(benefit);
		need.setArtifactName(artifact);
		need.setVersion(version);
		need.setRule(rule);
		need.setFeatures(features);
		need.setType();
		need.setDateTime();
		persist(need);
		JPAUtil.commitTransaction();
	}	
	
	public UNeed getNeed(Long identifier){
		UNeed need = new UNeed();
		try {
			need = createQueryUnique("Select a from UNeed a where a.identifier=:id", new Object[]{"id",identifier});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return need;
	}

	public UNeed getNeed(String id){
		UNeed need = new UNeed();
		try {
			need = createQueryUnique("Select a from UNeed a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return need;
	}

	public List<UNeed> getUNeedByArtifact(String artifactName){
		List<UNeed> needs  = new ArrayList<UNeed>();
		try {
			needs =  createQueryList("Select a from UNeed a where a.artifactName=:artifactName", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return needs;
	}
	
	public List<UNeed> getAllNeed(){
		List<UNeed> needs  = new ArrayList<UNeed>();
		try {
			needs =  createQueryList("Select a from UNeed a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return needs;
	}

}
