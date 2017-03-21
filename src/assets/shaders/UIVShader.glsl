#version 400

in vec2 pos;

out vec2 uv;

uniform float layer;
uniform mat4 trans;

void main() {
    gl_Position = trans * vec4(pos, layer, 1.0);
    uv = vec2((pos.x+1.0)/2.0, 1 - (pos.y+1.0)/2.0);
}
