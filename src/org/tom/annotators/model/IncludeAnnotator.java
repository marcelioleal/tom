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

public class IncludeAnnotator extends CasAnnotator_ImplBase {
	
	
	private Pattern include = Pattern.compile("<UML[:]Include\\sxmi.id\\s?=\\s?'((?:.*?))'(?si)(.*?)" +
												"<UML[:]Include[.]addition>(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)</UML[:]Include[.]addition>(?si)(.*?)" +
												"<UML[:]Include[.]base>(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)</UML[:]Include[.]base>" +
												"(?si)(.*?)</UML[:]Include>");
	
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}

	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS includeView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    includeView = aCas.getView("IncludeDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = includeView.getAnnotationType();
	    
	 // Get the Actor text
	    String includeText = includeView.getDocumentText();
	    
	 // Setup for translated text
	    int includeEnd = 0;
	    StringBuffer translation;// = new StringBuffer();

	    //search for Actor pattern
		Matcher matcher = include.matcher(includeText);
		
		while(matcher.find()){
			translation = new StringBuffer();
			
			int includeBegin = matcher.start();
			includeEnd = matcher.end();
			
			// Create Actor annotations on the text
		    AnnotationFS actorAnnot = includeView.createAnnotation(annot, includeBegin, includeEnd);
		    translation.append("include: " + matcher.group(1) + " " + matcher.group(8) + artifact + matcher.group(4) );
		    System.out.println(translation.toString());
		    includeView.addFsToIndexes(actorAnnot);
		}
		
		
	}

}
