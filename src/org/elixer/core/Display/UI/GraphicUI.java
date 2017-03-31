package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.Mesh;
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

    public GraphicUI(Texture tex) {
        super();
        width = tex.getWidth();
        height = tex.getHieght();
        texture = tex;
    }

    public GraphicUI(Texture tex, float x, float y) {
        super(x, y, tex.getWidth(), tex.getHieght());
        texture = tex;
    }

    public GraphicUI(Texture tex, float x, float y, float width, float height) {
        super(x, y, width, height);
        texture = tex;
    }

    @Override
    protected void onDraw() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glDrawArrays(GL_TRIANGLE_STRIP, 0, PanelUI.mesh.getVertecies());
    }
}
