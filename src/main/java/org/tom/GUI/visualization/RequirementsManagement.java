package org.tom.GUI.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tom.GUI.traceabilityMatrix.Matrix;
import org.tom.entities.structuredDB.Item;
import org.tom.manager.structured.ItemsManager;
import org.tom.util.FileManipulation;

import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;

public class RequirementsManagement {
	
	private JPanel panel;
	private JPanel requirementsPanel;
	private JPanel functionsPanel;
	private JLabel reqTitleLabel = new JLabel("<html><br><br></html>");
	private JLabel requirementsLabel;
	private String requirements;
	private JPanel evolutionPanel;
	private JPanel testVerificationPanel;
	private JPanel fullTraceabilityPanel;
	private JPanel authorTraceabilityPanel;
	private JButton matrixButton;
	private JButton graphButton;
	private JButton evolutionButton;
	private JButton authorButton;
	private JButton testButton;
	private JLabel label = new JLabel(" ");
	
	ItemsManager im = new ItemsManager();
	List<Item> needs = im.getItemsByType(1);
	List<Item> features = new ArrayList<Item>();
	FileManipulation fileManipulation = new FileManipulation();
	
	
	public RequirementsManagement(JPanel panel) {
		this.panel = panel;
	}
	
		
	private JPanel getRequirementsPanel(){
		if(requirementsPanel == null){
			requirementsPanel = new JPanel();
			requirementsPanel.setLayout(new BorderLayout());
			requirementsPanel.add(reqTitleLabel, BorderLayout.NORTH);
			requirementsPanel.setBorder(BorderFactory.createLineBorder( new Color(238,238,238), 20));
			requirements = getRequirements();
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(BorderFactory.createTitledBorder("Requirements - Need/Features - Actors - UseCases"));
			panel.add(getRequirementsLabel(requirements), BorderLayout.NORTH);
			requirementsPanel.add(panel, BorderLayout.CENTER);
		}
		
		return requirementsPanel;
	}
	
	private JPanel getFunctionsPanel(){
		if(functionsPanel == null){
			functionsPanel = new JPanel();
			functionsPanel.setSize(80, 1000);
			functionsPanel.setLayout(new GridLayout(8,1));
			functionsPanel.add(label);
			functionsPanel.add(getEvolutionPanel());
			functionsPanel.add(getTestVerificationPanel());
			functionsPanel.add(getFullTraceabilityPanel());
			functionsPanel.add(getAuthorTraceabilityPanel());
			functionsPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		}
		return functionsPanel;
	}

	
	public JPanel getRequirementsManagementPanel(){
		panel.setLayout(new BorderLayout());
		panel.add(getRequirementsPanel(), BorderLayout.CENTER);
		panel.add(getFunctionsPanel(), BorderLayout.EAST);
		return panel;
	}
	
	private JLabel getRequirementsLabel(String requirements){
		if(requirementsLabel == null){
			requirementsLabel = new JLabel(requirements);
		}
		return requirementsLabel;
	}
	
	private JPanel getEvolutionPanel(){
		if(evolutionPanel == null){
			evolutionPanel = new JPanel();
			evolutionPanel.setLayout(null);
			
			evolutionPanel = new JPanel();
			evolutionPanel.setLayout(new GridLayout(3,1));
			String evolutedRequirements = "Evoluted requirements: X%";
			JLabel label = new JLabel(evolutedRequirements);
			evolutionPanel.add(label);
			
			JPanel teste = new JPanel();
			teste.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("          ");
			JLabel label2 = new JLabel("          ");
			teste.add(label1, BorderLayout.WEST);
			teste.add(getEvolutionButton(), BorderLayout.CENTER);
			teste.add(label2, BorderLayout.EAST);
			
			evolutionPanel.add(teste);
			
			evolutionPanel.setBorder(BorderFactory.createTitledBorder("Evolution"));
		}
		return evolutionPanel;
	}
	
	
	private JPanel getTestVerificationPanel(){
		if(testVerificationPanel == null){
			testVerificationPanel = new JPanel();
			testVerificationPanel.setLayout(new GridLayout(3,1));
			String tests = "Tests: Y%";
			JLabel label = new JLabel(tests);
			testVerificationPanel.add(label);
			
			JPanel teste = new JPanel();
			teste.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("         ");
			JLabel label2 = new JLabel("         ");
			teste.add(label1, BorderLayout.WEST);
			teste.add(getTestButton(), BorderLayout.CENTER);
			teste.add(label2, BorderLayout.EAST);
			
			testVerificationPanel.add(teste);
			testVerificationPanel.setBorder(BorderFactory.createTitledBorder("Test Verification"));
		}
		return testVerificationPanel;
	}
	
	
	private JPanel getFullTraceabilityPanel(){
		if(fullTraceabilityPanel == null){
			fullTraceabilityPanel = new JPanel();
			fullTraceabilityPanel.setLayout(new FlowLayout());
			fullTraceabilityPanel.add(getMatrixButton());
			fullTraceabilityPanel.add(getGraphButton());
			fullTraceabilityPanel.setBorder(BorderFactory.createTitledBorder("Full Traceability"));
		}
		return fullTraceabilityPanel;
	}
	
	
	private JPanel getAuthorTraceabilityPanel(){
		if(authorTraceabilityPanel == null){
			authorTraceabilityPanel = new JPanel();
			authorTraceabilityPanel.setLayout(new GridLayout(3,1));
			String evolutedRequirements = "  ";
			JLabel label = new JLabel(evolutedRequirements);
			authorTraceabilityPanel.add(label);
			
			JPanel teste = new JPanel();
			teste.setLayout(new BorderLayout());
			JLabel label1 = new JLabel("         ");
			JLabel label2 = new JLabel("         ");
			teste.add(label1, BorderLayout.WEST);
			teste.add(getAuthorButton(), BorderLayout.CENTER);
			teste.add(label2, BorderLayout.EAST);
			
			authorTraceabilityPanel.add(teste);

			authorTraceabilityPanel.setBorder(BorderFactory.createTitledBorder("Author Traceability"));
		}
		return authorTraceabilityPanel;
	}
	
	private JButton getMatrixButton(){
		if(matrixButton == null){
			matrixButton = new JButton("Matrix");
			matrixButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					Matrix matrix = new Matrix(panel);
					matrix.getPanel();
					panel.validate();
				}
			});
		}
		return matrixButton;
	}
	
	private JButton getGraphButton(){
		if(graphButton == null){
			graphButton = new JButton("Graph");
			graphButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					
					FullTraceabilityGraph ftg = new FullTraceabilityGraph();
					//System.out.println(ftg.getXMLInformation());
					
					String xmlStr = ftg.getXMLInformation();
					
					Pattern pat 		= Pattern.compile("(?i)[ÁÍÓÚÉÄÏÖÜËÀÌÒÙÈÃÕÂÎÔÛÊáíóúéäïöüëàìòùèãõâîôûêÇç]*");
			    	Matcher matcher = pat.matcher(xmlStr);
			    	
			    	
			    	
			    	xmlStr = matcher.replaceAll("");
					
			    	System.out.println(xmlStr);
			    	
//					String strNew = "";
//					try{
//						byte[] utf8Bytes = xmlStr.getBytes("UTF8");
//						strNew = new String(utf8Bytes);
//					}catch (Exception e1) {
//						e1.printStackTrace();
//					}
					
					StringBufferInputStream str = new StringBufferInputStream(xmlStr);				 
					//BufferedInputStream bInp = new BufferedInputStream();

					
					
					FullTraceabilityRadialGraphView r = new FullTraceabilityRadialGraphView();
					try {
						panel.add(r.radialGraphView(new GraphMLReader().readGraph(str), "name"));
					} catch (DataIOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					panel.validate();
				}
			});
		}
		return graphButton;
	}
	
	private JButton getEvolutionButton(){
		if(evolutionButton == null){
			evolutionButton = new JButton("View evolution graphs");
			evolutionButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					RequirementsEvolution re = new RequirementsEvolution(panel);
					re.getRequirementsEvolutionPanel();
					panel.validate();
				}
			});
		}
		return evolutionButton;
	}
	
	private JButton getAuthorButton(){
		if(authorButton == null){
			authorButton = new JButton("View authorship graphs");
			authorButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					AuthorshipPanel ap = new AuthorshipPanel(panel);
					ap.getAuthorshipPanel();
					panel.validate();
				}
			});
		}
		return authorButton;
	}
	
	private JButton getTestButton(){
		if(testButton == null){
			testButton = new JButton("View tests graphs");
			testButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return testButton;
	}
	
	
	//************************* *************************//
	
	private String getRequirements(){
		
		StringBuffer content = new StringBuffer();
		content.append("<html>");
		content.append("<br>");
		content.append("<b>Needs / Features / Actors</b><br>");
		for (Item i : needs) {
			content.append(i.getName() + "<br>");
			features = im.getFeaturesByItemId(i.getIdItem());
			for (Item f : features) {
				content.append("&nbsp;&nbsp;&nbsp; -" + 
						f.getName() + "<br>");
				List<Item> actors = im.getActorsByItem(f.getIdItem());
				for (Item a : actors) {
					content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -" + 
							a.getName() + "<br>");
				}
			}
		}
		content.append("<br><br>");
		content.append("</hr>");
		content.append("<br><br>");
		content.append("<b>Casos de Uso</b><br><br>");
		List<Item> ucs = im.getItemsByType(4);		
		for (Item uc : ucs) {
			content.append("<b> -" + uc.getName() + "</b><br>");
			List<Item> actors = im.getActorsByItem(uc.getIdItem());
			for (Item a : actors) {
				content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -" + 
						a.getName() + "<br>");
			}
		}
		content.append("</html>");
		return content.toString();
	}
}