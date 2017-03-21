#version 400

in vec2 uv_out;

out vec4 color_out;

uniform vec3 color;
uniform sampler2D sampler;

void main() {
    color_out = vec4(color, texture(sampler, uv_out).a);
}
