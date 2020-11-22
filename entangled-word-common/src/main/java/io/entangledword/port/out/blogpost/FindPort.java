package io.entangledword.port.out.blogpost;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindPort<DTOType> {

	Mono<DTOType> getByID(String id);

	Flux<DTOType> getAll();

}