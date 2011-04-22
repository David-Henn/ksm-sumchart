/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui.lineFunctions;

import java.awt.Point;

/**
 *
 * @author jDave
 * @date 10.4.11
 */
public class yAxis implements Function{

    /**
     *
     * @param x x-Value
     * @param y doesn't matter
     * @return Point(x-Value,0)
     */
    public Point calcY(int x, int y) {
        return new Point((int)x,0);
    }

}
