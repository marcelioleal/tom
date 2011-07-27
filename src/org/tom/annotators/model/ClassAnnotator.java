package org.tom.annotators.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;

public class ClassAnnotator extends CasAnnotator_ImplBase{

	int position = 0;
	
	private Pattern classUML = Pattern.compile("<UML[:]Class(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
												"name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
												"visibility\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
												"isAbstract\\s?=\\s?[\"']((?:.*?))[\"']" +
												"(?si)(.*?)</UML[:]Class>");
	
	private Pattern attribute = Pattern.compile("<UML[:]Attribute(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"name\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)" +
										"visibility\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"<UML[:]StructuralFeature[.]type>(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)</UML[:]StructuralFeature[.]type>" +
										"(?si)(.*?)</UML[:]Attribute>");
	
	private Pattern operation = Pattern.compile("<UML[:]Operation(?si)(.*?)" +
										"xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"visibility\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"isAbstract\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"<UML[:]Parameter(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"kind\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
										"<UML[:]Parameter[.]type>(?si)(.*?)<UML[:]DataType(?:.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)" +
										"</UML[:]Operation>");
	
	private Pattern operation2 = Pattern.compile("<UML[:]Operation(?:.*?)visibility\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)isAbstract\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)name\\s?=\\s?[\"']((?:.*?))[\"'](?:.*?)/>");
	
	private Pattern parameter = Pattern.compile("<UML[:]Parameter(?:.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
			"name\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)" +
			"(?si)(.*?)xmi.idref\\s?=\\s?'((?:.*?))'(?si)(.*?)" +												
			"</UML[:]Parameter>"); 

	private Pattern operationPattern = Pattern.compile("<UML[:]Operation(?si)(.*?)xmi.id\\s?=\\s?[\"']((?:.*?))[\"'](?si)(.*?)");
	
	CAS classView, classElementsView, pathView;
	String artifactPath, artifact;
	Feature other;
	Type cross;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	@Override
	public void process(CAS aCas) throws AnalysisEngineProcessException{
		
		pathView = aCas.getView("filePath");
	    artifact = pathView.getDocumentText();
		
		// get the CAS view for the Actor document
	    classView = aCas.getView("ClassDocument");
	    classElementsView = aCas.createView("ClassElementsDocument");
	    
	    // Get some necessary Type System constants
	    Type annot = classView.getAnnotationType();
//	    cross = classView.getTypeSystem().getType("sofa.test.CrossAnnotation");
//	    other = cross.getFeatureByBaseName("otherAnnotation");
	    
	    // Get the Actor text
	    String classText = classView.getDocumentText();
	    
	    // Setup for translated text
	    int classEnd = 0;

	    //search for Class pattern
	    Matcher matcher = classUML.matcher(classText);
	    Matcher matcher2;
	    while(matcher.find()){
	    	String group = matcher.group();
	    	if ( group.matches("^(?si)(?:.*)</UML[:]Package>(?si)(?:.*?)$") ) {
	    		
	    		String newGroup = group.replaceAll("^(?si)(?:.*)</UML[:]Package>", "");
	    		int position = classText.indexOf(newGroup);
	    		matcher2 = classUML.matcher(newGroup);
	    		
	    		if(matcher2.find()){
	    			int classBegin = position;
	    			classEnd = position + newGroup.length();
	    			
	    			// Create Class annotations on the text
	    		    AnnotationFS actorAnnot = classView.createAnnotation(annot, classBegin, classEnd);
	    		    System.out.println("classe: " + matcher2.group(1) + " " + matcher2.group(3) + " " + matcher2.group(5) + " " + matcher2.group(7) );
	    		    classView.addFsToIndexes(actorAnnot);
	    		    
	    		    getAttributeAndOperation(matcher2.group(), matcher2.group(1));
  	    		}
	
	    	}
	    	else{
	    		
    			int classBegin = matcher.start();
    			classEnd = matcher.end();
    			
    			// Create Class annotations on the text
    		    AnnotationFS actorAnnot = classView.createAnnotation(annot, classBegin, classEnd);
    		    System.out.println("classe: " + matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(5) + " " + matcher.group(7) );
    		    classView.addFsToIndexes(actorAnnot);
    		    
    		    getAttributeAndOperation(matcher.group(), matcher.group(1));
	    	}
	    	
	    }
	    
	    
		
	}
	
	public void getAttributeAndOperation(String text, String id){
		String classId = id;
//		int classElementsBegin = 0;
//	    int classElementsEnd = 0;
		//look for the attribute pattern in the region selected before
		Matcher matcher = attribute.matcher(text);	
		while(matcher.find()){
		    System.out.println("atributo: " + matcher.group(1)+ " " + matcher.group(3)+ " " + matcher.group(4)+ " " + matcher.group(7) + " " + classId);

		}//end of while(matcher.find())		
			
		//look for the operation pattern in the region selected before
		matcher = operation.matcher(text);
		while(matcher.find()){
			System.out.println("operacao: " + matcher.group(2)+ " " + matcher.group(4)+ " " + matcher.group(8)+ " " + matcher.group(6) + " " + classId);
			getOperationParameter(matcher.group());
		}

		
		matcher = operation2.matcher(text);
		while(matcher.find()){
			System.out.println("operacao: " + matcher.group(2)+ " " + matcher.group(4)+ " " + matcher.group(3)+ " " + matcher.group(1) + " " + classId);
			getOperationParameter(matcher.group());
		}
		
	}
	
	public void getOperationParameter(String text){
		String opId = null;
		Matcher matcher = parameter.matcher(text);
		Matcher op = operationPattern.matcher(text);
		if(op.find()) opId = op.group(2);
		while(matcher.find()){	
			System.out.println("parametro: "+ matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(6) + " " + opId);
		}
	}
	
}
