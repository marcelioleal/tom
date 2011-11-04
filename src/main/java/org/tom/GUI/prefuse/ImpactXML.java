package org.tom.GUI.prefuse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;
import org.tom.util.FileManipulation;

public class ImpactXML {
	
	List<Item> items = new ArrayList<Item>();
	List<Link> links = new ArrayList<Link>();
	List<Item> itemsNodes = new ArrayList<Item>();
	List<Item> nodes = new ArrayList<Item>();
	
	public ImpactXML(List<Item> items, List<Link> links){
		this.items = items;
		this.links = links;
	}

	public String createXMLContent(String mainNode){
		StringBuffer content = new StringBuffer();
		content.append("<tree>");
		content.append("\n\t<declarations>");
		content.append("\n\t\t<attributeDecl name=\"name\" type=\"String\"/>");
		content.append("\n\t</declarations>");
		content.append("\n<branch>");
		content.append("\n\t<attribute name=\"name\" value=\"" + mainNode + "\"/>");
		
		content.append(middleContent(mainNode));
		
		content.append("\n</branch>");
		content.append("\n</tree>");
		return content.toString();
	}
	
	public String middleContent(String mainNode){
		StringBuffer content = new StringBuffer();
		
		Item mainItem = new Item();
		for (Item item : items) {
			if(item.getIdTypeItem() == 12) {
				itemsNodes.add(item);
				if(item.getName().equals(mainNode)) {
					mainItem = item;
				}
			}
		}
		
		nodes.add(mainItem);
		//generating edges
		CreateArtifactEdges ae = new CreateArtifactEdges(items, links, itemsNodes);
		List<ArtifactEdges> e = ae.getEdges();
//		List<Item> i;
//		for (ArtifactEdges a : e) {
//			i = new ArrayList<Item>();
//			System.out.println("\n\nnó: " + a.getNode().getName());
//			i = a.getRelatedItems();
//			for (Item i2 : i) {
//				System.out.println("item relacionado: " + i2.getName());
//			}
//		}
		
		content.append(branchLeafContent(e, mainItem));
		return content.toString();
	}
	
	public String teste(List<ArtifactEdges> e, Item no){
		StringBuffer content = new StringBuffer();
		List<Item> i;
		for (ArtifactEdges a : e) {
			i = new ArrayList<Item>();
			i = a.getRelatedItems();
			for (Item i2 : i) {
				if(i2.getIdItem() == no.getIdItem())
					if(nodes.contains(a.getNode())){
						content.append("\n<leaf>");
						content.append("\n<attribute name=\"name\" value=\"" + a.getNode().getName() + "\"/>");
						content.append("\n</leaf>");
					}
					else{
						nodes.add(a.getNode());
						content.append("\n<branch>");
						content.append("\n<attribute name=\"name\" value=\"" + a.getNode().getName() + "\"/>");						
						content.append(branchLeafContent(e, a.getNode()));
						content.append("\n</branch>");
					}
			}
		}
		return content.toString();
	}
	
	public String branchLeafContent(List<ArtifactEdges> e, Item mainItem){
		StringBuffer content = new StringBuffer();
		for (ArtifactEdges artifactEdges : e) {
			if(artifactEdges.getNode().equals(mainItem)){
				List<Item> relatedArtifacts = artifactEdges.getRelatedItems();
				for (Item item2 : relatedArtifacts) {
					if(nodes.contains(item2)){
						content.append("\n<leaf>");
						content.append("\n<attribute name=\"name\" value=\"" + item2.getName() + "\"/>");
						content.append("\n</leaf>");
					}
					else{
						nodes.add(item2);
						content.append("\n<branch>");
						content.append("\n<attribute name=\"name\" value=\"" + item2.getName() + "\"/>");						
						content.append(branchLeafContent(e, item2));
						content.append("\n</branch>");
					}
					
				}
			}
			System.out.println("\nitem: " + artifactEdges.getNode().getName());
			content.append(teste(e, mainItem));
		}
		
		return content.toString();
	}
	
	public static void main(String[] args) {

		ItemsManager im = new ItemsManager();
		LinkManager lm = new LinkManager();
		List<Item> items = im.getItems();
		List<Link> links = lm.getLinks();
		
		ImpactXML xml = new ImpactXML(items, links);
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(new File("/home/mayara/tree.xml"), xml.createXMLContent("DocumentodeVisão.doc"));
		
	}
		
}
