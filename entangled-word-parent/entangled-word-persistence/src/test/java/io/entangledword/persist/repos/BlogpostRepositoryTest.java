package io.entangledword.persist.repos;

import static reactor.test.StepVerifier.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import io.entangledword.persist.entity.BlogpostMongoDoc;

@DataMongoTest
class BlogpostRepositoryTest {
	@Autowired
	private BlogpostRepository testee;
	
	@Test
	void testSave() {
		BlogpostMongoDoc newPost = BlogpostMongoDoc.newInstance("Save 01 title", "Save 01 text", "Save 01  author");
		BlogpostMongoDoc anotherPost = BlogpostMongoDoc.newInstance("Another 01 title", "Another 01 text", "Another 01  author");
		create(testee.save(newPost)).
		expectNextMatches(saved -> saved.equals(newPost) && !saved.equals(anotherPost)).
		expectComplete().
		verify();
	}
	
	@Test
	void testGetById() {
		
	}

	@Test
	void testGetStream() {
		
	}

	@Test
	void testDelete() {
		
	}

}
