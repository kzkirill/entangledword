package io.entangledword.port.out;

import io.entangledword.domain.post.BlogpostDTO;
import reactor.core.publisher.Mono;

public interface CreatePostPort {

	Mono<BlogpostDTO> save(BlogpostDTO newPost);

}
