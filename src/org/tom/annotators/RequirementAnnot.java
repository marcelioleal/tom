
package org.tom.annotators;

import java.util.regex.Matcher;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.URequerimentManager;
import org.tom.manager.unstructured.UUseCaseManager;
import org.tom.util.FileManipulation;
import org.apache.uima.util.Level;

public class RequirementAnnot extends Annot{

	private URequerimentManager man = new URequerimentManager();
	private UUseCaseManager manUC = new UUseCaseManager();


	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS reqView = aCas.getView("RequirementSpecificationDocument");
		String text = reqView.getDocumentText();
		Type annotType = reqView.getAnnotationType();
		int end = 0;
		
		String artifact = this.getArtifactName(aCas);
		
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text);
			while (matcher.find()) {	
				int begin = matcher.start();
				end = matcher.end();
				AnnotationFS annot = reqView.createAnnotation(annotType, begin, end);
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					String UCs = createUCs(matcher.group(4),artifact,FileManipulation.clearRtfFormat(matcher.group(1)));
					try {
						man.requerimentPersist(FileManipulation.clearRtfFormat(matcher.group(2)), FileManipulation.clearRtfFormat(matcher.group(1)), artifact, "", rule[i],FileManipulation.clearRtfFormat(matcher.group(3)), UCs);
					} catch (ModeloException e) {
						e.printStackTrace();
					}
					reqView.addFsToIndexes(annot);
					getContext().getLogger().log(Level.FINEST, "Found: " + annot);
				}
			}
		}
	}

	private String createUCs(String text, String artifact,String reqId){
		String UCs= "";
		
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTest);
				if(!this.isTest){
					UCs += "|"+reqId+"_"+FileManipulation.clearRtfFormat(matcher.group(5));
					try {
						manUC.useCasePersist(reqId+"_"+FileManipulation.clearRtfFormat(matcher.group(5)),FileManipulation.clearRtfFormat(matcher.group(7)),null,null,null,null,null,null,null,null,artifact,"",rule[i]);
					} catch (ModeloException e) {
						e.printStackTrace();
					}			    
				}
			}
		}
		return UCs;
	}
}
