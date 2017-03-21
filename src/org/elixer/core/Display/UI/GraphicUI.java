package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.Texture;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by aweso on 3/19/2017.
 */
public class GraphicUI extends ElementUI {

    protected Texture texture;

    public GraphicUI(Texture tex, Vector2f pos) {
        super(pos);
        texture = tex;
    }

    @Override
    protected void onDraw() {
        shader.start();
        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        shader.setUniform("trans", getTransform());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, mesh.getVertecies());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.stop();
    }
}
