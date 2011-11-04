package org.tom.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.tom.TraceabilityAcquisition;
import org.tom.entities.unstructuredDB.UActor;
import org.tom.entities.unstructuredDB.UCode;
import org.tom.entities.unstructuredDB.UFeature;
import org.tom.entities.unstructuredDB.UItem;
import org.tom.entities.unstructuredDB.UNeed;
import org.tom.entities.unstructuredDB.UTestCase;
import org.tom.entities.unstructuredDB.UUseCase;
import org.tom.manager.unstructured.UActorManager;
import org.tom.manager.unstructured.UBusinessRuleManager;
import org.tom.manager.unstructured.UCodeManager;
import org.tom.manager.unstructured.UFeatureManager;
import org.tom.manager.unstructured.UNeedManager;
import org.tom.manager.unstructured.UTestCaseManager;
import org.tom.manager.unstructured.UUseCaseManager;
import org.tom.util.FileManipulation;

public class AcquisitionGUI {

	private FileManipulation fileManipulation = new FileManipulation();

	private JScrollPane filesTreeScroll = null;
	private JScrollPane infoScroll = null;
	private JList infoList = null;
	private JTree filesTree = null;
	private String projectPath = null;
	
	
	public void eraseUnstructuredInformations(JPanel jContentPane){
		TraceabilityAcquisition tProcess = new TraceabilityAcquisition();
		tProcess.eraseUnstructuredInformations();

		jContentPane.removeAll();
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Traceability Informations Erased."), BorderLayout.CENTER);
		jContentPane.add(panel, BorderLayout.CENTER);
		
		System.out.println("Traceability Informations Erased.");
	}
	
	
	public void displayUnstructuredInformations(JPanel jContentPane,boolean code) throws ConnectException{
		jContentPane.removeAll();
		
		MainInformations mInfoPanel = new MainInformations();
		jContentPane.add(mInfoPanel, BorderLayout.WEST);
		jContentPane.add(getFilesTreeScroll(getProjectTree(code)), BorderLayout.CENTER);
		jContentPane.add(getInfoScroll(), BorderLayout.EAST);
	}

	/**
	 * this method displays the project tree
	 * @throws ConnectException 
	 */
	public DefaultMutableTreeNode getProjectTree(boolean code) throws ConnectException{
		String filePath = fileManipulation.getFilePath(0, null); 

		DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode();
		if(filePath != null){			
			setProjectPath(filePath);

			mainNode = new DefaultMutableTreeNode(getProjectName(filePath));

			TraceabilityAcquisition tProcess = new TraceabilityAcquisition();
			tProcess.connectService();
			if(code){
				tProcess.iterateProjectCodes(filePath, mainNode);
			}else{
				tProcess.iterateProjectFiles(filePath, mainNode);
			}
			tProcess.disconnectService();
		}
		return mainNode;
	}	
	
	
	private String getProjectName(String filePath){
		int index = filePath.lastIndexOf(System.getProperty("file.separator")) + 1;
		return filePath.substring(index, filePath.length());
	}

	//************************ Files Tree ************************//

	/**
	 * This method initializes filesTreeScroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getFilesTreeScroll(DefaultMutableTreeNode node) {
		if (filesTreeScroll == null) {
			filesTreeScroll = new JScrollPane();
			filesTreeScroll.setViewportView(getFilesTree(node));
			filesTreeScroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		return filesTreeScroll;
	}

	/**
	 * This method initializes filesTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getFilesTree(DefaultMutableTreeNode mainNode) {
		if (filesTree == null) {
			filesTree = new JTree(mainNode);	
			filesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			filesTree.addTreeSelectionListener(new TreeSelectionListener(){
				public void valueChanged(TreeSelectionEvent evt) {
					reloadInfoScroll(filesTree.getLastSelectedPathComponent().toString());
				}
			});
		}
		return filesTree;
	}
	
	//************************ Info Scroll ************************//

	/**
	 * This method initializes filesTreeScroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getInfoScroll() {
		if (infoScroll == null) {
			infoScroll = new JScrollPane();
			infoScroll.setViewportView(getInformations(null));
			infoScroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		return infoScroll;
	}
	
	private void reloadInfoScroll(String path){
		infoScroll.setViewportView(getInformations(path));
	}
	
	private JList getInformations(String path){
		infoList = new JList();

		infoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		final String[] infoNames;
		if((path == null) || path.isEmpty()){
			infoNames 	= new String[1];
			infoNames[0] = "                        Select an artifact.                        ";
		}else{
			List<String> infos = new ArrayList<String>();
			//Need
			UNeedManager needManUns = new UNeedManager();
			List<UNeed> lNeedU =  needManUns.getUNeedByArtifact(path);
			for (UItem entity : lNeedU)
				infos.add("Need: "+entity.getId()+" - "+entity.getName());
			
			//Feature
			UFeatureManager featManUns = new UFeatureManager();
			List<UFeature> lFeatureU =  featManUns.getUFeatureByArtifact(path);
			for (UItem entity : lFeatureU)
				infos.add("Feature: "+entity.getId()+" - "+entity.getName());
			
			
			//Use Case
			UUseCaseManager ucManUns = new UUseCaseManager();
			List<UUseCase> lUseCaseU =  ucManUns.getUUseCaseByArtifact(path);
			for (UItem entity : lUseCaseU)
				infos.add("Use Case: "+entity.getId()+" - "+entity.getName());
			
			//Actor
			UActorManager actManUns = new UActorManager();
			List<UActor> lActorU =  actManUns.getUActorByArtifact(path);
			for (UItem entity : lActorU)
				infos.add("Actor: "+entity.getId()+" - "+entity.getName());
			
			//Actor
			UBusinessRuleManager bRuleManUns = new UBusinessRuleManager();
			List<UItem> lBRuleU =  bRuleManUns.getUBusinessRuleByArtifact(path);
			for (UItem entity : lBRuleU)
				infos.add("Business Rule: "+entity.getId()+" - "+entity.getName());
			
			//TestCase
			UTestCaseManager testCaseManUns = new UTestCaseManager();
			List<UTestCase> lTestCaseU =  testCaseManUns.getUTestCaseByArtifact(path);
			for (UItem entity : lTestCaseU)
				infos.add("Test Case: "+entity.getId()+" - "+entity.getName());
			
			//Code
			UCodeManager codeManUns = new UCodeManager();
			List<UCode> lCodeU =  codeManUns.getUCodeByArtifact(path);
			for (UItem entity : lCodeU)
				infos.add("Code: "+entity.getId()+" - "+entity.getName());
			
			if(infos.size() == 0)
				infos.add("No items extracted from this artifact.");
			
			infoNames 	= new String[infos.size()];
			int i =0;
			for (String info : infos) {
				infoNames[i] = info;
				i++;
			}
			
		}
		
		infoList.setModel(new javax.swing.DefaultListModel() {
			private static final long serialVersionUID = -2883572081964929846L;
			String[] strings = infoNames;
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});        

		infoList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				//useCaseListValueChanged(evt);
			}
		});
		
		return infoList;
	}

	private void addInfo(List<String> listInfo, String type, List<UItem> listUItems){
		for (UItem entity : listUItems)
			listInfo.add(type+": "+entity.getId()+" - "+entity.getName());
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}


//Others
	/**
	 * this method displays a popup menu for the selected file from the project tree
	 * @param e
	 * @param mainNode2 the main node (directory) of the project
	 * deprecaded
	 */
	public void displaySelectedTreeFilePopup(MouseEvent e, DefaultMutableTreeNode mainNode2){
		try {				
			if(SwingUtilities.isRightMouseButton(e)){
				int row=filesTree.getRowForLocation(e.getX(),e.getY());
				TreePath selPath = filesTree.getPathForLocation(e.getX(),e.getY());
				String path = "";
				String parentPath = "";
				if(row != -1) {
					if(e.getClickCount() == 1) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) filesTree.getLastSelectedPathComponent();
						DefaultMutableTreeNode auxiliarNode = new DefaultMutableTreeNode();
						if(node.isLeaf()){
							if(node.getParent() == mainNode2){
								path = getProjectPath() + System.getProperty("file.separator") + node.getUserObject();
							}
							else{
								auxiliarNode = node;
								for (int i = 0; i < node.getLevel() - 1; i++) {
									parentPath = auxiliarNode.getParent() + System.getProperty("file.separator") + path;
									auxiliarNode = (DefaultMutableTreeNode) auxiliarNode.getParent();
								}
								path = getProjectPath() + System.getProperty("file.separator") + parentPath + node.getUserObject();
							}
							filesTree.setSelectionPath(selPath);
							//JPopupMenu popup = getPopupMenu(path);
							//popup.show(filesTree,e.getX(),e.getY());
						}
						else{

						}
					}
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

}
