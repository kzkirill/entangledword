package io.entangledword.port.out.blogpost;

import static io.entangledword.domain.tag.Tag.newInstance;
import static reactor.core.publisher.Flux.fromIterable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.tag.TagInt;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import io.entangledword.port.out.DTOMappingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreatePostAdapter implements CreatePostPort {

	@Autowired
	private BlogpostRepository repo;
	@Autowired
	private DTOMappingService<BlogpostDTO, BlogpostMongoDoc> mapping;
	@Autowired
	private CreateTagPort tagCreate;

	@Override
	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		Flux<TagInt> saved = fromIterable(newPost.getTags()).flatMap(tagValue -> tagCreate.save(newInstance(tagValue)));
		return saved.then(repo.save(mapping.toEntity(newPost)).map(mapping::toDTO));
	}

}
