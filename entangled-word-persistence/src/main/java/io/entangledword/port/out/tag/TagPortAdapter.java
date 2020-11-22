package io.entangledword.port.out.tag;

import org.springframework.stereotype.Service;

import io.entangledword.domain.tag.Tag;
import io.entangledword.domain.tag.TagInt;
import io.entangledword.persist.entity.TagMongoDoc;
import io.entangledword.persist.repos.TagRepository;
import io.entangledword.port.out.DTOMappingService;
import io.entangledword.port.out.blogpost.CreateTagPort;
import io.entangledword.port.out.blogpost.FindPort;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Data
public class TagPortAdapter implements CreateTagPort, FindPort<Tag> {

	private final TagRepository repo;
	private final DTOMappingService<Tag, TagMongoDoc> mapping;

	@Override
	public Mono<TagInt> save(TagInt tag) {
		Mono<TagMongoDoc> ss = repo.save(mapping.toEntity((Tag) tag));
		return ss.map(mapping::toDTO);
	}

	@Override
	public Mono<Tag> getByID(String id) {
		return this.repo.findById(id).map(this.mapping::toDTO);
	}

	@Override
	public Flux<Tag> getAll() {
		return this.repo.findAll().map(this.mapping::toDTO);
	}
}
