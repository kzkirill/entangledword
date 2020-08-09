package io.entangledword.port.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FindPostsAdapter implements FindPostsPort {
	
	@Autowired
	private BlogpostRepository repo;
	@Autowired
	private DTOMappingService<BlogpostDTO, BlogpostMongoDoc> mapping;

	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return repo.findById(id).map(mapping::toDTO);
	}

	@Override
	public Flux<BlogpostDTO> getStream() {
		return repo.findAll().map(mapping::toDTO);
	}
}
