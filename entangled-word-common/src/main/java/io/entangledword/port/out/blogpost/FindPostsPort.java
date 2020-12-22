package io.entangledword.port.out.blogpost;

import java.util.Set;

import io.entangledword.domain.post.BlogpostDTO;
import reactor.core.publisher.Flux;

public interface FindPostsPort extends FindPort<BlogpostDTO> {
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues);
	public Flux<BlogpostDTO> getByAuthorsList(Set<String> authorsIDs);
}
