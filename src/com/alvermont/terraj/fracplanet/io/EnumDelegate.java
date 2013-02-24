/*
 * Java Terrain and Stellar System Ports
 *
 * Copyright (C) 2006 Martin H. Smith based on work by original
 * authors.
 *
 * Released under the terms of the GNU General Public License
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
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * Linking TerraJ statically or dynamically with other modules is making a
 * combined work based on TerraJ. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 *
 * In addition, as a special exception, the copyright holders of TerraJ
 * give you permission to combine this program with free software programs
 * or libraries that are released under the GNU LGPL and with code included
 * in the standard release of JOGL, Java Getopt and FreeMarker under the BSD
 * license (or modified versions of such code, with unchanged license) and with
 * Apache Commons and Log4J libraries under the Apache license (or modified versions
 * of such code. You may copy and distribute such a system following the terms
 * of the GNU GPL for TerraJ and the licenses of the other code concerned,
 * provided that you include the source code of that other code when and as the
 * GNU GPL requires distribution of source code.
 *
 * Note that people who make modified versions of TerraJ are not obligated to grant
 * this special exception for their modified versions; it is their choice whether
 * to do so. The GNU General Public License gives permission to release a modified
 * version without this exception; this exception also makes it possible to release
 * a modified version which carries forward this exception.
 */

/*
 * EnumDelegate.java
 *
 * Created on January 14, 2006, 10:33 AM
 */
package com.alvermont.terraj.fracplanet.io;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 * Persistence delegate for enums. Why oh why didn't they implement
 * this in Java 1.5 itself? I got this code from the Java forums and
 * changed it a bit:
 *
 * http://forum.java.sun.com/thread.jspa?threadID=419927&tstart=60
 *
 * @author martin
 * @version $Id: EnumDelegate.java,v 1.4 2006/07/06 06:59:43 martin Exp $
 */
public class EnumDelegate<T extends Enum> extends PersistenceDelegate
{
    private Class<T> type;

    /**
     * Create a new EnumDelegate instance of the specified type
     *
     * @param type The type that this object is to be a persistence delegate for
     */
    public EnumDelegate(Class<T> type)
    {
        this.type = type;
    }

    /**
     * Implement the <code>mutatesTo</code> method of the <code>PersistenceDelegate</code>
     * interface to indicate whether one object can be mutated into another.
     *
     * @param oldInstance The old instance of the object
     * @param newInstance The new instance of the object
     * @return <pre>true</pre> if this instance can be mutated, otherwise
     * <pre>false</pre>
     */
    protected boolean mutatesTo(Object oldInstance, Object newInstance)
    {
        return oldInstance == newInstance;
    }

    /**
     * Implement the <code>instantiate</code> method of the
     * <code>PersistenceDelegate</code> interface to instantiate an object
     * of this type.
     *
     * @param oldInstance The object instance
     * @param out The encoder to be used
     * @return An expression instantiating the object
     */
    protected Expression instantiate(Object oldInstance, Encoder out)
    {
        return new Expression(
            oldInstance, Enum.class, "valueOf",
            new Object[] { this.type, oldInstance.toString() });
    }
}