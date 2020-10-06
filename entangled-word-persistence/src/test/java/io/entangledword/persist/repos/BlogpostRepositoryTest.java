package io.entangledword.persist.repos;

import static io.entangledword.persist.entity.BlogpostMongoDoc.newInstance;
import static reactor.test.StepVerifier.create;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import io.entangledword.persist.entity.BlogpostMongoDoc;
import reactor.core.publisher.Flux;

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
	}

	@Test
	void testSave() {
		testee.deleteAll().block();
		BlogpostMongoDoc anotherPost = BlogpostMongoDoc.newInstance("Another 01 title", "Another 01 text",
				"Another 01  author");
		create(testee.save(newPost)).expectNextMatches(saved -> saved.equals(newPost) && !saved.equals(anotherPost))
				.expectComplete().verify();
	}

	@Test
	void testGetById() {
		testee.deleteAll().block();
		forGetIdTest = testee
				.save(BlogpostMongoDoc.newInstance("Get By ID 01 title", "Get by ID 01 text", "Get by ID 01  author"))
				.block();
		create(testee.findById(forGetIdTest.getId())).expectNext(forGetIdTest).expectComplete().verify();
	}

	@Test
	void testGetStream() {
		testee.deleteAll().block();
		Set<BlogpostMongoDoc> all = Set.of(newInstance("For stream 1", "Text for stream", "Stream Author"), newPost);
		Flux.fromIterable(all)
				.flatMap(post -> testee.save(post)).blockLast();
		create(testee.findAll())
			.expectNextMatches(next -> all.contains(next))
			.expectNextMatches(next -> all.contains(next))
			.expectComplete().verify();
	}

	@Test
	public void whenFindByTags_thenAllPostsWithTagsReturned() {
		testee.deleteAll().block();
		BlogpostMongoDoc withTags = BlogpostMongoDoc.newInstance("With tags 01 title", "With tags text",
				"With tags  01  author");
		Set<String> tagsValues = Set.of("tag1", "tag2", "tag4");
		withTags.setTags(tagsValues);
		BlogpostMongoDoc withDifferentTags = BlogpostMongoDoc.newInstance("With tags 02 title", "With tags 02 text",
				"With tags  02  author");
		withDifferentTags.setTags(Set.of("asas", "sdsdasd"));
		BlogpostMongoDoc withOneTag = BlogpostMongoDoc.newInstance("With tags 03 title", "With tags 03 text",
				"With tags  03  author");
		withOneTag.setTags(Set.of("tag1"));
		testee.save(withTags).block();
		testee.save(withDifferentTags).block();
		testee.save(withOneTag).block();
		create(testee.findAll()).expectNextCount(3l).expectComplete().verify();
		create(testee.findByTagsContaining(Set.of("tag2", "tag1")))
			.expectNext(withTags)
			.expectNext(withOneTag)
			.expectComplete().verify();
		create(testee.findByTagsContaining(Set.of("tag2")))
			.expectNext(withTags)
			.expectComplete().verify();
		create(testee.findByTagsContaining(Set.of("tag1", "tag2", "tag5")))
			.expectNextCount(2l)
			.expectComplete().verify();
	}

	@Test
	void testDelete() {

	}

	private boolean inAllExpected(BlogpostMongoDoc entity) {
		return entity.equals(forGetIdTest) || entity.equals(newPost);
	}

}
