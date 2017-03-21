package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.Mesh;
import org.elixer.core.ElixerGame;

/**
 * Created by aweso on 3/8/2017.
 */
public class Character {

    private Mesh mesh;
    private int id, x, y, width, height, xoffset, yoffset, xadvance;

    public Character(int id, int x, int y, int width, int hieght, int xoffset, int yoffset, int xadvance) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = hieght;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.xadvance = xadvance;
    }

    //Kept just in case
    public void setRenderInfo(float cursorX, float cursorY, float size, int texturWidth, int textureHeight) {
        if(mesh != null)
            mesh.destroy();

        float widthf = (float) texturWidth;
        float heightf = (float) textureHeight;

        float topLeftX = x/widthf;
        float topLeftY = y/heightf;
        float uvWidth = (x + this.width)/widthf;
        float uvHeight = (y + this.height)/heightf;

        float[] uv2 = new float[] {
                topLeftX, topLeftY,
                topLeftX, uvHeight,
                uvWidth, topLeftY,
                uvWidth, uvHeight
        };

        float screenWidth = ElixerGame.currWindow.getWidth();
        float screenHieght = ElixerGame.currWindow.getHeight();

        float glxOffset = ((xoffset)/ screenWidth) * size;
        float glyOffset = -((yoffset)/screenHieght) * size;
        float glX = ((cursorX/screenWidth) * size) + glxOffset;
        float glY = (-(cursorY/screenHieght) * size) + glyOffset;
        float glMaxX = (((cursorX+width)/screenWidth) * size) + glxOffset;
        float glMaxY = ((-(cursorY+height)/screenHieght) * size) + glyOffset;

        float[] points = new float[] {
                glX, glY,
                glX, glMaxY,
                glMaxX,glY,
                glMaxX,glMaxY
        };

        mesh = new Mesh(uv2, points);
    }

    public void setRenderInfo(Mesh mesh, float cursorX, float cursorY, float size, int texturWidth, int textureHeight) {
        if(mesh != null)
            mesh.destroy();

        float widthf = (float) texturWidth;
        float heightf = (float) textureHeight;

        float topLeftX = x/widthf;
        float topLeftY = y/heightf;
        float uvWidth = (x + this.width)/widthf;
        float uvHeight = (y + this.height)/heightf;

        float[] uv = new float[] {
                topLeftX, topLeftY,
                topLeftX, uvHeight,
                uvWidth, topLeftY,
                uvWidth, uvHeight
        };

        float screenWidth = ElixerGame.currWindow.getWidth();
        float screenHieght = ElixerGame.currWindow.getHeight();

        float glxOffset = ((xoffset)/ screenWidth) * size;
        float glyOffset = -((yoffset)/screenHieght) * size;
        float glX = ((cursorX/screenWidth) * size) + glxOffset;
        float glY = ((cursorY/screenHieght) * size) + glyOffset;
        float glMaxX = (((cursorX+width)/screenWidth) * size) + glxOffset;
        float glMaxY = (((cursorY+height)/screenHieght) * size) + glyOffset;

        float[] points = new float[] {
                glX, glY,
                glX, glMaxY,
                glMaxX,glY,
                glMaxX,glMaxY
        };

        //mesh.subData(points, 0);
        //mesh.subData(uv, 1);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXoffset() {
        return xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public int getXadvance() {
        return xadvance;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
