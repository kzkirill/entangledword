package io.entangledword.port.out.user;

import io.entangledword.domain.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindUserPort {
	public Flux<User> getAll();

	public Mono<User> getByID(String userID);
}
