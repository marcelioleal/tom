package org.tom.GUI.visualization.xml;

import java.util.ArrayList;
import java.util.List;

import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.ItemTypeEnum;
import org.tom.manager.BaselineManager;
import org.tom.manager.structured.CollaborationManager;
import org.tom.manager.structured.ItemsManager;

/*
 * Generate the Prefuse XML by one need
 */
public class EvolutionTreeXML {
	
	List<Item> features = new ArrayList<Item>();
	List<Item> useCases = new ArrayList<Item>();
	List<Item> testCases= new ArrayList<Item>();
	List<Item> classes 	= new ArrayList<Item>();
	
	BaselineManager bMan	= new BaselineManager();
	ItemsManager im 		= new ItemsManager();
	CollaborationManager am = new CollaborationManager();
	
	Item mainNode;
	
	public EvolutionTreeXML(){
	}
	
	public String createXMLContent(Item mainNode){
		this.mainNode = mainNode;
		String content = "";
		//content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		content += "<tree>";
		content += this.header();
		content += "\n<branch>";
		content += this.node(mainNode,null,ItemTypeEnum.NEED);
		content += featureContent(mainNode);
		content += "\n</branch>";
		content += "\n</tree>";
		return content;
	}
	
	private String header(){
		String content = "";
		content += "\n\t<declarations>";
		content += "\n\t\t<attributeDecl name=\"id\" type=\"String\"/>";
		content += "\n\t\t<attributeDecl name=\"name\" type=\"String\"/>";
		content += "\n\t\t<attributeDecl name=\"img\" type=\"String\"/>";
		content += "\n\t\t<attributeDecl name=\"artifact\" type=\"String\"/>";
		content += "\n\t</declarations>";
		return content;
	}
	
	public String featureContent(Item item){
		features = (ArrayList<Item>) im.getItemsRelatedByItemBaseIdAndTypeRelAndBaseline(item.getIdItem(), ItemTypeEnum.FEATURE.getId(), bMan.getActiveBaseline().getIdBaseline());
		String content = "";
		for (Item i : features) {
			content += "\n<branch>";
			content += this.node(i, "images/feature.png",ItemTypeEnum.FEATURE);			
			content += this.artifactContent(i);
			content += this.useCaseContent(i);
			content += "\n</branch>";
		}
		return content;
	}

	private String useCaseContent(Item item){
		String content = "";
		useCases = im.getItemsRelatedByItemBaseIdAndTypeRelAndBaseline(item.getIdItem(), ItemTypeEnum.USECASE.getId(), bMan.getActiveBaseline().getIdBaseline());
		for (Item uc : useCases) {
			content += "\n<branch>";
			content += this.node(uc, "images/usecase.png",ItemTypeEnum.USECASE);
			content += this.testCaseContent(uc);
			content += this.codeContent(uc);
			content += "\n</branch>";
		}
		return content;
	}
	
	private String testCaseContent(Item item){
		String content = "";
		testCases = im.getItemsRelatedByItemBaseIdAndTypeRelAndBaseline(item.getIdItem(), ItemTypeEnum.TESTCASE.getId(), bMan.getActiveBaseline().getIdBaseline());
		for (Item tc : testCases) {
			content += "\n<leaf>";
			content += this.node(tc, "images/testCase.png",ItemTypeEnum.CODE);
			content += this.codeContent(tc);
			content += "\n</leaf>";
		}
		return content;
	}
	
	
	private String codeContent(Item item){
		String content = "";
		classes = im.getItemsRelatedByItemBaseIdAndTypeRelAndBaseline(item.getIdItem(), ItemTypeEnum.CODE.getId(), bMan.getActiveBaseline().getIdBaseline());
		for (Item c : classes) {
			content += "\n<leaf>";
			content += this.node(c, "images/class.png",ItemTypeEnum.CODE);			
			content += "\n</leaf>";
		}
		return content;
	}
	
	
	
	private String node(Item item,String imagePath, ItemTypeEnum type){
		String content = "";
		content += "\n\t<attribute name=\"id\" value=\"" + item.getIdItem() + "\"/>";
		content += "\n\t<attribute name=\"name\" value=\"" + item.getId()+ "\"/>";
		if(imagePath != null)
			content += "\n\t<attribute name=\"img\" value=\""+imagePath+"\"/>";
		return content;
	}
	
	private String artifactContent(Item item){
		return "\n\t<attribute name=\"artifact\" value=\"" + item.getArtifactName()+ "\"/>";
	}
	

		
}
