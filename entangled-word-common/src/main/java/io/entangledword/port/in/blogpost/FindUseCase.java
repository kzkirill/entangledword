package io.entangledword.port.in.blogpost;

import java.util.Set;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindUseCase<DTOType> {
	public Mono<DTOType> getByID(String id);

	public Flux<DTOType> getStream();

	public Flux<DTOType> getByTagsList(Set<String> tagsValues);
}
