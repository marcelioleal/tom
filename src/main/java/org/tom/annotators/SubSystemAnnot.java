
package org.tom.annotators;

import java.util.regex.Matcher;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.util.Level;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.URequerimentManager;
import org.tom.manager.unstructured.USubSystemManager;
import org.tom.util.FileManipulation;

public class SubSystemAnnot extends Annot{

	private URequerimentManager man = new URequerimentManager();
	private USubSystemManager manSS = new USubSystemManager();


	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS sSystemView = aCas.getView("SubSystemSpecificationDocument");
		String text 	= sSystemView.getDocumentText();
		Type annotType 	= sSystemView.getAnnotationType();
		int end = 0;
		
	    String artifact = this.getArtifactName(aCas); 
		
		for (int i = 0; i < patArea.length; i++) {
			Matcher matcher = patArea[i].matcher(text);
			while (matcher.find()) {	
				int begin = matcher.start();
				end = matcher.end();
				AnnotationFS annot = sSystemView.createAnnotation(annotType, begin, end);
				this.printFields(matcher, this.isTestArea);
				if(!this.isTestArea){
					String reqs = this.createReqs(matcher.group(13),artifact, rule[i]);
					try {
						manSS.subSystemPersist(FileManipulation.clearRtfFormat(matcher.group(2)), FileManipulation.clearRtfFormat(matcher.group(2)), artifact, "", rule[i],FileManipulation.clearRtfFormat(matcher.group(5)+"\n"+matcher.group(9)), reqs);
					} catch (ModeloException e) {
						e.printStackTrace();
					}
					sSystemView.addFsToIndexes(annot);
					getContext().getLogger().log(Level.FINEST, "Found: " + annot);
				}
			}
		}
	}

	private String createReqs(String text,String artifact, String mainRule){
		String reqs = "";
		for (int i = 0; i < pat.length; i++) {
			Matcher matcher = pat[i].matcher(text);
			while (matcher.find()) {
				this.printFields(matcher,this.isTest);
				if(!this.isTest){
					if(!(mainRule.equalsIgnoreCase("ProCobraTec - P3") && rule[i].equalsIgnoreCase("ProCobraTec - P2"))){ //Gmb
						reqs += "|"+FileManipulation.clearRtfFormat(matcher.group(1));
						try {
							man.requerimentPersist(FileManipulation.clearRtfFormat(matcher.group(2)), FileManipulation.clearRtfFormat(matcher.group(1)), artifact, "", rule[i],"", "");						
						} catch (ModeloException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return reqs;
	}
	
}
