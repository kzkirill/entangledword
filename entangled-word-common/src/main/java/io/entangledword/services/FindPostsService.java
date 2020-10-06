package io.entangledword.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.port.in.FindPostsUseCase;
import io.entangledword.port.out.FindPostsPort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FindPostsService implements FindPostsUseCase {

	private final FindPostsPort findPosts;
	
	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return findPosts.getByID(id);
	}

	@Override
	public Flux<BlogpostDTO> getStream() {
		return findPosts.getStream();
	}

	@Override
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues) {
		return findPosts.getByTagsList(tagsValues);
	}

}
