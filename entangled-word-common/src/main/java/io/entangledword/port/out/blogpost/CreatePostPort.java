package io.entangledword.port.out.blogpost;

import io.entangledword.domain.post.BlogpostDTO;
import reactor.core.publisher.Mono;

public interface CreatePostPort {

	Mono<BlogpostDTO> save(BlogpostDTO newPost);

}
