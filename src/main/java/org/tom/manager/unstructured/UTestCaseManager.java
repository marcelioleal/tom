package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UTestCase;


public class UTestCaseManager extends JPABaseDAO<UTestCase>{
	
	public void testCasePersist(String id, String name, String description,String useCase, String artifact, String version, String rule) throws ModeloException{
		UTestCase testCase = new UTestCase();		
		testCase.setId(id);
		testCase.setName(name);
		testCase.setDescription(description);
		testCase.setUseCase(useCase);
		testCase.setArtifactName(artifact);
		testCase.setVersion(version);
		testCase.setRule(rule);
		testCase.setType();
		testCase.setDateTime();
		persist(testCase);
		JPAUtil.commitTransaction();
	}	
	

	public UTestCase getTestCase(Long identifier){
		UTestCase item = new UTestCase();
		try {
			item = createQueryUnique("Select a from UTestCase a where a.identifier=:id", new Object[]{"id",identifier});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public UTestCase getTestCase(String id){
		UTestCase item = new UTestCase();
		try {
			item = createQueryUnique("Select a from UTestCase a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public List<UTestCase> getUTestCaseByArtifact(String artifactName){
		List<UTestCase> items  = new ArrayList<UTestCase>();
		try {
			items =  createQueryList("Select a from UTestCase a where a.artifactName=:artifactName ", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<UTestCase> getAllTestCase(){
		List<UTestCase> items  = new ArrayList<UTestCase>();
		try {
			items =  createQueryList("Select a from UTestCase a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}


}
