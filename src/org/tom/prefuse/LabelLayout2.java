package org.tom.prefuse;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import prefuse.action.layout.Layout;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;

/**
 * Set label positions. Labels are assumed to be DecoratorItem instances,
 * decorating their respective nodes. The layout simply gets the bounds
 * of the decorated node and assigns the label coordinates to the center
 * of those bounds.
 */
class LabelLayout2 extends Layout {
    public LabelLayout2(String group) {
        super(group);
    }
    public void run(double frac) {
        Iterator iter = m_vis.items(m_group);
        while ( iter.hasNext() ) {
            DecoratorItem decorator = (DecoratorItem)iter.next();
            VisualItem decoratedItem = decorator.getDecoratedItem();
            Rectangle2D bounds = decoratedItem.getBounds();
            
            double x = bounds.getCenterX();
            double y = bounds.getCenterY();

            /* modification to move edge labels more to the arrow head
            double x2 = 0, y2 = 0;
            if (decoratedItem instanceof EdgeItem){
            	VisualItem dest = ((EdgeItem)decoratedItem).getTargetItem(); 
            	x2 = dest.getX();
            	y2 = dest.getY();
            	x = (x + x2) / 2;
            	y = (y + y2) / 2;
            }
            */
            
            setX(decorator, null, x);
            setY(decorator, null, y);
        }
    }
} // end of inner class LabelLayout
