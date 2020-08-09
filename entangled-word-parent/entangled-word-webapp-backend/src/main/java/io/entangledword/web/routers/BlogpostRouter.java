package io.entangledword.web.routers;

import static io.entangledword.web.controllers.BlogpostHandler.URI_BASE;
import static io.entangledword.web.controllers.BlogpostHandler.URI_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.web.controllers.RESTHandler;
import reactor.core.publisher.Mono;

@Configuration
public class BlogpostRouter {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(RESTHandler controller) {
		return route(POST(URI_BASE).and(accept(APPLICATION_JSON)), controller::post)
				.andRoute(GET(URI_BASE).and(accept(TEXT_EVENT_STREAM)), controller::getStream)
				.andRoute(GET(URI_BASE + "/{" + URI_ID + "}").and(accept(APPLICATION_JSON)), controller::get)
				.andRoute(PUT(URI_BASE + "/{" + URI_ID + "}").and(accept(APPLICATION_JSON)), controller::put)
				.andRoute(DELETE(URI_BASE + "/{" + URI_ID + "}"), controller::delete);
	}

	@Bean
	public RouterFunction<ServerResponse> htmlRouter(@Value("classpath:/resources/static/index.html") Resource html) {
		return route(GET("/"), request -> getIndexResponse(html))
				.andRoute(GET("/feed"), request -> getIndexResponse(html))
				.andRoute(GET("/newpost"), request -> getIndexResponse(html))
				.andRoute(GET("/login"), request -> getIndexResponse(html));
	}

	private Mono<ServerResponse> getIndexResponse(Resource html) {
		return ok().contentType(MediaType.TEXT_HTML).bodyValue(html);
	}
}
