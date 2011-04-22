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

import plugin.interfaces.IPluggable;
import plugin.interfaces.IPluginManager;

import pluginGui.SumChartPluginPerspective;

public class SumChartPlugin implements IPluggable {

    private IPluginManager manager;
    private SumChartPluginPerspective perspective;

    public void setPluginManager(IPluginManager manager) {
        this.manager = manager;
    }

    public boolean start() {
        perspective = new SumChartPluginPerspective();
        this.manager.addPerspective(perspective);
        return true;
    }

    public boolean refresh() {
        perspective.refresh();
        return true;
    }
}
