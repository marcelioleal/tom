package org.tom.GUI.matrix;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

public class ActorUseCase extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8078070993734767312L;
	private ItemsManager im = new ItemsManager();
	private LinkManager lm = new LinkManager();	
	private List<Item> actors = new ArrayList<Item>();
	private List<Item> useCases = new ArrayList<Item>();
	private List<Link> linkObjects = lm.getLinks();
	//this 2 arraylists are used to store the related items information
	private ArrayList<Integer> relatedIds = new ArrayList<Integer>();
	private ArrayList<String> relatedItemsNames = new ArrayList<String>();
	//it is used to store the data the is going to populate the traceability matrix
	private ArrayList<Data> matrixData = new ArrayList<Data>();
	//matrixIds store the ids that already are in the matrixData to avoid storing repeated items
	private ArrayList<Integer> matrixIds = new ArrayList<Integer>();
	private String[][] lines = null;
	private String[][] lines2 = null;
	private String[] columns = null;
	
	public void matrix(){
		actors = im.getItemsByType(3);
		useCases = im.getItemsByType(4);
		
		Data data;	
		Link link;
		Item item;
		
		//filling the matrixData - data that populate the traceability matrix
		for (Object object : linkObjects) {
			data = new Data();
			link = (Link) object;
			item = getItemById(link.getIdItem());
			if(item != null && (!matrixIds.contains(item.getIdItem()))) {
				matrixIds.add(item.getIdItem());
				data.setItem(item);
	    		for (int i = 0; i < actors.size(); i++) {
					if(actors.get(i).getIdItem() == link.getIdItem())
						relatedIds = getRelatedIdsByItemId(link.getIdItem());
				}    		
	    		data.setRelatedIds(relatedIds);
	    		for (Integer id : relatedIds) {
					relatedItemsNames.add(getNameById(id));
				}
	    		data.setRelatedItemsNames(relatedItemsNames);
	    		matrixData.add(data);
			}
    		
		}
		
	}
	
	public String[] getTableColumns(){
		//filling the column elements of the traceability matrix
		int columnNumber = useCases.size();
    	columns = new String[columnNumber + 1];
    	columns[0] = " ";    	
    	for (int i = 0; i < columnNumber; i++) columns[i+1] =  useCases.get(i).getName();
    	return columns;
	}
	
	public String[][] getTableLines(){
		//filling the line elements of the traceability matrix
    	//the first element is the need and the others are the traceability links
    	lines = new String [actors.size()][columns.length];
    	lines2 = new String [actors.size()][columns.length];
    	for(int i = 0; i < actors.size(); i++){
    		for(int j = 0; j < columns.length; j++){
    			if(j == 0) lines2[i][j] = actors.get(i).getName();    			
    			else if((i < matrixData.size()) && (matrixData.get(i).getRelatedIds().contains(useCases.get(j - 1).getIdItem()))) 
    				lines[i][j] = "x";
    		}
    	}
    	return lines2;
	}
	
	public ArrayList<Integer> getRelatedIdsByItemId(int id){
		ArrayList<Integer> relatedIds = new ArrayList<Integer>();
		for (Object link : linkObjects) {
			if( ((Link) link).getIdItem() == id ) {
				relatedIds.add(((Link) link).getIdItemRel());
			}
		}
		return relatedIds;
	}

	public Item getItemById(int id){
		Item item = null;
		for (Item actor : actors) {
			if(actor.getIdItem() == id){
				item = actor;
				break;
			}
		}
		return item;
	}
	
	public String getNameById(int id){
		String name = "";
		for (Item item : actors) {
			if(item.getIdItem() == id){
				name = item.getName();
				break;
			}
		}
		return name;
	}

	public String[][] getLines() {
		return lines;
	}

	public void setLines(String[][] lines) {
		this.lines = lines;
	}

}
