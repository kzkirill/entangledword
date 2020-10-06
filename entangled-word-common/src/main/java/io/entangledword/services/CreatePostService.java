package io.entangledword.services;

import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.port.in.CreatePostUseCase;
import io.entangledword.port.out.CreatePostPort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CreatePostService implements CreatePostUseCase {

	private CreatePostPort cpPost;
	
	@Override
	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		return cpPost.save(newPost);
	}

}
