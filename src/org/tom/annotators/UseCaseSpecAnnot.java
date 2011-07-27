
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
import org.tom.manager.unstructured.UBusinessRuleManager;
import org.tom.manager.unstructured.UFeatureManager;
import org.tom.manager.unstructured.UUseCaseManager;
import org.tom.util.FileManipulation;

public class UseCaseSpecAnnot extends Annot{

	private UActorManager manActor = new UActorManager();
	private UUseCaseManager manUC = new UUseCaseManager();
	private UFeatureManager manFeature = new UFeatureManager();
	private UBusinessRuleManager manBRule = new UBusinessRuleManager();

	 
	private Pattern[] patFeature;
	private Pattern[] patInclude;
	private Pattern[] patExtend;
	private Pattern[] patDependent;
	private Pattern[] patBusinessRule;
	
	private boolean isTestFeature;
	private boolean isTestInclude;
	private boolean isTestExtend;
	private boolean isTestDependent;
	private boolean isTestBusinessRule;
	
	private List<Integer> useCaseGroup;
	private List<Integer> actorGroup;
	private List<Integer> featureGroup;
	private List<Integer> includeGroup;
	private List<Integer> extendGroup;
	private List<Integer> dependentGroup;
	private List<Integer> businessRuleGroup;
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		String[] patternsFeature 		= (String[]) aContext.getConfigParameterValue("PatternsFeature");
		String[] patternsInclude 		= (String[]) aContext.getConfigParameterValue("PatternsInclude");
		String[] patternsExtend 		= (String[]) aContext.getConfigParameterValue("PatternsExtend");
		String[] patternsDependent 		= (String[]) aContext.getConfigParameterValue("PatternsDependent");
		String[] patternsBusinessRule 	= (String[]) aContext.getConfigParameterValue("PatternsBusinessRule");
		
		isTestFeature 		= (Boolean) aContext.getConfigParameterValue("isTestFeature");
		isTestInclude 		= (Boolean) aContext.getConfigParameterValue("isTestInclude");
		isTestExtend 		= (Boolean) aContext.getConfigParameterValue("isTestExtend");
		isTestDependent 	= (Boolean) aContext.getConfigParameterValue("isTestDependent");
		isTestBusinessRule 	= (Boolean) aContext.getConfigParameterValue("isTestBusinessRule");
		
		patFeature 		= new Pattern[patternsFeature.length];
		patInclude 		= new Pattern[patternsInclude.length];
		patExtend 		= new Pattern[patternsExtend.length];
		patDependent 	= new Pattern[patternsDependent.length];
		patBusinessRule = new Pattern[patternsBusinessRule.length];
		
		this.compilePattern(patFeature, patternsFeature);
		this.compilePattern(patInclude, patternsInclude);
		this.compilePattern(patExtend, patternsExtend);
		this.compilePattern(patDependent, patternsDependent);
		this.compilePattern(patBusinessRule, patternsBusinessRule);
		
		useCaseGroup 	= this.getEntityGroup("UseCase");
		actorGroup 		= this.getEntityGroup("Actors");
		featureGroup 	= this.getEntityGroup("Features");
		includeGroup 	= this.getEntityGroup("Includes");
		extendGroup 	= this.getEntityGroup("Extends");
		dependentGroup 	= this.getEntityGroup("Dependents");
		businessRuleGroup 	= this.getEntityGroup("BusinessRules");
	}

	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS reqView = aCas.getView("UseCaseSpecificationDocument");
		String text = reqView.getDocumentText();
		
		String artifact = this.getArtifactName(aCas);
		
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					
					String mainFlow 		= FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(4)));
					String alternateFlows 	= FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(5)));
					String extensionPoints 	= FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(6)));
					String description 		= FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(2)));
					String actorArea		= matcher.group(useCaseGroup.get(3));
					
					String actors 			= this.extractActors(actorArea, artifact);
					String features 		= this.extractFeatures(description,artifact);
					String incUseCases 		= this.extractIncludes(mainFlow);
					String extUseCases 		= this.extractExtends(mainFlow);
					extUseCases 			+= this.extractIncludes(alternateFlows);
					String depUseCases 		= this.extractDependents(extensionPoints);
					String depBusinessRules	= this.extractBusinessRules(mainFlow+alternateFlows,artifact);
					
					try {
						manUC.useCasePersist(FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(0))),FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(1))),
								description,actors,mainFlow+alternateFlows+extensionPoints, features, incUseCases, extUseCases, depUseCases , depBusinessRules ,artifact,null,rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private String extractActors(String text, String artifact){
		String actors = "";
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTest);
				if(!this.isTest){
					actors 		+= ((!actors.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(0)));
					/*try {
						actors 		+= ((!actors.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(0)));
						//this.manActor.actorPersist(null, FileManipulation.clearRtfFormat(matcher.group(actorGroup.get(0))), artifact, rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}*/			    
				}
			}
		}
		return actors;
	}
	
	
	private String extractFeatures(String text, String artifact){
		String features = "";
		for (int i = 0; i < patFeature.length; i++) {
			Matcher matcher = patFeature[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestFeature);
				if(!this.isTestFeature){
					features 		+= ((!features.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(0)));
					/*try {
						this.manFeature.featurePersist(FileManipulation.clearRtfFormat(matcher.group(featureGroup.get(0))),null, artifact, rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}	*/		    
				}
			}
		}
		return features;
	}
	
	
	private String extractIncludes(String text){
		String includes = "";
		for (int i = 0; i < patInclude.length; i++) {
			Matcher matcher = patInclude[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestInclude);
				if(!this.isTestInclude)
					includes	+= ((!includes.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(includeGroup.get(0)));
			}
		}
		return includes;
	}
	
	private String extractExtends(String text){
		String extend = "";
		for (int i = 0; i < patExtend.length; i++) {
			Matcher matcher = patExtend[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestExtend);
				if(!this.isTestExtend){
					extend 		+= ((!extend.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(extendGroup.get(0)));
				}
			}
		}
		return extend;
	}
	
	private String extractDependents(String text){
		String dependents = "";
		for (int i = 0; i < patDependent.length; i++) {
			Matcher matcher = patDependent[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestDependent);
				if(!this.isTestDependent){
					dependents 		+= ((!dependents.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(dependentGroup.get(0)));			    
				}
			}
		}
		return dependents;
	}
	
	private String extractBusinessRules(String text, String artifact){
		String businessRules = "";
		for (int i = 0; i < patBusinessRule.length; i++) {
			Matcher matcher = patBusinessRule[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTestBusinessRule);
				if(!this.isTestBusinessRule){
					businessRules 		+= ((!businessRules.isEmpty())?"|":"")+FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(0)));
					/*try {
						this.manBRule.businessRulePersist(FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(0))),FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(1))),null, artifact,null, rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}*/			    
				}
			}
		}
		return businessRules;
	}
	
}
