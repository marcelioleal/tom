package org.tom.GUI.prefuse;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

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
import prefuse.action.assignment.FontAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.force.DragForce;
import prefuse.util.force.ForceSimulator;
import prefuse.util.force.NBodyForce;
import prefuse.util.force.RungeKuttaIntegrator;
import prefuse.util.force.SpringForce;
import prefuse.visual.VisualItem;

public class ArtifactsGraph extends Display {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4258552306554667927L;
	public static final String GRAPH = "graph";
    public static final String NODES = "graph.nodes";
    public static final String EDGES = "graph.edges";
    List<Item> itemsNodes = new ArrayList<Item>();
    List<GraphEdgesAggregate> edges = new ArrayList<GraphEdgesAggregate>();
	List<Item> items;
	List<Link> links;
    
    public ArtifactsGraph(List<Item> items, List<Link> links) {
        // initialize display and data
        super(new Visualization());
        this.items = items;
		this.links = links;
		
        initDataGroups();
        
        LabelRenderer nodeRenderer = new LabelRenderer("name", "image");
        nodeRenderer.setVerticalAlignment(Constants.BOTTOM);
        nodeRenderer.setHorizontalPadding(0);
        nodeRenderer.setVerticalPadding(0);
        nodeRenderer.setImagePosition(Constants.TOP);
        nodeRenderer.setMaxImageDimensions(100,100);
        
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeRenderer);
        m_vis.setRendererFactory(drf);
         
        ColorAction nText = new ColorAction(NODES, VisualItem.TEXTCOLOR);
        nText.setDefaultColor(ColorLib.gray(100));
        
        
        ColorAction nEdges = new ColorAction(EDGES, VisualItem.STROKECOLOR);
        nEdges.setDefaultColor(ColorLib.gray(100));
        
        // bundle the color actions
        ActionList draw = new ActionList();
        
        draw.add(nText);
        draw.add(new FontAction(NODES, FontLib.getFont("Tahoma",Font.BOLD, 12)));
        draw.add(nEdges);
        
        m_vis.putAction("draw", draw);
        
        
        /*
         * All used parameters values are the default ones and just explicitly set
         * to show the way to go if you want to customize them
         * 
         * One exception is the defaultLength, for whic we choose a larger value 
         * (you might wonder why it is called defaultLength) 
         */
        
        ForceSimulator fsim = new ForceSimulator(new RungeKuttaIntegrator());

	float gravConstant = -1f; 
	float minDistance = -1f;
	float theta = 0.9f;

	float drag = 0.01f; 
	float springCoeff = 1E-4f;  
	float defaultLength = 250f;  //default: 50f

	fsim.addForce(new NBodyForce(gravConstant, minDistance, theta));
	fsim.addForce(new DragForce(drag));
	fsim.addForce(new SpringForce(springCoeff, defaultLength));
	
        ForceDirectedLayout fdl = new ForceDirectedLayout(GRAPH, fsim, true, false);
        
        // now create the main animate routine
        ActionList animate = new ActionList(Activity.INFINITY);
        animate.add(fdl);
        animate.add(new RepaintAction());
        m_vis.putAction("animate", animate);
        
        m_vis.runAfter("draw","animate");
        
        // set up the display
        setSize(500,500);
        pan(250, 250);
        setHighQuality(true);
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new DragControl());
        
        // set things running
        m_vis.run("draw");
        
    }

    
    private void initDataGroups() {
        
	Graph g = new Graph();
	
	g.addColumn("image", String.class);
	g.addColumn("name", String.class);
	g.addColumn("id", String.class);
	Node n;
	String name;
	
	//finding artifact nodes
	for (Item item : items) {
		if(item.getIdTypeItem() == 12) itemsNodes.add(item);
	}
	
	//generating edges
	CreateArtifactEdges ae = new CreateArtifactEdges(items, links, itemsNodes);
	List<ArtifactEdges> e;
	Item teste;
	List<Item> graphItems = new ArrayList<Item>();
	List<Node> graphNodes = new ArrayList<Node>();

	e = ae.getEdges();
	
	for (ArtifactEdges edge : e) {
		teste = edge.getNode();
		if(!graphItems.contains(teste)){
			n = g.addNode();
			name = teste.getName();
			n.setString("name", name);
			n.setString("id", String.valueOf(teste.getIdItem()));
			int point1 = name.lastIndexOf(".");
			if(name.substring(point1 + 1, name.length()).equals("doc")) n.setString("image", "images/specification.png");
			else if(name.substring(point1 + 1, name.length()).equals("xmi")) n.setString("image", "images/model.png");
			else n.setString("image", "images/code.png");
			graphItems.add(teste);
			graphNodes.add(n);
			
			findEdges(edge, graphItems, n, graphNodes, g);
		}
		else{
			int cont = 0;
			for (Node node : graphNodes) {
				if(Integer.parseInt(node.getString("id")) == teste.getIdItem()) {
					break;
				}
				else cont++;
			}
			findEdges(edge, graphItems, g.getNode(cont), graphNodes, g);
		}

		
	}
	
        m_vis.addGraph(GRAPH, g);
    }
    
    public void findEdges(ArtifactEdges edge, List<Item> graphItems, Node n, List<Node> graphNodes, Graph g){
    	Node n2;
		for (Item edges : edge.getRelatedItems()) {
			if(!graphItems.contains(edges)){
				graphItems.add(edges);
				n2 = g.addNode();
				String name = edges.getName();
				n2.setString("name", name);
				n2.setString("id", String.valueOf(edges.getIdItem()));
				int point2 = name.lastIndexOf(".");
				if(name.substring(point2 + 1, name.length()).equals("doc")) n2.setString("image", "images/specification.png");
				else if(name.substring(point2 + 1, name.length()).equals("xmi")) n2.setString("image", "images/model.png");
				else n2.setString("image", "images/code.png");
				graphNodes.add(n2);
				g.addEdge(n, n2);
			}
			
			else{
				int cont = 0;
				for (Node node : graphNodes) {
					if(Integer.parseInt(node.getString("id")) == edges.getIdItem()) {
						break;
					}
					else cont++;
				}
				g.addEdge(n, g.getNode(cont));
			}
		}
    }
    
    
    public static void main(String[] argv) {
        JFrame frame = demo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static JFrame demo() {
    	LinkManager lm = new LinkManager();
    	ItemsManager im = new ItemsManager();
        ArtifactsGraph sag = new ArtifactsGraph(im.getItems(), lm.getLinks());
        JFrame frame = new JFrame("impressing your grand children with text and images as nodes");
        frame.getContentPane().add(sag);
        frame.pack();
        return frame;
    }
    
} 

