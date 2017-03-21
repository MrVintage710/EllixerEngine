package org.elixer.core.Display.UI;

import org.elixer.core.Display.Shader.TextUIShader;
import org.elixer.core.Util.Console;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by aweso on 3/9/2017.
 */
public class TextUI extends ElementUI{
    private String message;
    private float minSpacing = 10f;
    private Font font;
    private TextUIShader shader = new TextUIShader();

    public TextUI(String message, Font font, Vector2f pos) {
        super(pos);
        this.message = message;
        this.font = font;
    }

    @Override
    public void onDraw() {
        float cursorX = 0;
        float cursorY = 0;

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, font.getTexture().getTextureID());
        for(char c: message.toCharArray()) {
            switch (c) {
                case '\n':
                    cursorX = 0;
                    cursorY += getSpacing();
                    break;
                default:
                    Character cha =  font.getChar((int)c);

                    shader.start();
                    cha.setRenderInfo(cursorX, cursorY, font.getSize(), font.getScaleH(), font.getScaleW());
                    glBindVertexArray(cha.getMesh().getVaoID());
                    glEnableVertexAttribArray(0);
                    glEnableVertexAttribArray(1);
                    shader.setUniform("trans", getTransform());
                    shader.setUniform("color", new Vector3f(1f,1,1f));
                    glDrawArrays(GL_TRIANGLE_STRIP, 0, cha.getMesh().getVertecies());
                    glDisableVertexAttribArray(0);
                    glDisableVertexAttribArray(1);
                    glBindVertexArray(0);
                    cursorX += cha.getXadvance();
                    shader.stop();
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(Object message) {
        this.message = message.toString();
    }

    public float getSpacing() {
        return (font.getHighest() * font.getSize()) + minSpacing;
    }
}
