package io.entangledword.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/api/**")
//        .allowedOrigins("https://domain2.com")
//        .allowedMethods("PUT", "DELETE")
//        .allowedHeaders("header1", "header2", "header3")
//        .exposedHeaders("header1", "header2")
//        .allowCredentials(true).maxAge(3600);
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").maxAge(3600);
	}
}
