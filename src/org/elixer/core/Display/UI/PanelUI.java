package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.RenderLayer;
import org.elixer.core.Display.Model.IRenderable;
import org.elixer.core.Entity.Module;

import java.util.ArrayList;

/**
 * Created by aweso on 3/9/2017.
 */
public class PanelUI extends Module implements IRenderable {

    private ArrayList<ElementUI> elements = new ArrayList<>();

    public void addElement(ElementUI element) {
        elements.add(element);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void draw() {
        for(ElementUI element: elements) {
            element.draw();
        }
    }

    @Override
    public RenderLayer renderLayer() {
        return RenderLayer.UI;
    }
}
