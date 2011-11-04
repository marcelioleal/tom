package org.tom.GUI.prefuse;

import javax.swing.JPanel;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.force.DragForce;
import prefuse.util.force.ForceSimulator;
import prefuse.util.force.NBodyForce;
import prefuse.util.force.RungeKuttaIntegrator;
import prefuse.util.force.SpringForce;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualItem;

public class ItemsGraph{
	
	public static final String GRAPH = "graph";
	private JPanel mainPanel;
	JSearchPanel searchPanel;


	public ItemsGraph(JPanel mainPanel){
    	setMainPanel(mainPanel);
    }
    
      public Display generateGraph(){  
        // -- 1. load the data ------------------------------------------------
        
        // load the socialnet.xml file. it is assumed that the file can be
        // found at the root of the java classpath
        Graph graph = null;
        try {
            graph = new GraphMLReader().readGraph("/home/mayara/teste.xml");//Universidade/projetoUIMA/implementacao/teste.xml");//socialnet.xml");
        } catch ( DataIOException e ) {
            e.printStackTrace();
            System.err.println("Error loading graph. Exiting...");
            System.exit(1);
        }
        
        
        // -- 2. the visualization --------------------------------------------
        
        // add the graph to the visualization as the data group "graph"
        // nodes and edges are accessible as "graph.nodes" and "graph.edges"
        final Visualization vis = new Visualization();
        vis.add("graph", graph);
        vis.setInteractive("graph.edges", null, false);
//        vis.addDecorators("nodeDeco", "graph.nodes", new HoverPredicate(), DECORATOR_SCHEMA);
        // -- 3. the renderers and renderer factory ---------------------------
        
        // draw the "name" label for NodeItems
        LabelRenderer r = new LabelRenderer("name");
        r.setVerticalPadding(6);
        r.setHorizontalPadding(6);
        r.setRoundedCorner(8, 8); // round the corners
        
        // create a new default renderer factory
        // return our name label renderer as the default for all non-EdgeItems
        // includes straight line edges for EdgeItems by default
        vis.setRendererFactory(new DefaultRendererFactory(r));

        
        // -- 4. the processing actions ---------------------------------------
        
        // create our nominal color palette
        // pink for females, baby blue for males
//        itemTypes:
        //tonalidade rosa
//        1 	Need
//        2 	Feature
        //tonalidade laranja/bege
//        3 	Actor
//        4 	Use Case
//        5 	ClassItem
//        6 	Test Case
        //tonalidade azul
//        7 	Vision
//        8 	Use Case Specification
//        9 	Use Case Diagram
//        10 	Class Diagram
//        11 	Test Case Specification
        
        int[] palette = new int[] { ColorLib.rgb(190,190,255), ColorLib.rgb(190,190,255), 
        		ColorLib.rgb(180, 238, 180), ColorLib.rgb(180, 238, 180), ColorLib.rgb(180, 238, 180),ColorLib.rgb(180, 238, 180), //ColorLib.rgb(230, 230, 250), ColorLib.rgb(230, 230, 250), ColorLib.rgb(230, 230, 250), ColorLib.rgb(230, 230, 250), 
        		
        		ColorLib.rgb(198, 226, 255), ColorLib.rgb(198, 226, 255), ColorLib.rgb(198, 226, 255), ColorLib.rgb(198, 226, 255), ColorLib.rgb(198, 226, 255)};
        // map nominal data values to colors using our provided palette
        DataColorAction fill = new DataColorAction("graph.nodes", "itemType",Constants.NUMERICAL, VisualItem.FILLCOLOR, palette);
//        fill.add(VisualItem.HIGHLIGHT,ColorLib.rgb(255,255,0));
        
        
     // create actions 
//        ColorAction fill = new ColorAction("graph.nodes",VisualItem.FILLCOLOR, ColorLib.rgb(200,200,255)); 
        fill.add(VisualItem.FIXED, ColorLib.rgb(139, 139, 122));//105, 105, 105)); 
        fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,255,0)); 
        fill.add("ingroup('_search_')", ColorLib.rgb(230, 230, 250));//147, 112, 219));//255,190,190)); // Highlight searched items 
        
        // use black for node text
        ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
        
        // use light grey for edges
        ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
        
        // create an action list containing all color assignments
        ActionList color = new ActionList();
        color.add(fill);
        color.add(text);
        color.add(edges);
        
        ForceSimulator fsim = new ForceSimulator(new RungeKuttaIntegrator());

    	float gravConstant = -1f; 
    	float minDistance = -1f;
    	float theta = 0.9f;

    	float drag = 0.01f; 
    	float springCoeff = 1E-4f;  
    	float defaultLength = 150f;  //default: 50f

    	fsim.addForce(new NBodyForce(gravConstant, minDistance, theta));
    	fsim.addForce(new DragForce(drag));
    	fsim.addForce(new SpringForce(springCoeff, defaultLength));
    	
//            ForceDirectedLayout fdl = new ForceDirectedLayout(GRAPH, fsim,true);
        
    	
    	// the search tuple set 
    	SearchTupleSet search = new PrefixSearchTupleSet(); 
    	vis.addFocusGroup(Visualization.SEARCH_ITEMS, search); 
    	
    	
        // create an action list with an animated layout
        ActionList layout = new ActionList(Activity.INFINITY);
        ForceDirectedLayout dLayout = new ForceDirectedLayout(GRAPH, fsim, true,false);
        layout.add(dLayout);
        layout.add(color);
        layout.add(new RepaintAction());
        
        // add the actions to the visualization
        vis.putAction("color", color);
        vis.putAction("layout", layout);
        
        
        // -- 5. the display and interactive controls -------------------------
        
        Display d = new Display(vis);
        d.pan(mainPanel.getWidth()/2, mainPanel.getHeight()/2);
//        d.setSize(mainPanel.getWidth(), mainPanel.getHeight()); // set display size
        d.addControlListener(new NeighborHighlightControl());
        // drag individual items around
        d.addControlListener(new DragControl());
        // pan with left-click drag on background
        d.addControlListener(new PanControl()); 
        // zoom with right-click drag
        d.addControlListener(new ZoomControl());
        
        
        // -- 6. launch the visualization -------------------------------------
        
        // create a new window to hold the visualization
//        JFrame frame = new JFrame("prefuse example");
//        // ensure application exits when window is closed
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(d);
//        frame.pack();           // layout components in window
//        frame.setVisible(true); // show the window
        
        // assign the colors
        vis.run("color");
        // start up the animated layout
        vis.run("layout");

     // add the search box 
//        SearchQueryBinding sq = new SearchQueryBinding((Table) d.getVisualization().getGroup("graph.nodes"), "label", (SearchTupleSet) d.getVisualization().getGroup(Visualization.SEARCH_ITEMS));
//        searchPanel = sq.createSearchPanel();  
        
        
        return d;
        
    }

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

    public JSearchPanel getSearchPanel() {
		return searchPanel;
	}

	public void setSearchPanel(JSearchPanel searchPanel) {
		this.searchPanel = searchPanel;
	}
}