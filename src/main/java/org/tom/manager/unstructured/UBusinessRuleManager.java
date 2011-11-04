package org.tom.manager.unstructured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.unstructuredDB.UItem;
import org.tom.entities.unstructuredDB.UItemTypeEnum;
import org.tom.util.FileManipulation;


public class UBusinessRuleManager extends JPABaseDAO<UItem>{
	
	public void businessRulePersist(String id, String name, String description, String artifact, String version, String rule) throws ModeloException{
		UItem item = new UItem();		
		item.setId(id);
		item.setName(FileManipulation.cleanBRName(name));
		item.setDescription(description);
		item.setArtifactName(artifact);
		item.setVersion(version);
		item.setRule(rule);
		item.setType(UItemTypeEnum.BUSINESSRULE.getType());
		item.setDateTime();
		persist(item);
		JPAUtil.commitTransaction();
	}	
	

	public UItem getItem(String id){
		UItem item = new UItem();
		try {
			item = createQueryUnique("Select a from UItem a where a.id=:id", new Object[]{"id",id});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public List<UItem> getUBusinessRuleByArtifact(String artifactName){
		List<UItem> items  = new ArrayList<UItem>();
		try {
			//String sql = "Select * from u_item a where a.artifactName=:artifactName and a.type='"+UItemTypeEnum.BUSINESSRULE.getType()+"'";
			//System.out.println(sql);
			//items =  createNativeQueryList(sql, UItem.class,new Object[]{"artifactName",artifactName});
			items =  createQueryList("Select a from UItem a where a.artifactName=:artifactName and a.type='"+UItemTypeEnum.BUSINESSRULE.getType()+"'",new Object[]{"artifactName",artifactName});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<UItem> getAllBusinessRule(){
		List<UItem> items  = new ArrayList<UItem>();
		try {
			items =  createQueryList("Select a from UItem a where a.type='"+UItemTypeEnum.BUSINESSRULE.getType()+"'");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}


}
