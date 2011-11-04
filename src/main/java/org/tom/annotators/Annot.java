package org.tom.annotators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.util.FileManipulation;

public class Annot extends CasAnnotator_ImplBase{
	protected Pattern[] pat;
	protected Pattern[] patArea;
	protected String[] rule;
	protected String[] entityGroupName;
	protected String[] entityGroupStr;
	protected List<List<Integer>> entityGroup = new ArrayList<List<Integer>>();
	
	protected Integer[] fields;
	protected Boolean isTest;
	protected Boolean isTestArea;
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		String[] patternsArea 	= (String[]) aContext.getConfigParameterValue("PatternsArea");
		String[] patterns 		= (String[]) aContext.getConfigParameterValue("Patterns");
		
		entityGroupStr 		= (String[]) aContext.getConfigParameterValue("EntityGroup");
		entityGroupName 	= (String[]) aContext.getConfigParameterValue("EntityGroupName");
		splitGroups();
		
		
		rule 		= (String[]) aContext.getConfigParameterValue("Rule");
		fields 		= (Integer[]) aContext.getConfigParameterValue("Fields");
		isTest 		= (Boolean) aContext.getConfigParameterValue("isTest");
		isTestArea 	= (Boolean) aContext.getConfigParameterValue("isTestArea");
		
		pat = new Pattern[patterns.length];
		this.compilePattern(pat, patterns);
		
		patArea = new Pattern[patternsArea.length];
		this.compilePattern(patArea, patternsArea);
	}
	
	private void splitGroups(){
		String[] groupsString;
		for (String str : entityGroupStr) {
			groupsString = str.split(",");
			List<Integer> newGroups = new ArrayList<Integer>();
			for (String str2 : groupsString) 
				newGroups.add(Integer.parseInt(str2.trim()));
			entityGroup.add(newGroups);
		}
	}
	
	protected List<Integer> getEntityGroup(String name){
		for (int i = 0; i < entityGroupName.length; i++) {
			if(entityGroupName[i].equalsIgnoreCase(name))
				return entityGroup.get(i);
		}	
		List<Integer> s = null;
		System.out.println("EntityGroup not finded.");
		return s;
	}
	
	protected void compilePattern(Pattern[] pat, String[] patStr){
		//pat = new Pattern[patStr.length];
		for (int i = 0; i < patStr.length; i++) 
			pat[i] = Pattern.compile(patStr[i]);
	}
	
	public void process(CAS aCas) throws AnalysisEngineProcessException{}
	
	protected String getArtifactName(CAS aCas){
		CAS pathView = aCas.getView("filePath");
	    return pathView.getDocumentText();
	}

	protected void printFields(Matcher m, Boolean isTest){
		if(fields.length > 0 && isTest.booleanValue())
			for(Integer field:fields){
				if(field.intValue() <= m.groupCount() )
					System.out.println(FileManipulation.clearRtfFormat(m.group(field.intValue()))+"\n\n");
			}
	}

}