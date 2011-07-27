package org.tom.GUI.maintaining;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class LinksPanel {


	private JPanel panel = new JPanel();
	private JTabbedPane linksTabs;
	private LinksMaintaining linksMain;
	
	public LinksPanel(JPanel panel){
		this.panel = panel;
	}
	
	
	public void getLinksTabs(){
		linksMain = new LinksMaintaining();
		
		linksTabs = new JTabbedPane();
		linksTabs.add("Links Maintaining", linksMain);
		
		panel.add(linksTabs);
	}


}
