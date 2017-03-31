package org.elixer.core.Entity;

import org.elixer.core.Display.Model.Model;
import org.elixer.core.Display.Model.RenderLayer;
import org.elixer.core.Display.Model.IRenderable;
import org.elixer.core.Display.Shader.ShaderProgram;
import org.elixer.core.Display.Shader.StaticShader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by aweso on 3/2/2017.
 */
public class ModelRenderer extends Module implements IRenderable {

    private ShaderProgram shader;
    private Model model;

    public ModelRenderer(Model model) {
        this.model = model;
        shader = new StaticShader();
    }

    public ModelRenderer(Model model, ShaderProgram shader) {
        this.shader = shader;
        this.model = model;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void draw() {
        glEnable(GL_BLEND);
        shader.start();
        glBindVertexArray(model.getMesh().getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        shader.setUniform("transform", entity.getTransform());
        shader.setUniform("projection", Scene.getCurrCamera().getProjectionMatrix());
        shader.setUniform("view", Scene.getCurrCamera().getViewMatrix());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getMesh().getVertecies(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.stop();
    }

    @Override
    public RenderLayer renderLayer() {
        return RenderLayer.SCENE;
    }
}
