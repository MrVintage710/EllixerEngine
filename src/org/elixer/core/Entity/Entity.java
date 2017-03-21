package org.elixer.core.Entity;

import org.elixer.core.Util.Ref;
import org.elixer.core.Util.Util;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * Created by aweso on 2/27/2017.
 */
public class Entity {

    protected Vector3f pos;
    protected Vector3f rot;
    protected Vector3f scale;

    protected Vector3f forward = new Vector3f(0, 0, 0);
    protected Vector3f up = new Vector3f(0, 0, 0);
    protected Vector3f right = new Vector3f(0, 0, 0);

    protected String name;

    protected ArrayList<Module> modules = new ArrayList<>();

    public Entity(String name, Vector3f pos, Vector3f rot, Vector3f scale) {
        this.name = name;
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        calcFacing();
    }

    public Entity(String name) {
        this.name = name;
        this.pos = new Vector3f(0, 0, 0);
        this.rot = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);
        calcFacing();
    }

    public Matrix4f getTransform() {
        Matrix4f transform = new Matrix4f();
        transform.setIdentity();
        transform.translate(pos);
        transform.rotate((float)toRadians(rot.x), Ref.X_AXIS);
        transform.rotate((float)toRadians(rot.y), Ref.Y_AXIS);
        transform.rotate((float)toRadians(rot.z), Ref.Z_AXIS);
        transform.scale(scale);
        return transform;
    }

    public void onUpdate() {
        for(Module module: modules) {
            module.update();
        }

        calcFacing();
    }

    public Vector3f getPos() {
        return pos;
    }

    public Entity setTranslation(Vector3f pos) {
        this.pos = pos;
        return this;
    }

    public Entity setTranslation(float x, float y, float z) {
        pos.set(x,y,z);
        return this;
    }

    public void translate(float x, float y, float z) {
        pos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }

    public void translateLocal(float right, float up, float forward) {

        //float forwardWZ = (float) -(cos(toRadians(rot.y)) * forward);
        //float forwardWX = (float) -(sin(toRadians(rot.y)) * forward);
        //float forwardWY = (float) (sin(toRadians(rot.x)) * forward);

        translate((this.forward.x * forward) + (this.right.x * right) + (this.up.x * up),
                  (this.forward.y * forward) + (this.right.y * right) + (this.up.y * up),
                  (this.forward.z * forward) + (this.right.z * right) + (this.up.z * up));
    }

    public void clampRotationX(float min, float max) {
        Util.clamp(min, max, rot.x);
    }

    public void translateLocal(Vector3f pos) {
        translate(pos.x, pos.y, pos.z);
    }

    public void localtrans(float x, float y, float z) {
    }

    public Vector3f getRot() {
        return rot;
    }

    public Entity setRotation(Vector3f rot) {
        this.rot = rot;
        return this;
    }

    public Entity setRotation(float x, float y, float z) {
        rot.set(x,y,z);
        return this;
    }

    public void rotate(float x, float y, float z) {
        rot.set(rot.getX() + x, rot.getY() + y, rot.getZ() + z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public Entity setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    public Entity setScale(float x, float y, float z) {
        scale.set(x,y,z);
        return this;
    }

    public void scale(float x, float y, float z) {
        scale.set(scale.getX() + x, scale.getY() + y, scale.getZ() + z);
    }

    public void addModule(Module mod) {
        mod.setEntity(this);
        modules.add(mod);
    }

    public <T extends Module> T addModuleR(T mod) {
        mod.setEntity(this);
        modules.add(mod);
        return mod;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    private void calcFacing() {
        float forwardWZ = (float) -(cos(toRadians(rot.y)));
        float forwardWX = (float) -(sin(toRadians(rot.y)));
        float forwardWY = (float) (sin(toRadians(rot.x)));

        forward.set(forwardWX, forwardWY, forwardWZ);
        Vector3f.cross(forward, new Vector3f(0, 1, 0), right);
        Vector3f.cross(forward, right, up);
        up.scale(-1);

        forward.normalise();
        right.normalise();
        up.normalise();
    }

    public Vector3f getForward() {
        return forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public Vector3f getRight() {
        return right;
    }

    public String getName() {
        return name;
    }
}
