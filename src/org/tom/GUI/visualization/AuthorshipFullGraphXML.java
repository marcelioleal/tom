package org.tom.GUI.visualization;

import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Collaboration;
import org.tom.entities.structuredDB.Item;

public class AuthorshipFullGraphXML {
	
	List<Item> items;
	List<Collaboration> authors;
	ArrayList<FullTraceabilityGraphEdges> edges;
	int cont = 1;

	public AuthorshipFullGraphXML(List<Item> items, List<Collaboration> authors, ArrayList<FullTraceabilityGraphEdges> edges){
		this.items = items;
		this.authors = authors;
		this.edges = edges;
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
		
		for (Item i : items) {
			content.append("\n<node id=\"" + cont + "\">");
			content.append("\n\t<data key=\"name\">" + i.getName() + "</data>");
			content.append("\n\t<data key=\"itemType\">" + i.getIdTypeItem() + "</data>");
			content.append("\n</node>");
			cont++;
		}
		
		for (Collaboration a : authors) {
			content.append("\n<node id=\"" + cont + "\">");
			content.append("\n\t<data key=\"name\">" + a.getLogin() + "</data>");
			content.append("\n\t<data key=\"itemType\">0</data>");
			content.append("\n</node>");
			cont++;
		}
		
		return content.toString();
	}
	
	public String createEdges(){
		StringBuffer content = new StringBuffer();
		
		for (FullTraceabilityGraphEdges e : edges) {
			content.append("\n<edge source=\"" + e.getSource() + "\" target=\"" + e.getTarget() + "\"></edge>");
		}
		
		return content.toString();
	}
	
}














