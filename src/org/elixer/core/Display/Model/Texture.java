package org.elixer.core.Display.Model;

import static org.lwjgl.opengl.GL11.*;

import org.elixer.core.Registry.Instancing;
import org.elixer.core.Util.Console;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by aweso on 2/28/2017.
 */
public class Texture {

    protected int textureID, width, hieght;

    public Texture(String filename) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File("src/assets/textures/" + filename));
            width = bi.getWidth();
            hieght = bi.getHeight();

            int[] rawPixelData = bi.getRGB(0, 0, width, hieght, null, 0, width);

            ByteBuffer pixelData = BufferUtils.createByteBuffer(width*hieght*4);

            for(int i = 0; i < rawPixelData.length; i++) {
                pixelData.put((byte) ((rawPixelData[i] >> 16) & 0xFF));
                pixelData.put((byte) ((rawPixelData[i] >> 8) & 0xFF));
                pixelData.put((byte) ((rawPixelData[i] >> 0) & 0xFF));
                pixelData.put((byte) ((rawPixelData[i] >> 24) & 0xFF));
            }

            pixelData.flip();

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, hieght, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);

            glBindTexture(GL_TEXTURE_2D, 0);
            Instancing.addTexture(textureID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHieght() {
        return hieght;
    }
}
