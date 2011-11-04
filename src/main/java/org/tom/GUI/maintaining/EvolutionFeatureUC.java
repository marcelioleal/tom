package org.tom.GUI.maintaining;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.tom.DAO.ModeloException;
import org.tom.entities.OperationEnum;
import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.ItemTypeEnum;
import org.tom.entities.structuredDB.Link;
import org.tom.entities.structuredDB.LinkTypeEnum;
import org.tom.manager.HistoryManager;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

public class EvolutionFeatureUC extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox comboFeatures 	= null;
	private JButton traceButton 		= null;

	private JList useCaseList 			= null;
	private JList linksList 			= null;

	private JScrollPane uCListScroll 	= null;
	private JScrollPane historyScroll 	= null;

	//Labels
	private JLabel labelFeature 		= new JLabel("   Feature:");
	private JLabel labelHistoryTitle 	= new JLabel("Evolution Links: ");
	private JLabel labelSave 			= new JLabel(" ");



	private ItemsManager im 			= new ItemsManager();
	private LinkManager lm 				= new LinkManager();
	private HistoryManager hm 				= new HistoryManager();

	private ArrayList<Item> features;
	private ArrayList<Item> useCases;
	private ArrayList<Link> links = new ArrayList<Link>();


	private int selectedFeature;
	private int[] uCIds;
	private int[] linksIds;
	private int[] selectedUseCases;


	public EvolutionFeatureUC(){
	}


	private JList getHistory(Integer idFeature){
		linksList = new JList();
		if(idFeature != null){
			links = (ArrayList<Link>) lm.getLinksByItemAndType(idFeature,LinkTypeEnum.SATISFACTION.getId());
			if(links != null){
				final String[] linksNames 	= new String[links.size()];
				linksIds 					= new int[links.size()];
				int counter = 0;

				for (Link l : links) {
					Item iRel = im.getItemById(l.getIdItemRel());
					if(iRel.getName().length() > 90)
						linksNames[counter] = iRel.getName().substring(0, 90)+"...";
					else
						linksNames[counter] = iRel.getName();
					linksIds[counter] = l.getIdLink().intValue();
					counter++;
				}


				linksList.setModel(new javax.swing.DefaultListModel() {
					private static final long serialVersionUID = -2983572081964929846L;
					String[] strings = linksNames;
					public int getSize() { return strings.length; }
					public Object getElementAt(int i) { return strings[i]; }
				});        
			}
		}
		return linksList;
	}

	private JScrollPane getHistoryScroll(){
		if(historyScroll == null){
			historyScroll = new JScrollPane();
			historyScroll.setViewportView(getHistory(null));
			historyScroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		return historyScroll;
	}

	private JScrollPane reloadHistoryScroll(){
		historyScroll.setViewportView(getHistory(selectedFeature));
		return historyScroll;
	}

	private JButton getTraceButton(){
		if(traceButton == null){
			traceButton = new JButton("    Trace    ");
			traceButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					boolean ret = createLink(getSelectedFeature(), selectedUseCases);
					if(ret)
						labelSave.setText("Link Saved");
				}
			});
		}
		return traceButton;
	}

	private JComboBox getComboFeatures(){	
		if (comboFeatures == null) {
			features = (ArrayList<Item>) im.getItemsByType(ItemTypeEnum.FEATURE.getId());
			String[] featuresName 		= new String[features.size() + 1];
			final int[] featuresId 		= new int[features.size() + 1];

			int counter = 1;
			featuresName[0] = "Select a feature";
			for (Item item : features) {
				if(item.getName().length() > 90)
					featuresName[counter] = item.getName().substring(0,90)+"...";
				else
					featuresName[counter] = item.getName();
				featuresId[counter] = item.getIdItem();
				counter++;
			}

			comboFeatures = new JComboBox(featuresName);

			comboFeatures.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedFeature(featuresId[comboFeatures.getSelectedIndex()]);
					labelSave.setText(" ");
					reloadHistoryScroll();
					reloadUCListScroll();
				}
			});

		}
		return comboFeatures;
	}

	private JScrollPane getUCListScroll(){
		if(uCListScroll == null){
			uCListScroll = new JScrollPane();
			uCListScroll.setViewportView(getUseCaseList());
		}
		return uCListScroll;
	}

	private JScrollPane reloadUCListScroll(){
		uCListScroll.setViewportView(getUseCaseList());
		return uCListScroll;
	}

	private JList getUseCaseList(){
		useCaseList = new JList();

		useCaseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		useCases = new ArrayList<Item>();
		useCases = (ArrayList<Item>) im.getItemsByType(ItemTypeEnum.USECASE.getId());

		final String[] useCasesNames 	= new String[useCases.size()];
		uCIds 							= new int[useCases.size()];
		int counter = 0;

		ArrayList<Link>	assocLinks = (ArrayList<Link>) lm.getLinksByItemAndType(selectedFeature,LinkTypeEnum.SATISFACTION.getId());
		boolean rel;
		for (Item i : useCases) {
			rel = false;
			for (Link link : assocLinks) {
				if(link.getIdItemRel() == i.getIdItem()){
					rel = true;
					break;
				}
			}
			if(rel)
				continue;
			if(i.getName().length() > 120)
				useCasesNames[counter] = i.getName().substring(0, 120);
			else
				useCasesNames[counter] = i.getName();
			uCIds[counter] = i.getIdItem();
			counter++;

			useCaseList.setModel(new javax.swing.DefaultListModel() {
				private static final long serialVersionUID = -2883572081964929846L;
				String[] strings = useCasesNames;
				public int getSize() { return strings.length; }
				public Object getElementAt(int i) { return strings[i]; }
			});        

			useCaseList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent evt) {
					useCaseListValueChanged(evt);
				}
			});
		}
		return useCaseList;
	}

	private void useCaseListValueChanged(ListSelectionEvent evt) {
		if(useCaseList.getSelectedIndices().length > 0){
			int[] useCaseIndices = useCaseList.getSelectedIndices();
			selectedUseCases = new int[useCaseIndices.length];
			for (int j = 0; j < useCaseIndices.length; j++) {
				selectedUseCases[j] = uCIds[useCaseIndices[j]];
			}
		}
		else JOptionPane.showMessageDialog(this, "You need to select at least one use case");
	}

	public void getEvolutionPanel(){
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(labelFeature)
				.addGroup(layout.createParallelGroup(LEADING)
						.addComponent(getComboFeatures())
						.addComponent(getUCListScroll()))
						.addGroup(layout.createParallelGroup(LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(getTraceButton()) 
										.addComponent(labelSave))                
										.addComponent(labelHistoryTitle)
										.addComponent(getHistoryScroll()))
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelFeature)
						.addComponent(getComboFeatures())
						.addComponent(getTraceButton())
						.addComponent(labelSave))
						.addGroup(layout.createParallelGroup(LEADING)
								.addComponent(getUCListScroll())
								.addGroup(layout.createSequentialGroup()
										.addComponent(labelHistoryTitle)
										.addComponent(getHistoryScroll()))
						));

	}

	public boolean createLink(int featureId, int[] selectedUseCases){
		//Nao deixar duplicar a associacao
		if(featureId == 0){ 
			JOptionPane.showMessageDialog(this, "You need to select a feature");
			return false;
		}else if(selectedUseCases.length == 0){ 
			JOptionPane.showMessageDialog(this, "You need to select at least onde Use Case");
			return false;
		}else{ 
			String log = "";
			for (int j = 0; j < selectedUseCases.length; j++) {
				try {
					lm.linkPersist(featureId, selectedUseCases[j], LinkTypeEnum.SATISFACTION.getId(), false, "Created Manually");
					System.out.println("Created "+selectedUseCases.length+" Depencency Link");
					reloadHistoryScroll();
					reloadUCListScroll();
					log += " Feature "+featureId+" with UseCase "+selectedUseCases[j]+" \n"; 
				} catch (ModeloException e) {
					e.printStackTrace();
					return false;
				}
			}
			String description = " Evolution Links: \n"+log;
			String user = null;
			try {
				hm.historyPersist(OperationEnum.MANUAL_LINK_CREATION.getDesc(), description, user);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			return true;
		}
	}


	public int getSelectedFeature() {
		return selectedFeature;
	}


	public void setSelectedFeature(int selectedFeature) {
		this.selectedFeature = selectedFeature;
	}


	public int[] getSelectedUseCases() {
		return selectedUseCases;
	}


	public void setSelectedUseCases(int[] selectedUseCases) {
		this.selectedUseCases = selectedUseCases;
	}

}
