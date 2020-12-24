package io.entangledword.web.routers;

import static io.entangledword.web.controllers.UserHandler.USER_ID;
import static io.entangledword.web.controllers.UserHandler.URI_BASE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.web.controllers.RESTHandler;

@Component
public class UserRouter {

	@Bean
	public RouterFunction<ServerResponse> userRouterFunction(RESTHandler userHandler) {
		return route(POST(URI_BASE).and(accept(APPLICATION_JSON)), userHandler::post)
				.andRoute(GET(URI_BASE).and(accept(TEXT_EVENT_STREAM)), userHandler::getStream)
				.andRoute(GET(URI_BASE + "/{" + USER_ID + "}").and(accept(APPLICATION_JSON)), userHandler::get)
				.andRoute(PUT(URI_BASE + "/{" + USER_ID + "}").and(accept(APPLICATION_JSON)), userHandler::put)
				.andRoute(DELETE(URI_BASE + "/{" + USER_ID + "}"), userHandler::delete);
	}

}
