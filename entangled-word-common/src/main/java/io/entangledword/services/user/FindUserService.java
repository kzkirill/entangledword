package io.entangledword.services.user;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.entangledword.domain.user.UserDTO;
import io.entangledword.port.in.FindUseCase;
import io.entangledword.port.out.user.FindUserPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FindUserService implements FindUseCase<UserDTO> {
	private final FindUserPort findUserPort;

	public FindUserService(FindUserPort findUserPort) {
		super();
		this.findUserPort = findUserPort;
	}

	@Override
	public Mono<UserDTO> getByID(String userID) {
		return this.findUserPort.getByID(userID);
	}

	@Override
	public Flux<UserDTO> getStream() {
		return this.findUserPort.getAll();
	}

	@Override
	public Flux<UserDTO> getByTagsList(Set<String> tagsValues) {
		return null;
	}

}
