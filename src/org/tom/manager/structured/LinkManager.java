package org.tom.manager.structured;

import java.util.ArrayList;
import java.util.List;

import org.tom.DAO.JPABaseDAO;
import org.tom.DAO.JPAUtil;
import org.tom.DAO.ModeloException;
import org.tom.entities.structuredDB.Link;

public class LinkManager extends JPABaseDAO<Link>{

	
	public void linkPersist(int idItem, int idItemRel, int idTypeLink,boolean automatic, String obs) throws ModeloException{
		Link link = new Link(idTypeLink, idItem, idItemRel, automatic, obs);
		persist(link);
		JPAUtil.commitTransaction();
	}	
	
	public List<Link> getLinks(){
		List<Link> links  = new ArrayList<Link>();
		try {
			links = createQueryList("Select a from Link a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return links;
	}
	
	public Link getLinkByItemAndItemRelAndType(int itemId, int itemRelatedId, int type){
		Link link = new Link();
		try {
			link = createQueryUnique("Select a from Link a where a.idItem=:id AND a.idItemRel=:itemRelatedId AND idTypeLink=:type", new Object[]{"id",itemId}, new Object[]{"itemRelatedId",itemRelatedId}, new Object[]{"type",type});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}
	
	
	public List<Link> getLinksRelByItem(int itemId){
		List<Link> link = new ArrayList<Link>();
		try {
			link = createQueryList("Select a from Link a where a.idItem=:id", new Object[]{"id",itemId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}
	
	public List<Link> getAllLinksByItem(int itemId){
		List<Link> link = new ArrayList<Link>();
		try {
			link = createQueryList("Select a from Link a where a.idItemRel=:id OR a.idItem=:id", new Object[]{"id",itemId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}
	
	public List<Link> getLinksByRelatedItem(int relatedItemId){
		List<Link> link = new ArrayList<Link>();
		try {
			link = createQueryList("Select a from Link a where a.idItemRel=:id", new Object[]{"id",relatedItemId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}
	
	public List<Link> getAllLink(){
		List<Link> links  = new ArrayList<Link>();
		try {
			links =  createQueryList("Select a from Link a");
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return links;
	}
	
	public List<Link> getLinksByItemAndType(int itemId, int typeId){
		List<Link> link = new ArrayList<Link>();
		try {
			link = createQueryList("Select a from Link a where a.idItem=:itemId AND a.idTypeLink=:typeId", new Object[]{"itemId",itemId},new Object[]{"typeId",typeId});
		} catch (ModeloException e) {
			e.printStackTrace();
			return null;
		}
		return link;
	}
}
