package io.entangledword.services;

import org.springframework.stereotype.Service;

import io.entangledword.port.in.DeletePostUseCase;
import io.entangledword.port.out.DeletePostPort;
import reactor.core.publisher.Mono;

@Service
public class DeletePostService implements DeletePostUseCase {

	private DeletePostPort deletePort;
	
	@Override
	public Mono<Void> delete(String id) {
		return deletePort.delete(id);
	}

}
