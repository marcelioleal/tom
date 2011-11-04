package org.tom.GUI.prefuse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.tom.GUI.MainWindow;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;
import prefuse.action.animate.PolarLocationAnimator;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.DefaultTupleSet;
import prefuse.data.tuple.TupleSet;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.util.ui.UILib;
import prefuse.visual.EdgeItem;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.visual.sort.TreeDepthItemSorter;


/**
 * Demonstration of a node-link tree viewer
 *
 * @version 1.0
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class RadialGraphView extends Display {

    public static final String DATA_FILE = "/home/mayara/teste.xml";//"/socialnet.xml";
    
    private static final String tree = "tree";
    private static final String treeNodes = "tree.nodes";
    private static final String treeEdges = "tree.edges";
    private static final String linear = "linear";
	private boolean nodesColored;
    
    private LabelRenderer m_nodeRenderer;
    private EdgeRenderer m_edgeRenderer;
    
    private String m_label = "label";
    
    MainWindow main = null;
    ColorAction textColor = null;
    
    /*** check boxes ***/
    JCheckBox checkAll;
    JCheckBox checkNeed;
    JCheckBox checkFeature;
    JCheckBox checkActor;
    JCheckBox checkUseCase;
    JCheckBox checkClass;
    JCheckBox checkTestCase;
    JCheckBox teste = new JCheckBox();
    

    
    public RadialGraphView(MainWindow main) {
      super(new Visualization());
      this.main = main;
    }
    
//    public RadialGraphView(Graph g, String label) {
//        super(new Visualization());
    public JPanel radialGraphView(Graph g, String label) {
        m_label = label;

        // -- set up visualization --
        m_vis.add(tree, g);
        m_vis.setInteractive(treeEdges, null, false);
        
        // -- set up renderers --
        m_nodeRenderer = new LabelRenderer(m_label);
        m_nodeRenderer.setRenderType(AbstractShapeRenderer.RENDER_TYPE_FILL);
        m_nodeRenderer.setHorizontalAlignment(Constants.CENTER);
        m_nodeRenderer.setRoundedCorner(8,8);
        m_edgeRenderer = new EdgeRenderer();
        
        DefaultRendererFactory rf = new DefaultRendererFactory(m_nodeRenderer);
        rf.add(new InGroupPredicate(treeEdges), m_edgeRenderer);
        m_vis.setRendererFactory(rf);
               
        // -- set up processing actions --
        
        // colors
        final ColorAction nodeColor = new ColorAction(treeNodes,VisualItem.FILLCOLOR,ColorLib.rgb(255,255,255));
        nodeColor.add("ingroup('_search_')", ColorLib.rgb(255,190,190));
        nodeColor.add("ingroup('_focus_')", ColorLib.rgb(198,229,229));
        textColor = new ColorAction(treeNodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0));
        m_vis.putAction("textColor", textColor);
        
        ItemAction edgeColor = new ColorAction(treeEdges,
                VisualItem.STROKECOLOR, ColorLib.rgb(200,200,200));
        
        FontAction fonts = new FontAction(treeNodes, 
                FontLib.getFont("Tahoma", 10));
        fonts.add("ingroup('_focus_')", FontLib.getFont("Tahoma", 11));
        
        
        // recolor
        ActionList recolor = new ActionList();
        recolor.add(nodeColor);
        recolor.add(textColor);
        m_vis.putAction("recolor", recolor);
        
        // repaint
        ActionList repaint = new ActionList();
        repaint.add(recolor);
        repaint.add(new RepaintAction());
        m_vis.putAction("repaint", repaint);
        
        // animate paint change
        ActionList animatePaint = new ActionList(400);
        animatePaint.add(new ColorAnimator(treeNodes));
        animatePaint.add(new RepaintAction());
        m_vis.putAction("animatePaint", animatePaint);
        
        // create the tree layout action
        RadialTreeLayout treeLayout = new RadialTreeLayout(tree); //organiza em forma de �rvore
//        treeLayout.setAngularBounds(-Math.PI/2, Math.PI);
        m_vis.putAction("treeLayout", treeLayout);
        
        CollapsedSubtreeLayout subLayout = new CollapsedSubtreeLayout(tree); //faz aparecer primeiro as edges
        m_vis.putAction("subLayout", subLayout);
        
        // create the filtering and layout
        ActionList filter = new ActionList();
        filter.add(new TreeRootAction(tree)); //centraliza o n�
        filter.add(fonts);
        filter.add(treeLayout); //organiza em forma de �rvore
        filter.add(subLayout); //faz aparecer primeiro as edges
        filter.add(textColor);
        filter.add(nodeColor);
        filter.add(edgeColor);
        m_vis.putAction("filter", filter);
        
        // animated transition
        ActionList animate = new ActionList(1250);
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(new QualityControlAnimator());
        animate.add(new VisibilityAnimator(tree)); //faz a anima��o ser "passo a passo"
        animate.add(new PolarLocationAnimator(treeNodes, linear)); //faz a anima��o ser "passo a passo"
        animate.add(new ColorAnimator(treeNodes));
        animate.add(new RepaintAction());
        m_vis.putAction("animate", animate);
        m_vis.alwaysRunAfter("filter", "animate");
        
        // ------------------------------------------------
        
        // initialize the display
        setSize(600,600);
        setItemSorter(new TreeDepthItemSorter());
        addControlListener(new DragControl());
        addControlListener(new ZoomToFitControl());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new FocusControl(1, "filter"));
//        addControlListener(new HoverActionControl("repaint"));
//        addControlListener(new ToolTipControl("name"));

        // ------------------------------------------------
        
        // filter graph and perform layout
        m_vis.run("filter");
        
        // maintain a set of items that should be interpolated linearly
        // this isn't absolutely necessary, but makes the animations nicer
        // the PolarLocationAnimator should read this set and act accordingly
        m_vis.addFocusGroup(linear, new DefaultTupleSet());
        m_vis.getGroup(Visualization.FOCUS_ITEMS).addTupleSetListener(
            new TupleSetListener() {
                public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {

                    TupleSet linearInterp = m_vis.getGroup(linear);
                    if ( add.length < 1 ) return; linearInterp.clear();
                    for ( Node n = (Node)add[0]; n!=null; n=n.getParent() )
                        linearInterp.addTuple(n);
                }
            }
        );
        
        SearchTupleSet search = new PrefixSearchTupleSet();
        m_vis.addFocusGroup(Visualization.SEARCH_ITEMS, search);
        search.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {

                m_vis.cancel("animatePaint"); //permite marcar mais de um n�
                m_vis.run("recolor"); //pinta o n� q corresponde a busca
                m_vis.run("animatePaint"); //pinta o n� q corresponde a busca
            }
        });
        
        
        
        // create a new radial tree view 
        // create a search panel for the tree map
        SearchQueryBinding sq = new SearchQueryBinding(
             (Table)m_vis.getGroup(treeNodes), label,
             (SearchTupleSet)m_vis.getGroup(Visualization.SEARCH_ITEMS));
        JSearchPanel search2 = sq.createSearchPanel();
        search2.setShowResultCount(true);
        search2.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search2.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 11));
        
        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        title.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 14));
        
        
        
        final String label2 = label;
        this.addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
                if ( item.canGetString(label2) )
                    title.setText(item.getString(label2));
                	Node no = (Node) item;                	
                	setToolTipText("<html>Original Artifact: " + no.getString("originalArtifact") + "<br>" +
                					"<hr></hr>Number of connections: " + no.getDegree()+"</html>");
            }
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
                setToolTipText(null);
            }
            public void itemClicked(VisualItem item, MouseEvent e){
            	if ( item.canGetString(label2) ){
            		if(SwingUtilities.isRightMouseButton(e)){
	            		JPopupMenu popupMenu = createNodePopupMenu(item);  
	            		popupMenu.show(e.getComponent(), e.getX(), e.getY());
            		}
           		} 
           	}
        });
        
        
        
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(title);
        box.add(Box.createHorizontalGlue());
        box.add(search2);
        box.add(Box.createHorizontalStrut(3));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this, BorderLayout.CENTER);
        panel.add(box, BorderLayout.SOUTH);
        
        JPanel painelDir = new JPanel();
        painelDir.setBackground(Color.WHITE);
        painelDir.setLayout(new BoxLayout(painelDir, BoxLayout.Y_AXIS));
        
        
        //box for the main label
        Box filterLabelBox = new Box(BoxLayout.X_AXIS);
        JLabel mainLabel = new JLabel();
        mainLabel.setText("   Filters:                     ");
        filterLabelBox.add(mainLabel);
        painelDir.add(filterLabelBox);
        
        
        
        //box for the checkBoxes for filter by type
        Box byTypeBox = new Box(BoxLayout.Y_AXIS);
        byTypeBox.setBorder(BorderFactory.createTitledBorder("By Type"));
        
        checkAll = new JCheckBox("All", false);
        checkAll.setBackground(Color.white);
        checkAll.setSelected(true);
        checkAll.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				filter();
			}
        	
        });
        byTypeBox.add(checkAll);
        
        checkNeed = new JCheckBox("Needs", false);
        checkNeed.setBackground(Color.white);
        checkNeed.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkNeed);
        
        
        checkFeature = new JCheckBox("Features", false);
        checkFeature.setBackground(Color.white);
        checkFeature.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkFeature);
        
        
        checkActor = new JCheckBox("Actors", false);
        checkActor.setBackground(Color.white);
        checkActor.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkActor);
        
        
        checkUseCase = new JCheckBox("Use Cases", false);
        checkUseCase.setBackground(Color.white);
        checkUseCase.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkUseCase);
        
        
        checkClass = new JCheckBox("Classes", false);
        checkClass.setBackground(Color.white);
        checkClass.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkClass);
        
        
        checkTestCase = new JCheckBox("Test Cases", false);
        checkTestCase.setBackground(Color.white);
        checkTestCase.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				checkAll.setSelected(false);
				filter();
			}
        });
        byTypeBox.add(checkTestCase);
        
        
        
        Box thirdBox = new Box(BoxLayout.X_AXIS);
        thirdBox.setMaximumSize(new Dimension(500,30));
        thirdBox.add(Box.createHorizontalStrut(10));
        thirdBox.add(byTypeBox);
        
        Box colorBox = new Box(BoxLayout.Y_AXIS);
        colorBox.setMaximumSize(new Dimension(500,60));
        colorBox.add(Box.createHorizontalStrut(10));
        colorBox.setBorder(BorderFactory.createTitledBorder("Number of Links"));
        teste = new JCheckBox();
        teste.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
				colorizeWeight(nodeColor);
			}
        });
        teste.setText("teste");
        colorBox.add(teste);
        
        Box colorBox2 = new Box(BoxLayout.X_AXIS);
        colorBox2.setMaximumSize(new Dimension(500,30));
        colorBox2.add(colorBox);
        colorBox2.add(Box.createHorizontalStrut(10));
        
        
        painelDir.add(thirdBox);
        painelDir.add(colorBox2);
        painelDir.add(Box.createVerticalStrut(5));
        
        painelDir.add(Box.createVerticalGlue());
        
        panel.add(painelDir, BorderLayout.EAST);
        Color BACKGROUND = Color.WHITE;
        Color FOREGROUND = Color.DARK_GRAY;
        UILib.setColor(panel, BACKGROUND, FOREGROUND);
        
        
        return panel;
    }
    
    // ------------------------------------------------------------------------
    
    JPopupMenu nodePopupMenu;
    JMenuItem popupMenuItemTraceOArtifact;
    
    public JPopupMenu createNodePopupMenu(VisualItem item){
    	nodePopupMenu = new JPopupMenu();
    	nodePopupMenu.add(getPopupMenuItemTraceOArtifact(item));
    	return nodePopupMenu;
    }
    
    public JMenuItem getPopupMenuItemTraceOArtifact(VisualItem item){
    	final VisualItem i = item;
		
    	popupMenuItemTraceOArtifact = new JMenuItem();
		popupMenuItemTraceOArtifact.setText("Trace Original Artifact");
		/*popupMenuItemTraceOArtifact.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				main.displaySelectedFileTraceability(i.getString("originalArtifact"));
			}
		});*/
    	return popupMenuItemTraceOArtifact;
    }
    
    public void colorizeWeight(ColorAction nodeColor){
    	Iterator it = m_vis.items();
    	int degree;
    	int maxDegree = -1;
    	int minDegree = 9999;
    	int mediumDegree = 0;
    	
    	if(teste.isSelected()){
    		while (it.hasNext()) {
    			VisualItem ed = (VisualItem) it.next();
    			if(ed.isInGroup("tree.nodes")){
    				Node no = (Node) ed;
    				degree = no.getDegree();
    				if(degree < minDegree) minDegree = degree;
    				if(degree > maxDegree) maxDegree = degree;
    			}
    		}
    		mediumDegree = (minDegree + maxDegree) / 2;
    		it = m_vis.items();
        	while (it.hasNext()) {  
    			VisualItem ed = (VisualItem) it.next(); 
    			if(ed.isInGroup("tree.nodes")){
    				Node no = (Node) ed;
    				degree = no.getDegree();
    				if(degree < 3){
    					ed.setFillColor(ColorLib.rgb(202, 225, 255));
    				}
    				else if(degree >= 3 && degree < 5) ed.setFillColor(ColorLib.rgb(99, 184, 255));
    				else ed.setFillColor(ColorLib.rgb(67, 110, 238));
    			}
        	}
        	m_vis.repaint();
    	}
    	else{
    		while (it.hasNext()) {  
    			VisualItem ed = (VisualItem) it.next(); 
    			if(ed.isInGroup("tree.nodes")){
   					ed.setFillColor(ColorLib.rgb(255,255,255));
    			}
        	}
        	m_vis.repaint();
    	}
    	nodesColored = true;
    	
    }
    
    public void filter(){
    	Iterator it = m_vis.items(); 
    	if(checkAll.isSelected()){
    		while (it.hasNext()) {
    			checkActor.setSelected(false);
    			checkClass.setSelected(false);
    			checkFeature.setSelected(false);
    			checkNeed.setSelected(false);
    			checkTestCase.setSelected(false);
    			checkUseCase.setSelected(false);
    			VisualItem ed = (VisualItem) it.next();
    			ed.setVisible(true);
    		}
    		m_vis.repaint();
    	}
    	else{
    		if(!checkActor.isSelected() && !checkFeature.isSelected() && !checkClass.isSelected()
    				&& !checkNeed.isSelected() && !checkTestCase.isSelected() && !checkTestCase.isSelected()){
    			checkAll.setSelected(true);
    			filter();
    		}
    		else{
				while (it.hasNext()) {  
					VisualItem ed = (VisualItem) it.next(); 
					if(ed.isInGroup("tree.nodes")){
						switch (Integer.parseInt(ed.getString("itemType"))) {
						case 1:{
							if(checkNeed.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						case 2:{
							if(checkFeature.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						case 3:{
							if(checkActor.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						case 4:{
							if(checkUseCase.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						case 5:{
							if(checkClass.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						case 6:{
							if(checkTestCase.isSelected()) ed.setVisible(true);
							else ed.setVisible(false);
							break;
						}
						default:
							break;
						}
						hideEdges();
						m_vis.repaint();
					}
				}
			}
    	}
    }

    
    public void hideEdges(){
		Iterator it = m_vis.items(); 
		while (it.hasNext()) {  
			VisualItem ed = (VisualItem) it.next(); 
			if(ed.isInGroup("tree.edges")){ 
    			EdgeItem e2 = (EdgeItem)ed;  
    			if (e2.getTargetItem().isVisible() == false || e2.getSourceItem().isVisible() == false){  
    				ed.setVisible(false);  
    			}
    			else ed.setVisible(true);
			}
		}			
    }
    
    public static void main(String argv[]) {
        String infile = DATA_FILE;
        String label = "name";
        
        if ( argv.length > 1 ) {
            infile = argv[0];
            label = argv[1];
        }
        
        UILib.setPlatformLookAndFeel();
        
        JFrame frame = new JFrame("p r e f u s e  |  r a d i a l g r a p h v i e w");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(demo(infile, label));
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    // ------------------------------------------------------------------------
    
    /**
     * Switch the root of the tree by requesting a new spanning tree
     * at the desired root
     */
    public static class TreeRootAction extends GroupAction {
        public TreeRootAction(String graphGroup) {
            super(graphGroup);
        }
        public void run(double frac) {
            TupleSet focus = m_vis.getGroup(Visualization.FOCUS_ITEMS);
            if ( focus==null || focus.getTupleCount() == 0 ) return;
            
            Graph g = (Graph)m_vis.getGroup(m_group);
            Node f = null;
            Iterator tuples = focus.tuples();
            while (tuples.hasNext() && !g.containsTuple(f=(Node)tuples.next()))
            {
                f = null;
            }
            if ( f == null ) return;
            g.getSpanningTree(f);
        }
    }
    
    /**
     * Set node fill colors
     */
    public static class NodeColorAction extends ColorAction {
        public NodeColorAction(String group) {
            super(group, VisualItem.FILLCOLOR, ColorLib.rgba(255,255,255,0));
            
//            add("_hover", ColorLib.gray(220,230));
            add("ingroup('_search_')", ColorLib.rgb(255,190,190));
            add("ingroup('_focus_')", ColorLib.rgb(198,229,229));
        }
                
    } // end of inner class NodeColorAction
    
    /**
     * Set node text colors
     */
    public static class TextColorAction extends ColorAction {
        public TextColorAction(String group, JCheckBox checkColorDegree) {
            super(group, VisualItem.TEXTCOLOR, ColorLib.gray(0));
            if(!checkColorDegree.isSelected()){
            	add("_hover", ColorLib.rgb(255,0,0));
            	System.out.println("akiii");
            }
            	
        }
    } // end of inner class TextColorAction
    
    
} // end of class RadialGraphView
