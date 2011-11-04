package org.tom.GUI.prefuse;

import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

public class CreateArtifactEdges {
	
	LinkManager lm = new LinkManager();
	ItemsManager im = new ItemsManager();
	List<Item> itemsNodes = new ArrayList<Item>();
	List<ArtifactEdges> edges = new ArrayList<ArtifactEdges>();
	ArtifactEdges edge;
	Item item;
	List<Item> items;
	List<Item> artifactItems;
	List<Link> links;
	List<Link> relatedLink;
	List<Link> relatedLink2;
	
	public CreateArtifactEdges(List<Item> items,List<Link> links, List<Item> itemsNodes){
		this.links = links;
		this.itemsNodes = itemsNodes;
	}

	public List<ArtifactEdges> getEdges(){
		for (Item node : itemsNodes) {
			edge = new ArtifactEdges();
			edge.setNode(node);
			items = new ArrayList<Item>();
			System.out.println("\n\nno: " + node.getName());
			for (Link link : links) {
				artifactItems = new ArrayList<Item>();
				
				
				//procurando os links do nó artefato
				if(link.getIdItem() == node.getIdItem()){
					relatedLink = new ArrayList<Link>();
					
					
					//pegando os links ligados ao link do nó artefato
					relatedLink = lm.getLinksRelByItem(link.getIdItemRel());
					
					
					//pegando os itens destes links
					for (Link l : relatedLink) {
						item = new Item();
						item = im.getItemById(l.getIdItemRel());
						if(!items.contains(item)){
							items.add(item);
						}
					}
					
					relatedLink = lm.getLinksByRelatedItem(link.getIdItemRel());
					//pegando os itens destes links
					for (Link l : relatedLink) {
						item = new Item();
						item = im.getItemById(l.getIdItemRel());
						if(!items.contains(item)){
							items.add(item);
						}
					}
					
				}
				else if(link.getIdItemRel() == node.getIdItem()){
					relatedLink2 = lm.getLinksRelByItem(link.getIdItem());
					for (Link l : relatedLink2) {
						item = im.getItemById(l.getIdItem());
						if(!items.contains(item)){
							items.add(item);
						}
					}
					
					relatedLink2 = lm.getLinksByRelatedItem(link.getIdItem());
					for (Link l : relatedLink2) {
						item = im.getItemById(l.getIdItem());
						if(!items.contains(item)){
							items.add(item);
						}
					}
				}
//				System.out.println("\n\nitem inicial: "+ node.getName());
				
//				for (Item i : artifactItems) {
//					System.out.println("akiii: "+i.getName());
//				}
								
			}
			artifactItems = getEdgeArtifact(items);
			
//			for (Item i : artifactItems) {
//			System.out.println("akiii: "+i.getName());
//		}
			edge.setRelatedItems(artifactItems);
			edges.add(edge);
		}
		
		return edges;
	}	
	
	public List<Item> getEdgeArtifact(List<Item> items){
		List<Item> artifacts = new ArrayList<Item>();
		List<Link> links = new ArrayList<Link>();
		Item aux = new Item();
		for (Item item : items) {
			System.out.println("item: "+ item.getName() + "  id: " + item.getIdItem() );
			links = lm.getLinksRelByItem(item.getIdItem());
			for (Link link : links) {
				aux = im.getItemById(link.getIdItem());
				
				if(aux.getIdTypeItem() == 12 && (!artifacts.contains(aux))) {
					System.out.println("item linkado: " + aux.getName() + "   idtype: " + aux.getIdTypeItem());
					artifacts.add(aux);
				}
			}
			links = lm.getLinksByRelatedItem(item.getIdItem());
			for (Link link : links) {
				aux = im.getItemById(link.getIdItem()); 
				
				if(aux.getIdTypeItem() == 12 && (!artifacts.contains(aux))) {
					System.out.println("item linkado: " + aux.getName() + "   idtype: " + aux.getIdTypeItem());
					artifacts.add(aux);
				}
			}
		}
		
		return artifacts;
	}
	
}
