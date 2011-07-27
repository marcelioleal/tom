package org.tom.GUI.visualization;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.BaselineManager;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

public class FullTraceabilityGraph {

	String content;
	ItemsManager im = new ItemsManager();
	BaselineManager bm = new BaselineManager();
	LinkManager lm = new LinkManager();
	
	ArrayList<FullTraceabilityGraphNodes> nodes = new ArrayList<FullTraceabilityGraphNodes>();
	ArrayList<FullTraceabilityGraphEdges> edges = new ArrayList<FullTraceabilityGraphEdges>();
	
	List<Item> items;
	
	FullTraceabilityGraphXML xml = new FullTraceabilityGraphXML();
	ArrayList<String> edgesList = new ArrayList<String>();
	
	public String getXMLInformation(){
		items = im.getItems(bm.getActiveBaseline().getIdBaseline());
		getNodes();
		getEdges();
		content = xml.createXMLContent(nodes, edges);
		return content;
	}
	
	public void getNodes(){
		FullTraceabilityGraphNodes node;
		for (Item i : items) {
					node = new FullTraceabilityGraphNodes();
					node.setId(i.getIdItem());
					//node.setNodeName("sssss");
					node.setNodeName(this.utf8Converter(i.getId()));
					node.setNodeType(i.getIdTypeItem());
					node.setOriginalArtifact(i.getArtifactName());
					nodes.add(node);
		}
	}
	
	public String utf8Converter(String str){
	    try{
			byte[] utf8 = str.getBytes("UTF-8");
	
		    // Convert from UTF-8 to Unicode
		    return new String(utf8, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	    	e.printStackTrace();
	    	return "";
	    }
	}
	
	public void getEdges(){
		List<Link> links;
		FullTraceabilityGraphEdges edge;
		
		for (FullTraceabilityGraphNodes n : nodes) {
			links = lm.getLinksRelByItem(n.getId());			
			for (Link l : links) {
				Item relatedItem = im.getItemById(l.getIdItemRel());
				Item item = im.getItemById(l.getIdItem());
					if(!edgesList.contains(n.getId()+","+l.getIdItemRel()) && !edgesList.contains(l.getIdItemRel()+","+n.getId())) {
						edge = new FullTraceabilityGraphEdges();
						edge.setSource(n.getId());
						edge.setTarget(l.getIdItemRel());					
						edges.add(edge);
						edgesList.add(n.getId()+","+l.getIdItemRel());
					}
			}
			links = lm.getLinksByRelatedItem(n.getId());
			for (Link l : links) {
				Item item = im.getItemById(l.getIdItem());
				Item relatedItem = im.getItemById(l.getIdItemRel());
					if(!edgesList.contains(n.getId()+","+l.getIdItem()) && !edgesList.contains(l.getIdItem()+","+n.getId())) {
						edge = new FullTraceabilityGraphEdges();
						edge.setSource(n.getId());
						edge.setTarget(l.getIdItem());
						edges.add(edge);
						edgesList.add(n.getId()+","+l.getIdItem());
					} //end of second if
			
			}
		}//end of outer for
	}
	
}