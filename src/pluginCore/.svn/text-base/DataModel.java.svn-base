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
package pluginCore;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;

import core.StartKSM;
import gui.AbstractGraphNode;
import gui.ksmEditor.KSMEditor;

public class DataModel {

    private double[] ps;
    private double[] as;
    private double[] q;
    private double[] p;
    private double max_ps_qs;
    private double max_q;
    private double passive;
    private double active;
    private double average;
    private double stable;
    private double critical;
    boolean draw;
    boolean drawArea;    
    private DefaultListModel listModelNodes;
    private DefaultListModel listModelOvals;
    private ArrayList<String> legendArray;
    private Vector<AbstractGraphNode> vectorNodes;
    private KSMEditor ksmEditor;

    public DataModel() {
        listModelNodes = new DefaultListModel();
        listModelOvals = new DefaultListModel();
        legendArray = new ArrayList<String>();
        vectorNodes = new Vector<AbstractGraphNode>();
        ksmEditor = StartKSM.getKSMEditor();
        draw = false;

        passive = 0.5;
        active = 1.5;
        stable = 60;
        critical = 800;
        average = 1.0;

        this.refreshData();
    }
    public boolean isDrawArea() {
        return drawArea;
    }

    public void setDrawArea(boolean drawArea) {
        this.drawArea = drawArea;
    }

    public double[] getAs() {
        return as;
    }

    public double[] getPs() {
        return ps;
    }

    public double getMax_ps_qs() {
        return max_ps_qs;
    }

    public DefaultListModel getListModelNodes() {
        return listModelNodes;
    }

    public DefaultListModel getListModelOvals() {
        return listModelOvals;
    }

    public KSMEditor getKSMEditor() {
        return ksmEditor;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public boolean getDraw() {
        return draw;
    }

    public void setPassive(double passive) {
        this.passive = passive;
    }

    public double getPassive() {
        return passive;
    }

    public void setActive(double active) {
        this.active = active;
    }

    public double getActive() {
        return active;
    }    
    
    public void setStable(double stable) {
        this.stable = stable;
    }

    public double getStable() {
        return stable;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getCritical() {
        return critical;
    }

    public double getAverage() {
        return average;
    }

    private void fillList() {
        this.listModelNodes.removeAllElements();
        int counter = 0;
        char num = 'A';
        int num2 = 0;
        String description;
        for (AbstractGraphNode node : this.vectorNodes) {
            if (num2 == 0) {
                description = (num) + ". ";
                this.listModelNodes.add(counter, description + node.getNodeName());
                if (num == 'Z') {
                    num = 'A';
                    num2++;
                } else {
                    ++num;
                }
                ++counter;
            } else {
                description = (num) + "";
                description = description + num2 + ". ";
                this.listModelNodes.add(counter, description + node.getNodeName());
                ++counter;
                ++num;
            }
        }
    }

    /**
     * Erweitert dynamisch die Legendenbeschriftung
     * @param ovalNr: Für jede Area, ein nummerierter Eintrag. ovalNr wird als Index verwendet
     * @param description: Beschreibung die an die Areanummerierung angehängt wird
     */
    public void fillLegendList(int ovalNr, String description) {
        this.listModelOvals.removeAllElements();
        this.legendArray.add("Area " + ovalNr + ": " + description);
        int i = 0;
        for (String d : legendArray) {
            this.listModelOvals.add(i, d);
            i++;
        }
    }

    public void refreshData() {
        vectorNodes = ksmEditor.getBG().getvJNodeButtos();
        fillList();
        ps = ksmEditor.getKtable().getColumSums();
        as = ksmEditor.getKtable().getRowSums();

        this.q = new double[this.ps.length];
        this.p = new double[this.ps.length];

        for (int counter = 0; counter < this.as.length; ++counter) {
            if (this.as[counter] > this.max_ps_qs || this.ps[counter] > this.max_ps_qs) {
                this.max_ps_qs = this.as[counter];
            }
            this.q[counter] = this.as[counter] / this.ps[counter];
            this.p[counter] = this.as[counter] * this.ps[counter];

            if (this.max_q < this.q[counter] && this.q[counter] != Float.POSITIVE_INFINITY) {
                this.max_q = this.q[counter];
            }
        }
    }
}
