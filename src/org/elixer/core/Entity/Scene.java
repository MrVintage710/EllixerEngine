package org.elixer.core.Entity;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by aweso on 3/2/2017.
 */
public final class Scene {

    private static ModuleCamera currCamera = new Entity("ModuleCamera")
            .addModuleR(new ModuleCamera(80, 0.001f, 100000000));

    private ArrayList<Entity> objects = new ArrayList<>();

    public Scene() {
        addEntity(currCamera.getEntity());
    }

    public void addEntity(Entity entity) {
        objects.add(entity);
    }

    public ArrayList<Entity> getObjects() {
        return objects;
    }

    public static ModuleCamera getCurrCamera() {
        return currCamera;
    }

    public static void setCurrCamera(ModuleCamera currCamera) {
        Scene.currCamera = currCamera;
    }
}
