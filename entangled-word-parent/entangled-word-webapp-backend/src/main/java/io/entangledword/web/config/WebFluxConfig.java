package io.entangledword.web.config;

import static reactor.core.publisher.Mono.just;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.PathResourceResolver;

import reactor.core.publisher.Mono;

@Configuration
//@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

	private static final String RESOURCES_STATIC = "resources/static/";
	private static final String RESOURCE_INDEX_HTML = "/" + RESOURCES_STATIC + "index.html";

	public static ClassPathResource indexPageResource() {
		return new ClassPathResource(RESOURCE_INDEX_HTML);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").maxAge(3600);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebFluxConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations(RESOURCES_STATIC).resourceChain(true)
				.addResolver(new PathResourceResolver() {
					@Override
					protected Mono<Resource> getResource(String resourcePath, Resource location) {
						try {
							Resource requestedResource = location.createRelative(resourcePath);
							Resource resource = requestedResource.exists() && requestedResource.isReadable()
									? requestedResource
									: indexPageResource();
							return just(resource);
						} catch (IOException e) {
							return just(indexPageResource());
						}
					}

				});
	}

}
