package io.entangledword.persist.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import io.entangledword.persist.entity.BlogpostMongoDoc;

@Repository
public interface BlogpostRepository extends ReactiveMongoRepository<BlogpostMongoDoc, String> {

}
