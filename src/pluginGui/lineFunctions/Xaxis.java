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

    /**
     *
     * @param x doesn't matter
     * @param y the y-Value
     * @return Point(0, y-Value)
     */
    public Point calcY(int x, int y) {
        //System.out.print(x);
        //System.out.print(", "+y+ "\n");

        int yOffset = 460;
        int xOffset = 40;
        int maxX = 520;

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
