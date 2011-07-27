package org.tom.GUI.prefuse;

import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

public class ItemsTraceability {
	
	private int nodeType;
	private String nodeName;
	private int edgeSource;
	private int edgeTarget;
	
	List<Item> items;
	List<Link> links;
	
	ArrayList<ItemsGraphNodes> nodes = new ArrayList<ItemsGraphNodes>();
	ArrayList<ItemsGraphEdges> edges = new ArrayList<ItemsGraphEdges>();
	ItemsXML xml = new ItemsXML();
	ItemsManager im = new ItemsManager();
	LinkManager lm = new LinkManager();
	
	public ItemsTraceability(List<Item> items, List<Link> links){
		this.items = items;
		this.links = links;
		
	}
	
	
	public String getXMLInformation(){
				
		ItemsGraphNodes node;
		
		for (Item item : items) {
			if(item.getIdTypeItem() != 12 && item.getIdTypeItem() != 7 && item.getIdTypeItem() != 8 && item.getIdTypeItem() != 9
					&& item.getIdTypeItem() != 10 && item.getIdTypeItem() != 11){
				node = new ItemsGraphNodes();
				node.setNodeName(item.getName());
				node.setNodeType(item.getIdTypeItem());
				node.setId(item.getIdItem());
				Item item2;
				List<Link> l = lm.getLinksRelByItem(item.getIdItem());
				for (Link link : l) {
					item2 = new Item();
					item2 = im.getItemById(link.getIdItemRel());
//					if(item2.getIdTypeItem() == 7 || item2.getIdTypeItem() == 8 || item2.getIdTypeItem() == 9
//							|| item2.getIdTypeItem() == 10 || item2.getIdTypeItem() == 11 || item2.getIdTypeItem() == 12)
					if(item2.getIdTypeItem() == 12) node.setOriginalArtifact(item2.getName());
				}
				
				l = lm.getLinksByRelatedItem(item.getIdItem());
				for (Link link : l) {
					item2 = new Item();
					 item2 = im.getItemById(link.getIdItem());
//					if(item2.getIdTypeItem() == 7 || item2.getIdTypeItem() == 8 || item2.getIdTypeItem() == 9
//							|| item2.getIdTypeItem() == 10 || item2.getIdTypeItem() == 11 || item2.getIdTypeItem() == 12)
					if(item2.getIdTypeItem() == 12)	node.setOriginalArtifact(item2.getName());
				}
				nodes.add(node);
			}
		}
		int counter = 1;
		for (ItemsGraphNodes graphNodes : nodes) {
			findEdgeInformation(graphNodes.getId(), counter);
			counter++;
		}
		
		String content = xml.createXMLContent(nodes, edges);
		return content;
	}
	
	
	public void findEdgeInformation(int idNode, int position){

		ItemsGraphEdges edge;
		for (Link link : links) {
			if(link.getIdItem() == idNode){
				Item id = im.getItemById(link.getIdItemRel());
				if(id.getIdTypeItem() != 7 && id.getIdTypeItem() != 8 && id.getIdTypeItem() != 9 
					&& id.getIdTypeItem() != 10 && id.getIdTypeItem() != 11 && id.getIdTypeItem() != 12 ){
					edge = new ItemsGraphEdges();
					edge.setEdgeSource(position);
					edge.setEdgeTarget(getNodePosition(link.getIdItemRel()));
					edges.add(edge);
				}				
			}
		}
	}
	

	public int getNodePosition(int idNode){
		int position = 0;
		int counter = 1;
		for (ItemsGraphNodes node : nodes) {
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
	
	
//	public static void main(String[] args) {
//	ConnectionControl control = new ConnectionControl();
//	try {
////		control.disconnect();
//		control.connect(1);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	ItemsTraceability fullTraceability = new ItemsTraceability();
//	
//	
//	FileManipulation fileManipulation = new FileManipulation();
//	fileManipulation.writeFile(new File("/home/mayara/teste.xml"), fullTraceability.getXMLInformation());
//	
//}
}
