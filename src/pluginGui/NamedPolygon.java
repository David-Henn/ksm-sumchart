/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pluginGui;

import java.awt.Polygon;

/**
 *
 * @author david
 */
public class NamedPolygon extends Polygon{
    String name;

    public NamedPolygon(int xpoints[], int ypoints[], int npoints){
        super(xpoints, ypoints, npoints);
    }
    public NamedPolygon(){
        super();
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

}
