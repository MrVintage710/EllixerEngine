#version 400

in vec2 uv_out;

out vec4 out_color;

uniform sampler2D sampler;

void main() {
        out_color = texture(sampler, uv_out);
}
