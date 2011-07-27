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

public class GeneralizationAnnotator extends CasAnnotator_ImplBase {
	
	private Pattern generalization = Pattern.compile("<UML[:]Generalization(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]Generalization.child>(?si)(.*?)" +
													"xmi[.]idref\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]Generalization.parent>(?si)(.*?)" +
													"xmi[.]idref\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"</UML[:]Generalization>"); 
	
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS generalizationView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    generalizationView = aCas.getView("GeneralizationDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = generalizationView.getAnnotationType();
	    
	 // Get the Actor text
	    String generalizationText = generalizationView.getDocumentText();
	    
	 // Setup for translated text
	    int generalizationEnd = 0;
	    StringBuffer translation;// = new StringBuffer();

	    //search for Actor pattern
		Matcher matcher = generalization.matcher(generalizationText);
		
		while(matcher.find()){
			translation = new StringBuffer();
			
			int generalizationBegin = matcher.start();
			generalizationEnd = matcher.end();
			
			// Create Actor annotations on the text
		    AnnotationFS generalizationAnnot = generalizationView.createAnnotation(annot, generalizationBegin, generalizationEnd);
		    translation.append("generalization " + matcher.group(1) + " " + matcher.group(7) + " " + matcher.group(4) );
		    System.out.println(translation.toString());
		    generalizationView.addFsToIndexes(generalizationAnnot);
		}
		
		
	}

}
