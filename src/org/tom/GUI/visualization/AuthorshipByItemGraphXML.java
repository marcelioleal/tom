package org.tom.GUI.visualization;

import java.util.List;

import org.tom.entities.structuredDB.Collaboration;
import org.tom.entities.structuredDB.Item;

public class AuthorshipByItemGraphXML {

	private Item item;
	private List<Collaboration> authors;
	int cont = 2;
	
	public AuthorshipByItemGraphXML(Item item, List<Collaboration> authors){
		this.item = item;
		this.authors = authors;
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
		content.append("\n\t<data key=\"name\">" + item.getName() + "</data>");
		content.append("\n\t<data key=\"itemType\">I</data>");
		content.append("\n</node>");
		
		
		for (Collaboration a : authors) {
			content.append("\n<node id=\"" + cont + "\">");
			content.append("\n\t<data key=\"name\">" + a.getLogin() + "</data>");
			content.append("\n\t<data key=\"itemType\">A</data>");
			content.append("\n</node>");
			cont++;
		}
		
		return content.toString();
	}
	
	public String createEdges(){
		StringBuffer content = new StringBuffer();
		
		for (int i = 0; i < authors.size(); i++) {
			content.append("\n<edge source=\"1\" target=\"" + (i+2) + "\"></edge>");
		}
		
		return content.toString();
	}
	
}



















