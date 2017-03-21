package org.elixer.core.Util;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by aweso on 2/13/2017.
 */
public class Util {

    public static FloatBuffer pointsToFloatBuffer(Vector3f... points) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(points.length * 3);
        for(Vector3f point: points) {
            buffer.put(point.getX());
            buffer.put(point.getY());
            buffer.put(point.getZ());
        }
        buffer.flip();
        return buffer;
    }

    public static float[] pointsToFloatArray(Vector3f... points) {
        float[] data = new float[points.length * 3];

        for(int i = 0; i < points.length; i++) {
            data[i] = points[i].getX();
            data[i+1] = points[i].getY();
            data[i+2] = points[i].getZ();
        }

        return data;
    }

    public static FloatBuffer floatsToFloatBuffer(float... floats) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(floats.length);
        floatBuffer.put(floats);
        floatBuffer.flip();
        return floatBuffer;
    }

    public static IntBuffer intsToIntBuffer(int... ints) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(ints.length);
        intBuffer.put(ints);
        intBuffer.flip();
        return intBuffer;
    }

    public static float clamp(float min, float max, float value) {
        if(value < min) {
            value = min;
        } else if(value > max) {
            value = max;
        }

        return value;
    }
}
