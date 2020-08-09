package io.entangledword.port.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import reactor.core.publisher.Mono;

@Service
public class CreatePostAdapter implements CreatePostPort{

	@Autowired
	private BlogpostRepository repo;
	@Autowired
	private DTOMappingService<BlogpostDTO, BlogpostMongoDoc> mapping;

	@Override
	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		return repo.save(mapping.toEntity(newPost)).map(mapping::toDTO);
	}

}
