package org.elixer.core.Display.Shader;

import org.elixer.core.Util.Console;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aweso on 2/21/2017.
 */
public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private HashMap<String, Integer> uniforms = new HashMap<>();
    private static FloatBuffer uniformProxy = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile) {
        vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        bindUniforms();
    }

    protected abstract void bindAttributes();

    protected abstract void bindUniforms();

    protected void bindUniform(String uniformName) {
        int uniform = glGetUniformLocation(programID, uniformName);

        if(uniform != -1) {
            uniforms.put(uniformName, uniform);
        } else {
            Console.printerr("SHADER UNIFORM ERROR: Uniform '" + uniformName + "' not found in memory.");
        }
    }

    public void setUniform(String name, float value) {
        if(uniforms.containsKey(name)) {
            glUniform1f(uniforms.get(name), value);
        } else {
           //Console.printerr("SHADER UNIFORM ERROR: The uniform '" + name + "' does not exist.");
        }
    }

    public void setUniform(String name, Vector3f value) {
        if(uniforms.containsKey(name)) {
            glUniform3f(uniforms.get(name), value.getX(), value.getY(), value.getZ());
        } else {
            //Console.printerr("SHADER UNIFORM ERROR: The uniform '" + name + "' does not exist.");
        }
    }

    public void setUniform(String name, boolean value) {
        if(uniforms.containsKey(name)) {
            int uniform = 0;
            if(value) {
                uniform = 1;
            }
            glUniform1i(uniforms.get(name), uniform);
        } else {
            //Console.printerr("SHADER UNIFORM ERROR: The uniform '" + name + "' does not exist.");
        }
    }

    public void setUniform(String name, Matrix4f value) {
        if(uniforms.containsKey(name)) {
            value.store(uniformProxy);
            uniformProxy.flip();
            glUniformMatrix4fv(uniforms.get(name), false, uniformProxy);
        } else {
            Console.printerr("SHADER UNIFORM ERROR: The uniform '" + name + "' does not exist.");
        }
    }

    public void start() {
        glUseProgram(programID);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void destroy() {
        stop();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }

    protected void bindAttribute(String var, int attribute) {
        glBindAttribLocation(programID, attribute, var);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS )== GL_FALSE){
            System.out.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

    public void setUniform(String name, Vector2f value) {
        if(uniforms.containsKey(name)) {
            glUniform2f(uniforms.get(name), value.getX(), value.getY());
        } else {
            Console.printerr("SHADER UNIFORM ERROR: The uniform '" + name + "' does not exist.");
        }
    }
}
