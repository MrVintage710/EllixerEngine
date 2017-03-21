package org.elixer.core.Display.Model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by aweso on 2/27/2017.
 */
public class Model implements IRenderable {

    private Mesh mesh;
    private Texture texture;

    public Model(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void draw() {
        glBindVertexArray(mesh.vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glDrawElements(GL_TRIANGLES, mesh.vertecies, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public RenderLayer renderLayer() {
        return RenderLayer.SCENE;
    }
}
