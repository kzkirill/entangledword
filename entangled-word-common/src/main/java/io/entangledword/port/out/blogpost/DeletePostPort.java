package io.entangledword.port.out.blogpost;

import reactor.core.publisher.Mono;

public interface DeletePostPort {

	Mono<Void> delete(String id);

}
