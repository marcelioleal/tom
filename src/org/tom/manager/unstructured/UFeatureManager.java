package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UFeature;
import org.tom.util.FileManipulation;


public class UFeatureManager extends JPABaseDAO<UFeature>{
	
	public void featurePersist(String id, String name, String description, String artifact, String version, String rule, String actors) throws ModeloException{
		UFeature feature = new UFeature();		
		feature.setId(id);
		feature.setName(FileManipulation.cleanFeatureName(name));
		feature.setDescription(description);
		feature.setArtifactName(artifact);
		feature.setVersion(version);
		feature.setRule(rule);
		feature.setActors(actors);
		feature.setType();
		feature.setDateTime();
		persist(feature);
		JPAUtil.commitTransaction();
	}	
	
	public void featurePersist(String id, String name, String artifact, String rule) throws ModeloException{
		UFeature feature = new UFeature();		
		feature.setId(id);
		feature.setName(name);
		feature.setArtifactName(artifact);
		feature.setRule(rule);
		feature.setType();
		feature.setDateTime();
		persist(feature);
		JPAUtil.commitTransaction();
	}	
	

	public UFeature getFeature(Long identifier){
		UFeature feature = new UFeature();
		try {
			feature = createQueryUnique("Select a from UFeature a where a.identifier=:id", new Object[]{"id",identifier});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return feature;
	}
	
	public UFeature getFeature(String id){
		UFeature feature = new UFeature();
		try {
			feature = createQueryUnique("Select a from UFeature a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return feature;
	}
	
	public List<UFeature> getUFeatureByArtifact(String artifactName){
		List<UFeature> features  = new ArrayList<UFeature>();
		try {
			features =  createQueryList("Select a from UFeature a where a.artifactName=:artifactName", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return features;
	}
	
	public List<UFeature> getAllFeature(){
		List<UFeature> features  = new ArrayList<UFeature>();
		try {
			features =  createQueryList("Select a from UFeature a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return features;
	}


}
