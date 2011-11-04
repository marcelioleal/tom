package org.tom.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.tom.GUI.maintaining.ConfigurationPanel;
import org.tom.GUI.maintaining.EvolutionPanel;
import org.tom.GUI.maintaining.LinksPanel;
import org.tom.GUI.traceabilityMatrix.Matrix;
import org.tom.GUI.visualization.RequirementsEvolution;
import org.tom.GUI.visualization.RequirementsManagement;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;


	private JPanel jContentPane = null;
	private JPanel mainPanel = null;

	private JMenuBar mainMenuBar = null;
	private JMenuItem mItemGenerateTraceModel = null;
	private JMenuItem mItemTraceProject = null;
	private JMenuItem mItemTraceProjectCode = null;
	private JMenuItem mItemEraseUnstructured = null;
	private JMenuItem mItemTraceEvolution = null;
	private JMenuItem mItemMatrix = null;
	private JMenuItem mItemEvolution = null;
	private JMenuItem mItemManagement = null;
	private JMenuItem mItemRelationship = null;
	private JMenuItem mProject = null;
	private JMenuItem mStartService = null;
	private JMenuItem mAbout = null;
	private JMenuItem mHome = null;

	private JMenu menuConfiguration = null;
	private JMenu menuMaintaining = null;
	private JMenu menuAnalisys = null;
	private JMenu menuTrace = null;
	private JMenu menuVisualization = null;
	private JMenu menuHelp = null;

	public MainWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1000, 800);
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("TOM - Software Traceability");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getMainPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}


	private JPanel getMainPanel(){
		if(mainPanel == null){
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			MainInformations mInfoPanel = new MainInformations();
			mainPanel.add(mInfoPanel, BorderLayout.CENTER);

			jContentPane.validate();
		}
		return mainPanel;
	}

	//***************** Menu *******************//

	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getMenuHome());
			mainMenuBar.add(getMenuTrace());
			mainMenuBar.add(getMenuAnalisys());
			mainMenuBar.add(getMenuMaintaining());
			mainMenuBar.add(getMenuVisualization());
			mainMenuBar.add(getMenuConfiguration());
			mainMenuBar.add(getMenuHelp());
		}
		return mainMenuBar;
	}
	
	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuHome() {
		if (mHome == null) {
			mHome = new JMenuItem();
			mHome.setText("Home");
			mHome.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					MainInformations mInfoPanel = new MainInformations();
					jContentPane.add(mInfoPanel, BorderLayout.CENTER);

					jContentPane.validate();
				}
			});
		}
		return mHome;
	}

	/**
	 * This method initializes menuMaintaining
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuMaintaining() {
		if (menuMaintaining == null) {
			menuMaintaining = new JMenu();
			menuMaintaining.setText("Maintaining Items and Links");
			menuMaintaining.add(getMItemRelationship());
			menuMaintaining.add(getMItemTraceEvolution());
		}
		return menuMaintaining;
	}

	/**
	 * This method initializes menuConfiguration
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuConfiguration() {
		if (menuConfiguration == null) {
			menuConfiguration = new JMenu();
			menuConfiguration.setText("Configuration");
			menuConfiguration.add(getMProject());
			menuConfiguration.add(getMStartService());
		}
		return menuConfiguration;
	}

	/**
	 * This method initializes menuHelp
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuHelp() {
		if (menuHelp == null) {
			menuHelp = new JMenu();
			menuHelp.setText("Help");
			menuHelp.add(getMAbout());
		}
		return menuHelp;
	}

	/**
	 * This method initializes menuTrace
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuTrace() {
		if (menuTrace == null) {
			menuTrace = new JMenu();
			menuTrace.setText("Aquisition");
			menuTrace.add(getMItemTraceProject());
			menuTrace.add(getMItemTraceProjectCode());
			menuTrace.add(getMItemEraseUnstructured());
		}
		return menuTrace;
	}
	
	
	/**
	 * This method initializes menuTrace
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuAnalisys() {
		if (menuAnalisys == null) {
			menuAnalisys = new JMenu();
			menuAnalisys.setText("Analisys");
			menuAnalisys.add(getMItemGenerateTraceModel());
		}
		return menuAnalisys;
	}


	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMItemTraceProject() {
		if (mItemTraceProject == null) {
			mItemTraceProject = new JMenuItem();
			mItemTraceProject.setText("Trace Project - General Artifacts");
			mItemTraceProject.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					try{
						AcquisitionGUI tGUI = new AcquisitionGUI();
						tGUI.displayUnstructuredInformations(jContentPane,false);
					} catch (ConnectException e1) {
						JOptionPane.showMessageDialog(mainPanel, "You need start OpenOffice service.");
					}

					jContentPane.validate();
				}
			});
		}
		return mItemTraceProject;
	}
	
	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMItemTraceProjectCode() {
		if (mItemTraceProjectCode == null) {
			mItemTraceProjectCode = new JMenuItem();
			mItemTraceProjectCode.setText("Trace Project - Code");
			mItemTraceProjectCode.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					try{
						AcquisitionGUI tGUI = new AcquisitionGUI();
						tGUI.displayUnstructuredInformations(jContentPane,true);
					} catch (ConnectException e1) {
						JOptionPane.showMessageDialog(mainPanel, "You need start OpenOffice service.");
					}

					jContentPane.validate();
				}
			});
		}
		return mItemTraceProjectCode;
	}

	/**
	 * This method initializes mItemGenerateTraceModel	
	 * 	
	 * @return javax.swing.getMItemGenerateTraceModel	
	 */
	private JMenuItem getMItemEraseUnstructured() {
		if (mItemEraseUnstructured == null) {
			mItemEraseUnstructured = new JMenuItem();
			mItemEraseUnstructured.setText("Erase Unstructured Informations");
			mItemEraseUnstructured.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					
					AcquisitionGUI tGUI = new AcquisitionGUI();
					tGUI.eraseUnstructuredInformations(jContentPane);
					
					jContentPane.validate();
				}
			});
		}
		return mItemEraseUnstructured;
	}	
	/**
	 * This method initializes mItemGenerateTraceModel	
	 * 	
	 * @return javax.swing.getMItemGenerateTraceModel	
	 */
	private JMenuItem getMItemGenerateTraceModel() {
		if (mItemGenerateTraceModel == null) {
			mItemGenerateTraceModel = new JMenuItem();
			mItemGenerateTraceModel.setText("Generate Traceability Model");
			mItemGenerateTraceModel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					AnalisysGUI aGUI = new AnalisysGUI();
					aGUI.analiseUnstruturedInformations(jContentPane);
					
					jContentPane.validate();
				}
			});
		}
		return mItemGenerateTraceModel;
	}

	/**
	 * This method initializes mItemTraceEvolution
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMItemTraceEvolution(){
		if(mItemTraceEvolution == null){
			mItemTraceEvolution = new JMenuItem();
			mItemTraceEvolution.setText("Create Evolution Links");
			mItemTraceEvolution.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					EvolutionPanel evLinks = new EvolutionPanel(jContentPane);
					evLinks.getEvolutionTabs();

					jContentPane.validate();
				}
			});
		}
		return mItemTraceEvolution;
	}
	/**
	 * This method initializes menuVisualization	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuVisualization() {
		if (menuVisualization == null) {
			menuVisualization = new JMenu();
			menuVisualization.setText("Visualization");
			menuVisualization.add(getMItemManagement());
			menuVisualization.add(getMItemMatrix());
			menuVisualization.add(getMItemEvolution());
		}
		return menuVisualization;
	}

	private JMenuItem getMItemManagement(){
		if(mItemManagement == null){
			mItemManagement = new JMenuItem();
			mItemManagement.setText("Requirements Management");
			mItemManagement.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					RequirementsManagement rm = new RequirementsManagement(jContentPane);
					rm.getRequirementsManagementPanel();

					jContentPane.validate();
				}
			});
		}
		return mItemManagement;
	}


	/**
	 * This method initializes mItemMatrix
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMItemMatrix(){
		if(mItemMatrix == null){
			mItemMatrix = new JMenuItem();
			mItemMatrix.setText("Traceability Matrix");
			mItemMatrix.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					Matrix matrix = new Matrix(jContentPane);
					matrix.getPanel();
					jContentPane.validate();
				}
			});
		}
		return mItemMatrix;
	}


	private JMenuItem getMItemEvolution(){
		if(mItemEvolution == null){
			mItemEvolution = new JMenuItem();
			mItemEvolution.setText("Requirements Evolution");
			mItemEvolution.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					RequirementsEvolution re = new RequirementsEvolution(jContentPane);
					re.getRequirementsEvolutionPanel();
					jContentPane.validate();
				}
			});
		}
		return mItemEvolution;
	}


	private JMenuItem getMItemRelationship(){
		if(mItemRelationship == null){
			mItemRelationship = new JMenuItem();
			mItemRelationship.setText("Maintain Links");
			mItemRelationship.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					LinksPanel evLinks = new LinksPanel(jContentPane);
					evLinks.getLinksTabs();

					jContentPane.validate();
				}
			});
		}
		return mItemRelationship;
	}

	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMProject() {
		if (mProject == null) {
			mProject = new JMenuItem();
			mProject.setText("Project And Database");
			mProject.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					ConfigurationPanel evConfig = new ConfigurationPanel(jContentPane);
					evConfig.getConfigTabs();

					jContentPane.validate();
				}
			});
		}
		return mProject;
	}

	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMStartService() {
		if (mStartService == null) {
			mStartService = new JMenuItem();
			mStartService.setText("Start OpenOffice Service");
			mStartService.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					//mainPanel.removeAll();

					Service serv = new Service();
					try {
						if(serv.testService()){
							JOptionPane.showMessageDialog(mainPanel, "This service is started.");
						}else{
							serv.startService();
							JOptionPane.showMessageDialog(mainPanel, "Service Started.");
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(mainPanel, "Error on OpenOffice Service.");
						e1.printStackTrace();
					}

					jContentPane.validate();
				}
			});
		}
		return mStartService;
	}


	/**
	 * This method initializes mItemTraceProject	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMAbout() {
		if (mAbout == null) {
			mAbout = new JMenuItem();
			mAbout.setText("About");
			mAbout.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					mainPanel.removeAll();
					jContentPane.removeAll();
					
					jContentPane.validate();
				}
			});
		}
		return mAbout;
	}
}
