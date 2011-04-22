/**
 * @author unascribed, Tobias Dreher
 *
 * Created on -
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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.KSMTable;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import pluginCore.DataModel;
import pluginGui.lineFunctions.Function;
import pluginGui.lineFunctions.Qaxis;
import pluginGui.lineFunctions.Xaxis;
import pluginGui.lineFunctions.yAxis;

public class DrawArea extends JPanel {

    private CenterPanel parent;
    private DataModel data;
    private int borderWidth = 50;
    private Point nullpoint;
    private Point maxpoint;
    private double pixels_per_unit_width; //How many pixels for a unit
    private double pixels_per_unit_higth;
    private double pixels_per_unit_norm_width;//how many pixels for 1
    private double pixels_per_unit_norm_higth;
    private Color as_ps = Color.blue;
    private Color q = Color.red;
    private int numberofunitsteps = 10;
    private Vector<Point> points;
    private int normal_point_diameter = 16;
    private int normal_point_radius;
    private int big_point_diameter = 20;
    private int big_point_radius;
    private Point drawQmin;
    private Point drawQmax;
    private int index_mouse_over_point;
    private boolean mouse_over_a_point;
    private boolean rightMouse;
    private javax.swing.JPopupMenu jPMChangeView;
    private javax.swing.JMenuItem jPMChangeViewGo;
    private javax.swing.JPopupMenu isOvalRight;
    private javax.swing.JMenuItem acceptOval;
    private javax.swing.JMenuItem cancelOval;
    //Draw Oval
    private Graphics2D g2d;
    private Rectangle actrect;
    private int currX, currY, currW, currH;
    private Rectangle[] ovals = new Rectangle[50];
    private int numOvals = 0;
    //Farben der Ovale
    private Color[][] ovalColor = new Color[][]{
        {new Color(247, 204, 36), new Color(254, 104, 61), new Color(255, 33, 28)}, //ho,hr,r
        {new Color(84, 241, 96), new Color(255, 141, 28), new Color(254, 104, 61)}, //hg,o,hr
        {new Color(17, 215, 32), new Color(84, 241, 96), new Color(247, 204, 36)}, //g,hg,ho
        {new Color(247, 204, 36), new Color(254, 104, 61), new Color(255, 33, 28)}, //ho,hr,r
        {new Color(84, 241, 96), new Color(255, 141, 28), new Color(254, 104, 61)}, //hg,o,hr
        {new Color(17, 215, 32), new Color(84, 241, 96), new Color(247, 204, 36)}, //g,hg,ho
        {new Color(247, 204, 36), new Color(254, 104, 61), new Color(255, 33, 28)}, //ho,hr,r
        {new Color(84, 241, 96), new Color(255, 141, 28), new Color(254, 104, 61)}, //hg,o,hr
        {new Color(17, 215, 32), new Color(84, 241, 96), new Color(247, 204, 36)} //g,hg,ho
    };

    private Point currentPoint; //Point to store the current location of a new polygon edge
    private Polygon polygon = new Polygon();
    private List<NamedPolygon> polygons = new ArrayList<NamedPolygon>();

    public DrawArea(CenterPanel parent) {
        this.parent = parent;
        this.data = parent.getDataModel();

        initGuiComponents();
        initOwnComponents();
    }

    public void initGuiComponents() {

        this.setBackground(new java.awt.Color(230, 230, 230));
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawAreaMouseClicked(evt);
                setRightMouse(SwingUtilities.isRightMouseButton(evt));
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent event){
                drawAreaMouseMoved(event);
            }
        });

        GroupLayout drawAreaLayout = new GroupLayout(this);
        this.setLayout(drawAreaLayout);
        drawAreaLayout.setHorizontalGroup(
                drawAreaLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 670, Short.MAX_VALUE));
        drawAreaLayout.setVerticalGroup(
                drawAreaLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 628, Short.MAX_VALUE));
    }

    public void initOwnComponents() {
        this.nullpoint = new Point();
        this.maxpoint = new Point();
        this.borderWidth = 40;
        normal_point_radius = (int) (((float) normal_point_diameter / 2.0));
        big_point_radius = (int) (((float) big_point_diameter / 2.0));
        points = new Vector<Point>();
    }

    private void drawAreaMouseClicked(java.awt.event.MouseEvent evt) {
        int counter = 0;
        this.setMouse_over_a_point(false);

        for (Point point : this.points) {
            if (evt.getX() > (point.x - this.normal_point_radius)
                    && evt.getX() < (point.x + this.normal_point_radius)
                    && evt.getY() > (point.y - this.normal_point_radius)
                    && evt.getY() < (point.y + this.normal_point_radius)) {

                this.setIndex_mouse_over_point(counter);
                this.parent.markListElement(counter);
                this.setMouse_over_a_point(true);
                break;
            }
            ++counter;
        }

        if (this.mouse_over_a_point == false) {
            this.parent.markListElement(-1);
        }

        //wenn Zeichnen=AN, initialisiere drawOval
        if (data.getDraw()) {
            actrect = new Rectangle(0, 0, 0, 0);
            addMouseListener(new MyMouseListener());
            addMouseMotionListener(new MyMouseMotionListener());
        }

        this.repaint();

        if(data.isDrawArea()){
            addMouseListener(new drawAreaMouseListener());
        }
    }

    private void drawAreaMouseMoved(MouseEvent event){
        if(data.isDrawArea()){
            currentPoint = new Point();
            //polygon = new Polygon();
            addMouseMotionListener(new drawAreaMouseMotionListener());
            addMouseListener(new drawAreaMouseListener());
        }
    }
    /**
     * @author jDave
     * @date 10.04.11
     * This method searches the nearest line to the given coordinates.
     * There it sets the points coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    private void setPoint(int x, int y){
        Function functions[] = new Function[3];
        functions[0]=new Xaxis();
        functions[1]=new yAxis();
        functions[2]=new Qaxis();

        Point bestPoint = new Point();
        int bestDist = 1000000;
        for (Function function : functions) {
            Point curPoint = function.calcY(x, y);

            int dist = Math.abs(x - curPoint.x) + Math.abs(y -curPoint.y);
            if(dist < bestDist){
                bestDist = dist;
                bestPoint = curPoint;
            }
        }

        currentPoint = bestPoint;

    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;

        this.setGrapicalConstants();
        super.paint(g);
        this.drawBackground(g);
        this.drawNodePoints(g);
        g2d.setStroke(new BasicStroke(1.0f));


        
        if(data.isDrawArea()){
            //paint currentPoint

            Color currCol = g.getColor();
            g.setColor(Color.RED);
            g.fillOval(currentPoint.x, currentPoint.y, 10, 10);
            g.setColor(currCol);
            if(polygon.npoints >0){
                //paint polygon
                currCol = g.getColor();


                int x_index = (int)polygon.getBounds().getCenterX()/ (this.getWidth() / 3)%2;
                int y_index = (int)polygon.getBounds().getCenterY()/ (this.getWidth() / 3)%2;
                int transparency = (int) ((int) polygon.getBounds().getWidth() + polygon.getBounds().getHeight());

                g.setColor(ovalColor[2-x_index][2-y_index]);
                Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Math.abs(1 - (float) transparency / 1000) % 1);

                g2d.setComposite(c);

                g.fillPolygon(polygon);
                g.setColor(currCol);
            }
        }
        Color currCol = g.getColor();
        for (NamedPolygon pol : polygons) {
            int x_index = (int) pol.getBounds().getCenterX() / (this.getWidth() / 3)%2;
            int y_index = (int) pol.getBounds().getCenterY() / (this.getWidth() / 3)%2;
            int transparency = (int) ((int) pol.getBounds().getWidth() + pol.getBounds().getHeight());

            g.setColor(Color.black);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g.drawString(pol.getName(), (int)pol.getBounds().getCenterX(), (int)pol.getBounds().getCenterY());
            g.setColor(ovalColor[2 - x_index][2 - y_index]);
            Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.abs(1 - (float) transparency / 1000) % 1);

            g2d.setComposite(c);
            g.fillPolygon(pol);
        }
        g.setColor(currCol);

        // workaround for this bug: change chart settings while "edit areas" is activ
        if (data == null || actrect == null) {
            return;
        }

        //paint ovale if toggle-button clicked
        if (data.getDraw() == true) {
            if (actrect.x > 0 || actrect.y > 0) {
                g2d.setColor(this.getBackground());
                g2d.drawOval(currX, currY, currW, currH);
                currX = actrect.x;
                currY = actrect.y;
                currW = actrect.width;
                currH = actrect.height;
                g2d.setColor(Color.black);
                g2d.drawOval(
                        actrect.x,
                        actrect.y,
                        actrect.width,
                        actrect.height);
            }

        }

        //add ovals
        for (int i = 0; i < numOvals; i++) {
            Point middle = new Point((ovals[i].x + ovals[i].width / 2), (ovals[i].y + ovals[i].height / 2));
            int transparency = ovals[i].width + ovals[i].height;
            //je nach Bereich farbe zuordnen
            //  _|_|_
            //  _|_|_
            //   | |
            //über den Index wird dank Ganzzahldivision ein wert zwuschen 0 und 2 errechnet und so das entsprechende array-feld ausgewählt
            int x_index = (middle.x - 1) / (this.getWidth() / 3);
            int y_index = (middle.y - 1) / (this.getHeight() / 3);

            g2d.setColor(ovalColor[2 - x_index][2 - y_index]);
            g2d.drawString("Area" + numOvals, ovals[i].x, ovals[i].y);

            // alpha channel
            Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (1 - (float) transparency / 1000) % 1);

            g2d.setComposite(c);
            g2d.fillOval(
                    ovals[i].x,
                    ovals[i].y,
                    ovals[i].width,
                    ovals[i].height);
        }
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Draw the x- and y-axis
        g2d.drawLine(nullpoint.x, nullpoint.y, maxpoint.x + 10, nullpoint.y);
        g2d.drawLine(nullpoint.x, nullpoint.y, nullpoint.x, maxpoint.y - 10);

        Font tmp = g2d.getFont();
        g2d.setFont(new Font("Arial", 3, 18));
        g2d.drawString("PS", maxpoint.x + 10, nullpoint.y + 20);
        g2d.drawString("AS", nullpoint.x - 30, maxpoint.y - 15);
        g2d.setFont(tmp);

        //Draw the q-axis
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawLine(nullpoint.x, maxpoint.y, maxpoint.x, maxpoint.y);
        g2d.drawLine(maxpoint.x, nullpoint.y, maxpoint.x, maxpoint.y);

        //print the unit-lines and numbers
        //x- y-axis
        NumberFormat n = NumberFormat.getInstance(); //Zum Runden

        n.setMaximumFractionDigits(2);
        double x;

        g2d.setColor(Color.black);
        for (int width = 0, counter = 0; width <= getWidth(); ++counter) {
            width = (int) (counter * pixels_per_unit_width) + borderWidth;
            if (width > maxpoint.x) {
                break;
            }
            g2d.drawLine(width, nullpoint.y + 5, width, nullpoint.y - 5);

            x = (counter * (data.getMax_ps_qs() / numberofunitsteps));
            g2d.drawString(String.valueOf(n.format(x)), width - 10, nullpoint.y + 20);
        }

        for (int hight = nullpoint.y, counter = 0; hight > maxpoint.y; ++counter) {
            hight = (int) (nullpoint.y - (counter * pixels_per_unit_higth));
            if (hight < maxpoint.y) {
                break;
            } else {
                g2d.drawLine(nullpoint.x + 5, hight, nullpoint.x - 5, hight);

                if (counter == 0) {
                    continue;
                }
                x = (counter * (data.getMax_ps_qs() / numberofunitsteps));
                g2d.drawString(String.valueOf(n.format(x)), nullpoint.x - 33, hight + 3);
            }
        }

        //q-axis
        //print the unit-lines and numbers of the q axis
        g2d.setColor(new Color(150, 150, 150));
        for (int width = maxpoint.x, counter = 0; width > borderWidth; ++counter) {

            width = (int) (maxpoint.x - (counter * pixels_per_unit_width));

            if (width <= borderWidth) {
                break;
            }
            g2d.drawLine(width, maxpoint.y + 5, width, maxpoint.y - 5);

            if (counter == 0) {
                continue;
            }

            x = (float) numberofunitsteps / (float) (numberofunitsteps - counter);
            g2d.drawString(String.valueOf(n.format(x)), width - 10, maxpoint.y - 10);
        }


        for (int hight = nullpoint.y, counter = 0; hight > maxpoint.y; ++counter) {
            hight = (int) (nullpoint.y - (counter * pixels_per_unit_higth));
            if (hight < maxpoint.y) {
                break;
            } else {
                g2d.drawLine(maxpoint.x + 5, hight, maxpoint.x - 5, hight);
                if (counter == 0) {
                    continue;
                }
                x = ((double) (((double) counter) * ((double) (((double) 1) / ((double) numberofunitsteps)))));
                g2d.drawString(String.valueOf(n.format(x)), maxpoint.x + 10, hight + 10);
            }
        }

        //paint diagonal for Qmin
        int actualx;
        int actualy;
        if (data.getPassive() <= 1) {
            actualx = (maxpoint.x - nullpoint.x);
            actualy = (int) (data.getPassive() * (nullpoint.y - maxpoint.y));
        } else {
            actualx = (int) ((1 / data.getPassive()) * (maxpoint.x - nullpoint.x));
            actualy = (int) (nullpoint.y - maxpoint.y);
        }
        g2d.setColor(as_ps);
        Point end_point = new Point(nullpoint.x + actualx, nullpoint.y - actualy);
        g2d.drawLine(nullpoint.x, nullpoint.y, end_point.x, end_point.y);

        tmp = g2d.getFont();
        g2d.setFont(new Font("Arial", 3, 14));
        drawQmin = new Point(end_point.x - 20, end_point.y - 7);
        g2d.drawString("Q= " + n.format(data.getPassive()), drawQmin.x, drawQmin.y);
        g2d.setFont(tmp);

        //paint diagonal for Qaverage
        if (data.getAverage() <= 1) {
            actualx = (maxpoint.x - nullpoint.x);
            actualy = (int) (data.getAverage() * (nullpoint.y - maxpoint.y));
        } else {
            actualx = (int) ((1 / data.getAverage()) * (maxpoint.x - nullpoint.x));
            actualy = (int) (nullpoint.y - maxpoint.y);
        }
        g2d.setColor(new Color(195, 195, 195));
        end_point = new Point(nullpoint.x + actualx, nullpoint.y - actualy);
        g2d.drawLine(nullpoint.x, nullpoint.y, end_point.x, end_point.y);

        g2d.setColor(new Color(140, 140, 140));
        tmp = g2d.getFont();
        g2d.setFont(new Font("Arial", 3, 14));
        g2d.drawString("Q= " + n.format(data.getAverage()), end_point.x - 20, end_point.y - 7);
        g2d.setFont(tmp);

        // Diagnole zeichnen für Qmax
        if (data.getActive() <= 1) {
            actualx = (maxpoint.x - nullpoint.x);
            actualy = (int) (data.getActive() * (nullpoint.y - maxpoint.y));
        } else {
            actualx = (int) ((1 / data.getActive()) * (maxpoint.x - nullpoint.x));
            actualy = (int) (nullpoint.y - maxpoint.y);
        }
        g2d.setColor(as_ps);
        end_point = new Point(nullpoint.x + actualx, nullpoint.y - actualy);
        g2d.drawLine(nullpoint.x, nullpoint.y, end_point.x, end_point.y);

        tmp = g2d.getFont();
        g2d.setFont(new Font("Arial", 3, 14));
        drawQmax = new Point(end_point.x - 20, end_point.y - 7);
        g2d.drawString("Q= " + n.format(data.getActive()), drawQmax.x, drawQmax.y);
        g2d.setFont(tmp);

        //primt Hyperbel 1 for konst_p;
        Polygon hyperbel1 = new Polygon();
        Polygon hyperbel2 = new Polygon();
        double qs1 = 0;
        double qs2 = 0;

        for (double ps = 0.1; ps < data.getMax_ps_qs(); ps = ps + 0.1) {

            qs1 = ((double) data.getStable()) / ((double) ps);
            qs2 = ((double) data.getCritical()) / ((double) ps);


            if (qs1 < data.getMax_ps_qs()) {
                hyperbel1.addPoint(((int) (ps * this.pixels_per_unit_norm_width)) + this.borderWidth,
                        (this.nullpoint.y - ((int) (qs1 * this.pixels_per_unit_norm_higth))));
            }

            if (qs2 < data.getMax_ps_qs()) {
                hyperbel2.addPoint(((int) (ps * this.pixels_per_unit_norm_width)) + this.borderWidth,
                        (this.nullpoint.y - ((int) (qs2 * this.pixels_per_unit_norm_higth))));
            }
        }

        g2d.setColor(this.q);
        g2d.drawPolyline(hyperbel1.xpoints, hyperbel1.ypoints, hyperbel1.npoints);
        g2d.drawPolyline(hyperbel2.xpoints, hyperbel2.ypoints, hyperbel2.npoints);
        g2d.setColor(Color.black);

    }

    private void drawNodePoints(Graphics g) {
        g2d = (Graphics2D) g;

        double[] as = data.getAs();
        double[] ps = data.getPs();

        Point pos = new Point();
        this.points.removeAllElements();
        int activeCounter = -1;
        for (int counter = 0; counter < as.length; counter++) {
            pos.x = this.nullpoint.x + (int) ((ps[counter] * this.pixels_per_unit_norm_width));
            pos.y = this.nullpoint.y - (int) ((as[counter] * this.pixels_per_unit_norm_higth));

            this.points.add(new Point(pos));

            if ((this.mouse_over_a_point == true && counter == this.getIndex_mouse_over_point()) && data.getDraw() == false) {
                activeCounter = counter;
                g2d.setColor(this.getBackground());
                g2d.fillRect(nullpoint.x - 38, pos.y - 12, 30, 20);
                g2d.fillRect(pos.x - 10, nullpoint.y + 3, 30, 20);

                g2d.setColor(Color.BLUE);
                g2d.fillOval(pos.x - big_point_radius, pos.y - big_point_radius, big_point_diameter, big_point_diameter);

                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", 1, 12));
                g2d.drawString(String.valueOf(ps[counter]), pos.x - 6, nullpoint.y + 20);

                g2d.setFont(new Font("Arial", 1, 12));
                g2d.drawString(String.valueOf(as[counter]), nullpoint.x - 33, pos.y + 3);

                g2d.drawLine(pos.x, pos.y, pos.x, nullpoint.y);
                g2d.drawLine(pos.x, pos.y, nullpoint.x, pos.y);

                g2d.setColor(Color.BLACK);
                Font tmp = g2d.getFont();
                g2d.setFont(new Font("Arial", 1, 16));

                g2d.setFont(tmp);
                g2d.setColor(Color.black);


                jPMChangeView = new javax.swing.JPopupMenu();
                jPMChangeViewGo = new javax.swing.JMenuItem();
                jPMChangeViewGo.setText("Go to Table");
                jPMChangeViewGo.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jPMChangeViewGoActionPerformed(evt);
                    }
                });
                jPMChangeView.add(jPMChangeViewGo);


                if (this.getRightMouse()) {
                    jPMChangeView.show(this, pos.x, pos.y);
                }

            } else {
                g2d.setColor(Color.black);
                g2d.fillOval(pos.x - normal_point_radius, pos.y - normal_point_radius, normal_point_diameter, normal_point_diameter);

                Font tmp = g2d.getFont();
                g2d.setFont(new Font("Arial", 0, 12));
                g2d.setFont(tmp);

            }
        }

        if (activeCounter != -1) {
            pos.x = this.nullpoint.x + (int) ((ps[activeCounter] * this.pixels_per_unit_norm_width));
            pos.y = this.nullpoint.y - (int) ((as[activeCounter] * this.pixels_per_unit_norm_higth));
            //Wirkung

            g2d.setColor(Color.BLUE);
            g2d.fillOval(pos.x - big_point_radius, pos.y - big_point_radius, big_point_diameter, big_point_diameter);

            g2d.setColor(this.getBackground());
            int boxwidth = 100;
            int abstandx = 10;
            int abstandy = 10;
            int boxheight = 50;
            int xpos;
            int ypos;
            if (pos.x < this.getWidth() / 2) {
                xpos = pos.x + abstandx;
            } else {
                xpos = pos.x - boxwidth - abstandx;
            }
            if (pos.y < this.getHeight() / 2) {
                ypos = pos.y + abstandy;
            } else {
                ypos = pos.y - boxheight - abstandy;
            }
            g2d.fillRect(xpos, ypos, boxwidth, boxheight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(xpos, ypos, boxwidth, boxheight);

            // Werteentscheidung
            double Q = as[activeCounter] / ps[activeCounter];
            double W = as[activeCounter] * ps[activeCounter];
            String Beschriftung1 = "";
            String Beschriftung2 = "";
            if (W > data.getCritical()) {
                Beschriftung1 = "kritisches";
                Beschriftung2 = "Verhalten";
            } else {
                if (Q > data.getActive()) {
                    Beschriftung1 = "aktiv";
                } else if (Q < data.getPassive()) {
                    Beschriftung1 = "passiv";
                }
                if (W < data.getStable() && !Beschriftung1.equals("")) {
                    Beschriftung2 = "stabilisierend";
                } else if (W < data.getStable()) {
                    Beschriftung1 = "stabilisierend";
                }
                if (Q > data.getPassive() && Q < data.getActive() && W > data.getStable() && W < data.getCritical()) {
                    Beschriftung1 = "neutrales";
                    Beschriftung2 = "Verhalten";
                }
            }
            g2d.setColor(Color.black);
            int width1 = g2d.getFontMetrics(g2d.getFont()).stringWidth(Beschriftung1);
            int width2 = g2d.getFontMetrics(g2d.getFont()).stringWidth(Beschriftung2);
            g2d.drawString(Beschriftung1, xpos + boxwidth / 2 - width1 / 2, ypos + 20);
            g2d.drawString(Beschriftung2, xpos + boxwidth / 2 - width2 / 2, ypos + 40);
        }
    }

    private void setGrapicalConstants() {

        this.nullpoint.x = this.borderWidth;
        this.nullpoint.y = this.getHeight() - this.borderWidth;

        this.maxpoint.x = this.getWidth() - this.borderWidth;
        this.maxpoint.y = this.borderWidth;

        this.pixels_per_unit_higth = ((this.getHeight() - (2.0 * this.borderWidth)) / this.numberofunitsteps);
        this.pixels_per_unit_width = ((this.getWidth() - (2.0 * this.borderWidth)) / this.numberofunitsteps);

        this.pixels_per_unit_norm_higth = ((this.getHeight() - (2.0 * this.borderWidth)) / this.data.getMax_ps_qs());
        this.pixels_per_unit_norm_width = ((this.getWidth() - (2.0 * this.borderWidth)) / this.data.getMax_ps_qs());
    }

    private void jPMChangeViewGoActionPerformed(java.awt.event.ActionEvent evt) {

        java.awt.Window[] array = KSMTable.getWindows();

        for (int i = array.length - 1; i >= 0; i--) {

            if (array[i].toString().contains("KSM Table-Editor")) {
                array[i].setVisible(true);
                array[i].setSize(Toolkit.getDefaultToolkit().getScreenSize());
                KSMTable ksmTable = (KSMTable) array[i];
                int row = this.getIndex_mouse_over_point();
                int col = this.getIndex_mouse_over_point() + 1;
                ksmTable.setFromGraphic(true);
                ksmTable.setMarkedRow(row);
                ksmTable.setMarkedCol(col);
                ksmTable.reloadAll();
                ksmTable.getTable().scrollRectToVisible(ksmTable.getTable().getCellRect(row, col, true)); // scrollt die Zelle in den sichtbaren Bereich

                break;
            }
        }
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i].toString().contains("EdgeAnalysing")) {
                array[i].dispose();
                break;
            }
        }
    }

    public Color setPointColor(String color) {
        if (color.equals("light red")) {
            return Color.red;
        } else if (color.equals("dark red")) {
            return Color.red;
        } else if (color.equals("light orange")) {
            return Color.orange;
        } else if (color.equals("dark orange")) {
            return Color.orange;
        } else if (color.equals("light yellow")) {
            return Color.yellow;
        } else if (color.equals("dark yellow")) {
            return Color.yellow;
        } else if (color.equals("light green")) {
            return Color.green;
        } else if (color.equals("dark green")) {
            return Color.green;
        } else if (color.equals("light blue")) {
            return Color.blue;
        } else if (color.equals("dark blue")) {
            return Color.blue;
        } else {
            return Color.black;
        }
    }

    private void OvalApplyActionPerformed(java.awt.event.ActionEvent evt) {
        ovals[numOvals] = new Rectangle(currX, currY, currW, currH);
        numOvals++;
        String description = JOptionPane.showInputDialog(parent, "Give in a description..", "Area Description", 3);
        data.fillLegendList(numOvals, description);
        this.repaint();
    }

    private void OvalCancelActionPerformed(java.awt.event.ActionEvent evt) {
        this.repaint();
    }

    public int getIndex_mouse_over_point() {
        return index_mouse_over_point;
    }

    public void setIndex_mouse_over_point(int index_mouse_over_point) {
        this.index_mouse_over_point = index_mouse_over_point;
    }

    public void setMouse_over_a_point(boolean mouse_over_a_point) {
        this.mouse_over_a_point = mouse_over_a_point;
    }

    public void setRightMouse(boolean rightMouse) {
        this.rightMouse = rightMouse;
    }

    public boolean getRightMouse() {
        return rightMouse;
    }

    //events for drawOval
    class MyMouseListener
            extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            actrect = new Rectangle(event.getX(), event.getY(), 0, 0);
        }

        public void mouseReleased(MouseEvent event) {
            if (data.getDraw() == true && !SwingUtilities.isRightMouseButton(event)) {
                isOvalRight = new javax.swing.JPopupMenu();
                acceptOval = new javax.swing.JMenuItem();
                acceptOval.setText("Accept");
                cancelOval = new javax.swing.JMenuItem();
                cancelOval.setText("Cancel");
                acceptOval.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        OvalApplyActionPerformed(evt);
                    }
                });
                cancelOval.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        OvalCancelActionPerformed(evt);
                    }
                });
                isOvalRight.add(acceptOval);
                isOvalRight.add(cancelOval);
                isOvalRight.show(parent.getDrawArea(), actrect.x, actrect.y);
                repaint();
            }
        }
    }

    class MyMouseMotionListener
            extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();
            if ((x > actrect.x && y > actrect.y)
                    && (x < data.getKSMEditor().getX()
                    + data.getKSMEditor().getWidth()
                    && y < data.getKSMEditor().getY()
                    + data.getKSMEditor().getHeight())) {
                actrect.width = x - actrect.x;
                actrect.height = y - actrect.y;
            }
            repaint();
        }
    }

    class drawAreaMouseMotionListener extends MouseMotionAdapter{

        @Override
        public void mouseMoved(MouseEvent event){
            setPoint(event.getX(), event.getY());
            repaint();
        }
    }

    /**
     *
     * @author jDave
     * @date 30.03.11
     * MouseAdapter impl, that enables the drawing of a polygon,
     * whose points are placed on existing lines
     * @see MouseAdapter
     */
    class drawAreaMouseListener extends MouseAdapter {

        private void ApplyActionPerformed(ActionEvent evt){
            
            numOvals++;
            String description = JOptionPane.showInputDialog(parent, "Give in a description..", "Area Description", 3);
            if(description == null){
                polygon = new Polygon();
                repaint();
                return;
            }
            NamedPolygon pol = new NamedPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
            pol.setName(description);
            searchInnerPoints(pol);
            polygons.add(pol);
            data.fillLegendList(numOvals, description);
            polygon = new Polygon();
            repaint();
            
        }
        private void CancelActionPerformed(ActionEvent evt){
            polygon = new Polygon();
        }

        /**
         * Simple Click: Adds the currently marked point on a line to the polygon and draws it
         * Double Click: like simple Click, but also finishes the polygon definition
         * and opens a dialog to name the polygon, or to reject it
         * @param event
         */
        @Override
        public void mouseClicked(MouseEvent event) {
            
            //TODO Bei Doppelklick aufhören und einen Dialog zur Namensvergabe anzeigen
            
            //Double Click
            if(event.getClickCount()>1){

                isOvalRight = new javax.swing.JPopupMenu();
                acceptOval = new javax.swing.JMenuItem();
                acceptOval.setText("Accept");
                cancelOval = new javax.swing.JMenuItem();
                cancelOval.setText("Cancel");
                acceptOval.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ApplyActionPerformed(evt);
                    }
                });
                cancelOval.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        CancelActionPerformed(evt);
                    }
                });
                isOvalRight.add(acceptOval);
                isOvalRight.add(cancelOval);
                isOvalRight.show(parent.getDrawArea(), event.getX(), event.getY());
                repaint();
                
            }else{
                //Single Click
                polygon.addPoint(currentPoint.x, currentPoint.y);
                repaint();
            }
        }
    }

    public void searchInnerPoints(NamedPolygon pol){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(pol.getName());
        List<String> innerPoints = new ArrayList<String>();
        for(int i=0; i<points.size();i++){
            if(pol.contains(points.elementAt(i))){
                String name = data.getListModelNodes().getElementAt(i).toString();
                innerPoints.add(name);
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(name,false);
                root.add(child);
            }
        }
        
        data.getPolyModel().insertNodeInto(root, data.getRoot(), 0);
    }
}
