package org.elixer.core.Display.Shader;

import org.elixer.core.Display.Shader.ShaderProgram;

/**
 * Created by aweso on 2/21/2017.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/assets/shaders/staticVertex.glsl";
    private static final String FRAGMENT_FILE = "src/assets/shaders/staticFragment.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute("position", 0);
        super.bindAttribute("uv", 1);
    }

    @Override
    protected void bindUniforms() {
        super.bindUniform("transform");
        super.bindUniform("projection");
        super.bindUniform("view");
    }
}
