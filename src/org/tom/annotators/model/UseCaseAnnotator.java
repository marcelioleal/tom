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
import org.tom.manager.unstructured.UUseCaseManager;

public class UseCaseAnnotator extends CasAnnotator_ImplBase{
	
	private Pattern useCase = Pattern.compile("<UML[:]UseCase(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)(<UML[:](?si)(.*?)</UML[:]UseCase>|/>)");
	private UUseCaseManager ucm = new UUseCaseManager();
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		CAS useCaseView;
		CAS pathView;
		
		// get the CAS view for the Actor document
	    useCaseView = aCas.getView("UseCaseDocument");
	    pathView = aCas.getView("filePath");
	    String artifact = pathView.getDocumentText();

	 // Get some necessary Type System constants
	    Type annot = useCaseView.getAnnotationType();
	    
	 // Get the Actor text
	    String useCaseText = useCaseView.getDocumentText();
	    
	 // Setup for translated text
	    int useCaseEnd = 0;

	    //search for Actor pattern
		Matcher matcher = useCase.matcher(useCaseText);
		
		while(matcher.find()){
			int useCaseBegin = matcher.start();
			useCaseEnd = matcher.end();
			
			// Create Actor annotations on the text
		    AnnotationFS actorAnnot = useCaseView.createAnnotation(annot, useCaseBegin, useCaseEnd);
		    System.out.println("use case: " + matcher.group(1) + " " + matcher.group(3) );
		    useCaseView.addFsToIndexes(actorAnnot);
		}
		
		
	}
}
