package io.entangledword.persist.repos;

import static reactor.test.StepVerifier.create;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import io.entangledword.persist.entity.BlogpostMongoDoc;

@DataMongoTest
@TestInstance(Lifecycle.PER_CLASS)
class BlogpostRepositoryTest {
	@Autowired
	private BlogpostRepository testee;
	private BlogpostMongoDoc forGetIdTest;
	private BlogpostMongoDoc newPost;
	
	@BeforeAll
	void setup() {
		newPost = BlogpostMongoDoc.newInstance("Save 01 title", "Save 01 text", "Save 01  author");
		forGetIdTest = testee
				.save(BlogpostMongoDoc.newInstance("Get By ID 01 title", "Get by ID 01 text", "Get by ID 01  author"))
				.block();
	}
	
	@Test
	void testSave() {
		BlogpostMongoDoc anotherPost = BlogpostMongoDoc.newInstance("Another 01 title", "Another 01 text", "Another 01  author");
		create(testee.save(newPost)).
		expectNextMatches(saved -> saved.equals(newPost) && !saved.equals(anotherPost)).
		expectComplete().
		verify();
	}
	
	@Test
	void testGetById() {
		create(testee.findById(forGetIdTest.getId())).
		expectNext(forGetIdTest).
		expectComplete().
		verify();
	}

	@Test
	void testGetStream() {
		create(testee.findAll()).
		expectNextMatches(this::inAllExpected).
		expectNextMatches(this::inAllExpected).
		expectComplete().
		verify();
	}

	@Test
	void testDelete() {
		
	}
	
	private boolean inAllExpected(BlogpostMongoDoc entity) {
		return entity.equals(forGetIdTest) || entity.equals(newPost);
	}

}
