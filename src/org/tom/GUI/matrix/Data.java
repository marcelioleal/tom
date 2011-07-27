package org.tom.GUI.matrix;

import java.util.ArrayList;

import org.tom.entities.structuredDB.Item;

public class Data {

//	private int requirementId;
//	private String requirementName;
	private Item item;
	private ArrayList<Integer> relatedIds;
	private ArrayList<String> relatedItemsNames;
	
//	public int getRequirementId() {
//		return requirementId;
//	} 
//	
//	public void setRequirementId(int requirementId) {
//		this.requirementId = requirementId;
//	}
//	
//	public String getRequirementName() {
//		return requirementName;
//	}
//	
//	public void setRequirementName(String requirementName) {
//		this.requirementName = requirementName;
//	}
	
	public ArrayList<Integer> getRelatedIds() {
		return relatedIds;
	}
	
	public void setRelatedIds(ArrayList<Integer> relatedIds) {
		this.relatedIds = relatedIds;
	}
	
	public ArrayList<String> getRelatedItemsNames() {
		return relatedItemsNames;
	}
	
	public void setRelatedItemsNames(ArrayList<String> relatedItemsNames) {
		this.relatedItemsNames = relatedItemsNames;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
