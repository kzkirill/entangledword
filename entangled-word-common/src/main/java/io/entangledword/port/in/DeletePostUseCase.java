package io.entangledword.port.in;

import reactor.core.publisher.Mono;

public interface DeletePostUseCase {
	public Mono<Void> delete(String id);
}
