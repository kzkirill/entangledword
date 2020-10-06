package io.entangledword.port.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entangledword.persist.repos.BlogpostRepository;
import reactor.core.publisher.Mono;

@Service
public class DeletePostAdapter implements DeletePostPort {

	@Autowired
	private BlogpostRepository repo;

	@Override
	public Mono<Void> delete(String id) {
		return repo.deleteById(id);
	}
}
