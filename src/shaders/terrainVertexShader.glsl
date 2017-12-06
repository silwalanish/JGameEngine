#version 120

attribute vec3 position;
attribute vec2 textures;
attribute vec3 normals;

varying vec2 tex_coord;
varying vec3 surfaceNormal;
varying vec3 toLight;
varying vec3 toCamera;
varying float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;
uniform vec3 cameraPos;
uniform float fogDensity;
uniform float fogGradient;

void main()
{
	vec4 worldPosition = transformationMatrix * vec4(position, 1);
	vec4 posRelativeToCam = viewMatrix * worldPosition;
	tex_coord = textures;
	surfaceNormal = (transformationMatrix * vec4(normals, 0)).xyz;
	toLight = lightPos - worldPosition.xyz;
	toCamera = cameraPos - worldPosition.xyz;

	float distanceToCam = length(posRelativeToCam);
	visibility = exp(-pow(distanceToCam * fogDensity, fogGradient));

	gl_Position	=  projectionMatrix * posRelativeToCam;
}

