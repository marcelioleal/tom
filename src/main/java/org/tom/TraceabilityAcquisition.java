package org.tom;

import java.io.File;
import java.net.ConnectException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.tom.DAO.ModeloException;
import org.tom.entities.OperationEnum;
import org.tom.entities.unstructuredDB.UActor;
import org.tom.entities.unstructuredDB.UCode;
import org.tom.entities.unstructuredDB.UFeature;
import org.tom.entities.unstructuredDB.UItem;
import org.tom.entities.unstructuredDB.UNeed;
import org.tom.entities.unstructuredDB.UTestCase;
import org.tom.entities.unstructuredDB.UUseCase;
import org.tom.manager.HistoryManager;
import org.tom.manager.unstructured.UActorManager;
import org.tom.manager.unstructured.UBusinessRuleManager;
import org.tom.manager.unstructured.UCodeManager;
import org.tom.manager.unstructured.UFeatureManager;
import org.tom.manager.unstructured.UNeedManager;
import org.tom.manager.unstructured.UTestCaseManager;
import org.tom.manager.unstructured.UUseCaseManager;
import org.tom.util.FileManipulation;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

public class TraceabilityAcquisition {

	private UIMAAcquisition application;
	private OpenOfficeConnection connection;
	private FileManipulation fileMan;
	private HistoryManager histMan = new HistoryManager();
	
	public TraceabilityAcquisition(){
		
	}
	
	public void connectService() throws ConnectException{
		this.fileMan = new FileManipulation();
		this.application = new UIMAAcquisition();
		this.connection = new SocketOpenOfficeConnection(8100);
		this.connection.connect();
	}
	
	public void disconnectService(){
		this.connection.disconnect();
	}
	
	public void iterateProjectFiles(String directoryPath, DefaultMutableTreeNode mainNode){
		DefaultMutableTreeNode fileNode;
		File directory 	= new File(directoryPath);
		File[] files 	= directory.listFiles();
		String log		= "";
		
		for (File file : files) {
			if(this.application.map.canAnalyseDirectory(file.getName())){
				fileNode = new DefaultMutableTreeNode(file.getName());
				
				if(file.isDirectory()){
					mainNode.add(fileNode);
					log += file.getName()+"\n";
					this.iterateProjectFiles(file.getAbsolutePath(), fileNode);
				}else {
					if(this.application.map.canConvert(file.getName())){
						mainNode.add(fileNode);
						log += file.getName()+"\n";

						this.eraseUInformations(file);
						
						File outputFile = fileMan.convertFile(file,this.connection);
						application.analyseProject(outputFile,file.getAbsolutePath());
					}
				}
			}
		}
		
		String description = " Files Affected: \n"+log;
		String user = null;
		try {
			histMan.historyPersist(OperationEnum.INFORMATION_EXTRACTION.getDesc(), description, user);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
			
	}
	
	public void iterateProjectCodes(String directoryPath, DefaultMutableTreeNode mainNode){
		DefaultMutableTreeNode fileNode;
		File directory 	= new File(directoryPath);
		File[] files 	= directory.listFiles();
		
		for (File file : files) {
			if(this.application.map.canAnalyseDirectory(file.getName())){
				fileNode = new DefaultMutableTreeNode(file.getName());
				if(file.isDirectory()){
					mainNode.add(fileNode);
					this.iterateProjectCodes(file.getAbsolutePath(), fileNode);
				}else {
					if(this.application.map.canAnalyseCode(file.getName())){
						mainNode.add(fileNode);
						this.eraseUInformations(file);
						application.analyseProjectCodes(file,file.getAbsolutePath());
					}
				}
			}
		}
	}
	
	public void eraseUInformations(File file){
		String fileName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(System.getProperty("file.separator")) + 1, file.getAbsolutePath().length());
		try{
			UActorManager manActor = new UActorManager();
			List<UActor> actors  = manActor.getUActorByArtifact(fileName);
			manActor.removeAll(actors);
			
			UNeedManager manNeed = new UNeedManager();
			List<UNeed> needs  = manNeed.getUNeedByArtifact(fileName);
			manNeed.removeAll(needs);
			
			UFeatureManager manFeature = new UFeatureManager();
			List<UFeature> features  = manFeature.getUFeatureByArtifact(fileName);
			manFeature.removeAll(features);
			
			UUseCaseManager manUseCase = new UUseCaseManager();
			List<UUseCase> ucs  = manUseCase.getUUseCaseByArtifact(fileName);
			manUseCase.removeAll(ucs);
			
			UBusinessRuleManager manBRule = new UBusinessRuleManager();
			List<UItem> bRule  = manBRule.getUBusinessRuleByArtifact(fileName);
			manBRule.removeAll(bRule);
			
			UTestCaseManager manTCase = new UTestCaseManager();
			List<UTestCase> tCase  = manTCase.getUTestCaseByArtifact(fileName);
			manTCase.removeAll(tCase);
			
			UCodeManager manCode = new UCodeManager();
			List<UCode> codes  = manCode.getUCodeByArtifact(fileName);
			manCode.removeAll(codes);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void eraseUnstructuredInformations(){
		try{
			UActorManager manActor = new UActorManager();
			List<UActor> actors  = manActor.getAllActor();
			manActor.removeAll(actors);
			
			UNeedManager manNeed = new UNeedManager();
			List<UNeed> needs  = manNeed.getAllNeed();
			manNeed.removeAll(needs);
			
			UFeatureManager manFeature = new UFeatureManager();
			List<UFeature> features  = manFeature.getAllFeature();
			manFeature.removeAll(features);
			
			UUseCaseManager manUseCase = new UUseCaseManager();
			List<UUseCase> ucs  = manUseCase.getAllUseCase();
			manUseCase.removeAll(ucs);
			
			UBusinessRuleManager manBRule = new UBusinessRuleManager();
			List<UItem> bRule  = manBRule.getAllBusinessRule();
			manBRule.removeAll(bRule);
			
			UTestCaseManager manTCase = new UTestCaseManager();
			List<UTestCase> tCase  = manTCase.getAllTestCase();
			manTCase.removeAll(tCase);
			
			UCodeManager manCode = new UCodeManager();
			List<UCode> codes  = manCode.getAllCode();
			manCode.removeAll(codes);
			
			String user = null;			
			histMan.historyPersist(OperationEnum.UNST_INFO_ERASE.getDesc(), OperationEnum.UNST_INFO_ERASE.getDesc(), user);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}