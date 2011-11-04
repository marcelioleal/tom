package org.tom.GUI.visualization;

import java.util.List;

import org.tom.entities.structuredDB.Collaboration;
import org.tom.entities.structuredDB.Item;

public class AuthorshipByAuthorGraphXML {

	private List<Item> items;
	private Collaboration author;
	int cont = 2;
	
	public AuthorshipByAuthorGraphXML(Collaboration author, List<Item> items){
		this.items = items;
		this.author = author;
	}
	
	public String createXMLContent(){
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		content.append("\n<!--  traceability graph  -->");
		content.append("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
		content.append("\n<graph edgedefault=\"undirected\">");
		content.append("\n\n<!-- data schema -->");
		content.append("\n<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>");
		content.append("\n<key id=\"itemType\" for=\"node\" attr.name=\"itemType\" attr.type=\"string\"/>");
		content.append("\n\n");
		
		content.append(createNodes());
		
		content.append("\n\n\n<!--Edges-->");
		content.append(createEdges());
		
		content.append("\n\n</graph>");
		content.append("\n</graphml>");
		
		return content.toString();
	}
	
	public String createNodes(){
		StringBuffer content = new StringBuffer();
		content.append("\n<node id=\"1\">");
		content.append("\n\t<data key=\"name\">" + author.getLogin() + "</data>");
		content.append("\n\t<data key=\"itemType\">A</data>");
		content.append("\n</node>");
		
		
		for (Item i : items) {
			content.append("\n<node id=\"" + cont + "\">");
			content.append("\n\t<data key=\"name\">" + i.getName() + "</data>");
			content.append("\n\t<data key=\"itemType\">I</data>");
			content.append("\n</node>");
			cont++;
		}
		
		return content.toString();
	}
	
	public String createEdges(){
		StringBuffer content = new StringBuffer();
		
		for (int i = 0; i < items.size(); i++) {
			content.append("\n<edge source=\"1\" target=\"" + (i+2) + "\"></edge>");
		}
		
		return content.toString();
	}
	
}
