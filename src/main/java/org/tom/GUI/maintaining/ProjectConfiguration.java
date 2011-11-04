package org.tom.GUI.maintaining;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.tom.DAO.ModeloException;
import org.tom.entities.Project;
import org.tom.manager.ProjectManager;

public class ProjectConfiguration  extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel labelName		= new JLabel("Project Name:");
	private JLabel labelDesc		= new JLabel("Project Description:");
	private JLabel labelObs			= new JLabel("Obs:");
	private JLabel labelStatus		= new JLabel("Status:");
	private JLabel labelStatusVal;
	private JLabel labelVersion 	= new JLabel("Version: ");
	private JLabel labelVersionVal;
	private JLabel labelSave 		= new JLabel("");
	
	protected JTextField txtName		= new JTextField();
	
	protected JTextArea txtDescription	= new JTextArea();
	protected JTextArea txtObs			= new JTextArea();
	
	protected JScrollPane scrollPane;
	
	private JButton saveButton 			= null;
	private JButton newVersionButton	= null;
	
	private Project project;
	private ProjectManager projMan = new ProjectManager();;
	
	
	
	public ProjectConfiguration(){
		this.setDataProject();
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(LEADING)	
							.addComponent(labelName)
							.addComponent(labelVersion)
							.addComponent(labelDesc)
							.addComponent(labelObs)
							.addComponent(saveButton)
					)
					.addGroup(layout.createParallelGroup(LEADING)
							.addComponent(txtName)
							.addComponent(labelVersionVal)
							.addComponent(scrollPane)
							.addComponent(txtObs)
							.addComponent(labelSave)
					)
					.addGroup(layout.createParallelGroup(LEADING)
							.addComponent(labelStatus)
							.addComponent(newVersionButton)
					)
					.addGroup(layout.createParallelGroup(LEADING)
							.addComponent(labelStatusVal)
					)
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelName)
						.addComponent(txtName)
						.addComponent(labelStatus)
						.addComponent(labelStatusVal)
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelVersion)
						.addComponent(labelVersionVal)
						.addComponent(newVersionButton)
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelDesc)
						.addComponent(scrollPane)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(labelObs)
						.addComponent(txtObs)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(saveButton)
						.addComponent(labelSave)
				)
		);
	}
	
	private void setDataProject(){ 
		this.project = projMan.getProject();

		this.txtName.setText(project.getName());
		this.txtName.setEditable(true);
		this.txtName.setEnabled(true);
		this.txtName.addActionListener(this);
		
		this.txtDescription.setText(project.getDescription());
		this.txtDescription.setLineWrap(true);
		this.txtDescription.setMaximumSize(new Dimension(400,100));
		this.txtDescription.setEditable(true);
		scrollPane = new JScrollPane(txtDescription);
		
		this.labelStatusVal = new JLabel(project.getStatus());
		System.out.println(project.getStatus());
		
		this.txtObs.setText(project.getObs());
		this.txtObs.setLineWrap(true);
		this.txtObs.setRows(5);
		this.txtObs.setColumns(30);
		this.txtObs.setMaximumSize(new Dimension(400,50));
		//this.txtObs.addAncestorListener(this);
		
		this.labelVersionVal = new JLabel("Versao de Testes");
		
		this.setSaveButton();
		this.setNewVersionButton();
	}
	
	private void setSaveButton(){
		if(saveButton == null){
			saveButton = new JButton("   Save   ");
			saveButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					saveProjectConfiguration(txtName.getText(),txtDescription.getText(),txtObs.getText());
					labelSave.setText("Configuration Saved");
				}
			});
		}
	}
	
	private void setNewVersionButton(){
		if(newVersionButton == null){
			newVersionButton = new JButton("New Version");
			newVersionButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {

				}
			});
		}
	}
	
	private boolean saveProjectConfiguration(String name, String desc, String obs){
		this.project = projMan.getProject();
		this.project.setName(name);
		this.project.setDescription(desc);
		this.project.setObs(obs);
		
		try {
			projMan.persist(project);
		} catch (ModeloException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
    public void actionPerformed(ActionEvent e) {

    }
	
}
