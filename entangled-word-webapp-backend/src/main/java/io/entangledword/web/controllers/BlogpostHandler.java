package io.entangledword.web.controllers;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.HashSet;
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
import reactor.core.publisher.Mono;

@Component
public class BlogpostHandler extends ReactiveRestHandlerAdapter<BlogpostDTO> {

	public static final String URI_TAGS = "tags";
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
		String tagsQuery = serverRequest.queryParam(URI_TAGS)
				.orElseThrow(() -> new IllegalArgumentException("Query parameters cannot be empty."));
		return ok().contentType(TEXT_EVENT_STREAM).body(
				findPostUC.getByTagsList(new HashSet<String>(Arrays.asList(tagsQuery.split(",")))), BlogpostDTO.class);
	}

}
