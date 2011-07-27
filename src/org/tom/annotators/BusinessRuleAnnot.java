
package org.tom.annotators;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.UBusinessRuleManager;
import org.tom.util.FileManipulation;

public class BusinessRuleAnnot extends Annot{

	private UBusinessRuleManager manBRule 		= new UBusinessRuleManager();
	
	private List<Integer> businessRuleGroup;
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		businessRuleGroup 	= this.getEntityGroup("BusinessRule");
	}
	
	

	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS bRuleView 	= aCas.getView("BusinessRuleSpecificationDocument");
		String text 	= bRuleView.getDocumentText();		
		String artifact = this.getArtifactName(aCas);
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text+"END;");
			while (matcher.find()) {	
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					try {
						this.manBRule.businessRulePersist(FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(0))),FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(1))),FileManipulation.clearRtfFormat(matcher.group(businessRuleGroup.get(2))), artifact,null, rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}	
				}
			}
		}
	}
	
}
