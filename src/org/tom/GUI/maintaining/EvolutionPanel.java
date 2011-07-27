package org.tom.GUI.maintaining;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;



/*
 * Classe that create the panels that allows the evolution links between the Items 
 */
public class EvolutionPanel {

	private JPanel panel = new JPanel();
	private JTabbedPane evolutionTabs;
	private EvolutionFeatureUC featureUc;
	
	public EvolutionPanel(JPanel panel){
		this.panel = panel;
	}
	
	
	public void getEvolutionTabs(){
		featureUc = new EvolutionFeatureUC();
		featureUc.getEvolutionPanel();
		
		evolutionTabs = new JTabbedPane();
		evolutionTabs.add("Feature - Use Case", featureUc);
		
		panel.add(evolutionTabs);
	}
	
}
