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
    private int yMax = 0;
    private double slope = 1;

    public Qaxis(int areaHeight, int areaWidth, int borderLength, double axisSlope)
    {
        this.yOffset = borderLength;
        this.yMax = (areaHeight - borderLength);
        this.xOffset = borderLength;
        this.slope = axisSlope;
    }
    
    public Point calcY(int x, int y) {
        if((x>=xOffset) && (y>=yOffset))
        {
            return new Point((int)x, (int)y);
        }
        else
        {
            return  new Point(0,0);
        }

    }

}
