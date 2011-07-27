package org.tom.GUI.prefuse;

import java.io.File;
import java.util.ArrayList;

import org.tom.util.FileManipulation;

public class ItemsXML {

	public String createXMLContent(ArrayList<ItemsGraphNodes> nodes, ArrayList<ItemsGraphEdges> edges){
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		content.append("\n<!--  traceability graph  -->");
		content.append("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
		content.append("\n<graph edgedefault=\"undirected\">");
		content.append("\n\n<!-- data schema -->");
		content.append("\n<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>");
		content.append("\n<key id=\"itemType\" for=\"node\" attr.name=\"itemType\" attr.type=\"int\"/>");
		content.append("\n<key id=\"originalArtifact\" for=\"node\" attr.name=\"originalArtifact\" attr.type=\"String\"/>");
		content.append("\n\n<!--");
		content.append("\nitemTypes:");
		content.append("\n1 	Need");
		content.append("\n2 	Feature");
		content.append("\n3 	Actor");
		content.append("\n4 	Use Case");
		content.append("\n5 	ClassItem");
		content.append("\n6 	Test Case");
		content.append("\n7 	Vision");
		content.append("\n8 	Use Case Specification");
		content.append("\n9 	Use Case Diagram");
		content.append("\n10 	Class Diagram");
		content.append("\n11 	Test Case Specification");
		content.append("\n-->");
		
		content.append("\n\n\n<!--Nodes-->");
		content.append(createNodes(nodes));
		
		content.append("\n\n\n<!--Edges-->");
		content.append(createEdges(edges));
		
		content.append("\n\n</graph>");
		content.append("\n</graphml>");
		
		return content.toString();
	}
	
	public String createNodes(ArrayList<ItemsGraphNodes> nodes){
		
		StringBuffer nodesContent = new StringBuffer();
		int cont = 1;
		for (ItemsGraphNodes graphNodes : nodes) {
			nodesContent.append("\n<node id=\"" + cont + "\">");
			nodesContent.append("\n\t<data key=\"name\">" + graphNodes.getNodeName() + "</data>");
			nodesContent.append("\n\t<data key=\"itemType\">" + graphNodes.getNodeType() + "</data>");
			nodesContent.append("\n\t<data key=\"originalArtifact\">" + graphNodes.getOriginalArtifact() + "</data>");
			nodesContent.append("\n</node>");
			cont++;
		}	
		
		return nodesContent.toString();
	}
	
	public String createEdges(ArrayList<ItemsGraphEdges> edges){
		
		StringBuffer edgesContent = new StringBuffer();
		for (ItemsGraphEdges graphEdges : edges) {
			edgesContent.append("\n<edge source=\"" + graphEdges.getEdgeSource() + "\" target=\"" + graphEdges.getEdgeTarget() + "\"></edge>");
		}
		return edgesContent.toString();
	}
	
	public void crateXMLFile(File file, ArrayList<ItemsGraphNodes> nodes, ArrayList<ItemsGraphEdges> edges){
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(file, createXMLContent(nodes, edges));
	}
	
	public static void main(String[] args) {
		ArrayList<ItemsGraphNodes> nodes = new ArrayList<ItemsGraphNodes>();
		ArrayList<ItemsGraphEdges> edges = new ArrayList<ItemsGraphEdges>();
		
		ItemsGraphNodes node1 = new ItemsGraphNodes();
		node1.setNodeName("Ator 1");
		node1.setNodeType(3);
		nodes.add(node1);
		
		ItemsGraphNodes node2 = new ItemsGraphNodes();
		node2.setNodeName("Ator 2");
		node2.setNodeType(3);
		nodes.add(node2);
		
		ItemsGraphNodes node3 = new ItemsGraphNodes();
		node3.setNodeName("Caso de Uso 1");
		node3.setNodeType(4);
		nodes.add(node3);
		
		ItemsGraphNodes node4 = new ItemsGraphNodes();
		node4.setNodeName("Caso de Uso 2");
		node4.setNodeType(4);
		nodes.add(node4);
		
		ItemsGraphEdges edge1 = new ItemsGraphEdges();
		edge1.setEdgeSource(1);
		edge1.setEdgeTarget(3);
		edges.add(edge1);
		
		ItemsGraphEdges edge2 = new ItemsGraphEdges();
		edge2.setEdgeSource(1);
		edge2.setEdgeTarget(4);
		edges.add(edge2);
		
		ItemsGraphEdges edge3 = new ItemsGraphEdges();
		edge3.setEdgeSource(2);
		edge3.setEdgeTarget(3);
		edges.add(edge3);
		
		ItemsXML xml = new ItemsXML();
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(new File("/home/mayara/teste.xml"), xml.createXMLContent(nodes, edges));
		
	}
	
	
	
	
}
