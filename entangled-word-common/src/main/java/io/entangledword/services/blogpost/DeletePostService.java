package io.entangledword.services.blogpost;

import org.springframework.stereotype.Service;

import io.entangledword.port.in.DeleteByIDUseCase;
import io.entangledword.port.out.blogpost.DeletePostPort;
import reactor.core.publisher.Mono;

@Service
public class DeletePostService implements DeleteByIDUseCase {

	private DeletePostPort deletePort;
	
	@Override
	public Mono<Void> delete(String id) {
		return deletePort.delete(id);
	}

}
