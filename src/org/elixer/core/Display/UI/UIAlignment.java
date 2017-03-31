package org.elixer.core.Display.UI;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by aweso on 3/26/2017.
 */
public enum UIAlignment {
    TOP_LEFT(0,0,0,0),
    TOP(0.5f, 0, 0.5f, 0),
    TOP_RIGHT(1, 0, 1, 0),
    CENTER_LEFT(0, 0.5f, 0, 0.5f),
    CENTER(0.5f, 0.5f, 0.5f, 0.5f),
    CENTER_RIGHT(1, 0.5f, 1, 0.5f),
    BOTTOM_LEFT(0, 1, 0, 1),
    BOTTOM(0.5f, 1, 0.5f, 1),
    BOTTOM_RIGHT(1, 1, 1, 1);

    private Vector2f pivot;
    private Vector2f pos;

    UIAlignment(float posX, float posY, float pivotX, float pivotY) {
        this.pos = new Vector2f(posX, posY);
        this.pivot = new Vector2f(pivotX, pivotY);
    }

    public void set(ElementUI element) {
        element.setPivot(pivot);
        element.setPos(pos);
    }
}
