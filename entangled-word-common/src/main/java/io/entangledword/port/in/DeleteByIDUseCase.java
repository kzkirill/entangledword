package io.entangledword.port.in;

import reactor.core.publisher.Mono;

public interface DeleteByIDUseCase {
	public Mono<Void> delete(String id);
}
