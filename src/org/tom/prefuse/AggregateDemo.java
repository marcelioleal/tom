/**
 * Copyright (c) 2004-2006 Regents of the University of California.
 * See "license-prefuse.txt" for licensing terms.
 */
package org.tom.prefuse;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.tom.GUI.prefuse.GraphEdgesAggregate;
import org.tom.entities.structuredDB.Item;
import org.tom.entities.structuredDB.Link;
import org.tom.manager.structured.ItemsManager;
import org.tom.manager.structured.LinkManager;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.PolygonRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.HoverPredicate;
import prefuse.visual.expression.InGroupPredicate;


/**
 * Demo application showcasing the use of AggregateItems to
 * visualize groupings of nodes with in a graph visualization.
 * 
 * This class uses the AggregateLayout class to compute bounding
 * polygons for each aggregate and the AggregateDragControl to
 * enable drags of both nodes and node aggregates.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class AggregateDemo extends Display {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2273502138820519265L;
	public static final String GRAPH = "graph";
    public static final String NODES = "graph.nodes";
    public static final String EDGES = "graph.edges";
    public static final String AGGR = "aggregates";
    public static final String NODE_DECORATORS = "nodeDeco";
    public static final String AGGR_DECORATORS = "aggrDeco";
    public static final String EDGE_DECORATORS = "edgeDeco";
    
    private static final Schema DECORATOR_SCHEMA = PrefuseLib.getVisualItemSchema(); 
    static { 
    	DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false); 
    	DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(128)); 
    	DECORATOR_SCHEMA.setDefault(VisualItem.FONT, FontLib.getFont("Tahoma",12));
    }
    
    public AggregateDemo() {
        // initialize display and data
        super(new Visualization());
        initDataGroups();
        
        // set up the renderers
        // draw the nodes as basic shapes
        Renderer nodeR = new ShapeRenderer(30);
        
        // draw aggregates as polygons with curved edges
        Renderer polyR = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
        ((PolygonRenderer)polyR).setCurveSlack(0.15f);
        
        LabelRenderer labelR = new LabelRenderer("label");
        labelR.setRoundedCorner(8, 8);
        
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeR);
        drf.add("ingroup('aggregates')", polyR);
        drf.add("ingroup('aggregates')",labelR);
        drf.add(new InGroupPredicate(NODE_DECORATORS), new LabelRenderer(VisualItem.LABEL));
        drf.add(new InGroupPredicate(AGGR_DECORATORS), new LabelRenderer("name"));
        m_vis.setRendererFactory(drf);
        
//        ((LabelRenderer)drf.getDefaultRenderer()).setTextField("label");
        
        // adding decorators, one group for the nodes, one for the edges and one
        // for the aggregates
        DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(0));
        m_vis.addDecorators(EDGE_DECORATORS, EDGES, DECORATOR_SCHEMA);
        
        DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(128));
        m_vis.addDecorators(NODE_DECORATORS, NODES, DECORATOR_SCHEMA);
        
        // the HoverPredicate makes this group of decorators to appear only on mouseOver
        DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, ColorLib.gray(255, 128));
        DECORATOR_SCHEMA.setDefault(VisualItem.FONT, FontLib.getFont("Tahoma", Font.BOLD, 20));
        m_vis.addDecorators(AGGR_DECORATORS, AGGR, new HoverPredicate(), DECORATOR_SCHEMA);
        
        // set up the visual operators
        // first set up all the color actions
        ColorAction nStroke = new ColorAction(NODES, VisualItem.STROKECOLOR);
        nStroke.setDefaultColor(ColorLib.gray(100));
        nStroke.add("_hover", ColorLib.gray(50));
        
        ColorAction nFill = new ColorAction(NODES, VisualItem.FILLCOLOR);
//        nFill.add(VisualItem.HIGHLIGHT,ColorLib.rgb(255,255,0));
        nFill.setDefaultColor(ColorLib.gray(255));
        nFill.add("_hover", ColorLib.gray(200));
        
        ColorAction nEdges = new ColorAction(EDGES, VisualItem.STROKECOLOR);
        nEdges.setDefaultColor(ColorLib.gray(100));
        
        ColorAction aStroke = new ColorAction(AGGR, VisualItem.STROKECOLOR);
        aStroke.setDefaultColor(ColorLib.gray(200));
        aStroke.add("_hover", ColorLib.rgb(255,100,100));
        
        int[] palette = new int[] {
            ColorLib.rgba(255,200,200,150),
            ColorLib.rgba(200,255,200,150),
            ColorLib.rgba(200,200,255,150)
        };
        ColorAction aFill = new DataColorAction(AGGR, "id",
                Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        
        // bundle the color actions
        ActionList colors = new ActionList();
        colors.add(nStroke);
        colors.add(nFill);
        colors.add(nEdges);
        colors.add(aStroke);
        colors.add(aFill);
        
        // now create the main layout routine
        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(colors);
        ForceDirectedLayout dLayout = new ForceDirectedLayout(GRAPH, true,false);
//        dLayout.
        layout.add(dLayout);
        layout.add(new AggregateLayout2(AGGR));
        layout.add(new LabelLayout2(EDGE_DECORATORS));
        layout.add(new LabelLayout2(NODE_DECORATORS));
        layout.add(new LabelLayout2(AGGR_DECORATORS));
        layout.add(new RepaintAction());
        m_vis.putAction("layout", layout);

        
        // set up the display
        setSize(500,500);
        pan(250, 250);
        setHighQuality(true);
        addControlListener(new AggregateDragControl2());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new NeighborHighlightControl());
        
        // set things running
        m_vis.run("layout");
    }
    
    LinkManager lm = new LinkManager();
	ItemsManager im = new ItemsManager();
	List<GraphEdgesAggregate> edges = new ArrayList<GraphEdgesAggregate>();
	List<Node> itemsNodes = new ArrayList<Node>();
	
	public void findEdges(Node node){
		GraphEdgesAggregate edge;
    	List<Link> links = lm.getLinks();
		for (Link link : links) {
			if(String.valueOf(link.getIdItem()).equals(node.getString("id"))){
				edge = new GraphEdgesAggregate();
				edge.setNode(node);
				for (Node relatedNode : itemsNodes) {
		    		if(relatedNode.getString("id").equals(String.valueOf(link.getIdItemRel()))){
		    			edge.setRelatedNode(relatedNode);
		    			edges.add(edge);
		    		}
				}
			}
		}
	}

	
    private void initDataGroups() {
        // create sample graph
        // 9 nodes broken up into 3 interconnected cliques
    	Graph g = new Graph();
    	g.addColumn(VisualItem.LABEL, String.class);
    	g.addColumn("id", String.class);
    	g.addColumn("group", String.class);
    	
    	List<Item> items = im.getItems();
    	
    	Node n;
    	
    	for (int i = 0; i < items.size(); i++) {
			n = g.addNode();
			
			n.setString(VisualItem.LABEL, items.get(i).getName());
			n.setString("id", ""+items.get(i).getIdItem());
			n.setString("group", ""+items.get(i).getIdTypeItem());
			itemsNodes.add(n);
		}
    	
    	for (Node node : itemsNodes) {
    		findEdges(node);
		}
    	
    	for (GraphEdgesAggregate edge : edges) {
			g.addEdge(edge.getNode(), edge.getRelatedNode());
		}
    	
        // add visual data groups
        VisualGraph vg = m_vis.addGraph(GRAPH, g);
        m_vis.setInteractive(EDGES, null, false);
        m_vis.setValue(NODES, null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));
        
        AggregateTable at = m_vis.addAggregates(AGGR);
        at.addColumn(VisualItem.POLYGON, float[].class);
        at.addColumn("id", int.class);
        at.addColumn("name",String.class);
        
        // add nodes to aggregates
        // create an aggregate for each 3-clique of nodes
        AggregateItem aitem1 = (AggregateItem)at.addItem();
        aitem1.setString("name", "Specification");
        
        AggregateItem aitem2 = (AggregateItem)at.addItem();
        aitem2.setString("name", "Model");
        
        AggregateItem aitem3 = (AggregateItem)at.addItem();
        aitem3.setString("name", "SourceCode");
        
        Iterator nodes = vg.nodes();
        for ( int j=0; j < itemsNodes.size(); ++j ) {
        	Node n2 = (Node) nodes.next();
        	if(n2.getString("group").equals("3") || n2.getString("group").equals("4") || 
        			n2.getString("group").equals("5") || n2.getString("group").equals("6")){
        		VisualItem item = (VisualItem)n2;
                aitem2.addItem(item);
        	}
        	else{
        		VisualItem item = (VisualItem)n2;
                aitem1.addItem(item);
        	}
        }
    }
    
    public static void main(String[] argv) {
        JFrame frame = demo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static JFrame demo() {
        AggregateDemo ad = new AggregateDemo();
        JFrame frame = new JFrame("p r e f u s e  |  a g g r e g a t e d");
        frame.getContentPane().add(ad);
        frame.pack();
        return frame;
    }
    
} // end of class AggregateDemo

