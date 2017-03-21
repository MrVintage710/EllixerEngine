package org.elixer.core.Display.Shader;

/**
 * Created by aweso on 3/17/2017.
 */
public class UIShader extends ShaderProgram{
    private static final String VERTEX_FILE = "src/assets/shaders/UIVShader.glsl";
    private static final String FRAGMENT_FILE = "src/assets/shaders/UIFShader.glsl";

    public UIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute("pos", 0);
    }

    @Override
    protected void bindUniforms() {
        super.bindUniform("layer");
        super.bindUniform("trans");
    }
}
