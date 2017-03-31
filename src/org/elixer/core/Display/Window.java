package org.elixer.core.Display;

import org.elixer.core.Util.Console;
import org.elixer.core.Util.Debug;
import org.elixer.core.Util.Input;
import org.elixer.core.Util.Util;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by aweso on 2/13/2017.
 */
public class Window {

    private long windowID;

    private int width, height, screenWidth, screenHeight;

    GLFWKeyCallback keyCallback = GLFWKeyCallback.create(this::onKeyCallback);
    GLFWErrorCallback errorCallback = GLFWErrorCallback.create(this::onErrorCallback);
    GLFWCursorPosCallback cursorPosCallback = GLFWCursorPosCallback.create(this::onCursorPos);
    GLFWWindowSizeCallback windowSizeCallback = GLFWWindowSizeCallback.create(this::onWindowResize);
    GLFWMouseButtonCallback mouseButtonCallback = GLFWMouseButtonCallback.create(this::onMouseButton);

    public Window(String title, int width, int hieght) {
        this.width = width;
        this.height = hieght;
        if(!glfwInit()) {
            Console.printend("GLFW couldn't init.");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);
        glfwWindowHint(GLFW_RESIZABLE, 1);

        windowID = glfwCreateWindow(width, hieght, title, 0 , 0);
        if(windowID == NULL) {
            Console.printend("Window could not be opened.");
        }

        glfwSetWindowSizeCallback(windowID, windowSizeCallback);
        glfwSetKeyCallback(windowID, keyCallback);
        glfwSetErrorCallback(errorCallback);
        glfwSetCursorPosCallback(windowID, cursorPosCallback);
        glfwSetMouseButtonCallback(windowID, mouseButtonCallback);

        int[] widthBuffer = new int[2];
        int[] heightBuffer = new int[2];
        glfwGetMonitorPhysicalSize(glfwGetPrimaryMonitor(), widthBuffer, heightBuffer);
        screenWidth = widthBuffer[0];
        screenHeight = heightBuffer[0];

        glfwMakeContextCurrent(windowID);
        GL.createCapabilities();
        glfwSwapInterval(1);
        glfwShowWindow(windowID);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(windowID);
    }

    public void destroy() {
        glfwDestroyWindow(windowID);
        glfwTerminate();
        System.exit(0);
    }

    private void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        switch (key) {
            case GLFW_KEY_W:
                switch (action) {
                    case GLFW_PRESS:
                        Input.W = true;
                        break;
                    case GLFW_RELEASE:
                        Input.W = false;
                }
                break;
            case GLFW_KEY_S:
                switch (action) {
                    case GLFW_PRESS:
                        Input.S = true;
                        break;
                    case GLFW_RELEASE:
                        Input.S = false;
                }
                break;
            case GLFW_KEY_A:
                switch (action) {
                    case GLFW_PRESS:
                        Input.A = true;
                        break;
                    case GLFW_RELEASE:
                        Input.A = false;
                }
                break;
            case GLFW_KEY_D:
                switch (action) {
                    case GLFW_PRESS:
                        Input.D = true;
                        break;
                    case GLFW_RELEASE:
                        Input.D = false;
                }
                break;
            case GLFW_KEY_UP:
                switch (action) {
                    case GLFW_PRESS:
                        Input.UP = true;
                        break;
                    case GLFW_RELEASE:
                        Input.UP = false;
                }
                break;
            case GLFW_KEY_DOWN:
                switch (action) {
                    case GLFW_PRESS:
                        Input.DOWN = true;
                        break;
                    case GLFW_RELEASE:
                        Input.DOWN = false;
                }
                break;
            case GLFW_KEY_LEFT:
                switch (action) {
                    case GLFW_PRESS:
                        Input.LEFT = true;
                        break;
                    case GLFW_RELEASE:
                        Input.LEFT = false;
                }
                break;
            case GLFW_KEY_RIGHT:
                switch (action) {
                    case GLFW_PRESS:
                        Input.RIGHT = true;
                        break;
                    case GLFW_RELEASE:
                        Input.RIGHT = false;
                }
                break;
            case GLFW_KEY_LEFT_ALT:
                switch (action) {
                    case GLFW_PRESS:
                        Input.LEFT_ALT = true;
                        break;
                    case GLFW_RELEASE:
                        Input.LEFT_ALT = false;
                }
                break;
            case GLFW_KEY_F1:
                switch (action) {
                    case GLFW_PRESS:
                        Debug.toggle();
                        break;
                    case GLFW_RELEASE:
                }
                break;
            case GLFW_KEY_SPACE:
                switch (action) {
                    case GLFW_PRESS:
                        Input.SPACE = true;
                        break;
                    case GLFW_RELEASE:
                        Input.SPACE = false;
                }
                break;
        }

        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            destroy();
        }
    }

    private void onCursorPos(long window, double xpos, double ypos) {
        Input.setX((float)xpos);
        Input.setY((float)ypos);
    }

    private void onWindowResize(long windowID, int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, this.width, this.height);
        Console.println(width +" | " + this.height);
    }

    private void onMouseButton(long window, int button, int action, int mods) {
        if(GLFW_MOUSE_BUTTON_1 == button && GLFW_PRESS == action) {
            Input.clickedX = Input.X;
            Input.clickedY = Input.Y;
        }
    }

    public void setCursorDisabled(boolean bool) {
        glfwSetCursorPos(windowID, width/2, height /2);
        Input.setX((float) width/2);
        Input.setY((float) height /2);
        if(bool) {
            glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        } else {
            glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
    }

    public void onErrorCallback(int error, long description) {
        Console.printerr(error + ": " + description);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public float getDensity() { return (float)screenWidth/(float)screenWidth; }
}
