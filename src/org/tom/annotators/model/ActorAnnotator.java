package org.tom.annotators.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;
import org.tom.DAO.ModeloException;
import org.tom.manager.unstructured.UActorManager;


public class ActorAnnotator extends CasAnnotator_ImplBase {

	private Pattern actor = Pattern.compile("(<UML[:]Actor(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)/>)");
	private UActorManager am = new UActorManager();
	private Pattern[] actorPat;
	private String[] rule;

	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		String[] patterns = (String[]) aContext.getConfigParameterValue("Patterns");
	    rule = (String[]) aContext.getConfigParameterValue("Rule");

	    actorPat = new Pattern[patterns.length];
	    for (int i = 0; i < patterns.length; i++) {
	    	actorPat[i] = Pattern.compile(patterns[i]);
	    }
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS actorView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    actorView = aCas.getView("ActorDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = actorView.getAnnotationType();
	    
//	    actorView.setSofaDataString(actorView.getDocumentText(), "string");
	    
	 // Get the Actor text

	    String actorText = actorView.getDocumentText();
	    int actorEnd = 0;
	    StringBuffer translation;// = new StringBuffer();

		Matcher matcher = actor.matcher(actorText);		
		while(matcher.find()){
			translation = new StringBuffer();
			
			int actorBegin = matcher.start();
			actorEnd = matcher.end();
		    AnnotationFS actorAnnot = actorView.createAnnotation(annot, actorBegin, actorEnd);
		    translation.append("ator: " + matcher.group(2) + " " + matcher.group(4) );
		    try {
				am.actorPersist(matcher.group(4), matcher.group(2), artifact,"","","","");
			} catch (ModeloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(translation.toString());
		    actorView.addFsToIndexes(actorAnnot);
		}
		
		
	}
	
	public void teste(){
		
	}

}
