package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UUseCase;
import org.tom.util.FileManipulation;

public class UUseCaseManager extends JPABaseDAO<UUseCase>{
	
	public void useCasePersist(String id, String name, String description, String actors, String flows, String features, String includesUC,String extendsUC,String dependentsUC,String businessRuleRel,String artifact, String version, String rule) throws ModeloException{
			UUseCase useCase = new UUseCase();
			useCase.setId(FileManipulation.cleanUCName(id));
			useCase.setName(FileManipulation.cleanUCName(name));
			useCase.setArtifactName(artifact);
			useCase.setVersion(version);
			useCase.setRule(rule);
			useCase.setDescription(description);
			useCase.setActors(actors);
			useCase.setFlows(flows);
			useCase.setFeatures(features);
			useCase.setIncludesUC(includesUC);
			useCase.setExtendsUC(extendsUC);
			useCase.setDependentsUC(dependentsUC);
			useCase.setBusinessRulesRel(businessRuleRel);
			useCase.setType();
			useCase.setDateTime();
			persist(useCase);	
			JPAUtil.commitTransaction();
	}	
	
	public UUseCase getUseCase(String useCaseId){
		UUseCase useCase = new UUseCase();
		try {
			useCase = createQueryUnique("Select a from UUseCase a where a.id=:id", new Object[]{"id",useCaseId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return useCase;
	}
	
	public UUseCase getUseCase(Long identifier){
		UUseCase useCase = new UUseCase();
		try {
			useCase = createQueryUnique("Select a from UUseCase a where a.identifier=:id", new Object[]{"id",identifier});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return useCase;
	}
	
	public List<UUseCase> getUUseCaseByArtifact(String artifactName){
		List<UUseCase> ucs  = new ArrayList<UUseCase>();
		try {
			ucs =  createQueryList("Select a from UUseCase a where a.artifactName=:artifactName", new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return ucs;
	}
	
	public List<UUseCase> getAllUseCase(){
		List<UUseCase> ucs  = new ArrayList<UUseCase>();
		try {
			ucs =  createQueryList("Select a from UUseCase a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return ucs;
	}
	
}
