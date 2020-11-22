package io.entangledword.port.out.user;

import io.entangledword.domain.user.UserDTO;
import reactor.core.publisher.Mono;

public interface CreateUserPort {
	public Mono<UserDTO> save(UserDTO user);
}
