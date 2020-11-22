package io.entangledword.web.controllers;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.domain.tag.Tag;
import io.entangledword.port.in.FindUseCase;
import reactor.core.publisher.Mono;

@Component
public class TagHandler extends ReactiveRestHandlerAdapter<Tag> {

	public static final String URI_BASE = "/tag";

	public TagHandler(FindUseCase<Tag> findUseCase) {
		super(Tag.class, URI_BASE, null, findUseCase);
	}

	@Override
	public Mono<ServerResponse> post(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

	@Override
	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

	@Override
	public Mono<ServerResponse> getPostsByQueryParams(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

}
