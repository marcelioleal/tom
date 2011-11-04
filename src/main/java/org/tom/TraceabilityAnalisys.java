package org.tom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.tom.DAO.ModeloException;
import org.tom.config.MapArtifacts;
import org.tom.entities.Artifact;
import org.tom.entities.ArtifactTypeEnum;
import org.tom.entities.Baseline;
import org.tom.entities.OperationEnum;
import org.tom.entities.Project;
import org.tom.entities.ProjectStatusEnum;
import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.ItemTypeEnum;
import org.tom.entities.structuredDB.Link;
import org.tom.entities.structuredDB.LinkTypeEnum;
import org.tom.entities.unstructuredDB.UActor;
import org.tom.entities.unstructuredDB.UCode;
import org.tom.entities.unstructuredDB.UFeature;
import org.tom.entities.unstructuredDB.UItem;
import org.tom.entities.unstructuredDB.UNeed;
import org.tom.entities.unstructuredDB.UTestCase;
import org.tom.entities.unstructuredDB.UUseCase;
import org.tom.manager.ArtifactManager;
import org.tom.manager.BaselineManager;
import org.tom.manager.HistoryManager;
import org.tom.manager.ProjectManager;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;
import org.tom.manager.unstructured.UActorManager;
import org.tom.manager.unstructured.UBusinessRuleManager;
import org.tom.manager.unstructured.UCodeManager;
import org.tom.manager.unstructured.UFeatureManager;
import org.tom.manager.unstructured.UNeedManager;
import org.tom.manager.unstructured.UTestCaseManager;
import org.tom.manager.unstructured.UUseCaseManager;
import org.tom.util.FileManipulation;

public class TraceabilityAnalisys{
	private ItemsManager itemMan 		= new ItemsManager();
	private LinkManager linkMan 		= new LinkManager();
	private BaselineManager baselineMan = new BaselineManager();
	private HistoryManager historyMan 	= new HistoryManager();
	private ProjectManager projectMan 	= new ProjectManager();
	private ArtifactManager artM = new ArtifactManager();

	//Unstructured
	private UNeedManager unstNeedM 			= new UNeedManager();
	private UFeatureManager unstFeatureM 	= new UFeatureManager();
	private UActorManager unstActorM 		= new UActorManager();
	private UUseCaseManager unstUseCaseM 	= new UUseCaseManager();
	private UTestCaseManager unstTestCaseM 	= new UTestCaseManager();
	private UCodeManager unstCodeM 			= new UCodeManager();
	private UBusinessRuleManager unstBusinessRuleM 	= new UBusinessRuleManager();


	private MapArtifacts map;

	private Baseline actBaseline;

	private List<HashMap<Integer,String>> needToFeatures 	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> featureToActors 	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToFeatures = new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToActors	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToUseCaseIncludes	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToUseCaseExtends	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToBusinessRules	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> useCaseToUseCaseDepends 	= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> testCaseToUseCase			= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> codeToUseCases			= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> codeToBusinessRules		= new ArrayList<HashMap<Integer,String>>();
	private List<HashMap<Integer,String>> codeToTestCases			= new ArrayList<HashMap<Integer,String>>();

	public TraceabilityAnalisys(){
		actBaseline = baselineMan.getActiveBaseline();
	}

	public void analiseUnstruturedInformations(){
		try{
			this.createItems();
			this.createLinks();
			this.changeProjectStatus();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createItems() throws ModeloException{
		this.createVisionItems();
		this.createBusinessRuleItems();
		this.createUseCaseItems();
		this.createTestCaseItems();
		this.createCodeItems();
	}

	public void createLinks() throws ModeloException{
		this.createEvolutionLinks();
		this.createDependenceLinks();
	}

	public void changeProjectStatus(){
		Project project = projectMan.getProject();
		project.setStatus(ProjectStatusEnum.STRUCTURED.getId());
		try {
			projectMan.persist(project);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	//Items	
	public void createVisionItems() throws ModeloException{
		List<Artifact> artifacts = artM.getArtifactsByType(ArtifactTypeEnum.VISION.getType());
		for (Artifact artifact : artifacts) {
			List<UActor> unstActors 		= unstActorM.getUActorByArtifact(artifact.getName());
			List<UNeed> unstNeeds 			= unstNeedM.getUNeedByArtifact(artifact.getName());
			List<UFeature> unstFeatures 	= unstFeatureM.getUFeatureByArtifact(artifact.getName());

			this.createItems(artifact, unstNeeds,ItemTypeEnum.NEED.getId());
			this.createItems(artifact, unstActors,ItemTypeEnum.ACTOR.getId());
			this.createItems(artifact, unstFeatures, ItemTypeEnum.FEATURE.getId());
		}
	}

	public void createBusinessRuleItems() throws ModeloException{
		List<Artifact> artifacts = artM.getArtifactsByType(ArtifactTypeEnum.BUSINESSRULES.getType());
		for (Artifact artifact : artifacts) {
			List<UItem> unstBusinessRules 	= unstBusinessRuleM.getUBusinessRuleByArtifact(artifact.getName());
			this.createItems(artifact,unstBusinessRules,ItemTypeEnum.BUSINESSRULE.getId());
		}
	}

	public void createUseCaseItems() throws ModeloException{
		ArtifactManager artM = new ArtifactManager();
		List<Artifact> artifacts = artM.getArtifactsByType(ArtifactTypeEnum.USECASESPEC.getType());

		for (Artifact artifact : artifacts){
			List<UUseCase> unstUseCases 	= unstUseCaseM.getUUseCaseByArtifact(artifact.getName());
			this.createItems(artifact,unstUseCases,ItemTypeEnum.USECASE.getId());
		}
	}

	public void createTestCaseItems() throws ModeloException{
		ArtifactManager artM = new ArtifactManager();
		List<Artifact> artifacts = artM.getArtifactsByType(ArtifactTypeEnum.TESTCASESPEC.getType());

		for (Artifact artifact : artifacts) {
			List<UTestCase> unstTestCases 	= unstTestCaseM.getUTestCaseByArtifact(artifact.getName());
			this.createItems(artifact,unstTestCases,ItemTypeEnum.TESTCASE.getId());
		}
	}

	public void createCodeItems() throws ModeloException{
		ArtifactManager artM = new ArtifactManager();
		List<Artifact> artifacts = artM.getArtifactsByType(ArtifactTypeEnum.CODE.getType());

		for (Artifact artifact : artifacts) {
			List<UCode> unstCodes 	= unstCodeM.getUCodeByArtifact(artifact.getName());
			this.createItems(artifact,unstCodes,ItemTypeEnum.CODE.getId());
		}
	}

	public <T extends UItem> void createItems(Artifact artifact, List<T> unstL, Integer type) throws ModeloException{
		if(!unstL.isEmpty()){
			List<Item> items = new ArrayList<Item>();
			items = itemMan.getItemsByTypeAndArtifactAndBaseline(type, artifact.getName(),actBaseline.getIdBaseline());
			if(!items.isEmpty()){
				for (Item item: items) {
					boolean finded = false;
					for(int i=0;i<unstL.size();i++){
						if(unstL.get(i).getName().equalsIgnoreCase(item.getName())){
							this.updateItem(item,unstL.get(i),type);
							this.addLinksByType(item,unstL.get(i),type);
							unstL.remove(i);
							finded = true;
							break;
						}
					}
					if(!finded)
						this.eraseItem(item);
				}
			}
			if(!unstL.isEmpty()){
				for (T uItem : unstL){
					Item it = itemMan.getItemByNameAndTypeAndBaseline(uItem.getName(), type,  actBaseline.getIdBaseline());
					if(it == null){
						Item itemNew = itemMan.itemPersist(this.formatId(uItem.getId(),type), uItem.getName(), uItem.getDescription(), type, actBaseline.getIdBaseline(), uItem.getArtifactName(), true, null);
						this.addLinksByType(itemNew,uItem,type);
					}else{
						System.out.println("Duplicated type "+type+": "+uItem.getName());
						historyMan.historyPersist(OperationEnum.ITEM_DUPLICATED.getDesc(), "The Type"+type+" is duplicated: "+uItem.getName(), null);
					}
				}
			}
		}
	}

	private void updateItem(Item item, UItem uItem,int type) throws ModeloException{
		item.setId(this.formatId(uItem.getId(),type));
		item.setDescription(uItem.getDescription());
		item.setVersion(null);
		item.setAutomatic(true);
		itemMan.persist(item);
	}

	private String formatId(String id, int type){
		if(id.endsWith("."))
			id = id.substring(0, id.length()-1);

		if(type == ItemTypeEnum.ACTOR.getId()){
			if(id.startsWith("ACT"))
				return id;
			else
				return "ACT"+id.replaceAll("(?i)[a-z]*", "");
		}else if(type == ItemTypeEnum.NEED.getId()){
			if(id.startsWith("ND"))
				return id;
			else
				return "ND"+id.replaceAll("(?i)[a-z]*", "");
		}else if(type == ItemTypeEnum.FEATURE.getId()){
			if(id.startsWith("FT"))
				return id;
			else
				return "FT"+id.replaceAll("(?i)[a-z]*", "");
		}else if(type == ItemTypeEnum.USECASE.getId()){
			/*if(id.startsWith("UC")){
				return id;
			}else{
				id.replaceAll("(?i)[a-z]*", "");
				return "UC"+id;
			}*/
			return id;
		}else if(type == ItemTypeEnum.BUSINESSRULE.getId()){
			if( id.startsWith("BR"))
				return id;
			else
				return "BR"+id.replaceAll("(?i)[a-z]*", "");
		}else if(type == ItemTypeEnum.TESTCASE.getId()){
			if(id.startsWith("TC"))
				return id;
			else
				return "TC"+id.replaceAll("(?i)[a-z]*", "");
		}else{
			return id;
		}
	}

	private void eraseItem(Item item) throws ModeloException{
		if(item.isAutomatic()){
			String log = "Removing Item and itself links ... \n ";
			log += item.toString();

			List<Link> links = linkMan.getAllLinksByItem(item.getIdItem());
			linkMan.removeAll(links);

			itemMan.remove(item);

			historyMan.historyPersist(OperationEnum.STR_ITEM_ERASE.getDesc(), log, null);
		}
	}

	public void addLinksByType(Item item, UItem uItem,Integer type){
		if(type == ItemTypeEnum.NEED.getId()){
			UNeed uNeed = unstNeedM.getNeed(uItem.getIdentifier());

			this.addLinks(needToFeatures,item.getIdItem(),uNeed.getFeatures());
		}else if(type == ItemTypeEnum.FEATURE.getId()){
			UFeature uFeature = unstFeatureM.getFeature(uItem.getIdentifier());

			this.addLinks(featureToActors,item.getIdItem(),uFeature.getActors());
		}else if(type == ItemTypeEnum.USECASE.getId()){
			UUseCase uUseCase = unstUseCaseM.getUseCase(uItem.getIdentifier());

			this.addLinks(useCaseToFeatures,item.getIdItem(),uUseCase.getFeatures());
			this.addLinks(useCaseToActors,item.getIdItem(),uUseCase.getActors());
			this.addLinks(useCaseToUseCaseIncludes,item.getIdItem(),uUseCase.getIncludesUC());
			this.addLinks(useCaseToUseCaseExtends,item.getIdItem(),uUseCase.getExtendsUC());
			this.addLinks(useCaseToBusinessRules,item.getIdItem(),uUseCase.getBusinessRulesRel());
			this.addLinks(useCaseToUseCaseDepends,item.getIdItem(),uUseCase.getDependentsUC());
		}else if(type == ItemTypeEnum.TESTCASE.getId()){
			UTestCase uTestCase = unstTestCaseM.getTestCase(uItem.getIdentifier());

			this.addLinks(testCaseToUseCase,item.getIdItem(),uTestCase.getUseCase());
		}else if(type == ItemTypeEnum.CODE.getId()){
			UCode uCode = unstCodeM.getCode(uItem.getIdentifier());

			this.addLinks(codeToUseCases,item.getIdItem(),uCode.getUseCases());
			this.addLinks(codeToBusinessRules,item.getIdItem(),uCode.getBusinessRules());
			this.addLinks(codeToTestCases,item.getIdItem(),uCode.getTestCases());
		}
	}

	public void addLinks(List<HashMap<Integer,String>> list,int idItem,String linksStr){
		if(!linksStr.isEmpty()){
			HashMap<Integer,String> links = new HashMap<Integer,String>(1);
			links.put(idItem, linksStr);
			list.add(links);
		}
	}

	//Links
	public void createEvolutionLinks() throws ModeloException{
		this.createAnalisysLinks(needToFeatures, true, ItemTypeEnum.FEATURE,LinkTypeEnum.EVOLUTION);
		this.createAnalisysInvertLinks(useCaseToFeatures, false, ItemTypeEnum.FEATURE,LinkTypeEnum.SATISFACTION);
		this.createAnalisysInvertLinks(testCaseToUseCase, true, ItemTypeEnum.USECASE,LinkTypeEnum.SATISFACTION);
		this.createAnalisysInvertLinks(codeToUseCases, true, ItemTypeEnum.USECASE,LinkTypeEnum.SATISFACTION);
		this.createAnalisysInvertLinks(codeToBusinessRules, false, ItemTypeEnum.BUSINESSRULE,LinkTypeEnum.SATISFACTION);
		this.createAnalisysInvertLinks(codeToTestCases, false, ItemTypeEnum.TESTCASE,LinkTypeEnum.SATISFACTION);
	}

	public void createDependenceLinks() throws ModeloException{
		this.createAnalisysLinks(featureToActors, true, ItemTypeEnum.ACTOR,LinkTypeEnum.DEPENDENCY);
		this.createAnalisysLinks(useCaseToActors, true, ItemTypeEnum.ACTOR,LinkTypeEnum.DEPENDENCY);		
		this.createAnalisysLinks(useCaseToBusinessRules, false, ItemTypeEnum.BUSINESSRULE,LinkTypeEnum.DEPENDENCY);
		this.createAnalisysLinks(useCaseToUseCaseIncludes, false, ItemTypeEnum.USECASE,LinkTypeEnum.DEPENDENCY);
		this.createAnalisysLinks(useCaseToUseCaseExtends, false, ItemTypeEnum.USECASE,LinkTypeEnum.DEPENDENCY);
		this.createAnalisysLinks(useCaseToUseCaseDepends, false, ItemTypeEnum.USECASE,LinkTypeEnum.DEPENDENCY);
	}

	public void createAnalisysLinks(List<HashMap<Integer,String>> linksList,boolean byName, ItemTypeEnum typeRelEnum, LinkTypeEnum linkEnum) throws ModeloException{
		String paramsName = "";
		if(!linksList.isEmpty()){
			for (HashMap<Integer,String> link : linksList) {
				for (Integer key : link.keySet()) {
					int itemId 	= key;
					String linksBruto = link.get(key);
					String[] parameters = linksBruto.split("\\|");
					for (String param : parameters) {
						paramsName = "";
						Item itemRelated = null;
						if(byName)
							itemRelated 	= this.itemMan.getItemByNameAndTypeAndBaseline(FileManipulation.cleanName(param,typeRelEnum.getId()),typeRelEnum.getId(),actBaseline.getIdBaseline());
						else
							itemRelated 	= this.itemMan.getItemByIdAndTypeAndBaseline(this.formatId(param,typeRelEnum.getId()),typeRelEnum.getId(),actBaseline.getIdBaseline());
							if((itemRelated != null)&&(!itemRelated.getId().isEmpty())){
								Link linkObj = linkMan.getLinkByItemAndItemRelAndType(itemId, itemRelated.getIdItem(), linkEnum.getId());
								if(linkObj == null){
									linkMan.linkPersist(itemId, itemRelated.getIdItem(), linkEnum.getId(), true, null);
									paramsName += param+" - ";
								}
							}else{
								System.out.println("The "+typeRelEnum.getDesc()+" not finded: "+param);
								historyMan.historyPersist(OperationEnum.LINK_NOT_FINDED.getDesc(), "The "+typeRelEnum.getDesc()+" not finded: "+param, null);
							}
					}
					if(!paramsName.isEmpty())
							historyMan.historyPersist(OperationEnum.LINK_FINDED.getDesc(), "The "+typeRelEnum.getDesc()+" was finded: "+paramsName, null);	
				}
			}
		}
	}

	public void createAnalisysInvertLinks(List<HashMap<Integer,String>> linksList,boolean byName, ItemTypeEnum typeRelEnum, LinkTypeEnum linkEnum) throws ModeloException{
		String paramsName = "";

		if(!linksList.isEmpty()){
			for (HashMap<Integer,String> link : linksList) {
				for (Integer key : link.keySet()) {
					int itemRelatedId 	= key;
					String[] parameters = link.get(key).split("\\|");
					for (String param : parameters) {
						paramsName = "";
						Item itemBase = null;
						if(byName)
							itemBase 	= this.itemMan.getItemByNameAndTypeAndBaseline(FileManipulation.cleanName(param,typeRelEnum.getId()),typeRelEnum.getId(),actBaseline.getIdBaseline());
						else{
							itemBase 	= this.itemMan.getItemByIdAndTypeAndBaseline(this.formatId(param,typeRelEnum.getId()),typeRelEnum.getId(),actBaseline.getIdBaseline());
						}
						if((itemBase != null)&&(!itemBase.getId().isEmpty())){
							Link linkObj = linkMan.getLinkByItemAndItemRelAndType(itemBase.getIdItem(),itemRelatedId, linkEnum.getId());
							if(linkObj == null){
								linkMan.linkPersist(itemBase.getIdItem(), itemRelatedId, linkEnum.getId(), true, null);
								paramsName += param+" - ";
							}
						}else{
							System.out.println("The "+typeRelEnum.getDesc()+" not finded: "+param);
							historyMan.historyPersist(OperationEnum.LINK_NOT_FINDED.getDesc(), "The "+typeRelEnum.getDesc()+" not finded: "+param, null);
						}
					}
					if(!paramsName.isEmpty())
						historyMan.historyPersist(OperationEnum.LINK_FINDED.getDesc(), "The "+typeRelEnum.getDesc()+" was finded: "+paramsName, null);	
				}
			}
		}
	}

	public void createArtifact(String artifact,File file){
		ArtifactManager artifactMan = new ArtifactManager();
		Artifact art = artifactMan.getArtifact(artifact);
		if((art == null) || (art.getName().isEmpty())){
			map = new MapArtifacts();
			try{
				artifactMan.artifactPersist(artifact,map.getArtifactType(artifact).getType(),file.getAbsolutePath(),null);
			}catch(ModeloException e){
				e.printStackTrace();
			}
		}
	}

}