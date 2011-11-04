package org.tom.GUI.traceabilityMatrix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


public class Matrix {

	private static final long serialVersionUID = 1L;
	private JPanel panel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	Object[][] lin = null;
	Object[] col = null;
	ArrayList<String> lines = null;
	MatrixData md = new MatrixData();
	ArrayList<String> newTable = new ArrayList<String>();
	JPanel tablePanel = null;
	MatrixTableRenderer renderer = new MatrixTableRenderer();
	
	//v�ri�veis de controle
	boolean openSpecification = false;
	boolean openModel = false;
	boolean openCode = false;
	
	
	//vari�veis de teste
	ArrayList<String> specificationArtifacts;
	ArrayList<String> modelArtifacts;
	ArrayList<String> codeArtifacts;
	
	//cores linhas
//	Color specificationColor = new Color(230, 230, 250);
//	Color modelColor = new Color(255, 218, 185);
//	Color codeColor = new Color(202, 225, 255);
	
	
	Color specificationColor = new Color(230, 230, 250);
	Color modelColor = new Color(240, 255, 255);
	Color codeColor = new Color(240, 255, 240);

	/**
	 * This is the default constructor
	 */
	public Matrix(JPanel panel) {
		this.panel = panel;
		specificationArtifacts = new ArrayList<String>();
		modelArtifacts = new ArrayList<String>();
		codeArtifacts = new ArrayList<String>();
		
		specificationArtifacts.add("artefato 1");
		specificationArtifacts.add("artefato 2");
		specificationArtifacts.add("artefato 3");
		
		modelArtifacts.add("artefato 4");
		modelArtifacts.add("artefato 5");
		modelArtifacts.add("artefato 6");
		
		codeArtifacts.add("artefato 7");
		codeArtifacts.add("artefato 8");
		codeArtifacts.add("artefato 9");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public void getPanel() {
		panel.setLayout(new BorderLayout());
		lines = new ArrayList<String>();
		lines.add("+Specification");
		lines.add("+Model");
		lines.add("+Code");
		newTable = lines;
			
		panel.add(getJScrollPane(), BorderLayout.CENTER);
	}
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTablePanel() {
		if(tablePanel == null){
			tablePanel = new JPanel();
			tablePanel.setLayout(new BorderLayout());
			JTable table = getJTable(lines);
			tablePanel.add(table, BorderLayout.CENTER);
			tablePanel.setPreferredSize( new Dimension(table.getColumnCount() * 110, table.getRowCount() * 10) );
			tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

		}
		return tablePanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane.setViewportView(getTablePanel());
		}
		return jScrollPane;
	}
	
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable(ArrayList<String> tabelLines) {
		final ArrayList<String> lines = tabelLines;
		lin = md.fillDiagonal(md.getLinesName(lines));
		col = md.getColumns(lin.length);
		if (jTable == null) {
			jTable = new JTable(getMatrixModel(getLin(), getCol()));			
		}
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
		jTable.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				int line = jTable.getSelectedRow();
				getArtefatos(line, lines);
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		
        jTable.setDefaultRenderer(Object.class, renderer);
        for (int k = 0; k < lin.length; k++) {
        	if(k == 0){
        		for (int k2 = 1; k2 < lin[0].length; k2++) {
        			renderer.paintCell(k, k2, specificationColor);
    			}
        	}			
        	else if(k == 1){
        		for (int k2 = 1; k2 < lin[0].length; k2++) {
        			renderer.paintCell(k, k2, modelColor);
    			}
        	}
        	else if(k == 2){
        		for (int k2 = 1; k2 < lin[0].length; k2++) {
        			renderer.paintCell(k, k2, codeColor);
    			}
        	}
		}
        
		
		return jTable;
	}

	
	public DefaultTableModel getMatrixModel(Object[][] matrixLines, Object[] columns){
		DefaultTableModel model = new DefaultTableModel(matrixLines, columns);
		return model;
	}

	public void getArtefatos(int line, ArrayList<String> lines){
		
		String name = lines.get(line);

		if(name.equals("+Specification")) repaintMatrix(1, specificationArtifacts, newTable);
		else if(name.equals("-Specification")) repaintMatrix(2, specificationArtifacts, newTable);
		
		else if(name.equals("+Model"))repaintMatrix(3, modelArtifacts, newTable);
		else if(name.equals("-Model")) repaintMatrix(4, modelArtifacts, newTable);

		else if(name.equals("+Code"))repaintMatrix(5, codeArtifacts, newTable);
		else if(name.equals("-Code")) repaintMatrix(6, codeArtifacts, newTable);
	}
	

	
	public void repaintMatrix(int cod, ArrayList<String> artifacts, ArrayList<String> lines){
		ArrayList<String> newLines = getConteudoMatriz(cod, artifacts, lines);
			
		tablePanel.remove(jTable);
		tablePanel.remove(jTable.getTableHeader());
		jScrollPane.remove(tablePanel);
		lin = md.fillDiagonal(md.getLinesName(newLines));
		col = md.getColumns(lin.length);
		
		jTable = new JTable(getMatrixModel(lin, col));
		jTable.setAutoscrolls(true);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		newTable = newLines;
		final ArrayList<String> novas = newLines;
		jTable.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				int linha = jTable.getSelectedRow();
				getArtefatos(linha, novas);
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		int modelBegin = 1;
		int codeBegin = 2;
		
		if(openSpecification){
			modelBegin += specificationArtifacts.size();
			codeBegin += specificationArtifacts.size();
		}
		if(openModel) codeBegin += modelArtifacts.size();
		
		jTable.setDefaultRenderer(Object.class, renderer);
        
		for (int i = 0; i < modelBegin; i++) {
			for (int j = 1; j < lin[0].length; j++) {
				renderer.paintCell(i, j, specificationColor);
			}
		}
		for (int i = modelBegin; i < codeBegin; i++) {
			for (int j = 1; j < lin[0].length; j++) {
				renderer.paintCell(i, j, modelColor);
			}
		}
		for (int i = codeBegin; i < lin.length; i++) {
			for (int j = 1; j < lin[0].length; j++) {
				renderer.paintCell(i, j, codeColor);
			}
		}
		
		tablePanel.add(jTable, BorderLayout.CENTER);
		
		tablePanel.setPreferredSize( new Dimension(jTable.getColumnCount() * 110, jTable.getRowCount() * 10) );
		tablePanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
		tablePanel.validate();
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setViewportView(tablePanel);
	}
	
	public ArrayList<String> getConteudoMatriz(int cod, ArrayList<String> artifacts, ArrayList<String> lines){
		ArrayList<String> newLines = new ArrayList<String>();
		switch(cod){
		case 1:{
			openSpecification = true;
			for (String l : lines) {
				if(l.equals("+Specification")){
					newLines.add("-Specification");
					for (String s : artifacts) newLines.add("   " + s);
				}
				else newLines.add(l);
			}
			break;
		}
		case 2:{
			openSpecification = false;
			boolean specification = false;
			for (String l : lines) {
				if(l.equals("-Specification")){
					newLines.add("+Specification");				
					specification = true;
				}
				if(l.equals("+Model") || l.equals("-Model")) specification = false;
				if(!specification) newLines.add(l);
			}
			break;
		}
		case 3:{
			openModel = true;
			for (String l : lines) {
				if(l.equals("+Model")){
					newLines.add("-Model");
					for (String s : artifacts) newLines.add("   " + s);
				}
				else newLines.add(l);
			}
			break;
		}
		case 4:{
			openModel = false;
			boolean model = false;
			for (String l : lines) {
				if(l.equals("-Model")){
					newLines.add("+Model");				
					model = true;
				}
				if(l.equals("+Code") || l.equals("-Code")) model = false;
				if(!model) newLines.add(l);
			}
			break;
		}
		case 5:{
			openCode = true;
			for (String l : lines) {
				if(l.equals("+Code")){
					newLines.add("-Code");
					for (String s : artifacts) newLines.add("   " + s);
				}
				else newLines.add(l);
			}
			break;
		}
		case 6:{
			openCode = false;
			for (String l : lines) {
				if(l.equals("-Code")){
					newLines.add("+Code");				
					break;
				}
				else newLines.add(l);
			}
			break;
		}
		}
		
		return newLines;
	}
	
	public static void main(String[] args) {
//		Matrix matrix = new Matrix();
//		
//		matrix.specificationArtifacts = new ArrayList<String>();
//		matrix.modelArtifacts = new ArrayList<String>();
//		matrix.codeArtifacts = new ArrayList<String>();
//		
//		matrix.specificationArtifacts.add("artefato 1");
//		matrix.specificationArtifacts.add("artefato 2");
//		matrix.specificationArtifacts.add("artefato 3");
//		
//		matrix.modelArtifacts.add("artefato 4");
//		matrix.modelArtifacts.add("artefato 5");
//		matrix.modelArtifacts.add("artefato 6");
//		
//		matrix.codeArtifacts.add("artefato 7");
//		matrix.codeArtifacts.add("artefato 8");
//		matrix.codeArtifacts.add("artefato 9");
//		
//		matrix.setVisible(true);
	}

	public Object[][] getLin() {
		return lin;
	}

	public void setLin(String[][] lin) {
		this.lin = lin;
	}

	public Object[] getCol() {
		return col;
	}

	public void setCol(String[] col) {
		this.col = col;
	}

	public void setJTable(JTable table) {
		jTable = table;
	}
}
