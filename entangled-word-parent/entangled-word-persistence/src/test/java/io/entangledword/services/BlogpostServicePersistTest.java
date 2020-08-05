package io.entangledword.services;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
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

	@Test
	void whenSaved_thenReturnsTheSavedBlogpost() {
		when(repo.save(any(entityClass))).thenReturn(just(postMongoDOc));
		create(testee.save(postDTO)).expectNextMatches(this::matchPost).expectComplete().verify();
		verify(repo).save(any(entityClass));
	}

	@Test
	void whenStreamAsked_thenReturnsFluxOfAllPosts() {
		when(repo.findAll()).thenReturn(Flux.just(postMongoDOc, postMongoDOc));
		create(testee.getAll()).expectNextMatches(this::matchPost).expectNextMatches(this::matchPost).expectComplete()
				.verify();
		verify(repo).findAll();
	}

	@Test
	void whenDeleteByPostObject_thenReturnesDeletedPost() {
		when(repo.delete(eq(postMongoDOc))).thenReturn(Mono.empty().then());
		create(testee.delete(postDTO)).expectNextMatches(this::matchPost).expectComplete().verify();
		verify(repo).delete(eq(postMongoDOc));
	}

	@Test
	void whenDeleteByID_thenReturnesNothing() {
		when(repo.deleteById(eq(testId1))).thenReturn(Mono.empty().then());
		create(testee.delete(testId1)).expectComplete().verify();
		verify(repo).deleteById(eq(testId1));
	}

	@Test
	void whenGetByID_thenRetursTheBlogpostObject() {
		when(repo.findById(eq(testId1))).thenReturn(just(postMongoDOc));
		create(testee.getByID(testId1)).expectNextMatches(this::matchPost).expectComplete().verify();
		verify(repo).findById(testId1);
		verify(repo).findById(eq(testId1));
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
