package org.tom.GUI;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tom.entities.History;
import org.tom.entities.Project;
import org.tom.entities.ProjectStatusEnum;
import org.tom.entities.Baseline;
import org.tom.manager.HistoryManager;
import org.tom.manager.ProjectManager;
import org.tom.manager.BaselineManager;

public class MainInformations extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private JLabel labelName		= new JLabel("Project Name:");
	private JLabel labelNameVal;
	private JLabel labelLastHist	= new JLabel("Last History:");
	private JLabel labelLastHistVal;
	private JLabel labelStatus		= new JLabel("Status:");
	private JLabel labelStatusVal;
	private JLabel labelVersion 	= new JLabel("Active Baseline: ");
	private JLabel labelVersionVal;

	private JButton serviceButton 			= null;

	private Project project;
	private Baseline baseline;
	private History lastHist;
	private ProjectManager projMan = new ProjectManager();
	private BaselineManager versionMan = new BaselineManager();
	private HistoryManager historyMan = new HistoryManager();
	
	
	public MainInformations(){
		
		labelName.setFont(new Font("Serif", Font.PLAIN, 16));
		labelStatus.setFont(new Font("Serif", Font.PLAIN, 16));
		labelVersion.setFont(new Font("Serif", Font.PLAIN, 16));
		labelLastHist.setFont(new Font("Serif", Font.PLAIN, 16));
		
		this.setDataProject();
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING)	
						.addComponent(labelName)
						.addComponent(labelStatus)
						.addComponent(labelVersion)
						//.addComponent(labelLastHist)
						//.addComponent(serviceButton)
				)
				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(labelNameVal)
						.addComponent(labelStatusVal)
						.addComponent(labelVersionVal)
						//.addComponent(labelLastHistVal)
				)
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelName)
						.addComponent(labelNameVal)
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelStatus)
						.addComponent(labelStatusVal)
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelVersion)
						.addComponent(labelVersionVal)
				)
				/*.addGroup(layout.createParallelGroup()
						.addComponent(labelLastHist)
						.addComponent(labelLastHistVal)
				)*/
				.addGroup(layout.createParallelGroup()
						//.addComponent(serviceButton)
				)
		);
	}
	
	private void setDataProject(){ 
		this.project = projMan.getProject();
		this.baseline = versionMan.getActiveBaseline();
		this.lastHist = historyMan.getLastHistory();

		this.labelNameVal = new JLabel(project.getName());
		this.labelNameVal.setToolTipText(project.getDescription());
		this.labelNameVal.setFont(new Font("Serif", Font.BOLD, 16));
		
		
		this.labelStatusVal = new JLabel(project.getStatusDescription());
		if(project.getStatus().equals(ProjectStatusEnum.UNSTRUCTURED.getId()))
			this.labelStatusVal.setForeground(Color.red);
		else
			this.labelStatusVal.setForeground(Color.blue);
		this.labelStatusVal.setFont(new Font("Serif", Font.BOLD, 16));
		
		if(lastHist != null){
			this.labelLastHistVal = new JLabel(lastHist.getOperation()+" \n " +lastHist.getDate()+" "+ lastHist.getTime() );
			this.labelLastHistVal.setToolTipText(lastHist.getDescription());
			this.labelLastHistVal.setFont(new Font("Serif", Font.BOLD, 16));
		}

		this.labelVersionVal = new JLabel(baseline.getId()+" - "+baseline.getPhase());
		this.labelVersionVal.setToolTipText(baseline.getDescription());
		this.labelVersionVal.setFont(new Font("Serif", Font.BOLD, 16));
		
		//setServiceButton();
	}
	
	private void setServiceButton(){
		if(serviceButton == null){
			serviceButton = new JButton("Start Service");
			serviceButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Service serv = new Service();
					try {	
						if(serv.testService()){
							//JOptionPane.showMessageDialog(mainPanel, "This service is started.");
						}else{
							serv.startService();
							//JOptionPane.showMessageDialog(mainPanel, "Service Started.");
						}
					} catch (IOException e1) {
						//JOptionPane.showMessageDialog(this, "Error on OpenOffice Service.");
						e1.printStackTrace();
					}
				}
			});
		}
	}

}
