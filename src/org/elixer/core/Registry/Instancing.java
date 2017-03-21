package org.elixer.core.Registry;

import org.elixer.core.Display.Model.Mesh;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;

/**
 * Created by aweso on 2/27/2017.
 */
public final class Instancing {
    private static ArrayList<Mesh> meshes = new ArrayList<>();
    private static ArrayList<Integer> textures = new ArrayList<>();
    private static ArrayList<Integer> shaders = new ArrayList<>();
    private static ArrayList<Integer> programs = new ArrayList<>();
    private static ArrayList<Long> windows = new ArrayList<>();

    public static void addWindow(long windowID) {
        windows.add(windowID);
    }

    public static void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public static void removeMesh(Mesh mesh) {
        meshes.remove(mesh);
    }

    public static void addTexture(int id) {textures.add(id); }

    public static void addShader(int id) {shaders.add(id); }

    public static void destroyAllInstaces() {

        for(Mesh mesh: meshes) {
            mesh.destroy(false);
        }
        meshes.clear();

        for(int tex: textures) {
            glDeleteTextures(tex);
        }

        for(int shader: shaders) {
            glDeleteShader(shader);
        }
    }

    public static String printOut() {
        return "Meshes: " + meshes.size() + " Textures: " + textures.size() + " Windows: " + windows.size();
    }
}
