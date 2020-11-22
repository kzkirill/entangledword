package io.entangledword.port.out.user;

import org.springframework.stereotype.Service;

import io.entangledword.domain.user.User;
import io.entangledword.persist.entity.UserMongoDoc;
import io.entangledword.persist.repos.UserRepository;
import io.entangledword.port.out.DTOMappingService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FindUserAdapter implements FindUserPort {
	
	private UserRepository repo;
	private DTOMappingService<User, UserMongoDoc> mapping;

	@Override
	public Flux<User> getAll() {
		return this.repo.findAll().map(this.mapping::toDTO);
	}

	@Override
	public Mono<User> getByID(String userID) {
		return repo.findById(userID).map(this.mapping::toDTO);
	}

}
