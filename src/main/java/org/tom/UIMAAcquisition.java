package org.tom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.util.XMLInputSource;
import org.tom.config.AE;
import org.tom.config.MapArtifacts;
import org.tom.util.FileManipulation;

public class UIMAAcquisition {
	
	FileManipulation fileManipulation 	= new FileManipulation();;
	MapArtifacts map 					= new MapArtifacts();;
	TraceabilityAnalisys tm 				= new TraceabilityAnalisys(); 
	
	public UIMAAcquisition(){
	}
      
    public void analyseProject(File file, String filePath){
    	try {
    		if(this.map.canAnalyse(file.getName())){
    			List<AE> AEs = this.map.getAEs(file.getName());
    			for(AE ae : AEs)
    				getItemsInformation(ae.getDesc(), ae.getVision(), file, filePath);
    		}
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void analyseProjectCodes(File file, String filePath){
    	try {
			List<AE> AEs = this.map.getAEs("CODE_"+file.getName());
			for(AE ae : AEs)
				getItemsInformation(ae.getDesc(), ae.getVision(), file, filePath);
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void getItemsInformation(String analyseEngine, String viewName, File file,String filePath) throws UIMAException, IOException{
	    XMLInputSource input 			= new XMLInputSource("descriptors/" + analyseEngine + ".xml");
	    AnalysisEngineDescription desc 	= UIMAFramework.getXMLParser().parseAnalysisEngineDescription(input);
	    AnalysisEngine seAnnotator 		= UIMAFramework.produceAnalysisEngine(desc);
	    
	    CAS cas 	= seAnnotator.newCAS();
	    
	    CAS view 	= cas.createView(viewName);
	    view.setDocumentText(fileManipulation.fileToString(file));

	    CAS viewPath = cas.createView("filePath");
	    
	    String artifact = filePath.substring(filePath.lastIndexOf(System.getProperty("file.separator")) + 1, filePath.length());

	    tm.createArtifact(artifact,file);
	    
	    viewPath.setDocumentText(artifact);
	    
	    seAnnotator.process(cas);
	    seAnnotator.destroy();
	}

}
