package io.entangledword.services.tag;

import org.springframework.stereotype.Service;

import io.entangledword.domain.tag.Tag;
import io.entangledword.port.in.FindUseCase;
import io.entangledword.port.out.blogpost.FindPort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class FindTagsService implements FindUseCase<Tag> {
	private final FindPort<Tag> findPort;

	@Override
	public Mono<Tag> getByID(String id) {
		return this.findPort.getByID(id);
	}

	@Override
	public Flux<Tag> getStream() {
		return this.findPort.getAll();
	}

}
