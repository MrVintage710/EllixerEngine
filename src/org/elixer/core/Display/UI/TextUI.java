package org.elixer.core.Display.UI;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by aweso on 3/9/2017.
 */
public class TextUI extends ElementUI{
    private String message;
    private float minSpacing = 45f;
    private Font font;

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
                    cha.setUVValues(PanelUI.mesh, font.getScaleH(), font.getScaleW());
                    width = cha.getWidth() * font.getSize();
                    height = cha.getHeight() * font.getSize();
                    float x = (pos.x + cursorX + cha.getXoffset()) * font.getSize();
                    float y = (pos.y + cursorY + cha.getYoffset()) * font.getSize();
                    glBindVertexArray(PanelUI.mesh.getVaoID());
                    glEnableVertexAttribArray(0);
                    glEnableVertexAttribArray(1);
                    shader.setUniform("trans", getTransform(x,-y));
                    glDrawArrays(GL_TRIANGLE_STRIP, 0, PanelUI.mesh.getVertecies());
                    glDisableVertexAttribArray(0);
                    glDisableVertexAttribArray(1);
                    PanelUI.mesh.subData(new float[]{0,0,0,1,1,0,1,1}, 1);
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
