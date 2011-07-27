package org.tom.GUI.maintaining;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

/*
 * Class that perform the database configuration
 * Status: incomplete
 * Version 2.0
 */
public class DBConfiguration extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	/**
	 * This is the default constructor
	 */
	public DBConfiguration() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(548, 303);
		this.setContentPane(getJContentPane());
		this.setTitle("Database Configuration");
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
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
