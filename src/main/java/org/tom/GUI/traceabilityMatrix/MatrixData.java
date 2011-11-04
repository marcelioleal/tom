package org.tom.GUI.traceabilityMatrix;

import java.util.ArrayList;

import javax.swing.JTable;

public class MatrixData {
	
	public Object[] getColumns(int columnsNumber){
//		int numColunas = col.size();
		Object[] columns = new Object[columnsNumber + 1];
		columns[0] = " "; 
		for (int i = 0; i < columnsNumber; i++) columns[i+1] =  String.valueOf(i+1);//col.get(i) + "   " + (i+1);
		return columns;
	}
	
	public Object[][] getLinesName(ArrayList<String> lin){
		Object[][] lines = new String[lin.size()][lin.size() + 1];
		for(int i = 0; i < lin.size(); i++){
    		for(int j = 0; j < lin.size() + 1; j++){
    			if(j == 0) lines[i][j] = (i+1) + "   " + lin.get(i);    
    		}
    	}
		
		return lines;
	}
	
	public Object[][] fillDiagonal(Object[][] lines){
		Object[][] matrix = new Object[lines.length][lines[0].length];
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[0].length; j++) {
				if(j == (i+1)) matrix[i][j] = "   .";
				else matrix[i][j] = lines[i][j];
			}
		}
		return matrix;
	}
	
	public JTable insertElement(int lin, int col, JTable lines){
		JTable table = lines;
		lines.setValueAt("X", lin, col);
		return table;
//		String[][] matriz = new String[linhas.length][linhas[0].length];
//		for (int i = 0; i < linhas.length; i++) {
//			for (int j = 0; j < linhas[0].length; j++) {
//				if((i == lin - 1) && (j == col)) matriz[i][j] = "x";
//				else matriz[i][j] = linhas[i][j];
//			}			
//		}
//		return matriz;
	}
	
	public String getElement(int pos, String[][] lines){
		String element = null;
		element = lines[pos][0];				
		return element;
	}

}
