package io.entangledword.port.in.user;

import io.entangledword.domain.user.User;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {
	public Mono<User> save(User user);
}
