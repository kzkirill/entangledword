package io.entangledword.web.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class IndexHtmlRouter {
	@Bean
	public RouterFunction<ServerResponse> htmlRouter(@Value("classpath:/resources/static/index.html") Resource html) {
		return route(GET("/"), request -> getIndexResponse(html))
				.andRoute(GET("/feed"), request -> getIndexResponse(html))
				.andRoute(GET("/newpost"), request -> getIndexResponse(html))
				.andRoute(GET("/details" + "/{ID}"), request -> getIndexResponse(html))
				.andRoute(GET("/login"), request -> getIndexResponse(html));
	}

	private Mono<ServerResponse> getIndexResponse(Resource html) {
		return ok().contentType(MediaType.TEXT_HTML).bodyValue(html);
	}

}
