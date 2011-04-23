/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pluginGui;

import gui.RightPanelMenuEntry;
import java.awt.ScrollPane;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import pluginCore.DataModel;

/**
 *
 * @author david
 */
public class PolyTree implements RightPanelMenuEntry{

    private DataModel model;
    private JPanel pane;
    JScrollPane scPane;
    JTree tree;

    public PolyTree(DataModel model) {
        this.model = model;
        
        
    }

    public String getName() {
        return "Areas";
    }

    public JPanel getPanel() {
        pane = new JPanel();
        scPane = new JScrollPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        tree = model.getPolygonTree();
        tree.setRootVisible(false);
        tree.expandPath(new TreePath(model.getRoot()));
        scPane.setViewportView(tree);
        pane.add(scPane);
        return pane;
    }

    

}
