package org.tom.prefuse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.LinkManager;
import org.tom.util.FileManipulation;

public class FullTraceability {
	
	private int nodeType;
	private String nodeName;
	private int edgeSource;
	private int edgeTarget;
	
	LinkManager lm = new LinkManager();
	ItemsManager im = new ItemsManager();
	List<Item> items = im.getItems();
	List<Link> links = lm.getLinks();
	
	ArrayList<GraphNodes> nodes = new ArrayList<GraphNodes>();
	ArrayList<GraphEdges> edges = new ArrayList<GraphEdges>();
	XML xml = new XML();
	
	
	public static void main(String[] args) {

		FullTraceability fullTraceability = new FullTraceability();
		
		
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(new File("/home/mayara/teste.xml"), fullTraceability.getXMLInformation());
		
	}
	
	public String getXMLInformation(){
				
		GraphNodes node;
		
		
		for (Item item : items) {
			node = new GraphNodes();
			node.setNodeName(item.getName());
			node.setNodeType(item.getIdTypeItem());
			node.setId(item.getIdItem());
			nodes.add(node);
		}
		int counter = 1;
		for (GraphNodes graphNodes : nodes) {
			findEdgeInformation(graphNodes.getId(), counter);
			counter++;
		}
		
		String content = xml.createXMLContent(nodes, edges);
		return content;
	}
	
	
	public void findEdgeInformation(int idNode, int position){
		GraphEdges edge;
		for (Link link : links) {
			if(link.getIdItem() == idNode){
				edge = new GraphEdges();
				edge.setEdgeSource(position);
				edge.setEdgeTarget(getNodePosition(link.getIdItemRel()));
				edges.add(edge);
			}
		}
	}
	

	public int getNodePosition(int idNode){
		int position = 0;
		int counter = 1;
		for (GraphNodes node : nodes) {
			if(idNode == node.getId()){
				position = counter;
				break;
			}
			counter++;
		}
		return position;
	}
	
	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getEdgeSource() {
		return edgeSource;
	}

	public void setEdgeSource(int edgeSource) {
		this.edgeSource = edgeSource;
	}

	public int getEdgeTarget() {
		return edgeTarget;
	}

	public void setEdgeTarget(int edgeTarget) {
		this.edgeTarget = edgeTarget;
	}
}
