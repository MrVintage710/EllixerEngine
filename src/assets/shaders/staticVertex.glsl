#version 400 core

in vec3 position;
in vec2 uv;

out vec2 color;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {
    gl_Position = projection * view * transform * vec4(position, 1.0);
    color = uv;
}
