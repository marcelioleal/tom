package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UCode;


public class UCodeManager extends JPABaseDAO<UCode>{
	
	public void codePersist(String id, String name, String description,String useCases,String businessRules,String testCases, String artifact, String version, String rule) throws ModeloException{
		UCode code = new UCode();		
		code.setId(id);
		code.setName(name);
		code.setDescription(description);
		code.setUseCases(useCases);
		code.setBusinessRules(businessRules);
		code.setTestCases(testCases);
		code.setArtifactName(artifact);
		code.setVersion(version);
		code.setRule(rule);
		code.setType();
		code.setDateTime();
		persist(code);
		JPAUtil.commitTransaction();
	}	

	
	public UCode getCode(Long identifier){
		UCode item = new UCode();
		try {
			item = createQueryUnique("Select a from UCode a where a.identifier=:id", new Object[]{"id",identifier});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}

	public UCode getCode(String id){
		UCode item = new UCode();
		try {
			item = createQueryUnique("Select a from UCode a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public List<UCode> getUCodeByArtifact(String artifactName){
		List<UCode> items  = new ArrayList<UCode>();
		try {
			items =  createQueryList("Select a from UCode a where a.artifactName=:artifactName ", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<UCode> getAllCode(){
		List<UCode> items  = new ArrayList<UCode>();
		try {
			items =  createQueryList("Select a from UCode a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}


}
