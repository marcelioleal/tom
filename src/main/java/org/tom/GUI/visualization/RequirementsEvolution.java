package org.tom.GUI.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tom.GUI.visualization.prefuse.TreeView;
import org.tom.GUI.visualization.xml.EvolutionTreeXML;
import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.ItemTypeEnum;
import org.tom.manager.BaselineManager;
import org.tom.manager.structured.ItemsManager;


/*
 * Evolution visualization 
 */
public class RequirementsEvolution {

	private JPanel panel 			= null;
	private JPanel controlsPanel 	= null;
	private JPanel graphPanel 		= null;
	private JLabel needLabel 		= new JLabel("Needs: ");
	
	
	private JComboBox comboNeeds = null;
	private JButton viewTracksButton = null;
	
	ArrayList<Item> needs;
	
	Item selectedNeed;
	int selectedNeedId;
	
	public int width, height;
	
	TreeView tree = null;
	
	public RequirementsEvolution(JPanel panel){
		panel.removeAll();
		this.panel = panel;
	}
	
	
	private JComboBox getComboNeeds(){
		if(comboNeeds == null){
			ItemsManager im = new ItemsManager();
			BaselineManager bm = new BaselineManager(); 
			needs = (ArrayList<Item>) im.getItemsByTypeAndBaseline(ItemTypeEnum.NEED.getId(),bm.getActiveBaseline().getIdBaseline());
			String[] needsName = new String[needs.size() + 1];
			needsName[0] = "Select a need";			
			int counter = 1;
			for (Item i : needs) {
				needsName[counter] = i.getName();
				counter++;
			}
			
			comboNeeds = new JComboBox(needsName);
			
			comboNeeds.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedNeed(needs.get(comboNeeds.getSelectedIndex() -1));
					setSelectedNeedId(needs.get(comboNeeds.getSelectedIndex()-1).getIdItem());
				}
			});
		}
		return comboNeeds;
	}
	
	private JButton getViewTracksButton(){
		if(viewTracksButton == null){
			viewTracksButton = new JButton("View Tracks");
			viewTracksButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if ( graphPanel != null ) 
						panel.remove(graphPanel);
					
					EvolutionTreeXML xml = new EvolutionTreeXML();					
					
					BufferedInputStream bInp = new BufferedInputStream(new StringBufferInputStream(xml.createXMLContent(getSelectedNeed())));

					graphPanel = TreeView.demo(bInp, "name");
					panel.add(graphPanel, BorderLayout.CENTER);
					panel.validate();
				}
			});
		}
		return viewTracksButton;
	}
	
	private JPanel getControlsPanel(){
		if(controlsPanel == null){
			controlsPanel = new JPanel();
			controlsPanel.setLayout(new FlowLayout());
			controlsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			controlsPanel.add(needLabel);
			controlsPanel.add(getComboNeeds());
			controlsPanel.add(getViewTracksButton());
		}
		return controlsPanel;
	}
	
	private JPanel getGraphPanel(){
		if(graphPanel == null){
			graphPanel = new JPanel();
			graphPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		}
		
		return graphPanel;
	}
	
	public JPanel getRequirementsEvolutionPanel(){
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(getControlsPanel(), BorderLayout.NORTH);
		panel.validate();
		panel.repaint();
		
		return panel;
	}

	public Item getSelectedNeed() {
		return selectedNeed;
	}


	public void setSelectedNeed(Item selectedNeed) {
		this.selectedNeed = selectedNeed;
	}


	public int getSelectedNeedId() {
		return selectedNeedId;
	}


	public void setSelectedNeedId(int selectedNeedId) {
		this.selectedNeedId = selectedNeedId;
	}
	
}
