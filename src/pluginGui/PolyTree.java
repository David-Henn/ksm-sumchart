/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui;

import gui.RightPanelMenuEntry;
import java.awt.ScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import pluginCore.DataModel;

/**
 *
 * @author david
 */
public class PolyTree implements RightPanelMenuEntry {
    private DataModel model;
    public PolyTree(DataModel model){
        this.model = model;
    }
    public String getName() {
        return "Areas";
    }

    public JPanel getPanel() {
       JPanel pane = new JPanel();
       JScrollPane scPane = new JScrollPane();
       pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
       JTree tree = model.getPolygonTree();
       tree.setRootVisible(false);
       tree.expandPath(new TreePath(model.getRoot()));
       scPane.setViewportView(tree);
       pane.add(scPane);
       return pane;
    }

}
