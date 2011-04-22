/**
 * @author Tobias Dreher
 *
 * Created on 08.11.2010
 * Last update on -
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

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.RightPanelMenuEntry;

public class TextAreas implements RightPanelMenuEntry{

    private SumChartPluginPerspective parent;
    private String name;
    private JPanel panel;
    private JList list;
    private JScrollPane scrollPane;

    public TextAreas(SumChartPluginPerspective parent) {
        this.parent = parent;
        this.name = "Text areas";

        list = new JList();
        list.setModel(parent.getDataModel().getListModelOvals());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        panel.add(scrollPane);
    } 

    public String getName() {
        return name;
    }

    public JPanel getPanel() {
        return panel;
    }

}
