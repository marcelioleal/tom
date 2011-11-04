package org.tom.GUI.maintaining;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ConfigurationPanel {

		private JPanel panel = new JPanel();
		private JTabbedPane configTabs;
		
		public ConfigurationPanel(JPanel panel){
			this.panel = panel;
		}
		
		
		public void getConfigTabs(){
			this.panel.removeAll();
			
			ProjectConfiguration pc = new ProjectConfiguration();
			DatabaseConfiguration dc = new DatabaseConfiguration();
			
			configTabs = new JTabbedPane();
			configTabs.add("Project", pc);
			configTabs.add("Database", dc);
			
			panel.add(configTabs);
		}


	

}
