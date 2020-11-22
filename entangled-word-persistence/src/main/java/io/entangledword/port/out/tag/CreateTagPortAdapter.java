package io.entangledword.port.out.tag;

import org.springframework.stereotype.Service;

import io.entangledword.domain.tag.Tag;
import io.entangledword.domain.tag.TagInt;
import io.entangledword.persist.entity.TagMongoDoc;
import io.entangledword.persist.repos.TagRepository;
import io.entangledword.port.out.DTOMappingService;
import io.entangledword.port.out.blogpost.CreateTagPort;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Data
public class CreateTagPortAdapter implements CreateTagPort {

	private final TagRepository repo;
	private final DTOMappingService<Tag, TagMongoDoc> mapping;

	@Override
	public Mono<TagInt> save(TagInt tag) {
		Mono<TagMongoDoc> ss = repo.save(mapping.toEntity((Tag) tag));
		return ss.map(mapping::toDTO);
	}

}
