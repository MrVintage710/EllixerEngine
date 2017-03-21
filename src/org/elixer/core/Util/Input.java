package org.elixer.core.Util;

/**
 * Created by aweso on 3/3/2017.
 */
public class Input {

    public static boolean W = false;
    public static boolean A = false;
    public static boolean S = false;
    public static boolean D = false;
    public static boolean UP= false;
    public static boolean DOWN = false;
    public static boolean LEFT = false;
    public static boolean RIGHT = false;
    public static boolean LEFT_ALT = false;
    public static boolean SPACE = false;

    public static boolean justMoved = false;
    public static float X = 0;
    public static float Y = 0;
    public static float clickedX = 0;
    public static float clickedY = 0;

    private static float xLast;
    private static float yLast;

    public static void setX(float x) {
        xLast = X;
        X = x;
    }

    public static float getXD(){
        float XD = X - xLast;
        xLast = X;
        return XD;
    }

    public static void setY(float y) {
        yLast = Y;
        Y = y;
    }

    public static float getYD(){
        float YD = Y - yLast;
        yLast = Y;
        return YD;
    }
}
