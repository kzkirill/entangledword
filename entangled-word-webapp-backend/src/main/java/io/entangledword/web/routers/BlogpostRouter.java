package io.entangledword.web.routers;

import static io.entangledword.web.controllers.BlogpostHandler.BLOGPOST_ID;
import static io.entangledword.web.controllers.BlogpostHandler.URI_BASE;
import static io.entangledword.web.controllers.BlogpostHandler.URI_SEARCH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.web.controllers.RESTHandler;

@Configuration
public class BlogpostRouter {
	@Bean
	public RouterFunction<ServerResponse> blogpostRouterFunction(RESTHandler blogpostHandler) {
		return route(POST(URI_BASE).and(accept(APPLICATION_JSON)), blogpostHandler::post)
				.andRoute(GET(URI_BASE).and(accept(TEXT_EVENT_STREAM)), blogpostHandler::getStream)
				.andRoute(GET(URI_BASE + URI_SEARCH).and(accept(TEXT_EVENT_STREAM)),
						blogpostHandler::getPostsByQueryParams)
				.andRoute(GET(URI_BASE + "/{" + BLOGPOST_ID + "}").and(accept(APPLICATION_JSON)), blogpostHandler::get)
				.andRoute(PUT(URI_BASE + "/{" + BLOGPOST_ID + "}").and(accept(APPLICATION_JSON)), blogpostHandler::put)
				.andRoute(DELETE(URI_BASE + "/{" + BLOGPOST_ID + "}"), blogpostHandler::delete);
	}

}
