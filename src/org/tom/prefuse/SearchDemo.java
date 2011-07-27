package org.tom.prefuse;

import java.awt.BorderLayout; 

import javax.swing.JFrame; 
import javax.swing.UIManager; 
 
import prefuse.Display; 
import prefuse.Visualization; 
import prefuse.action.ActionList; 
import prefuse.action.RepaintAction; 
import prefuse.action.assignment.ColorAction; 
import prefuse.action.layout.graph.ForceDirectedLayout; 
import prefuse.activity.Activity; 
import prefuse.controls.DragControl; 
import prefuse.data.Graph; 
import prefuse.data.Node; 
import prefuse.data.Table; 
import prefuse.data.query.SearchQueryBinding; 
import prefuse.data.search.PrefixSearchTupleSet; 
import prefuse.data.search.SearchTupleSet; 
import prefuse.render.DefaultRendererFactory; 
import prefuse.render.LabelRenderer; 
import prefuse.util.ColorLib; 
import prefuse.util.ui.JSearchPanel; 
import prefuse.visual.VisualItem; 
 
public class SearchDemo extends JFrame{ 
public static final String GRAPH = "graph"; 
public static final String NODES = "graph.nodes"; 
public static final String EDGES = "graph.edges"; 
 
public SearchDemo(){ 
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
this.setLayout(new BorderLayout()); 
 
// add the display 
Display display = initPrefuse(); 
this.add(display,BorderLayout.CENTER); 
 
// add the search box 
SearchQueryBinding sq = new SearchQueryBinding((Table) display.getVisualization().getGroup(NODES), "label", (SearchTupleSet) display.getVisualization().getGroup(Visualization.SEARCH_ITEMS)); 
JSearchPanel searchPanel = sq.createSearchPanel();  
this.add(searchPanel,BorderLayout.NORTH); 
 
this.pack(); 
this.setSize(640,480); 
this.setVisible(true); 
display.getVisualization().run("draw");  
} 
 
// initialize all the prefuse stuff 
public Display initPrefuse(){ 
// build a graph 
Graph graph = new Graph();  
graph.addColumn("label", String.class); 
Node n1 = graph.addNode();  
Node n2 = graph.addNode(); 
Node n3 = graph.addNode();  
n1.setString("label","George Washington"); 
n2.setString("label","John Adams"); 
n3.setString("label","Thomas Jefferson"); 
graph.addEdge(n1, n2); 
graph.addEdge(n1, n3); 
 
// init visualization 
Visualization visualization = new Visualization(); 
visualization.addGraph(GRAPH, graph); 
 
// create renderer 
LabelRenderer labelrenderer = new LabelRenderer("label"); 
labelrenderer.setRoundedCorner(8, 8); 
visualization.setRendererFactory(new DefaultRendererFactory(labelrenderer)); 
 
// the search tuple set 
SearchTupleSet search = new PrefixSearchTupleSet(); 
visualization.addFocusGroup(Visualization.SEARCH_ITEMS, search); 
 
// create actions 
ColorAction fill = new ColorAction(NODES,VisualItem.FILLCOLOR, ColorLib.rgb(200,200,255)); 
fill.add(VisualItem.FIXED, ColorLib.rgb(255,100,100)); 
fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125)); 
fill.add("ingroup('_search_')", ColorLib.rgb(255,190,190)); // Highlight searched items 
 
ColorAction edges = new ColorAction(EDGES, VisualItem.STROKECOLOR); 
edges.setDefaultColor(ColorLib.gray(100));  
 
ActionList draw = new ActionList(); 
draw.add(fill); 
draw.add(edges); 
draw.add(new ColorAction(NODES, VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0))); 
 
ActionList animate = new ActionList(Activity.INFINITY); 
animate.add(new ForceDirectedLayout(GRAPH,true)); 
animate.add(fill); 
animate.add(new RepaintAction()); 
 
// add the actions to the visualization 
visualization.putAction("draw", draw); 
visualization.putAction("layout", animate); 
visualization.runAfter("draw", "layout"); 
 
 
// create display 
Display display = new Display(visualization); 
display.setSize(700,700); 
display.pan(350, 350); 
 
 
// main display controls 
display.addControlListener(new DragControl()); 
return display; 
} 
 
 
public static void main(String[] args) { 
java.awt.EventQueue.invokeLater(new Runnable() { 
public void run() {  
try { 
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
} catch (Exception e) { 
e.printStackTrace(); 
} 
new SearchDemo(); 
} 
}); 
} 
}

