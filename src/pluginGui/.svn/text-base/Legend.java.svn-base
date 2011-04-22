/**
 * @author Tobias Dreher
 *
 * Created on 03.11.2010
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

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.RightPanelMenuEntry;

public class Legend implements RightPanelMenuEntry {

    private String name;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JList list;

    public Legend() {
        this.name = "Legend";

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        list = new JList();
        list.setModel(new AbstractListModel() {

            String[] strings = {"AS: active sum", "PS: passive sum",
                        "Q: active / passive", "P: active * passive"};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });

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
