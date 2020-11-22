package io.entangledword.port.in.user;

import io.entangledword.domain.user.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindUserUseCase {
	public Flux<UserDTO> getAll();
	public Mono<UserDTO> getByID(String userID);
}
