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

public class DataTypeAnnotator extends CasAnnotator_ImplBase{

	
	private Pattern data = Pattern.compile("(<UML[:]DataType(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)/>)");
	
	private Pattern objectData = Pattern.compile("<UML[:]Class\\s?xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.[^<]*?)name\\s?=\\s?[\"'](.[^<]*?)[\"'](?si)(.[^<]*?)/>");
	
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS dataTypeView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    dataTypeView = aCas.getView("DataTypeDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();
	    
	 // Get some necessary Type System constants
	    Type annot = dataTypeView.getAnnotationType();
	    
	 // Get the Actor text
	    String dataTypeText = dataTypeView.getDocumentText();
	    
	 // Setup for translated text
	    int dataTypeEnd = 0;

	    //search for dataType pattern		
		Matcher matcher = data.matcher(dataTypeText);
		
		while(matcher.find()){
			int dataTypeBegin = matcher.start();
			dataTypeEnd = matcher.end();
			
			// Create Actor annotations on the text
			System.out.println("DataType: " + matcher.group(2) + " " + matcher.group(4) );
		    AnnotationFS actorAnnot = dataTypeView.createAnnotation(annot, dataTypeBegin, dataTypeEnd);
		    
		    dataTypeView.addFsToIndexes(actorAnnot);
		}
		
		matcher = objectData.matcher(dataTypeText);		
		while(matcher.find()){
			int dataTypeBegin = matcher.start();
			dataTypeEnd = matcher.end();
			
			// Create Actor annotations on the text
		    AnnotationFS actorAnnot = dataTypeView.createAnnotation(annot, dataTypeBegin, dataTypeEnd);
		    System.out.println("DataType: " + matcher.group(1) + " " + matcher.group(3) );
		    dataTypeView.addFsToIndexes(actorAnnot);
		}
		
		
	}

}
