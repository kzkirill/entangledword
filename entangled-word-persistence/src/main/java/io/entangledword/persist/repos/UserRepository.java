package io.entangledword.persist.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import io.entangledword.persist.entity.UserMongoDoc;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserMongoDoc, String> {

}
