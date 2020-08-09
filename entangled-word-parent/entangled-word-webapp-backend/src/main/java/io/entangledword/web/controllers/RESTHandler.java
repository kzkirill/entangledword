package io.entangledword.web.controllers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface RESTHandler {

	public Mono<ServerResponse> get(ServerRequest serverRequest);
	public Mono<ServerResponse> getStream(ServerRequest serverRequest);
	public Mono<ServerResponse> post(ServerRequest serverRequest);
	public Mono<ServerResponse> put(ServerRequest serverRequest);
	public Mono<ServerResponse> delete(ServerRequest serverRequest);

}
