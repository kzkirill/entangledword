package io.entangledword.web.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;

import io.entangledword.model.post.BlogpostDTO;
import io.entangledword.services.BlogpostService;
import reactor.core.publisher.Mono;

@Component
public class BlogpostHandler implements RESTHandler {

	public static final String URI_ID = "ID";
	public static final String URI_BASE = "/blogpost";
	public static final String URI_ALL = "all";
	private BlogpostService blogpostService;

	public BlogpostHandler(BlogpostService blogpostService) {
		super();
		this.blogpostService = blogpostService;
	}

	@Override
	public Mono<ServerResponse> get(ServerRequest serverRequest) {
		Mono<BlogpostDTO> found = blogpostService.getByID(serverRequest.pathVariable(URI_ID));
		return found.flatMap((BlogpostDTO post) -> okWithJason().body(fromPublisher(Mono.just(post), BlogpostDTO.class)))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
		return okWithJason().body(fromPublisher(blogpostService.getAll(), BlogpostDTO.class));
	}

	@Override
	public Mono<ServerResponse> post(ServerRequest requestWithBlogpost) {
		final Mono<BlogpostDTO> blogpost = getObject(requestWithBlogpost).flatMap(blogpostService::save);
		
		return blogpost
				.flatMap(saved -> created(fromUriString(URI_BASE + "/" + saved.getID()).build().toUri())
						.contentType(APPLICATION_JSON).body(fromPublisher(blogpost, BlogpostDTO.class)))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		return okWithJason()
				.body(fromPublisher(getObject(serverRequest).flatMap(blogpostService::save), BlogpostDTO.class))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		String ID = serverRequest.pathVariable(URI_ID);
		return blogpostService.getByID(ID).flatMap(p -> blogpostService.delete(p)).flatMap(p -> noContent().build())
				.switchIfEmpty(notFound().build());
	}

	protected Mono<BlogpostDTO> getObject(ServerRequest requestWithBlogpost) {
		return requestWithBlogpost.bodyToMono(BlogpostDTO.class);
	}

	private BodyBuilder okWithJason() {
		return ok().contentType(APPLICATION_JSON);
	}

	@Override
	public Mono<ServerResponse> getStream(ServerRequest serverRequest) {
		return ok().contentType(TEXT_EVENT_STREAM).body(blogpostService.getStream(), BlogpostDTO.class);
	}

}
