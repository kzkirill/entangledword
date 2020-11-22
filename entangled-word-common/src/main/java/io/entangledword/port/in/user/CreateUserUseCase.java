package io.entangledword.port.in.user;

import io.entangledword.domain.user.UserDTO;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {
	public Mono<UserDTO> save(UserDTO user);
}
