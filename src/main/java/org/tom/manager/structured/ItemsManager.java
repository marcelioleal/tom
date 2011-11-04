package org.tom.manager.structured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.ItemTypeEnum;

public class ItemsManager extends JPABaseDAO<Item>{
	
	
	public Item itemPersist(String id, String name, String description, Integer type,Integer idBaseline,String artifact,boolean automatic,String version) throws ModeloException{
		Item item = new Item();
		item.setId(id.toUpperCase());
		item.setName(name.toUpperCase());
		item.setDescription(description);
		item.setIdBaseline(idBaseline);
		item.setVersion(version);
		item.setArtifactName(artifact);
		item.setAutomatic(automatic);
		item.setIdTypeItem(type);
		
		persist(item);
		JPAUtil.commitTransaction();
		
		return item;
	}
	
	public List<Item> getItems(){
		List<Item> items  = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<Item> getItems(int baselineId){
		List<Item> items  = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a where a.idBaseline=:baseline", new Object[]{"baseline",baselineId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<Item> getItemsByTypeAndArtifactAndBaseline(int type, String artifactName, Integer idBaseline){
		List<Item> items = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a where a.idTypeItem=:type and a.idBaseline =:baseline and a.artifactName=:artifactName", new Object[]{"type",type}, new Object[]{"artifactName",artifactName}, new Object[]{"baseline",idBaseline});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public Item getItemByNameAndTypeAndBaseline(String name, int type, Integer baselineId){
		Item item = new Item();
		try {
			String sql = "Select a from Item a where a.name=:name AND a.idTypeItem=:type AND a.idBaseline=:baseline";
			item = createQueryUnique(sql, new Object[]{"name",name.toUpperCase().trim()},new Object[]{"type",type},new Object[]{"baseline",baselineId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public Item getItemByIdAndTypeAndBaseline(String id, int type, Integer baselineId){
		Item item = new Item();
		try {
			String sql = "Select a from Item a where a.idLoco=:id AND a.idTypeItem=:type AND a.idBaseline=:baseline";
			item = createQueryUnique(sql, new Object[]{"id",id.toUpperCase().trim()},new Object[]{"type",type},new Object[]{"baseline",baselineId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public List<Item> getItemsRelatedByItemBaseIdAndTypeRelAndBaseline(int idItem,int type, int baselineId){
		List<Item> items = new ArrayList<Item>();
		try {
			String sql = "Select it.* from item it " +
			"JOIN link li ON (li.idItemRel = it.IdItem) " +
			"where it.idTypeItem =:type AND li.idItem =:idItem and it.idBaseline=:baseline";
			items = createNativeQueryList(sql,Item.class, new Object[]{"idItem",idItem},new Object[]{"type",type},new Object[]{"baseline",baselineId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<Item> getItemsByTypeAndBaseline(int type,int baselineId){
		List<Item> items = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a where a.idTypeItem=:id AND a.idBaseline=:baseline", new Object[]{"id",type}, new Object[]{"baseline",baselineId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}	

	// End ok methods
	
	public Item getItemById(int itemId){
		Item item = new Item();
		try {
			item = createQueryUnique("Select a from Item a where a.idItem=:id", new Object[]{"id",itemId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}
	
	public List<Item> getItemsByType(int type){
		List<Item> items = new ArrayList<Item>();
		try {
			items = createQueryList("Select a from Item a where a.idTypeItem=:id", new Object[]{"id",type});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}	
	
	public List<Item> getFeaturesByItemId(int idItem){
		List<Item> features = new ArrayList<Item>();
		try {
			String sql = "Select * from item it " +
			"JOIN link li ON (li.idItemRel = it.IdItem) " +
			"where it.idTypeItem = "+ItemTypeEnum.FEATURE.getId()+" AND li.idItem = '" + idItem + "'";
			features = createNativeQueryList(sql,Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return features;
	}
	
	public List<Item> getActorsByItem(int idItem){
		List<Item> actors = new ArrayList<Item>();
		try {
			String sql = "Select * from item it " +
			"JOIN link li ON (li.idItemRel = it.IdItem) " +
			"where it.idTypeItem = "+ItemTypeEnum.ACTOR.getId()+" AND li.idItem = '" + idItem + "'";
			actors = createNativeQueryList(sql, Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return actors;
	}
	
	public List<Item> getUseCasesByItemId(int idItem){
		List<Item> useCases = new ArrayList<Item>();
		try {
			String sql = "Select * from item it " +
			"JOIN link li ON (li.idItemRel = it.IdItem) " +
			"where it.idTypeItem = "+ItemTypeEnum.USECASE.getId()+" AND li.idItem = '" + idItem + "'";
			useCases = createNativeQueryList(sql,Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return useCases;
	}
	
	/*
	public List<Item> getClassesByItemId(int idItem){
		List<Item> features = new ArrayList<Item>();
		try {
			features = createNativeQueryList("Select * from item it " +
											"JOIN link li ON (li.idItemRel = it.IdItem) " +
											"where it.idTypeItem = "+ItemTypeEnum.CLASS.getId()+" AND li.idItem = '" + idItem + "'", Item.class);

		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return features;
	}
*/
	public List<Item> getArtifactsByAuthor(int idAuthor){
		List<Item> artifacts = new ArrayList<Item>();
		try {
			artifacts = createNativeQueryList("Select * from item it " +
											"JOIN author a ON (a.idItem = it.IdItem) " +
											"WHERE a.identifier = '" + idAuthor + "'", Item.class);

		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return artifacts;
	}
	
	public List<Item> getItemsByArtifact(int idArtifact){
		List<Item> items = new ArrayList<Item>();
		try {
			items = createNativeQueryList("Select * from item i " +
											"JOIN link l ON (l.idItem = i.idItem) " +
											"JOIN typeItem tI ON (i.idTypeItem = tI.idTypeItem AND tI.subType = 'I') " +
											"WHERE l.idItemRel = '" + idArtifact + "'", Item.class);
			
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	
	public List<Item> getNonArtifactItems(){
		List<Item> items  = new ArrayList<Item>();
		try {
			items = createNativeQueryList("Select * from item i JOIN typeItem tI ON (i.idTypeItem = tI.idTypeItem AND tI.subType = 'I')", Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	
	public List<Item> getItemsByRelatedItems(int idItem){
		List<Item> items  = new ArrayList<Item>();
		try {
			items = createNativeQueryList("Select * from item it " +
											"JOIN link li ON (li.idItemRel = it.IdItem OR li.IdItem = it.IdItem ) " +
											"where li.idItem ='" + idItem + "'", Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	public List<Item> getAllItem(){
		List<Item> items  = new ArrayList<Item>();
		try {
			items =  createQueryList("Select a from Item a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	
	public List<Item> getItemByNameSubType(String name, String subType){
		List<Item> items  = new ArrayList<Item>();
		try {
			items =  createNativeQueryList("Select it.* from item it " +
											" JOIN typeItem tI ON (it.idTypeItem = tI.idTypeItem AND tI.subType = '" + subType + "') " +
											"where it.name ='" + name + "'", Item.class);
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return items;
	}
	
	
}