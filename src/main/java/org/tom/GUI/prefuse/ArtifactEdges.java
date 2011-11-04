package org.tom.GUI.prefuse;

import java.util.List;

import org.tom.entities.structuredDB.Item;

public class ArtifactEdges {
	
	private Item node;
	private List<Item> relatedItems;
	

	public Item getNode() {
		return node;
	}
	public void setNode(Item node) {
		this.node = node;
	}
	public List<Item> getRelatedItems() {
		return relatedItems;
	}
	public void setRelatedItems(List<Item> relatedItems) {
		this.relatedItems = relatedItems;
	}
}
