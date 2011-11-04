package org.tom.socialNetwork;

import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.manager.structured.ItemsManager;

public class TaskDependenciesMatrix {

	private ItemsManager im = new ItemsManager();
	private List<Item> artifacts;// = im.getArtifactItems();
	int[][] matrix;
	private List<Item> artifactItem;
	private List<Item> relatedItems;
	private List<Item> relatedArtifacts;
	private List<String> links = new ArrayList<String>();
	private List<ArtifactLinks> artifactLinks = new ArrayList<ArtifactLinks>();
	String link;
	ArtifactLinks al;
	
	public void getArtifactLinks(){
		for (Item i : artifacts) {
			artifactItem = im.getItemsByArtifact(i.getIdItem());
			
			for (Item it : artifactItem) {
				relatedItems = im.getItemsByRelatedItems(it.getIdItem());
				
				for (Item a : relatedItems) {
					//relatedArtifacts = im.getArtifactsByItem(a.getIdItem());
					
					for (Item r : relatedArtifacts) {
						link = i.getIdItem() + "," + r.getIdItem();
						if(!links.contains(link) && i.getIdItem() != r.getIdItem()){
							al = new ArtifactLinks();
							al.setSource(i.getIdItem());
							al.setTarget(r.getIdItem());
							artifactLinks.add(al);
							links.add(link);
						}
					}
				}
			}
		}
	}
	
	
	public int[][] generateMatrix(){
		matrix = new int[artifacts.size()][artifacts.size()];
		getArtifactLinks();

		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if(i == j) matrix[i][j] = 0;
				else{
					int idI = artifacts.get(i).getIdItem();
					int idJ = artifacts.get(j).getIdItem();
					for (ArtifactLinks al : artifactLinks) {
						if( (al.getSource() == idI && al.getTarget() == idJ) || (al.getSource() == idJ && al.getTarget() == idI) )
							matrix[i][j] = 1;
					}
				}
			}
		}
		
		return matrix;
	}
	
	public static void main(String[] args) {
		TaskDependenciesMatrix tdm = new TaskDependenciesMatrix();
		int[][] matrix = tdm.generateMatrix();
		
		for (ArtifactLinks a : tdm.artifactLinks) {
			System.out.println("source: " + a.getSource() + "   target: " + a.getTarget());
		}
		
		Matrix m = new Matrix();
		m.printMatrix(matrix);
		
	}
	
}
