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
uniform int useFakeLighting;
uniform float fogGradient;
uniform float fogDensity;

void main()
{
	vec4 worldPosition = transformationMatrix * vec4(position, 1);
	vec4 posRelativeToCam = viewMatrix * worldPosition;
	vec3 actualNormal = normals;

	if(useFakeLighting == 1){
		actualNormal = vec3(0, 1, 0);
	}

	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0)).xyz;

	tex_coord = textures;
	toLight = lightPos - worldPosition.xyz;
	toCamera = cameraPos - worldPosition.xyz;
	float distanceToCamera = length(posRelativeToCam.xyz);
	visibility = exp(-pow(distanceToCamera * fogDensity, fogGradient));
	visibility = clamp(visibility, 0, 1);
	gl_Position	=  projectionMatrix * posRelativeToCam;
}

