package io.entangledword.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

	private static final String RESOURCES_STATIC = "resources/static/";
	private static final String RESOURCE_INDEX_HTML = "/" + RESOURCES_STATIC + "index.html";
	private static final Logger log = LoggerFactory.getLogger(WebFluxConfig.class);

	public static ClassPathResource indexPageResource() {
		return new ClassPathResource(RESOURCE_INDEX_HTML);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").maxAge(3600);
	}
}
