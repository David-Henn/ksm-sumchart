/**
 * @author Tobias Dreher
 *
 * Created on 03.11.2010
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.RightPanelMenuEntry;

import pluginCore.DataModel;

public class Settings implements RightPanelMenuEntry {

    private SumChartPluginPerspective parent;
    private DataModel data;
    private String name;
    private JPanel panelInside;
    private JPanel panelOutside;
    private JTextField activeTF;
    private JTextField passiveTF;
    private JTextField stableTF;
    private JTextField criticalTF;

    public Settings(SumChartPluginPerspective parent) {
        this.parent = parent;
        this.name = "Chart settings";

        data = parent.getDataModel();

        passiveTF = new JTextField(String.valueOf(data.getPassive()));
        activeTF = new JTextField(String.valueOf(data.getActive()));
        stableTF = new JTextField(String.valueOf(data.getStable()));
        criticalTF = new JTextField(String.valueOf(data.getCritical()));

        addTFListener();

        panelInside = new JPanel(new GridLayout(4, 2));
        panelInside.add(new JLabel("passive until"));
        panelInside.add(passiveTF);
        panelInside.add(new JLabel("active from"));
        panelInside.add(activeTF);
        panelInside.add(new JLabel("stable until"));
        panelInside.add(stableTF);
        panelInside.add(new JLabel("critical from"));
        panelInside.add(criticalTF);

        panelOutside = new JPanel();
        panelOutside.setLayout(new BoxLayout(panelOutside, BoxLayout.PAGE_AXIS));
        panelOutside.add(panelInside);

        //Workaround for expandings labels
        for (int i = 0; i < 20; i++) {
            panelOutside.add(Box.createVerticalGlue());
        }
    }

    public String getName() {
        return name;
    }

    public JPanel getPanel() {
        return panelOutside;
    }

    private void refresh() {
        parent.refresh();
    }

    private void addTFListener() {
        passiveTF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    data.setPassive(Double.parseDouble(passiveTF.getText().replace(',', '.')));
                } catch (NumberFormatException ex) {
                    passiveTF.setText(String.valueOf(data.getPassive()));
                }
                refresh();
            }
        });
        activeTF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    data.setActive(Double.parseDouble(activeTF.getText().replace(',', '.')));
                } catch (NumberFormatException ex) {
                    activeTF.setText(String.valueOf(data.getActive()));
                }
                refresh();
            }
        });
        stableTF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    data.setStable(Double.parseDouble(stableTF.getText().replace(',', '.')));
                } catch (NumberFormatException ex) {
                    stableTF.setText(String.valueOf(data.getStable()));
                }
                refresh();
            }
        });
        criticalTF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    data.setCritical(Double.parseDouble(criticalTF.getText().replace(',', '.')));
                } catch (NumberFormatException ex) {
                    criticalTF.setText(String.valueOf(data.getCritical()));
                }
                refresh();
            }
        });
    }
}
