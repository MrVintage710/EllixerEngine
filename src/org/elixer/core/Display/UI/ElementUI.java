package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.RenderLayer;
import org.elixer.core.Display.Model.IRenderable;
import org.elixer.core.Display.Shader.UIShader;
import org.elixer.core.ElixerGame;
import org.elixer.core.Util.Util;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by aweso on 3/9/2017.
 */
public abstract class ElementUI implements IRenderable{

    protected Vector2f pivot = new Vector2f(0.5f, 0.5f);
    protected Vector2f pos = new Vector2f(0f, 0f);
    protected float width = 0, height = 0;
    protected static UIShader shader;
    protected UITransformRule transRule = UITransformRule.PERCENTAGE_BASED;
    protected UITransformRule scaleRule = UITransformRule.PIXEL_BASED;

    private Matrix4f transformProx = new Matrix4f();
    private Vector2f vec2Prox = new Vector2f();
    private Vector3f vec3Prox = new Vector3f();

    public ElementUI() {

    }

    public ElementUI(float x, float y, float width, float height) {
        this.pos = new Vector2f(x, y);
        this.width = width;
        this.height = height;
        shader = new UIShader();
    }

    protected Matrix4f getTransform() {
        return getTransform(pos.x, pos.y, width, height);
    }

    protected Matrix4f getTransform(float x, float y) {
        return getTransform(x, y, width, height);
    }

    protected Matrix4f getTransform(float x, float y, float width, float height) {
        transformProx.setIdentity();

        float pivotX;
        float pivotY;
        float glX;
        float glY;

        switch (transRule) {
            case PERCENTAGE_BASED:
                glX = 2*x-1;
                glY = -(2*y-1);
                switch (scaleRule) {
                    case PERCENTAGE_BASED:
                        pivotX = ((width/2) - (pivot.x*width))*2;
                        pivotY = ((height/2) - (pivot.y*height))*2;
                        vec2Prox.set(glX + pivotX, glY - pivotY);
                        transformProx.translate(vec2Prox);
                        break;
                    case PIXEL_BASED:
                        float newWidth = width/ElixerGame.currWindow.getWidth();
                        float newHeight = height/ElixerGame.currWindow.getHeight();
                        pivotX = ((newWidth/2) - (pivot.x*newWidth))*2;
                        pivotY = ((newHeight/2) - (pivot.y*newHeight))*2;
                        vec2Prox.set(glX + pivotX, glY - pivotY);
                        transformProx.translate(vec2Prox);
                        break;
                }
                break;
            case PIXEL_BASED:
                float pixelX = x/ElixerGame.currWindow.getWidth();
                float pixelY = y/ElixerGame.currWindow.getHeight();
                glX = 2*pixelX-1;
                glY = -(2*pixelY-1);
                switch (scaleRule) {
                    case PERCENTAGE_BASED:
                        pivotX = ((width/2) - (pivot.x*width))*2;
                        pivotY = ((height/2) - (pivot.y*height))*2;
                        vec2Prox.set(glX + pivotX, glY - pivotY);
                        transformProx.translate(vec2Prox);
                        break;
                    case PIXEL_BASED:
                        float newWidth = width/ElixerGame.currWindow.getWidth();
                        float newHeight = height/ElixerGame.currWindow.getHeight();
                        pivotX = ((newWidth/2) - (pivot.x*newWidth))*2;
                        pivotY = ((newHeight/2) - (pivot.y*newHeight))*2;
                        vec2Prox.set(glX + pivotX, glY - pivotY);
                        transformProx.translate(vec2Prox);
                        break;
                }
                break;
        }

        switch(scaleRule) {
            case PERCENTAGE_BASED:
                vec3Prox.set(width, height, 1);
                transformProx.scale(vec3Prox);
                break;
            case PIXEL_BASED:
                vec3Prox.set(width/ElixerGame.currWindow.getWidth(), height/ElixerGame.currWindow.getHeight(), 1);
                transformProx.scale(vec3Prox);
                break;
        }

        return transformProx;
    }

    @Override
    public void draw() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        shader.start();
        glBindVertexArray(PanelUI.mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        shader.setUniform("trans", getTransform());

        onDraw();

        glDrawArrays(GL_TRIANGLE_STRIP, 0, PanelUI.mesh.getVertecies());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.stop();
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
    }

    protected abstract void onDraw();

    @Override
    public RenderLayer renderLayer() {
        return RenderLayer.UI;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setDim(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public void setPos(float x, float y) {
        this.pos.set(x, y);
    }

    public void setPivot(Vector2f pivot) {
        this.pivot = Util.clamp(0, 1, pivot);
    }

    public void setPivot(float x, float y) {
        Util.clamp(0, 1, x);
        Util.clamp(0, 1, y);
        pivot.set(x,y);
    }

    public void setAlignment(UIAlignment alignment) {
        setTransRule(UITransformRule.PERCENTAGE_BASED);
        alignment.set(this);
    }

    public void setTransRule(UITransformRule transRule) {
        this.transRule = transRule;
    }

    public void setScaleRule(UITransformRule scaleRule) {
        this.scaleRule = scaleRule;
    }

    protected float toScreenSpaceWidth(float value) {
        return value/ElixerGame.currWindow.getWidth();
    }

    protected float toScreenSpaceHeight(float value) {
        return value/ElixerGame.currWindow.getHeight();
    }
}
