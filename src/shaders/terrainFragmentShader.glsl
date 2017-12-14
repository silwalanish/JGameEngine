#version 120

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

varying vec2 tex_coord;
varying vec3 surfaceNormal;
varying vec3 toLight;
varying vec3 toCamera;
varying float visibility;

void main()
{
	vec4 blendMapColor = texture2D(blendMap, tex_coord);

	float backTexAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoord = tex_coord * 40.0;
	vec4 backTexColor = texture2D(backgroundTexture, tiledCoord) * backTexAmount;
	vec4 rTexColor = texture2D(rTexture, tiledCoord) * blendMapColor.r;
	vec4 gTexColor = texture2D(gTexture, tiledCoord) * blendMapColor.g;
	vec4 bTexColor = texture2D(bTexture, tiledCoord) * blendMapColor.b;

	vec4 totalColor = backTexColor + rTexColor + gTexColor + bTexColor;

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLight = normalize(toLight);
	vec3 unitCamera = normalize(toCamera);
	vec3 lightDir = -unitLight;
	vec3 reflectLightDir = reflect(lightDir, unitNormal);

	float dotP = dot(unitNormal, unitLight);
	float brightness = max(dotP, 0.2);

	vec3 halfV = normalize(toCamera + toLight);
	float specularFact = dot(reflectLightDir, unitNormal);
	specularFact = max(specularFact, 0);
	float dampedFactor = pow(specularFact, shineDamper);

	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

	vec3 diffuse = brightness * lightColor;

	vec4 actualColor = vec4(diffuse, 1.0) * totalColor + vec4(finalSpecular, 1.0);

	gl_FragColor = mix(vec4(skyColor, 1), actualColor, visibility);
}
