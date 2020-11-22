package io.entangledword.web.routers;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.web.controllers.RESTHandler;
import io.entangledword.web.controllers.TagHandler;

@Configuration
public class TagRouter {
	@Bean
	public RouterFunction<ServerResponse> tagRouterFunction(RESTHandler tagHandler) {
		return route(GET(TagHandler.URI_BASE).and(accept(TEXT_EVENT_STREAM)), tagHandler::getStream);
	}
}
