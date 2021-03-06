package io.entangledword.port.out.blogpost;

import io.entangledword.domain.tag.TagInt;
import reactor.core.publisher.Mono;

public interface CreateTagPort {
	public Mono<TagInt> save(TagInt tag);
}
