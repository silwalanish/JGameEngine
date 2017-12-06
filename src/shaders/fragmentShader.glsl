#version 120

uniform sampler2D samplr;
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
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLight = normalize(toLight);
	vec3 unitCamera = normalize(toCamera);
	vec3 lightDir = -unitLight;
	vec3 reflectLightDir = reflect(lightDir, unitNormal);

	float dotP = dot(unitNormal, unitLight);
	float brightness = max(dotP, 0.2);

	float specularFact = dot(reflectLightDir, unitNormal);
	specularFact = max(specularFact, 0);
	float dampedFactor = pow(specularFact, shineDamper);

	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

	vec3 diffuse = brightness * lightColor;

	vec4 texture_color = texture2D(samplr, tex_coord);

	if(texture_color.a < 0.5f){
		discard;
	}

	vec4 actualColor = vec4(diffuse, 1.0) * texture_color  + vec4(finalSpecular, 1.0);

	gl_FragColor = mix(vec4(skyColor, 1.0), actualColor, visibility);
}
