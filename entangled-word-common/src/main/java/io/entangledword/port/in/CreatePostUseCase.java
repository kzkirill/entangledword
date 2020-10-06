package io.entangledword.port.in;

import io.entangledword.domain.post.BlogpostDTO;
import reactor.core.publisher.Mono;

public interface CreatePostUseCase {
	public Mono<BlogpostDTO> save(BlogpostDTO newPost);
}
