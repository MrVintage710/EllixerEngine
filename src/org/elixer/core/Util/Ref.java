package org.elixer.core.Util;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by aweso on 2/13/2017.
 */
public class Ref {

    public static final String VERSION = "Alpha 2";
    public static final String OS = System.getProperty("os.name");
    public static final String OPENGL_VERSION = glGetString(GL_VERSION);

    public static final Vector3f X_AXIS = new Vector3f(1,0,0);
    public static final Vector3f Y_AXIS = new Vector3f(0,1,0);
    public static final Vector3f Z_AXIS = new Vector3f(0,0,1);
}
