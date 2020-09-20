package io.entangledword.persist.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.entangledword.persist.entity.TagMongoDoc;
import reactor.core.publisher.Flux;

public interface TagRepository extends ReactiveMongoRepository<TagMongoDoc, String> {

	public Flux<TagMongoDoc> findByValue(String value);

}
