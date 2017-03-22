package org.elixer.core.Display.UI;

import org.elixer.core.Display.Model.Texture;
import org.elixer.core.Util.Console;

import java.io.*;

/**
 * Created by aweso on 3/8/2017.
 */
public class Font {
    Character[] characters;

    private float size;
    private float highest = 0f;
    private int scaleW;
    private int scaleH;

    private BufferedReader reader;
    private Texture texture;
    private String textureLocation;

    public Font(String filename, float size) {
        this.size = size/20;
        try {
            reader = new BufferedReader(new FileReader(new File("src/assets/fonts/" + filename)));
        } catch (FileNotFoundException e) {
            Console.printerr(e.getMessage());
        }

        String line;

        int amount = 0;

        try {
            while ((line = reader.readLine()) != null) {
                switch (line.split(" ")[0]) {
                    case "info":
                        break;
                    case "common":
                        scaleW = getIValue("scaleW", line);
                        scaleH = getIValue("scaleH", line);
                        break;
                    case "page":
                        textureLocation = getSValue("file", line);
                        break;
                    case "chars":
                        characters = new Character[getIValue("count", line) +1];
                        break;
                    case "char":
                        characters[amount] = new Character(
                                getIValue("id", line),
                                getIValue("x", line),
                                getIValue("y", line),
                                getIValue("width", line),
                                getIValue("height", line),
                                getIValue("xoffset", line),
                                getIValue("yoffset", line),
                                getIValue("xadvance", line)
                        );
                        if(characters[amount].getHeight() > highest) {
                            highest = characters[amount].getHeight();
                        }
                        amount++;
                        break;
                }
            }
        } catch (IOException e) {
            Console.printerr(e.getMessage());
        }

        texture = new Texture(textureLocation);
    }

    private int getIValue(String value, String line) {
        int result = 0;
        if(line.contains(value)) {
            result =  Integer.parseInt(line.split(value+"=")[1].split(" ")[0]);
        } else {
            Console.printerr("FONT INPUT ERROR: no such value '" + value + "' in line '" + line.split(" ")[0] + "'.");
        }

        return result;
    }

    private String getSValue(String value, String line) {
        String result = "";
        if(line.contains(value)) {
            result = line.split(value + "=\"")[1].split("\"")[0];
        } else {
            Console.printerr("FONT INPUT ERROR: no such value '" + value + "' in line '" + line.split(" ")[0] + "'.");
        }

        return result;
    }

    public Character getChar(int ascii) {
        for(Character c: characters) {
            if(c.getId() == ascii) {
                return c;
            }
        }

        Console.printerr("FONT PRINT ERROR: Character with ascii code '" + ascii + "' doesn't exist in this font. Returning null.");
        return null;
    }

    public float getSize() {
        return size;
    }

    public int getScaleW() {
        return scaleW;
    }

    public int getScaleH() {
        return scaleH;
    }

    public float getHighest() {
        return highest;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Texture getTexture() {
        return texture;
    }
}
