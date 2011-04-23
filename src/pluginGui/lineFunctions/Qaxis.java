/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui.lineFunctions;

import java.awt.Point;

/**
 *
 * @author david
 */
public class Qaxis implements Function{

    private int xOffset = 0;
    private int yOffset = 0;
    private int xMax = 0;
    private int yMax = 0;
    private double slope = 1;
    private int qY = 0;
    private int qX = 0;

    public Qaxis(int areaHeight, int areaWidth, int borderLength, double axisSlope, Point QPoint)
    {
        this.yOffset = borderLength;
        this.yMax = (areaHeight - borderLength);
        this.xOffset = borderLength;
        this.xMax = (areaWidth - borderLength);
        this.slope = axisSlope;
        this.qY = QPoint.y;
        this.qX = QPoint.x;
    }
    
    public Point calcY(int x, int y) {
        if((x>=xOffset) && (y>=yOffset) && (x<=xMax) && (y<=yMax))
        {
            int height = 0;
            int b = 0;
            b = ((int) ((yMax - qY) - (slope * qX)));
            height = (int) (slope * x) + b;
            height = (yMax - height);

            return new Point((int)x, height);
        }
        else
        {
            return  new Point(xOffset, yOffset);
        }

    }

}
