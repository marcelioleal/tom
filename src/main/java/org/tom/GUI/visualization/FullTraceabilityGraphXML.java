package org.tom.GUI.visualization;

import java.util.ArrayList;

public class FullTraceabilityGraphXML {

	public String createXMLContent(ArrayList<FullTraceabilityGraphNodes> nodes, ArrayList<FullTraceabilityGraphEdges> edges){
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		content.append("\n<!--  traceability graph  -->");
		content.append("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
		content.append("\n<graph edgedefault=\"undirected\">");
		content.append("\n\n<!-- data schema -->");
		content.append("\n<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>");
		content.append("\n<key id=\"itemType\" for=\"node\" attr.name=\"itemType\" attr.type=\"int\"/>");
		content.append("\n<key id=\"originalArtifact\" for=\"node\" attr.name=\"originalArtifact\" attr.type=\"String\"/>");
		content.append("\n\n");
		
		content.append(createNodes(nodes));
		
		content.append("\n\n\n<!--Edges-->");
		content.append(createEdges(edges));
		
		content.append("\n\n</graph>");
		content.append("\n</graphml>");
		
		return content.toString();
	}
	
	public String createNodes(ArrayList<FullTraceabilityGraphNodes> nodes){
		
		StringBuffer nodesContent = new StringBuffer();
		int cont = 1;
		for (FullTraceabilityGraphNodes graphNodes : nodes) {
			nodesContent.append("\n<node id=\"" + graphNodes.getId() + "\">");
			nodesContent.append("\n\t<data key=\"name\">" + graphNodes.getNodeName() + "</data>");
			nodesContent.append("\n\t<data key=\"itemType\">" + graphNodes.getNodeType() + "</data>");
			nodesContent.append("\n\t<data key=\"originalArtifact\">" + graphNodes.getOriginalArtifact() + "</data>");
			nodesContent.append("\n</node>");
			cont++;
		}	
		
		return nodesContent.toString();
	}
	
	public String createEdges(ArrayList<FullTraceabilityGraphEdges> edges){
		
		StringBuffer edgesContent = new StringBuffer();
		for (FullTraceabilityGraphEdges graphEdges : edges) {
			edgesContent.append("\n<edge source=\"" + graphEdges.getSource() + "\" target=\"" + graphEdges.getTarget() + "\"></edge>");
		}
		return edgesContent.toString();
	}
	
}
