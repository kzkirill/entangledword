package io.entangledword.web.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.port.in.CreatePostUseCase;
import io.entangledword.port.in.DeletePostUseCase;
import io.entangledword.port.in.FindPostsUseCase;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class BlogpostHandler implements RESTHandler {

	public static final String URI_ID = "ID";
	public static final String URI_TAGS = "tags";
	public static final String URI_BASE = "/blogpost";
	public static final String URI_SEARCH = "/search";
	@Autowired
	private CreatePostUseCase createUC;
	@Autowired
	private DeletePostUseCase deleteUC;
	@Autowired
	private FindPostsUseCase findPostsUC;

	public BlogpostHandler(DeletePostUseCase deletePost, CreatePostUseCase createPost) {
		super();
		this.createUC = createPost;
		this.deleteUC = deletePost;
	}

	@Override
	public Mono<ServerResponse> get(ServerRequest serverRequest) {
		Mono<BlogpostDTO> found = findPostsUC.getByID(serverRequest.pathVariable(URI_ID));
		return found
				.flatMap((BlogpostDTO post) -> okWithJason().body(fromPublisher(Mono.just(post), BlogpostDTO.class)))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> post(ServerRequest requestWithBlogpost) {
		final Mono<BlogpostDTO> blogpost = getObject(requestWithBlogpost).flatMap(createUC::save);

		return blogpost
				.flatMap(saved -> created(fromUriString(URI_BASE + "/" + saved.getID()).build().toUri())
						.contentType(APPLICATION_JSON).body(fromPublisher(blogpost, BlogpostDTO.class)))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		return okWithJason().body(fromPublisher(getObject(serverRequest).flatMap(createUC::save), BlogpostDTO.class))
				.switchIfEmpty(notFound().build());
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		String ID = serverRequest.pathVariable(URI_ID);
		return findPostsUC.getByID(ID).flatMap(p -> deleteUC.delete(ID)).flatMap(p -> noContent().build())
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
		return ok().contentType(TEXT_EVENT_STREAM).body(findPostsUC.getStream(), BlogpostDTO.class);
	}

	@Override
	public Mono<ServerResponse> getPostsByQueryParams(ServerRequest serverRequest) {
		String tagsQuery = serverRequest.queryParam(URI_TAGS)
				.orElseThrow(() -> new IllegalArgumentException("Query parameters cannot be empty."));
		return ok().contentType(TEXT_EVENT_STREAM).body(
				findPostsUC.getByTagsList(new HashSet<String>(Arrays.asList(tagsQuery.split(",")))), BlogpostDTO.class);
	}

}
