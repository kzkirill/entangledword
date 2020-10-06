package io.entangledword.persist.repos;

import java.util.Set;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import io.entangledword.persist.entity.BlogpostMongoDoc;
import reactor.core.publisher.Flux;

@Repository
public interface BlogpostRepository extends ReactiveMongoRepository<BlogpostMongoDoc, String> {

	public Flux<BlogpostMongoDoc> findByTagsContaining(Set<String> tagsValues);
}
