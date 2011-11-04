package org.tom.GUI.maintaining;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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

public class LinksMaintaining extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox comboItemType 	= null;
	private JComboBox comboLinkType 	= null;
	private JComboBox comboRelItemType 	= null;
	private JComboBox comboItems 		= null;

	private JButton traceButton 		= null;

	private JList itemsList 			= null;
	private JList linksList 			= null;

	private JScrollPane itemListScroll 	= null;
	private JScrollPane linkScroll 	= null;

	//Labels
	
	private JLabel labelItemType	= new JLabel("   Item Type:");
	private JLabel labelItem		= new JLabel("   Item:");
	private JLabel labelRelItemType	= new JLabel("   Related Item Type:");
	private JLabel labelRelItem		= new JLabel("   Related Item:");
	private JLabel labelLinks 		= new JLabel("Links: ");
	private JLabel labelLinkType 	= new JLabel("Link Types: ");
	private JLabel labelSave 			= new JLabel(" ");



	private ItemsManager im 			= new ItemsManager();
	private LinkManager lm 				= new LinkManager();
	private HistoryManager hm 				= new HistoryManager();

	private ArrayList<Item> items;
	private ArrayList<Link> links = new ArrayList<Link>();


	private Integer selectedRelItemType = null;
	private Integer selectedItemType = null;
	private Integer selectedLinkType = null;
	private Integer selectedItem = null;	

	private int[] itemsIds;
	
	private int[] linksIds;
	private int[] selectedItems;


	public LinksMaintaining(){		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(LEADING)
					.addGroup(layout.createSequentialGroup()	
							.addComponent(labelItemType)
							.addComponent(getComboItemTypes())
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(labelItem)
							.addComponent(getComboItems())
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(labelRelItemType)
							.addComponent(getComboRelItemTypes())
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(labelRelItem)
					)
					.addComponent(getItemsListScroll())
				)
				.addGroup(layout.createParallelGroup(LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(labelLinkType)
								.addComponent(getComboLinkTypes())
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(getTraceButton()) 
								.addComponent(labelSave)
						)
						.addComponent(labelLinks)	
						.addComponent(getLinkScroll())
				)
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelItemType)
						.addComponent(getComboItemTypes())
						.addComponent(labelLinkType)
						.addComponent(getComboLinkTypes())
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelItem)
						.addComponent(getComboItems())
						.addComponent(getTraceButton())
						.addComponent(labelSave)
				)
				.addGroup(layout.createParallelGroup(BASELINE)
						.addComponent(labelRelItemType)
						.addComponent(getComboRelItemTypes())
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(labelRelItem)
						.addComponent(labelLinks)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(getItemsListScroll())
						.addComponent(getLinkScroll())
				)
		);

	}
	
	public void clean(){
		labelSave.setText(" ");
	}
	
	private JComboBox getComboLinkTypes(){	
		if(comboLinkType == null){
			String[] linkTypeName 		= new String[LinkTypeEnum.values().length + 1];
			final int[] linkTypeId 		= new int[LinkTypeEnum.values().length + 1];

			int counter = 1;
			linkTypeName[0] = "Select a Link Item";
			for (LinkTypeEnum linkType : LinkTypeEnum.values()) {
				linkTypeName[counter] = linkType.name();
				linkTypeId[counter] = linkType.getId();
				counter++;
			}

			comboLinkType = new JComboBox(linkTypeName);

			comboLinkType.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedLinkType(linkTypeId[comboLinkType.getSelectedIndex()]);
					reloadItemsListScroll();
					reloadLinkScroll();
					clean();
				}
			});
		}
		return comboLinkType;
	}

	private JComboBox getComboItemTypes(){	
		if(comboItemType == null){
			String[] itemTypeName 		= new String[ItemTypeEnum.values().length + 1];
			final int[] itemTypeId 		= new int[ItemTypeEnum.values().length + 1];

			int counter = 1;
			itemTypeName[0] = "Select a Type Item";
			for (ItemTypeEnum itemType : ItemTypeEnum.values()) {
				itemTypeName[counter] = itemType.name();
				itemTypeId[counter] = itemType.getId();
				counter++;
			}

			comboItemType = new JComboBox(itemTypeName);

			comboItemType.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedItemType(itemTypeId[comboItemType.getSelectedIndex()]);
					setSelectedItem(null);
					reloadComboItems();
					reloadItemsListScroll();
					reloadLinkScroll();
					clean();
				}
			});
		}
		return comboItemType;
	}

	private JComboBox getComboRelItemTypes(){	
		if(comboRelItemType == null){
			String[] itemTypeName 		= new String[ItemTypeEnum.values().length + 1];
			final int[] itemTypeId 		= new int[ItemTypeEnum.values().length + 1];

			int counter = 1;
			itemTypeName[0] = "Select a Type Item for Related Items";
			for (ItemTypeEnum itemType : ItemTypeEnum.values()) {
				itemTypeName[counter] = itemType.name();
				itemTypeId[counter] = itemType.getId();
				counter++;
			}

			comboRelItemType = new JComboBox(itemTypeName);

			comboRelItemType.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setSelectedRelItemType(itemTypeId[comboRelItemType.getSelectedIndex()]);
					reloadItemsListScroll();
					reloadLinkScroll();
					clean();
				}
			});
		}
		return comboRelItemType;
	}

	private JComboBox getComboItems(){	
		if(comboItems == null){
			String[] itemsName 	= new String[1];
			final int[] itemsId	= new int[1];
			itemsName[0] = "Select a Item Type ";

			comboItems = new JComboBox(itemsName);
		}
		return comboItems;
	}
		
	private void reloadComboItems(){	
		String[] itemsName;
		final int[] itemsId;
		if (this.getSelectedItemType() != null) {
			ArrayList<Item> items = (ArrayList<Item>) im.getItemsByType(this.getSelectedItemType());
			itemsName 		= new String[items.size() + 1];
			itemsId	= new int[items.size() + 1];

			int counter = 1;
			itemsName[0] = "Select a Item ";
			for (Item item : items) {
				if(item.getName().length() > 90)
					itemsName[counter] = item.getName().substring(0,90)+"...";
				else
					itemsName[counter] = item.getName();
				itemsId[counter] = item.getIdItem();
				counter++;
			}
		}else{
			itemsName 		= new String[1];
			itemsId	= new int[1];
			itemsName[0] = "Select a Item Type ";
		}
		
		

		comboItems.setModel(new DefaultComboBoxModel(itemsName));
		

		comboItems.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setSelectedItem(itemsId[comboItems.getSelectedIndex()]);
				reloadItemsListScroll();
				reloadLinkScroll();
				clean();
			}
		});
	}
	
	private JList getLinks(){
		linksList = new JList();
		if((getSelectedItem() != null) && (getSelectedLinkType() != null)){
			links = (ArrayList<Link>) lm.getLinksByItemAndType(getSelectedItem(),getSelectedLinkType());
			if(links != null){
				final String[] linksNames 	= new String[links.size()];
				linksIds 					= new int[links.size()];
				int counter = 0;

				for (Link l : links) {
					Item iRel = im.getItemById(l.getIdItemRel());
					if(iRel.getName().length() > 60)
						linksNames[counter] = iRel.getName().substring(0, 60)+"...";
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

	private JScrollPane getLinkScroll(){
		if(linkScroll == null){
			linkScroll = new JScrollPane();
			linkScroll.setViewportView(getLinks());
			linkScroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
		return linkScroll;
	}

	private JScrollPane reloadLinkScroll(){
		linkScroll.setViewportView(getLinks());
		return linkScroll;
	}
	
	private JScrollPane getItemsListScroll(){
		if(itemListScroll == null){
			itemListScroll = new JScrollPane();
			itemListScroll.setViewportView(getItemsList());
		}
		return itemListScroll;
	}
	
	private JScrollPane reloadItemsListScroll(){
		itemListScroll.setViewportView(getItemsList());
		return itemListScroll;
	}
	
	private JList getItemsList(){
		itemsList = new JList();
		itemsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		items = new ArrayList<Item>();
		if(getSelectedRelItemType() != null){
			items = (ArrayList<Item>) im.getItemsByType(getSelectedRelItemType());

			final String[] itemsNames 	= new String[items.size()];
			itemsIds 					= new int[items.size()];
			int counter = 0;
			
			ArrayList<Link>	assocLinks = new ArrayList<Link>();
			if((getSelectedLinkType() != null) && (getSelectedItem() != null))
				assocLinks = (ArrayList<Link>) lm.getLinksByItemAndType(getSelectedItem(),getSelectedLinkType());
	
			boolean rel;
			for (Item i : items) {
				rel = false;
				for (Link link : assocLinks) {
					if(link.getIdItemRel() == i.getIdItem()){
						rel = true;
						break;
					}
				}
				if(rel)
					continue;
				if(i.getName().length() > 90)
					itemsNames[counter] = i.getName().substring(0, 90)+"...";
				else
					itemsNames[counter] = i.getName();
				itemsIds[counter] = i.getIdItem();
				counter++;
	
				itemsList.setModel(new javax.swing.DefaultListModel() {
					private static final long serialVersionUID = -2883572081964929846L;
					String[] strings = itemsNames;
					public int getSize() { return strings.length; }
					public Object getElementAt(int i) { return strings[i]; }
				});        
	
				itemsList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent evt) {
						itemsListValueChanged(evt);
					}
				});
			}
		}
		return itemsList;
	}
	
	private void itemsListValueChanged(ListSelectionEvent evt) {
		if(itemsList.getSelectedIndices().length > 0){
			int[] itemsIndices = itemsList.getSelectedIndices();
			selectedItems = new int[itemsIndices.length];
			for (int j = 0; j < itemsIndices.length; j++) {
				selectedItems[j] = itemsIds[itemsIndices[j]];
			}
		}
		else JOptionPane.showMessageDialog(this, "You need to select at least one use case");
	}


	private JButton getTraceButton(){
		if(traceButton == null){
			traceButton = new JButton("    Trace    ");
			traceButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					boolean ret = createLink();
					if(ret)
						labelSave.setText("Link Saved");
				}
			});
		}
		return traceButton;
	}
	
	public boolean createLink(){
		//#TODO Nao duplicar o link

		if(selectedItem == 0){ 
			JOptionPane.showMessageDialog(this, "You need to select a Item");
			return false;
		}else if(selectedLinkType == 0){ 
			JOptionPane.showMessageDialog(this, "You need to select a Link Type");
			return false;
		}else if(selectedItems.length == 0){ 
			JOptionPane.showMessageDialog(this, "You need to select at least one Related Item");
			return false;
		}else{
			String log = "";
			for (int j = 0; j < selectedItems.length; j++) {
				try {
					lm.linkPersist(selectedItem, selectedItems[j], selectedLinkType, false,"Created Manually");
					reloadLinkScroll();
					reloadItemsListScroll();
					log += " Type "+selectedLinkType+" Item "+selectedItem+" with Item Rel "+selectedItems[j]+ " \n";
				} catch (ModeloException e) {
					e.printStackTrace();
					return false;
				}
				System.out.println("Created "+selectedItems.length+" Links Manually");
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

	public Integer getSelectedItemType() {
		return selectedItemType;
	}

	public void setSelectedItemType(int selectedItemType) {
		this.selectedItemType = selectedItemType;
	}


	public Integer getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Integer selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Integer getSelectedRelItemType() {
		return selectedRelItemType;
	}

	public void setSelectedRelItemType(Integer selectedRelItemType) {
		this.selectedRelItemType = selectedRelItemType;
	}


	
	public Integer getSelectedLinkType() {
		return selectedLinkType;
	}


	public void setSelectedLinkType(Integer selectedLinkType) {
		this.selectedLinkType = selectedLinkType;
	}


	public int[] getSelectedItems() {
		return selectedItems;
	}


	public void setSelectedItems(int[] selectedItems) {
		this.selectedItems = selectedItems;
	}

}