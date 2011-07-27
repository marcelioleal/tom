
package org.tom.annotators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.UActorManager;
import org.tom.manager.unstructured.UFeatureManager;
import org.tom.manager.unstructured.UNeedManager;
import org.tom.util.FileManipulation;

public class VisionAnnot extends Annot{

	private UNeedManager manNeed 		= new UNeedManager();
	private UActorManager manActor 		= new UActorManager();
	private UFeatureManager manFeature 	= new UFeatureManager();
	
	private Boolean isTestI;
	private Boolean isTestII;
	private Pattern[] patI;
	private Pattern[] patII;
	
	private List<Integer> areaGroup;
	private List<Integer> actorGroup;
	private List<Integer> needGroup;
	private List<Integer> featureGroup;

	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		String[] patternsI 	= (String[]) aContext.getConfigParameterValue("PatternsI");
		String[] patternsII = (String[]) aContext.getConfigParameterValue("PatternsII");
		
		this.isTestI 	= (Boolean) aContext.getConfigParameterValue("isTestI");
		this.isTestII 	= (Boolean) aContext.getConfigParameterValue("isTestII");
		
		patI = new Pattern[patternsI.length];
		this.compilePattern(patI, patternsI);
		patII = new Pattern[patternsII.length];
		this.compilePattern(patII, patternsII);
		
		areaGroup 	= this.getEntityGroup("Area");
		actorGroup 	= this.getEntityGroup("Actor");
		needGroup 	= this.getEntityGroup("Need");
		featureGroup 	= this.getEntityGroup("Feature");
	}
	
	

	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS reqView 	= aCas.getView("VisionDocument");
		String text 	= reqView.getDocumentText();		
		String artifact = this.getArtifactName(aCas);
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					this.createActors(matcher.group(areaGroup.get(0)),artifact);
					this.createNeeds(matcher.group(areaGroup.get(1)),artifact);
				}
			}
		}
	}

	private void createActors(String text, String artifact){
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTest);
				if(!this.isTest){
					try {
						this.manActor.actorPersist(FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(0))), FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(1))), 
								artifact, null, rule[i], FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(2))), FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(3))));
					} catch (ModeloException e) {
						e.printStackTrace();
					}			    
				}
			}
		}
	}
	
	private void createNeeds(String text, String artifact){
		for (int i = 0; i < patI.length; i++) {
			Matcher matcher = patI[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestI);
				if(!this.isTestI){
					try {
						String features = this.createFeatures(matcher.group(needGroup.get(4)), artifact,FileManipulation.clearRtfFormat(matcher.group(needGroup.get(0))));
						
						this.manNeed.needPersist(FileManipulation.clearRtfFormat(matcher.group(needGroup.get(0))),FileManipulation.clearRtfFormat(matcher.group(needGroup.get(1))),
								FileManipulation.clearRtfFormat(matcher.group(needGroup.get(2))),FileManipulation.clearRtfFormat(matcher.group(needGroup.get(3))),artifact,null,rule[i],features);
					} catch (ModeloException e) {
						e.printStackTrace();
					}			    
				}
			}
		}
	}
	
	private String createFeatures(String text, String artifact,String needId){
		String features= "";
		for (int i = 0; i < patII.length; i++) {
			Matcher matcher = patII[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestII);
				if(!this.isTestII){
					String featId = FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(0)));
					features += ((!features.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(1)));
					String actors = FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(3))).replaceAll("/", "|"); 
					try {
						this.manFeature.featurePersist(featId,FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(1))),FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(2))),artifact,null,rule[i],actors);
					} catch (ModeloException e) {
						e.printStackTrace();
					}			    
				}
			}
		}
		return features;
	}
	
}
