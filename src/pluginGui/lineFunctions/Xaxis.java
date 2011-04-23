/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui.lineFunctions;

import java.awt.Point;

/**
 *
 * @author david
 * @date 10.4.11
 */
public class Xaxis implements Function {

    private int yOffset = 460;
    private int xOffset = 40;
    private int maxX = 520;


    public Xaxis(int areaHeight, int areaWidth, int borderLength)
    {
        this.xOffset = borderLength;
        this.yOffset = (areaHeight - borderLength);
        this.maxX = (areaWidth - borderLength);
    }

    /**
     *
     * @param x doesn't matter
     * @param y the y-Value
     * @return Point(0, y-Value)
     */
    public Point calcY(int x, int y) {
        //System.out.print(x);
        //System.out.print(", "+y+ "\n");

        if(x<xOffset)
        {
            return new Point(xOffset,yOffset);
        }
        else{
            if(x>maxX)
            {
                return new Point(maxX,yOffset);
            }
            else
            {
                return new Point((int)x,yOffset);
            }
        }
        
    }

}
