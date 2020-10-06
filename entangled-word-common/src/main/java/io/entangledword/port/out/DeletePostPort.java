package io.entangledword.port.out;

import reactor.core.publisher.Mono;

public interface DeletePostPort {

	Mono<Void> delete(String id);

}
