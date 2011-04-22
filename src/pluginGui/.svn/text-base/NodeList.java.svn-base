/**
 * @author Tobias Dreher
 *
 * Created on 02.11.2010
 * Last update on 08.11.2010
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pluginGui;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BoxLayout;

import pluginCore.DataModel;

public class NodeList extends JPanel {

    private SumChartPluginPerspective parent;
    private DataModel data;
    private JList nodeList;
    private JScrollPane scrollPane;

    public NodeList(SumChartPluginPerspective parent, DataModel data) {
        this.parent = parent;
        this.data = data;

        scrollPane = new JScrollPane();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        nodeList = new JList();
        nodeList.setModel(data.getListModelNodes());
        nodeList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                listValueChanged(evt);
            }
        });

        scrollPane.setViewportView(nodeList);
        this.add(scrollPane);
    }

    public JList getNodeList() {
        return nodeList;
    }

    private void listValueChanged(ListSelectionEvent evt) {
        int selectedItem = this.nodeList.getSelectedIndex();
        parent.getCenterPane().getDrawArea().setIndex_mouse_over_point(selectedItem);
        parent.getCenterPane().getDrawArea().setMouse_over_a_point(true);
        parent.getCenterPane().repaint();
    }
}
