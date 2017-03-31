package org.elixer.core.Entity;

import org.elixer.core.ElixerGame;
import org.elixer.core.Util.Debug;
import org.elixer.core.Util.Input;
import org.elixer.core.Util.Ref;
import org.elixer.core.Util.Util;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by aweso on 3/3/2017.
 */
public class ModuleCamera extends Module {

    private float fov, nearPlane, farPlane, left, right, top, bottom;

    public ModuleCamera(float fov, float nearPlane, float farPlane) {
        this.fov = fov;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;

        right = (float) 600;
        left = 600f;
        top = (float) 600;
        bottom = 600f;
    }

    public ModuleCamera(float fov, float nearPlane, float farPlane, float left, float right, float top, float bottom) {
        this.fov = fov;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void onUpdate() {
        if(Input.W) {
            entity.translateLocal(0, 0, 0.1f);
        }

        if(Input.S) {
            entity.translateLocal(0, 0, -0.1f);
        }

        if(Input.A) {
            entity.translateLocal(-0.1f, 0, 0);
        }

        if(Input.D) {
            entity.translateLocal(0.1f, 0, 0);
        }

        if(Input.UP) {
            entity.rotate(1f, 0, 0);
        }

        if(Input.DOWN) {
            entity.rotate(-1f, 0, 0);
        }

        if(Input.LEFT) {
            entity.rotate(0, 1f, 0);
        }

        if(Input.RIGHT) {
            entity.rotate(0, -1f, 0);
        }

        if(!Debug.isActive()) {
            entity.rotate(Input.getYD() * -0.1f, Input.getXD() * -0.1f, 0);
        }

        if(Input.SPACE) {
            entity.translateLocal(0, 0.1f, 0);
        }
        entity.setRotation(Util.clamp(-90, 90, entity.getRot().x), entity.getRot().y, 0);
    }

    public Matrix4f getProjectionMatrix() {
        float aspectRatio = (float) (ElixerGame.currWindow.getWidth() / ElixerGame.currWindow.getHeight());
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
        projectionMatrix.m33 = 0;
        return projectionMatrix;
    }

    public Matrix4f getOrthoMatrix() {
        float m00 = 2/(right - left);
        float m03 = -((right+left)/(right-left));
        float m11 = 2/(top-bottom);
        float m13 = -((top+bottom)/(top-bottom));
        float m22 = 1/(farPlane-nearPlane);
        float m23 = -((nearPlane)/(farPlane-nearPlane));
        float m33 = 1;

        Matrix4f ortho = new Matrix4f();
        ortho.setIdentity();
        ortho.m00 = m00;
        ortho.m03 = m03;
        ortho.m11 = m11;
        ortho.m13 = m13;
        ortho.m22 = m22;
        ortho.m23 = m23;
        ortho.m33 = m33;
        return ortho;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        viewMatrix.rotate((float) Math.toRadians(-entity.getRot().getX()), Ref.X_AXIS);
        viewMatrix.rotate((float) Math.toRadians(-entity.getRot().getY()), Ref.Y_AXIS);
        viewMatrix.translate(new Vector3f(-entity.pos.getX(), -entity.pos.getY(),-entity.pos.getZ()));
        return viewMatrix;
    }
}
