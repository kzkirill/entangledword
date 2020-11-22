package io.entangledword.web.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import java.util.function.Function;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;

import io.entangledword.port.in.DeleteByIDUseCase;
import io.entangledword.port.in.FindUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public abstract class ReactiveRestHandlerAdapter<DTOType> implements RESTHandler {

	public static final String URI_ID = "ID";
	private Class<DTOType> dtoType;
	private String uriBase;
	protected DeleteByIDUseCase deleteUC;
	protected FindUseCase<DTOType> findUseCase;

	public Mono<ServerResponse> get(ServerRequest serverRequest) {
		Mono<DTOType> found = findUseCase.getByID(serverRequest.pathVariable(URI_ID));
		log.trace("DTO object found: " + found);
		return found.flatMap((DTOType post) -> okWithJason().body(fromPublisher(Mono.just(post), this.dtoType)))
				.switchIfEmpty(notFound().build());
	}

	public Mono<ServerResponse> getStream(ServerRequest serverRequest) {
		return ok().contentType(TEXT_EVENT_STREAM).body(findUseCase.getStream(), this.dtoType);
	}

	protected Mono<ServerResponse> post(ServerRequest requestWithBlogpost, Function<DTOType, Mono<DTOType>> save,
			Function<DTOType, String> getID) {
		final Mono<DTOType> blogpost = getObject(requestWithBlogpost).flatMap(save::apply);
		log.trace("DTO object found: " + blogpost);
		return blogpost.flatMap(
				(DTOType saved) -> created(fromUriString(this.uriBase + "/" + getID.apply(saved)).build().toUri())
						.contentType(APPLICATION_JSON).body(fromPublisher(blogpost, this.dtoType)))
				.switchIfEmpty(notFound().build());
	}

	protected Mono<ServerResponse> put(ServerRequest serverRequest, Function<DTOType, Mono<DTOType>> save) {
		return okWithJason().body(fromPublisher(getObject(serverRequest).flatMap(save::apply), this.dtoType))
				.switchIfEmpty(notFound().build());
	}

	protected Mono<ServerResponse> delete(ServerRequest serverRequest, Function<String, Mono<DTOType>> getByID,
			Function<String, Mono<Void>> deletOne) {
		String ID = serverRequest.pathVariable(URI_ID);
		log.trace("ID to delete : " + ID);
		return getByID.apply(ID).flatMap(p -> deletOne.apply(ID)).flatMap(p -> noContent().build())
				.switchIfEmpty(notFound().build());
	}

	protected Mono<DTOType> getObject(ServerRequest requestWithBlogpost) {
		return requestWithBlogpost.bodyToMono(this.dtoType);
	}

	protected BodyBuilder okWithJason() {
		return ok().contentType(APPLICATION_JSON);
	}

}