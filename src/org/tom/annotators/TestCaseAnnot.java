
package org.tom.annotators;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.UTestCaseManager;
import org.tom.util.FileManipulation;

public class TestCaseAnnot extends Annot{

	private UTestCaseManager manTCase 		= new UTestCaseManager();
	
	private List<Integer> areaGroup;
	private List<Integer> testCaseGroup;
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		testCaseGroup 	= this.getEntityGroup("TestCase");
		areaGroup 	= this.getEntityGroup("Area");
	}
	
	

	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS bRuleView 	= aCas.getView("TestCaseSpecificationDocument");
		String text 	= bRuleView.getDocumentText();		
		String artifact = this.getArtifactName(aCas);
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text);
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					createTestCase(matcher.group(areaGroup.get(0))+"END;",artifact);	
				}
			}
		}
	}
	
	private void createTestCase(String text, String artifact){
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTest);
				if(!this.isTest){
					try {
						this.manTCase.testCasePersist(FileManipulation.clearRtfFormat(matcher.group(testCaseGroup.get(0))),FileManipulation.clearRtfFormat(matcher.group(testCaseGroup.get(1))),FileManipulation.clearRtfFormat(matcher.group(testCaseGroup.get(2))),FileManipulation.clearRtfFormat(matcher.group(testCaseGroup.get(3))), artifact,null, rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}			    
				}
			}
		}
	}
	
	
}
