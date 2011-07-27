package org.tom.GUI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tom.TraceabilityAnalisys;

public class AnalisysGUI {

	
	public void analiseUnstruturedInformations(JPanel jContentPane){
		TraceabilityAnalisys traceAnalisys = new TraceabilityAnalisys();
		traceAnalisys.analiseUnstruturedInformations();

		JPanel panel = new JPanel();
		panel.add(new JLabel("Traceability Model Generated."), BorderLayout.CENTER);
		jContentPane.add(panel, BorderLayout.CENTER);

		System.out.println("Traceability Model Generation completed successfully.");
	}
	
	
}
