package org.elixer.core.Display.Model;

import org.elixer.core.Registry.Instancing;
import org.elixer.core.Util.Console;
import org.elixer.core.Util.Util;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by aweso on 2/13/2017.
 */
public class Mesh {

    protected List<Integer> vbos = new ArrayList<>();
    protected int vaoID;
    protected int vertecies;

    public Mesh(int[] indicies, float[] uv, float[] points) {
        vertecies = indicies.length;

        initVAO(indicies);
        {
            createVBO(Util.floatsToFloatBuffer(points), 0, 3);
            createVBO(Util.floatsToFloatBuffer(uv), 1, 2);
        }
        endVAO();

        Instancing.addMesh(this);
    }

    public Mesh(float[] uv, float[] points) {
        vertecies = points.length/2;

        initVAO();
        {
            createVBO(Util.floatsToFloatBuffer(points), 0, 2);
            createVBO(Util.floatsToFloatBuffer(uv), 1, 2);
        }
        endVAO();

        Instancing.addMesh(this);
    }

    public Mesh(float[] points) {
        vertecies = points.length/2;

        initVAO();
        {
            createVBO(Util.floatsToFloatBuffer(points), 0, 2);
        }
        endVAO();

        Instancing.addMesh(this);
    }

    protected void initVAO() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
    }

    protected void initVAO(int[] indecies) {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        int elementBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.intsToIntBuffer(indecies), GL_DYNAMIC_DRAW);
        vbos.add(elementBuffer);
    }

    protected void createVBO(FloatBuffer floats, int attrib, int size) {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glBufferData(GL_ARRAY_BUFFER, floats, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(attrib, size, GL_FLOAT, false, 0, 0);
        vbos.add(vboID);
    }

    protected void endVAO() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void destroy() {
        for(Integer i: vbos) {
            glDeleteBuffers(i);
        }

        glDeleteVertexArrays(vaoID);
        Instancing.removeMesh(this);
    }

    public void destroy(boolean delete) {
        for(Integer i: vbos) {
            glDeleteBuffers(i);
        }

        glDeleteVertexArrays(vaoID);
        if(delete)
            Instancing.removeMesh(this);
    }

    public void subData(float[] data, int index) {
        glBindBuffer(GL_ARRAY_BUFFER, vbos.get(index));
        glBufferSubData(GL_ARRAY_BUFFER, 0, Util.floatsToFloatBuffer(data));
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void subData(int[] data, int index) {
        glBindBuffer(GL_ARRAY_BUFFER, vbos.get(index));
        glBufferSubData(GL_ARRAY_BUFFER, 0, Util.intsToIntBuffer(data));
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertecies() {
        return vertecies;
    }
}