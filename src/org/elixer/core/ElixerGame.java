package org.elixer.core;

import org.elixer.core.Display.Model.*;
import org.elixer.core.Display.UI.GraphicUI;
import org.elixer.core.Display.UI.PanelUI;
import org.elixer.core.Display.Window;
import org.elixer.core.Entity.Entity;
import org.elixer.core.Entity.ModelRenderer;
import org.elixer.core.Entity.Module;
import org.elixer.core.Entity.Scene;
import org.elixer.core.Registry.Instancing;
import org.elixer.core.Util.Console;
import org.elixer.core.Util.Debug;
import org.elixer.core.Util.Input;
import org.elixer.core.Util.Ref;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by brookspalin on 2/13/17.
 */

//TO DO Section
    //TODO Finish the first iteration of the input system.
    //TODO Create a dynamic game loop with a fixed step and a free step.
    //TODO Add module interactivity.
    //TODO Do first round of optimisations.
    //TODO Add .obj support for meshes.
    //TODO first lighting model
    //TODO make sure all classes that need to destroy data use the instancer.
    //TODO make render Layers more efficient.
    //TODO Fix projection matrix: any aspect ratio other than 1:1
    //TODO Make Text/UI rendering more efficient. Make them only use 1 mesh.


public class ElixerGame {

    private boolean isRunning = false;
    public static Window currWindow;
    private Mesh mesh;

    Scene currScene = new Scene();

    public static void main(String[] args) {
        ElixerGame game = new ElixerGame();
        game.start();
    }

    private void end() {
        Instancing.destroyAllInstaces();
        currWindow.destroy();
    }

    private void run() {

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
        };

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f
        };

        mesh = new Mesh(indices, textureCoords, vertices);

        Model model = new Model(mesh, new Texture("brick.png"));

        PanelUI panelUI = new PanelUI();
        panelUI.addElement(new GraphicUI(new Texture("glass.png"), new Vector2f(0,0)));

        Entity entity = new Entity("Test Model", new Vector3f(0,0,0), new Vector3f(0, 0, 0), new Vector3f(100, 100, 100));

        entity.addModule(new ModelRenderer(model));
        entity.addModule(panelUI);

        currScene.addEntity(entity);

        Debug.addMessage("clicked", "Clicked: (" + Input.clickedX + ", " + Input.clickedY + ")");
        Debug.addMessage("inst", Instancing.printOut());

        float counter = 0f;
        while (!currWindow.shouldClose() && isRunning) {
            Debug.editMessage("clicked", "Clicked: (" + Input.clickedX + ", " + Input.clickedY + ")");
            Debug.editMessage("inst", Instancing.printOut());
            render();
            currWindow.update();

            counter += 0.05;
        }
    }

    private void init() {
        currWindow = new Window("Elixer " + Ref.VERSION, 1100, 1100);

        //GL CALLS
        glEnable(GL_DEPTH_TEST);

        //DATA INIT
        currScene.addEntity(Debug.getEntity());

        Debug.toggle();

        Console.println("Elixer " + Ref.VERSION);
        Console.println("OpenGL " + Ref.OPENGL_VERSION);
    }

    public void start() {
        isRunning = true;
        init();
        run();
        end();
    }

    public void stop() {
        isRunning = false;
    }

    private void render() {
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);
        glClear(GL_DEPTH_BUFFER_BIT);

        for (Entity entity : currScene.getObjects()) {
            for (Module mod : entity.getModules()) {
                if (mod instanceof IRenderable && mod.isActive() && ((IRenderable) mod).renderLayer() == RenderLayer.SCENE) {
                    ((IRenderable) mod).draw();
                }
            }
            entity.onUpdate();
        }

        for (Entity entity : currScene.getObjects()) {
            for (Module mod : entity.getModules()) {
                if (mod instanceof IRenderable && mod.isActive() && ((IRenderable) mod).renderLayer() == RenderLayer.UI) {
                    ((IRenderable) mod).draw();
                }
            }
        }
    }
}