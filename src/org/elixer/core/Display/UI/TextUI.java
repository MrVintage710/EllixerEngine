package org.elixer.core.Display.UI;

import org.elixer.core.ElixerGame;
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
    private UITextAlignment alignment = UITextAlignment.LEFT;
    private GraphicUI graphProx;
    private Font font;

    public TextUI(String message, Font font, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.message = message;
        this.font = font;
        this.graphProx = new GraphicUI(font.getTexture());
    }

    public TextUI(String message, Font font) {
        super(0,0, ElixerGame.currWindow.getWidth(),ElixerGame.currWindow.getHeight());
        this.message = message;
        this.font = font;
        this.graphProx = new GraphicUI(font.getTexture());
    }

    /*
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

                    cha.setUVValues(PanelUI.mesh, font.getScaleH(), font.getScaleW());
                    width = cha.getWidth() * font.getSize();
                    height = cha.getHeight() * font.getSize();
                    float x = (pos.x + cursorX + cha.getXoffset());
                    float y = (pos.y + cursorY + cha.getYoffset());
                    glBindVertexArray(PanelUI.mesh.getVaoID());
                    shader.setUniform("trans", getTransform(x,y));
                    glDrawArrays(GL_TRIANGLE_STRIP, 0, PanelUI.mesh.getVertecies());
                    glBindVertexArray(0);
                    cursorX += cha.getXadvance();
            }
        }

        PanelUI.mesh.subData(new float[]{0,0,0,1,1,0,1,1}, 1);
    }
    */

    @Override
    protected void onDraw() {
        float cursorX = 0;
        float cursorY = 0;

        for(char c: message.toCharArray()) {
            switch (c) {
                case '\n':
                    cursorX = 0;
                    cursorY += (font.getHighest() * font.getSize()) + 40;
                    break;
                default:
                    Character cha =  font.getChar((int)c);
                    cha.setUVValues(PanelUI.mesh, font.getScaleH(), font.getScaleW());

                    switch (transRule) {
                        case PERCENTAGE_BASED:
                            float newCursorX = ((cursorX+cha.getXoffset())/ElixerGame.currWindow.getWidth())*font.getSize();
                            float newCursorY = ((cursorY+cha.getYoffset())/ElixerGame.currWindow.getHeight())*font.getSize();
                            graphProx.setPos(pos.x + newCursorX, pos.y + newCursorY);
                            graphProx.setDim(cha.getWidth() * font.getSize(), cha.getHeight() * font.getSize());
                            graphProx.setPivot(pivot.x, pivot.y);
                            break;
                        case PIXEL_BASED:
                            graphProx.setPos(pos.x + cursorX + cha.getXoffset(), pos.y + cursorY + cha.getYoffset());
                            graphProx.setDim(cha.getWidth() * font.getSize(), cha.getHeight() * font.getSize());
                            graphProx.setPivot(pivot.x, pivot.y);
                            break;
                    }

                    graphProx.draw();
                    cursorX += cha.getXadvance();
            }
        }

        glBindVertexArray(0);
        PanelUI.mesh.subData(new float[]{0,0,0,1,1,0,1,1}, 1);
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

    public void setAlignment(UITextAlignment alignment) {
        this.alignment = alignment;
    }


    public float getSpacing() {
        switch (transRule) {
            case PERCENTAGE_BASED:
                return ((font.getHighest() * font.getSize()))/ElixerGame.currWindow.getHeight();
            case PIXEL_BASED:
                return (font.getHighest() * font.getSize());
        }

        return 0f;
    }

}
