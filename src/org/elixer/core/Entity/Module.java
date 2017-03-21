package org.elixer.core.Entity;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.elixer.core.Util.Console;

/**
 * Created by aweso on 3/2/2017.
 */
public abstract class Module {

    protected Entity entity;
    protected boolean isActive = true;

    protected abstract void onUpdate();

    public void update() {
        if(isActive) {
            onUpdate();
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        for(Module mod: entity.getModules()) {
            if(clazz.isInstance(mod)) {
                return (T) mod;
            }
        }

        Console.printerr("MODULE FIND ERROR: Module of type '" + clazz.toString() + "' wasn't found in entity '" + entity.getName() +"'. Returning null.");
        return null;
    }
}
