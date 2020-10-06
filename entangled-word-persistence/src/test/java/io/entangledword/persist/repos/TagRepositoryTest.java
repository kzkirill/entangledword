package io.entangledword.persist.repos;

import static io.entangledword.persist.entity.TagMongoDoc.newInstance;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.test.StepVerifier.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import io.entangledword.persist.entity.TagMongoDoc;

@DataMongoTest
public class TagRepositoryTest {

	@Autowired
	private TagRepository testee;
	private String valueForSaved = "tag01";
	private String valueDuplicate = "tag011";
	private String valueForNonEqualTest = "tag02";

	@Test
	public void whenSaved_theSameReturned() {
		TagMongoDoc newTag = newInstance(valueForSaved);
		create(testee.save(newTag)).expectNext(newTag).expectComplete().verify();
	}

	@Test
	public void whenNotSaved_thenNotFoundByValue() {
		create(testee.findByValue("I am not saved")).expectNextCount(0l).expectComplete().verify();

	}

	@Test
	public void whenSaved_thenFetchedByValue() {
		TagMongoDoc newTag = newInstance(valueForSaved);
		testee.save(newTag).block();
		create(testee.findByValue(newTag.getValue()))
				.expectNextMatches(found -> found.equals(newTag) && !found.getValue().equals(valueForNonEqualTest))
				.expectComplete().verify();
	}

	@Test
	public void whenSavedTwiceSameValue_onlyOneExists() {
		TagMongoDoc newTag = newInstance(valueDuplicate);
		TagMongoDoc newTagSame = newInstance(valueDuplicate);
		assertTrue(newTag.equals(newTagSame));
		testee.save(newTag).block();
		testee.save(newTagSame).block();
		create(testee.findByValue(valueDuplicate)).expectNextCount(1l).expectComplete().verify();
	}

	@Test
	public void whenHaveTags_thenAllAreFetched() {
		testee.deleteAll().block();
		TagMongoDoc newTag = newInstance(valueForSaved);
		testee.save(newTag).block();
		newTag = newInstance(valueDuplicate);
		testee.save(newTag).block();
		create(testee.findAll()).expectNextCount(2l).expectComplete().verify();
	}
}
