package org.elixer.core.Display.Shader;

/**
 * Created by aweso on 3/9/2017.
 */
public class TextUIShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/assets/shaders/TextUIVShader.glsl";
    private static final String FRAGMENT_FILE = "src/assets/shaders/TextUIFShader.glsl";

    public TextUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute("pos", 0);
        super.bindAttribute("uv", 1);
    }

    @Override
    protected void bindUniforms() {
        super.bindUniform("trans");
        super.bindUniform("color");
    }
}
