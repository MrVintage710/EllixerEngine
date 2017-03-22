#version 400

in vec2 pos;
in vec2 uv;

out vec2 uv_out;

uniform float layer;
uniform mat4 trans;

void main() {
    gl_Position = trans * vec4(pos, layer, 1.0);
    uv_out = uv;
}
