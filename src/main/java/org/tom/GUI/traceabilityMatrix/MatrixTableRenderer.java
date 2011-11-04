package org.tom.GUI.traceabilityMatrix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatrixTableRenderer extends DefaultTableCellRenderer{
	
	private Map _colors = new HashMap();  
	
	private Point _tempCel = new Point(0, 0);  

    private Color _defaultBG;  
    
    public MatrixTableRenderer(){
    	this._defaultBG = getBackground();  
    }
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
   
		Component c = super.getTableCellRendererComponent(  
				table,  
				value,  
				isSelected,  
				hasFocus,  
				row,  
				column);  
   
		this._tempCel.x = row;  
        this._tempCel.y = column;  
        if (this._colors.containsKey(this._tempCel)) {  
            c.setBackground((Color) this._colors.get(this._tempCel));  
        } else {  
            c.setBackground(this._defaultBG);  
        }  
        
        return c;
	}  
	
    public void paintCell(int row, int col, Color cor) {  
        this._colors.put(new Point(row, col), cor);  
    }  

}
