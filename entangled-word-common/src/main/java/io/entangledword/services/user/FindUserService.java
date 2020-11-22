package io.entangledword.services.user;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.entangledword.domain.user.User;
import io.entangledword.port.in.blogpost.FindUseCase;
import io.entangledword.port.out.user.FindUserPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FindUserService implements FindUseCase<User> {
	private final FindUserPort findUserPort;

	public FindUserService(FindUserPort findUserPort) {
		super();
		this.findUserPort = findUserPort;
	}

	@Override
	public Mono<User> getByID(String userID) {
		return this.findUserPort.getByID(userID);
	}

	@Override
	public Flux<User> getStream() {
		return this.findUserPort.getAll();
	}

	@Override
	public Flux<User> getByTagsList(Set<String> tagsValues) {
		return null;
	}

}
