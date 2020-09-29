package io.entangledword.port.in;

import java.util.Set;

import io.entangledword.domain.post.BlogpostDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindPostsUseCase {
	public Mono<BlogpostDTO> getByID(String id);
	public Flux<BlogpostDTO> getStream();
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues);
}
