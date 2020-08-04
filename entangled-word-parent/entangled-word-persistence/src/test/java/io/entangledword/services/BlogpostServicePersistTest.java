package io.entangledword.services;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import io.entangledword.model.post.Blogpost;
import io.entangledword.model.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class BlogpostServicePersistTest {

	@TestConfiguration
	static class BlogpostServicePersistTestConfiguration {
		@Bean
		public BlogpostService service() {
			return new BlogpostServicePersist();
		}

		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}
	}

	@Autowired
	private BlogpostServicePersist testee;

	@MockBean
	private BlogpostRepository repo;
	private Class<BlogpostMongoDoc> entityClass = BlogpostMongoDoc.class;
	private String testId1 = "save 01 ID";
	private BlogpostMongoDoc postMongoDOc = (BlogpostMongoDoc) initialize(
			BlogpostMongoDoc.newInstance(testId1, "save 01 title", "save 01 text", "save 01 author"));;
	private BlogpostDTO postDTO = (BlogpostDTO) initialize(
			BlogpostDTO.newInstance(testId1, "save 01 title", "save 01 text", "save 01 author"));

	@BeforeAll
	void setup() {
		when(repo.save(any(entityClass))).thenReturn(just(postMongoDOc));
//		when(repo.deleteById(anyString())).thenReturn(Mono.empty().then());
//		when(repo.findById(eq(testId1))).thenReturn(just(postMongoDOc));
//		when(repo.findAll()).thenReturn(Flux.just(postMongoDOc, postMongoDOc));
	}

	@Test
	void testSave() {
		create(testee.save(postDTO)).expectNextMatches(this::matchPost).expectComplete().verify();
	}

	@Test
	void testGetStream() {
//		create(testee.getAll()).expectNextMatches(this::matchPost).expectNextMatches(this::matchPost).expectComplete()
//				.verify();
	}

	@Test
	void testDelete() {
//		create(testee.delete(testId1)).expectComplete().verify();
	}

	@Test
	void testGetByID() {
//		create(testee.getByID(testId1)).expectNextMatches(this::matchPost).expectComplete().verify();
	}

	private boolean matchPost(BlogpostDTO saved) {
		return saved.getID().equals(postDTO.getID());
	}

	private static Blogpost initialize(Blogpost noOptional) {
		noOptional.setCreated(now());
		noOptional.setUpdated(now());
		noOptional.setReplies(new ArrayList<>());
		noOptional.setTags(new HashSet<String>());
		return noOptional;
	}

}
