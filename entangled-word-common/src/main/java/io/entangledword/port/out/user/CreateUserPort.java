package io.entangledword.port.out.user;

import io.entangledword.domain.user.User;
import reactor.core.publisher.Mono;

public interface CreateUserPort {
	public Mono<User> save(User user);
}
