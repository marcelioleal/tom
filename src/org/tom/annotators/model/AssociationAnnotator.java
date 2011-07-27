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

public class AssociationAnnotator extends CasAnnotator_ImplBase{

//	private Pattern umbrello = Pattern.compile("<XMI[.]exporter>umbrello");
//	private Pattern association = Pattern.compile("<UML[:]Association(?:.*?)xmi.id=\"((?:.*?))\"(?:.*?)name=\"((?:.*?))\"(?si)(.*?)<UML[:]AssociationEnd(?:.*?)type=\"((?:.*?))\"(?si)(.*?)<UML[:]AssociationEnd(?:.*?)type=\"((?:.*?))\"(?si)(.*?)</UML[:]Association>");
	
	private Pattern association2 = Pattern.compile("(<UML[:]Association[^E](?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)name\\s?=\\s?'((?:.*?))'(?si)(.*?)" +
													"<UML:AssociationEnd[^.](?si)(.*?)visibility\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)isNavigable\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)aggregation\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]MultiplicityRange(?si)(.*?)lower\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)upper\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]AssociationEnd[.]participant(?si)(.*?)xmi.idref\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]MultiplicityRange(?si)(.*?)lower\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)upper\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
													"<UML[:]AssociationEnd.participant(?si)(.*?)xmi.idref\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +													
													"</UML[:]Association>)");
	
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS associationView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    associationView = aCas.getView("AssociationDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = associationView.getAnnotationType();
	    
	 // Get the Actor text
	    String associationText = associationView.getDocumentText();
	    
	 // Setup for translated text
	    int associationEnd = 0;

	    //search for Actor pattern
		Matcher matcher = association2.matcher(associationText);
		
		while(matcher.find()){
			int associationBegin = matcher.start();
			associationEnd = matcher.end();
			
			// Create Association annotations on the text
		    AnnotationFS actorAnnot = associationView.createAnnotation(annot, associationBegin, associationEnd);
		    System.out.println("associação: " + matcher.group(2) + " " + matcher.group(4) + " " + matcher.group(7) + " " + 
		    		matcher.group(9) + " " + matcher.group(11) + " " + matcher.group(14) + " " + 
		    		matcher.group(15) + " " + matcher.group(18) + " " + matcher.group(21) + " " + 
		    		matcher.group(22) + " " + matcher.group(25) );
		    associationView.addFsToIndexes(actorAnnot);
		}
		
	}
}
