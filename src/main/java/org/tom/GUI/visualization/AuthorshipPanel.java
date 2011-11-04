package org.tom.GUI.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tom.entities.structuredDB.Collaboration;
import org.tom.entities.structuredDB.Item;
import org.tom.manager.structured.CollaborationManager;
import org.tom.manager.structured.ItemsManager;
import org.tom.util.FileManipulation;

import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;

public class AuthorshipPanel {

	private JPanel panel;
	private JPanel graphPanel;
	private JPanel controlPanel;
	private JPanel byItemPanel;
	private JPanel byAuthorPanel;
	private JPanel fullAuthorshipPanel;
	private JPanel socialNetworkPanel;
	private JComboBox comboItems;
	private JComboBox comboAuthors;
	private JButton byItemGraphButton;
	private JButton byAuthorGraphButton;
	private JButton fullAuthorshipButton;
	private JButton socialNetworkButton;
	
	private ItemsManager im = new ItemsManager();
	private List<Item> items = im.getNonArtifactItems();
	private CollaborationManager am = new CollaborationManager();
	private List<Collaboration> authors = am.getCollaborations();
	
	private int selectedItemId;
	private int selectedAuthorId;
	private FileManipulation fileManipulation = new FileManipulation();


	public AuthorshipPanel(JPanel panel){
		this.panel = panel; 
	}
	
	
	public JPanel getAuthorshipPanel(){
		panel.setLayout(new BorderLayout());
		panel.add(getGraphPanel(), BorderLayout.CENTER);
		panel.add(getControlPanel(), BorderLayout.EAST);
		return panel;
	}
	
	
	public JPanel getGraphPanel(){
		if(graphPanel == null){
			graphPanel = new JPanel();
			graphPanel.setLayout(new BorderLayout());
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(new Color(184,207,229), 1));
			graphPanel.add(panel, BorderLayout.CENTER);
			graphPanel.setBorder(BorderFactory.createLineBorder( new Color(238,238,238), 20));
		}
		return graphPanel;
	}

	
	public JPanel getControlPanel(){
		if(controlPanel == null){
			controlPanel = new JPanel();
			controlPanel.setLayout(new GridLayout(7,1));
			JLabel label = new JLabel("  ");
			controlPanel.add(label);
			controlPanel.add(getByItemPanel());
			controlPanel.add(getByAuthorPanel());
			controlPanel.add(getFullAuthorshipPanel());
			controlPanel.add(getSocialNetworkPanel());
			JLabel label2 = new JLabel("  ");
			controlPanel.add(label2);
		}
		return controlPanel;
	}
	
	
	private JPanel getByItemPanel(){
		if(byItemPanel == null){
			byItemPanel = new JPanel();
			byItemPanel.setLayout(new GridLayout(3,1));
			
			JPanel panelCombo = new JPanel();
			panelCombo.setLayout(new FlowLayout());
			JLabel label = new JLabel("Items: ");
			panelCombo.add(label);
			panelCombo.add(getComboItems());
			
			byItemPanel.add(panelCombo);
			JLabel label3 = new JLabel("  ");
			byItemPanel.add(label3);
			
			JPanel panelButton = new JPanel();
			panelButton.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("           ");
			JLabel label2 = new JLabel("           ");
			panelButton.add(label1, BorderLayout.WEST);
			panelButton.add(getByItemGraphButton(), BorderLayout.CENTER);
			panelButton.add(label2, BorderLayout.EAST);
			
			byItemPanel.add(panelButton);
			byItemPanel.setBorder(BorderFactory.createTitledBorder("By Item"));
		}
		return byItemPanel;
	}
	
	
	private JPanel getByAuthorPanel(){
		if(byAuthorPanel == null){
			byAuthorPanel = new JPanel();
			byAuthorPanel.setLayout(new GridLayout(3,1));
			
			JPanel panelCombo = new JPanel();
			panelCombo.setLayout(new FlowLayout());
			JLabel label = new JLabel("Authors: ");
			panelCombo.add(label);
			panelCombo.add(getComboAuthors());
			
			byAuthorPanel.add(panelCombo);
			JLabel label3 = new JLabel("  ");
			byAuthorPanel.add(label3);
			
			JPanel panelButton = new JPanel();
			panelButton.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("           ");
			JLabel label2 = new JLabel("           ");
			panelButton.add(label1, BorderLayout.WEST);
			panelButton.add(getByAuthorGraphButton(), BorderLayout.CENTER);
			panelButton.add(label2, BorderLayout.EAST);
			
			byAuthorPanel.add(panelButton);
			byAuthorPanel.setBorder(BorderFactory.createTitledBorder("By Author"));
		}
		return byAuthorPanel;
	}
	
	
	private JPanel getFullAuthorshipPanel(){
		if(fullAuthorshipPanel == null){
			fullAuthorshipPanel = new JPanel();
			fullAuthorshipPanel.setLayout(new GridLayout(3,1));
			String evolutedRequirements = "  ";
			JLabel label = new JLabel(evolutedRequirements);
			fullAuthorshipPanel.add(label);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("           ");
			JLabel label2 = new JLabel("           ");
			panel.add(label1, BorderLayout.WEST);
			panel.add(getFullAuthorshipButton(), BorderLayout.CENTER);
			panel.add(label2, BorderLayout.EAST);
			
			fullAuthorshipPanel.add(panel);

			fullAuthorshipPanel.setBorder(BorderFactory.createTitledBorder("Full Authorship Graph"));
		}
		return fullAuthorshipPanel;
	}
	
	
	private JPanel getSocialNetworkPanel(){
		if(socialNetworkPanel == null){
			socialNetworkPanel = new JPanel();
			socialNetworkPanel.setLayout(new GridLayout(3,1));
			String evolutedRequirements = "  ";
			JLabel label = new JLabel(evolutedRequirements);
			socialNetworkPanel.add(label);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("           ");
			JLabel label2 = new JLabel("           ");
			panel.add(label1, BorderLayout.WEST);
			panel.add(getSocialNetworkButton(), BorderLayout.CENTER);
			panel.add(label2, BorderLayout.EAST);
			
			socialNetworkPanel.add(panel);

			socialNetworkPanel.setBorder(BorderFactory.createTitledBorder("Social Network"));
		}
		return socialNetworkPanel;
	}
	
	
	private JComboBox getComboItems(){
		if(comboItems == null){
			String[] itemsNames = new String[items.size()+1];
			itemsNames[0] = "Select an item";
			int counter = 1;
			for (Item i : items) {
				itemsNames[counter] = i.getName();
				counter++;
			}
			comboItems = new JComboBox(itemsNames);
			comboItems.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedItemId(comboItems.getSelectedIndex() -1);
				}
			});
		}
		return comboItems;
	}
	
	
	private JComboBox getComboAuthors(){
		if(comboAuthors == null){
			String[] authorsNames = new String[authors.size()+1];
			authorsNames[0] = "Select an author";
			int counter = 1;
			for (Collaboration a : authors) {
				authorsNames[counter] = a.getLogin();
				counter++;
			}
			comboAuthors = new JComboBox(authorsNames);
			comboAuthors.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedAuthorId(comboAuthors.getSelectedIndex() -1);
				}
			});
		}
		return comboAuthors;
	}
	
	
	private JButton getByItemGraphButton(){
		if(byItemGraphButton == null){
			byItemGraphButton = new JButton("View graph");
			byItemGraphButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					graphPanel.removeAll();
					//List<Item> artifacts = im.getArtifactsByItem(items.get(getSelectedItemId()).getIdItem());
					List<Item> artifacts = new ArrayList<Item>();
					List<Collaboration> authors = new ArrayList<Collaboration>();
					List<Collaboration> aux;
					for (Item i : artifacts) {
						aux = am.getCollaborationByArtifact(i.getIdItem());
						for (Collaboration a : aux) {
							authors.add(a);
						}
					}
					AuthorshipByItemGraphXML abig = new AuthorshipByItemGraphXML(items.get(getSelectedItemId()), authors);
					fileManipulation.writeFile(new File("authorshipByItemGraph.xml"), abig.createXMLContent());
					AuthorshipRadialGraphView r = new AuthorshipRadialGraphView();
					try {
						graphPanel.add(r.radialGraphView(new GraphMLReader().readGraph("authorshipByItemGraph.xml"), "name"));
						panel.validate();
					} catch (DataIOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
			});
		}
		return byItemGraphButton;
	}
	
	
	private JButton getByAuthorGraphButton(){
		if(byAuthorGraphButton == null){
			byAuthorGraphButton = new JButton("View graph");
			byAuthorGraphButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					graphPanel.removeAll();
					//List<Item> artifacts = im.getArtifactsByAuthor(authors.get(getSelectedAuthorId()).getIdentifier());
					List<Item> artifacts = new ArrayList<Item>();
					List<Item> items = new ArrayList<Item>();
					List<Item> aux;
					for (Item i : artifacts) {
						aux = im.getItemsByArtifact(i.getIdItem());
						for (Item it : aux) {
							items.add(it);
						}
					}
					AuthorshipByAuthorGraphXML abag = new AuthorshipByAuthorGraphXML(authors.get(getSelectedAuthorId()), items);
					fileManipulation.writeFile(new File("authorshipByAuthorGraph.xml"), abag.createXMLContent());
					AuthorshipRadialGraphView r = new AuthorshipRadialGraphView();
					try {
						graphPanel.add(r.radialGraphView(new GraphMLReader().readGraph("authorshipByAuthorGraph.xml"), "name"));
						panel.validate();
					} catch (DataIOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
			});
		}
		return byAuthorGraphButton;
	}
	
	
	private JButton getFullAuthorshipButton(){
		if(fullAuthorshipButton == null){
			fullAuthorshipButton = new JButton("View graph");
			fullAuthorshipButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					graphPanel.removeAll();
					
					List<Collaboration> aux;
					List<Collaboration> authors = new ArrayList<Collaboration>();
					List<String> aut = new ArrayList<String>();
					
					for (Item i : items) {
						aux = am.getCollaborationByItem(i.getIdItem());
						for (Collaboration a : aux) {
							if(!aut.contains(a.getLogin())){
								authors.add(a);
								aut.add(a.getLogin());
							}
						}
					}
					AuthorshipFullGraphXML xml = new AuthorshipFullGraphXML(items, authors, createEdges());
					fileManipulation.writeFile(new File("authorshipFullGraph.xml"), xml.createXMLContent());
					AuthorshipFullGraphView r = new AuthorshipFullGraphView();
					try {
						graphPanel.add(r.radialGraphView(new GraphMLReader().readGraph("authorshipFullGraph.xml"), "name"));
						panel.validate();
					} catch (DataIOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});
		}
		return fullAuthorshipButton;
	}
	
	
	private JButton getSocialNetworkButton(){
		if(socialNetworkButton == null){
			socialNetworkButton = new JButton("View social network");
			socialNetworkButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return socialNetworkButton;
	}

	
	public ArrayList<FullTraceabilityGraphEdges> createEdges(){
		ArrayList<FullTraceabilityGraphEdges> edges = new ArrayList<FullTraceabilityGraphEdges>();
		FullTraceabilityGraphEdges edge;
		ArrayList<String> ed = new ArrayList<String>();
		List<Item> aux;
		List<Collaboration> aux2;
		int cont = 1;
		int itemsSize = items.size();
		
		for (Item i : items) {
			aux = im.getItemsByRelatedItems(i.getIdItem());
			for (Item it : aux) {
				if(it.getIdTypeItem() < 7){
					int position = items.indexOf(it) + 1;
					if((cont != position) && !ed.contains(cont + "," + position) && !ed.contains(position + "," + cont)){
						edge = new FullTraceabilityGraphEdges();
						edge.setSource(cont);
						edge.setTarget(position);
						edges.add(edge);
						ed.add(cont + "," + position);
//						System.out.println("escreveu: " + cont + "," + position);
//						System.out.println("segundo elemento: " + items.get(position).getName());
					}
				}
			}
			
			aux2 = am.getCollaborationByItem(i.getIdItem());
			for (Collaboration a : aux2) {
				int position = authors.indexOf(a) +1 + itemsSize;
				if((cont != position) && !ed.contains(cont + "," + position) && !ed.contains(position + "," + cont)){
					edge = new FullTraceabilityGraphEdges();
					edge.setSource(cont);
					edge.setTarget(position);
					edges.add(edge);
					ed.add(cont + "," + position);
//					System.out.println("escreveu: " + cont + "," + position);
				}				
			}
			cont++;
		}
		
		
		return edges;
	}
	
	
	public int getSelectedItemId() {
		return selectedItemId;
	}


	public void setSelectedItemId(int selectedItemId) {
		this.selectedItemId = selectedItemId;
	}


	public int getSelectedAuthorId() {
		return selectedAuthorId;
	}


	public void setSelectedAuthorId(int selectedAuthorId) {
		this.selectedAuthorId = selectedAuthorId;
	}
	
}
















