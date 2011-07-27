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

public class ExtendAnnotator extends CasAnnotator_ImplBase {

	
	private Pattern extend = Pattern.compile("<UML[:]Extend\\sxmi.id\\s?=\\s?'((?:.*?))'(?si)(.*?)" +
												"<UML[:]Extend[.]base>(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)</UML[:]Extend[.]base>(?si)(.*?)" +
												"<UML[:]Extend[.]extension>(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)</UML[:]Extend[.]extension>" +
												"(?si)(.*?)</UML[:]Extend>");
	
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}

	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS extendView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    extendView = aCas.getView("ExtendDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = extendView.getAnnotationType();
	    
	 // Get the Actor text
	    String extendText = extendView.getDocumentText();
	    
	 // Setup for translated text
	    int extendEnd = 0;
	    StringBuffer translation;// = new StringBuffer();

	    //search for Actor pattern
		Matcher matcher = extend.matcher(extendText);
		
		while(matcher.find()){
			translation = new StringBuffer();
			
			int extendBegin = matcher.start();
			extendEnd = matcher.end();
			
			// Create Actor annotations on the text
		    AnnotationFS actorAnnot = extendView.createAnnotation(annot, extendBegin, extendEnd);
		    translation.append("extend: " + matcher.group(1) + " " + matcher.group(8) + " " + matcher.group(4) );
		    System.out.println(translation.toString());
		    extendView.addFsToIndexes(actorAnnot);
		}
		
		
	}
}
