/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui.lineFunctions;

import java.awt.Point;

/**
 *
 * @author jDave, Mischa
 * @date 10.4.11
 */
public class yAxis implements Function{

    int xOffset = 40;
    int maxY = 460;
    int yOffset = 40;

    public yAxis(int areaHeight, int areaWidth, int borderLength)
    {
        this.xOffset = borderLength;
        this.yOffset = borderLength;
        this.maxY = (areaHeight - borderLength);
    }


    /**
     *
     * @param x x-Value
     * @param y doesn't matter
     * @return Point(x-Value,0)
     */
    public Point calcY(int x, int y) {

        if(y>maxY)
        {
            return new Point(xOffset, maxY);
        }
        else
        {
            if(y<yOffset)
            {
                return new Point(xOffset, yOffset);
            }
            else
            {
                return new Point(xOffset, (int)y);
            }
        }
    }

}
