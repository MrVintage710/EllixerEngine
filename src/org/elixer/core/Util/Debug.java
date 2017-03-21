package org.elixer.core.Util;

import org.elixer.core.Display.UI.Font;
import org.elixer.core.Display.UI.PanelUI;
import org.elixer.core.Display.UI.TextUI;
import org.elixer.core.ElixerGame;
import org.elixer.core.Entity.Entity;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

/**
 * Created by aweso on 3/4/2017.
 */
public final class Debug {

    private static Font font = new Font("debug.fnt", 5);
    private static boolean isActive = true;
    private static PanelUI panel = new Entity("DEBUG").addModuleR(new PanelUI());
    private static HashMap<String, TextUI> messages = new HashMap<>();

    public static void addMessage(String id, String message) {
        TextUI textUI = new TextUI(message, font, new Vector2f(0, 0));
        textUI.getPos().set(0, font.getSize() * (messages.size() * 40));
        messages.put(id, textUI);
        panel.addElement(textUI);
    }

    public static void addMessage(String id, Object message) {
        addMessage(id, message.toString());
    }

    public static void editMessage(String id, String message) {
        messages.get(id).setMessage(message);
    }

    public static void editMessage(String id, Object message) {
        if(messages.containsKey(id))
            messages.get(id).setMessage(message);
    }

    public static void toggle() {
        isActive = !isActive;
        ElixerGame.currWindow.setCursorDisabled(isActive);
        panel.setActive(isActive);
    }

    public static Entity getEntity() {
        return panel.getEntity();
    }

    public static boolean isActive() {
        return isActive;
    }
}
