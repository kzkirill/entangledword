package io.entangledword.web.controllers;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import io.entangledword.port.in.DeleteByIDUseCase;
import io.entangledword.port.in.FindUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
public abstract class ReactiveRestHandlerAdapter<DTOType> implements RESTHandler {

	public static final String URI_ID = "ID";
	private Class<DTOType> dtoType;
	private String uriBase;
	protected DeleteByIDUseCase deleteUC;
	protected FindUseCase<DTOType> findUseCase;

	public Mono<ServerResponse> get(ServerRequest serverRequest) {
		Mono<DTOType> found = findUseCase.getByID(serverRequest.pathVariable(URI_ID)).map(foundObject -> {
			log.info(format("DTO object found: %s", foundObject));
			return foundObject;
		});

		return found.flatMap((DTOType dto) -> okWithJason().body(fromPublisher(Mono.just(dto), this.dtoType)))
				.switchIfEmpty(notFound().build());
	}

	public Mono<ServerResponse> getStream(ServerRequest serverRequest) {
		return ok().contentType(TEXT_EVENT_STREAM).body(findUseCase.getStream(), this.dtoType);
	}

	protected Mono<ServerResponse> post(ServerRequest requestWithObject, Function<DTOType, Mono<DTOType>> save,
			Function<DTOType, String> getID) {
		log.info("POST called on blogpost handler");
		final Mono<DTOType> dtoMono = getObject(requestWithObject).flatMap(save::apply);
		return dtoMono.flatMap(saved -> {
			log.info(format("POST: Saved dto id:%s %s", getID.apply(saved), saved));
			return ServerResponse
					.created(
							UriComponentsBuilder.fromUriString(this.uriBase + "/" + getID.apply(saved)).build().toUri())
					.contentType(APPLICATION_JSON).body(fromPublisher(Mono.just(saved), this.dtoType));
		});
	}

	protected Mono<ServerResponse> put(ServerRequest serverRequest, Function<DTOType, Mono<DTOType>> save) {
		String ID = serverRequest.pathVariable(URI_ID);
		log.info("PUT: ID to update : " + ID);
		return this.findUseCase.getByID(ID)
				.flatMap(found -> getObject(serverRequest))
				.flatMap(fromRequest -> save.apply(fromRequest))
				.flatMap(saved -> okFromDto(saved, this.dtoType))
				.switchIfEmpty(logAndCreateNotFound(ID));

	}
	
	protected Mono<ServerResponse> okFromDto(DTOType dto , Class<DTOType> dtoType) {
		return okWithJason().body(fromPublisher(Mono.just(dto),dtoType));
	}

	protected Mono<ServerResponse> delete(ServerRequest serverRequest, Function<String, Mono<DTOType>> getByID,
			Function<String, Mono<Void>> deletOne) {
		String ID = serverRequest.pathVariable(URI_ID);
		log.info("DELETE: ID to delete : " + ID);
		return getByID.apply(ID).flatMap(p -> deletOne.apply(ID)).flatMap(p -> noContent().build())
				.switchIfEmpty(logAndCreateNotFound(ID));
	}

	protected Mono<DTOType> getObject(ServerRequest requestWithObject) {
		Mono<DTOType> DTOObjectMono = requestWithObject.bodyToMono(this.dtoType).map(fromRequest -> {
			log.info("DTO object in request: " + fromRequest);
			return fromRequest;
		});
		return DTOObjectMono;
	}

	protected BodyBuilder okWithJason() {
		return ok().contentType(APPLICATION_JSON);
	}

	protected Mono<ServerResponse> logAndCreateNotFound(String ID) {
		log.info("ID not found : " + ID);
		return notFound().build();
	}

	protected Mono<ServerResponse> badRequestResponse(String message) {
		log.error(message);
		return ServerResponse.badRequest().body(BodyInserters.fromPublisher(Mono.just(message), String.class));
	}

}