/**
 * @author Tobias Dreher
 *
 * Created on 01.11.2010
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

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.jdesktop.layout.GroupLayout;

import pluginCore.DataModel;

public class CenterPanel extends JPanel {

    private SumChartPluginPerspective parent;
    private DataModel data;
    private DrawArea drawArea;

    /** 
     * Creates the CenterPanel
     */
    public CenterPanel(SumChartPluginPerspective parent, DataModel data) {
        this.parent = parent;
        this.data = data;
        this.drawArea = new DrawArea(this);

        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        drawArea.setBackground(new java.awt.Color(210, 210, 210)); //gray

        GroupLayout drawAreaLayout = new GroupLayout(drawArea);
        drawArea.setLayout(drawAreaLayout);
        drawAreaLayout.setHorizontalGroup(
                drawAreaLayout.createParallelGroup(GroupLayout.LEADING).add(100, 450, Short.MAX_VALUE));
        drawAreaLayout.setVerticalGroup(
                drawAreaLayout.createParallelGroup(GroupLayout.LEADING).add(100, 300, Short.MAX_VALUE));

        this.add(drawArea);

        // Refresh Data when CenterPanel is set visible
        this.addAncestorListener(new AncestorListener() {

            public void ancestorAdded(AncestorEvent event) {
                refreshData();
                drawArea.repaint();

               
            }

            public void ancestorRemoved(AncestorEvent event) {
            }

            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }


    public SumChartPluginPerspective getCenterParent() {
        return parent;
    }

    public void markListElement(int i) {
        if (i < 0) {
            parent.getLeftPane().getNodeList().clearSelection();
        } else {
            parent.getLeftPane().getNodeList().setSelectedIndex(i);
        }
    }

    public DrawArea getDrawArea() {
        return drawArea;
    }

    public DataModel getDataModel() {
        return data;
    }

    private void refreshData() {
        data.refreshData();
    }
}
