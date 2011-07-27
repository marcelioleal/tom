
package org.tom.annotators;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.UCodeManager;
import org.tom.util.FileManipulation;

public class CodeAnnot extends Annot{

	private UCodeManager manCode 		= new UCodeManager();
	
	private List<Integer> useCaseGroup;
	private List<Integer> businessRuleGroup;
	private List<Integer> testCaseGroup;
	
	private Pattern[] patTestCase;
	
	protected Boolean isTestTestCase;
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		String[] patternsTestCase 		= (String[]) aContext.getConfigParameterValue("PatternsTestCase");
		isTestTestCase 		= (Boolean) aContext.getConfigParameterValue("isTestTestCase");
		patTestCase = new Pattern[patternsTestCase.length];
		this.compilePattern(patTestCase, patternsTestCase);
		
		businessRuleGroup 	= this.getEntityGroup("BusinessRule");
		useCaseGroup 	= this.getEntityGroup("UseCase");
		testCaseGroup 	= this.getEntityGroup("TestCase");
	}
	
	

	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS codeView 	= aCas.getView("Code");
		String text 	= codeView.getDocumentText();		
		String artifact = this.getArtifactName(aCas);
		try{
			String useCases 		= extractUseCases(text);
			String businessRules 	= extractBusinessRules(text);
			String testCases 		= extractTestCases(text);
			if((!this.isTestArea) && (!this.isTest) && (!this.isTestTestCase))
				this.manCode.codePersist(artifact, artifact, null,useCases ,businessRules , testCases, artifact, null, rule[0]);
		}catch(ModeloException e){
			e.printStackTrace();
		}
	}
	
	
	public String extractUseCases(String code){
		String useCases = "";
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(code);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea)
					useCases += ((useCases.isEmpty())?"":"|")+FileManipulation.clearRtfFormat(matcher.group(useCaseGroup.get(0)));
			}
		}
		return useCases;
	}
	
	public String extractTestCases(String code){
		String testCases = "";
		for (int i = 0; i < patTestCase.length; i++) {
			Matcher matcher = patTestCase[i].matcher(code);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestTestCase);
				if(!this.isTestTestCase)
					testCases += ((testCases.isEmpty())?"":"|")+FileManipulation.clearRtfFormat(matcher.group(testCaseGroup.get(0)));
			}
		}
		return testCases;
	}
	
	public String extractBusinessRules(String code){
		String businessRules = "";
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(code);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTest);
				if(!this.isTest)
					businessRules += ((businessRules.isEmpty())?"":"|")+FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(0)));
			}
		}
		return businessRules;
	}
	
}
