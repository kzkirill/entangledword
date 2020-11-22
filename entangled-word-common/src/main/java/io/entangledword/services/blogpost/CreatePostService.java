package io.entangledword.services.blogpost;

import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.port.in.blogpost.CreatePostUseCase;
import io.entangledword.port.out.blogpost.CreatePostPort;
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
