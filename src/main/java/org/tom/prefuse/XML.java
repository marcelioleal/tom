package org.tom.prefuse;

import java.io.File;
import java.util.ArrayList;

import org.tom.util.FileManipulation;

public class XML {

	public String createXMLContent(ArrayList<GraphNodes> nodes, ArrayList<GraphEdges> edges){
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		content.append("\n<!--  traceability graph  -->");
		content.append("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
		content.append("\n<graph edgedefault=\"undirected\">");
		content.append("\n\n<!-- data schema -->");
		content.append("\n<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>");
		content.append("\n<key id=\"itemType\" for=\"node\" attr.name=\"itemType\" attr.type=\"int\"/>");
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
	
	public String createNodes(ArrayList<GraphNodes> nodes){
		
		StringBuffer nodesContent = new StringBuffer();
		int cont = 1;
		for (GraphNodes graphNodes : nodes) {
			nodesContent.append("\n<node id=\"" + cont + "\">");
			nodesContent.append("\n\t<data key=\"name\">" + graphNodes.getNodeName() + "</data>");
			nodesContent.append("\n\t<data key=\"itemType\">" + graphNodes.getNodeType() + "</data>");
			nodesContent.append("\n</node>");
			cont++;
		}	
		
		return nodesContent.toString();
	}
	
	public String createEdges(ArrayList<GraphEdges> edges){
		
		StringBuffer edgesContent = new StringBuffer();
		for (GraphEdges graphEdges : edges) {
			edgesContent.append("\n<edge source=\"" + graphEdges.getEdgeSource() + "\" target=\"" + graphEdges.getEdgeTarget() + "\"></edge>");
		}
		return edgesContent.toString();
	}
	
	public void crateXMLFile(File file, ArrayList<GraphNodes> nodes, ArrayList<GraphEdges> edges){
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(file, createXMLContent(nodes, edges));
	}
	
	public static void main(String[] args) {
		ArrayList<GraphNodes> nodes = new ArrayList<GraphNodes>();
		ArrayList<GraphEdges> edges = new ArrayList<GraphEdges>();
		
		GraphNodes node1 = new GraphNodes();
		node1.setNodeName("Ator 1");
		node1.setNodeType(3);
		nodes.add(node1);
		
		GraphNodes node2 = new GraphNodes();
		node2.setNodeName("Ator 2");
		node2.setNodeType(3);
		nodes.add(node2);
		
		GraphNodes node3 = new GraphNodes();
		node3.setNodeName("Caso de Uso 1");
		node3.setNodeType(4);
		nodes.add(node3);
		
		GraphNodes node4 = new GraphNodes();
		node4.setNodeName("Caso de Uso 2");
		node4.setNodeType(4);
		nodes.add(node4);
		
		GraphEdges edge1 = new GraphEdges();
		edge1.setEdgeSource(1);
		edge1.setEdgeTarget(3);
		edges.add(edge1);
		
		GraphEdges edge2 = new GraphEdges();
		edge2.setEdgeSource(1);
		edge2.setEdgeTarget(4);
		edges.add(edge2);
		
		GraphEdges edge3 = new GraphEdges();
		edge3.setEdgeSource(2);
		edge3.setEdgeTarget(3);
		edges.add(edge3);
		
		XML xml = new XML();
		FileManipulation fileManipulation = new FileManipulation();
		fileManipulation.writeFile(new File("/home/mayara/teste.xml"), xml.createXMLContent(nodes, edges));
		
	}
	
	
	
	
}
