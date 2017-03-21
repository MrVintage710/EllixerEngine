package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.Mesh;
import org.elixer.core.Display.Model.RenderLayer;
import org.elixer.core.Display.Model.IRenderable;
import org.elixer.core.Display.Shader.UIShader;
import org.elixer.core.ElixerGame;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by aweso on 3/9/2017.
 */
public abstract class ElementUI implements IRenderable {

    private Vector2f pos = new Vector2f();
    private float width = 500, height = 500;
    protected static UIShader shader;
    protected static Mesh mesh;

    public ElementUI(Vector2f pos) {
        this.pos = pos;
        mesh = new Mesh(new float[] {-1,1,-1,-1,1,1,1,-1});
        shader = new UIShader();
    }

    protected Matrix4f getTransform() {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        float x = ((pos.x + (width/2))*2/ElixerGame.currWindow.getWidth())-1;
        float y = ((pos.y - (height/2))*2/ElixerGame.currWindow.getHeight())+1;
        matrix.translate(new Vector2f(x, y));
        matrix.scale(new Vector3f(width/ElixerGame.currWindow.getWidth(), height/ElixerGame.currWindow.getHeight(), 1));
        return matrix;
    }

    @Override
    public void draw() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);

        onDraw();

        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
    }

    protected abstract void onDraw();

    @Override
    public RenderLayer renderLayer() {
        return RenderLayer.UI;
    }

    public void setPos(float x, float y) {
        pos.setX(-0.5f + (x/ElixerGame.currWindow.getWidth()));
        pos.setY(-0.5f + (y/ElixerGame.currWindow.getHeight()));
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        setPos(pos.getX(), pos.getY());
    }

    protected float toScreenSpaceWidth(float value) {
        return value/ElixerGame.currWindow.getWidth();
    }

    protected float toScreenSpaceHeight(float value) {
        return value/ElixerGame.currWindow.getHeight();
    }
}
