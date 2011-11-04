package org.tom.GUI.visualization;

public class FullTraceabilityGraphNodes {

	private String nodeName;
	private int nodeType;
	private int id;
	private String originalArtifact;
	
	public String getOriginalArtifact() {
		return originalArtifact;
	}
	public void setOriginalArtifact(String originalArtifact) {
		this.originalArtifact = originalArtifact;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	
}
