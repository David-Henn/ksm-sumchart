/**
 * @author Tobias Dreher
 *
 * Created on 01.11.2010
 * Last update on 04.11.2010
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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import gui.RightPanelMenuEntry;
import gui.RightPanelSkeleton;
import plugin.interfaces.IPerspective;

import pluginCore.DataModel;

public class SumChartPluginPerspective implements IPerspective {

    private DataModel data;
    private NodeList leftPanel;
    private CenterPanel centerPanel;
    private JPanel rightPanel;
    private List<JToolBar> toolbars;
    private JButton editAreasButton;
    private JButton drawAreaButton;
    private Color btnColor;

    public SumChartPluginPerspective() {
        data = new DataModel();

        leftPanel = new NodeList(this, data);
        centerPanel = new CenterPanel(this, data);

        List<RightPanelMenuEntry> entries = new LinkedList<RightPanelMenuEntry>();
        entries.add(new Legend());
        entries.add(new Settings(this));
        entries.add(new TextAreas(this));
        rightPanel = new RightPanelSkeleton(entries);

        JToolBar toolbar = new JToolBar();
        editAreasButton = new JButton("Edit Areas");
        btnColor = editAreasButton.getForeground();
        editAreasButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!data.getDraw()) {
                    data.setDraw(true);
                    editAreasButton.setForeground(Color.RED);
                } else {
                    data.setDraw(false);
                    editAreasButton.setForeground(btnColor);
                }
            }
        });
        toolbar.add(editAreasButton);

        drawAreaButton = new JButton("Draw Area");
        btnColor = editAreasButton.getForeground();
        drawAreaButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!data.isDrawArea()) {
                    data.setDrawArea(true);
                    drawAreaButton.setForeground(Color.RED);
                } else {
                    data.setDrawArea(false);
                    drawAreaButton.setForeground(btnColor);
                }
            }
        });
        toolbar.add(drawAreaButton);
        toolbars = new ArrayList<JToolBar>();
        toolbars.add(toolbar);
    }

    public NodeList getLeftPane() {
        return leftPanel;
    }

    public CenterPanel getCenterPane() {
        return centerPanel;
    }

    public JPanel getRightPane() {
        return rightPanel;
    }

    public List<JToolBar> getToolbars() {
        return toolbars;
    }

    public DataModel getDataModel() {
        return data;
    }

    /**
     * Display a Perspective-Name
     */
    @Override
    public String toString() {
        return "Sum Chart";
    }

    public void refresh() {
        data.refreshData();
        leftPanel.repaint();
        rightPanel.repaint();
        centerPanel.repaint();
    }

    public JMenu getMenu() {
        return null;
    }
}
