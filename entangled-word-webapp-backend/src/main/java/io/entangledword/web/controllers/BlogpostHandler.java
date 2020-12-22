package io.entangledword.web.controllers;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.post.BlogpostPreview;
import io.entangledword.port.in.DeleteByIDUseCase;
import io.entangledword.port.in.FindUseCase;
import io.entangledword.port.in.blogpost.CreatePostUseCase;
import io.entangledword.port.in.blogpost.FindBlogpostSpecificUseCase;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class BlogpostHandler extends ReactiveRestHandlerAdapter<BlogpostDTO> {

	public static final String URI_TAGS = "tags";
	public static final String URI_AUTHOR = "author";
	public static final String URI_BASE = "/blogpost";
	public static final String URI_SEARCH = "/search";
	@Autowired
	private CreatePostUseCase createUC;
	private FindBlogpostSpecificUseCase findPostUC;

	public BlogpostHandler(CreatePostUseCase createUC, DeleteByIDUseCase deleteUC, FindUseCase<BlogpostDTO> findPostsUC,
			FindBlogpostSpecificUseCase findPostPreviewUC) {
		super(BlogpostDTO.class, URI_BASE, deleteUC, findPostsUC);
		this.createUC = createUC;
		this.findPostUC = findPostPreviewUC;
	}

	@Override
	public Mono<ServerResponse> getStream(ServerRequest serverRequest) {
		return ok().contentType(TEXT_EVENT_STREAM).body(findPostUC.getAllPreviews(), BlogpostPreview.class);
	}

	@Override
	public Mono<ServerResponse> post(ServerRequest requestWithBlogpost) {
		Function<BlogpostDTO, String> getID = (BlogpostDTO dto) -> dto.getID();
		return super.post(requestWithBlogpost, this.createUC::save, getID);
	}

	@Override
	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		return super.put(serverRequest, this.createUC::save);
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		return super.delete(serverRequest, findUseCase::getByID, this.deleteUC::delete);
	}

	@Override
	public Mono<ServerResponse> getPostsByQueryParams(ServerRequest serverRequest) {
		return serverRequest.queryParams().keySet().size() == 0
				? badRequestResponse("No query parameters were provided.")
				: fromParamNames(serverRequest);

	}

	private Mono<ServerResponse> fromParamNames(ServerRequest serverRequest) {
		Set<String> paramsNames = serverRequest.queryParams().keySet();
		log.info("All query params in the request : " + paramsNames + " " + serverRequest.queryParams().values());
		String key = paramsNames.iterator().next();
		Optional<Function<HashSet<String>, Flux<BlogpostPreview>>> getByQuery = getMethod(key);
		return getByQuery.isEmpty() ? badRequestResponse(format("No method defined for parameter %s", key))
				: fromQuery(serverRequest, key, getByQuery.get());
	}

	private Mono<ServerResponse> fromQuery(ServerRequest serverRequest, String key,
			Function<HashSet<String>, Flux<BlogpostPreview>> getByQuery) {
		String[] asArray = serverRequest.queryParam(key).map(param -> param.split(",")).get();
		HashSet<String> query = new HashSet<>();
		stream(asArray).filter(value -> value.length() > 0).forEach(member -> query.add(member));
		log.info(format("Query params received:%s %s size %d ", asArray, query, query.size()));
		return query.isEmpty() ? badRequestResponse("Query parameters cannot be empty.")
				: ok().contentType(TEXT_EVENT_STREAM).body(getByQuery.apply(query), BlogpostPreview.class);
	}

	protected Optional<Function<HashSet<String>, Flux<BlogpostPreview>>> getMethod(String queryKey) {
		if (URI_TAGS.equals(queryKey)) {
			return Optional.of((parameter) -> findPostUC.getByTagsList(parameter));
		}
		if (URI_AUTHOR.equals(queryKey)) {
			return Optional.of((parameter) -> findPostUC.getByAuthorsList(parameter));
		}
		return Optional.empty();

	}

}
