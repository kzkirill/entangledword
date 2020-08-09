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

import io.entangledword.domain.post.Blogpost;
import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import io.entangledword.port.out.CreatePostAdapter;
import io.entangledword.port.out.DTOMappingService;
import io.entangledword.port.out.DeletePostAdapter;
import io.entangledword.port.out.FindPostsAdapter;
import io.entangledword.port.out.FindPostsPort;
import reactor.core.publisher.Mono;

@SpringBootTest
class OutPortsAdaptersTest {

	@TestConfiguration
	static class OutPortsAdaptersTestConfiguration {
		@Bean
		public FindPostsPort findPostsPort() {
			return new FindPostsAdapter();
		}

		@Bean
		public CreatePostAdapter createPostsPort() {
			return new CreatePostAdapter();
		}

		@Bean
		public DeletePostAdapter deletePostsPort() {
			return new DeletePostAdapter();
		}

		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}

		@Bean
		public DTOMappingService<BlogpostDTO, BlogpostMongoDoc> mapping() {
			return new DTOMappingService<>(BlogpostMongoDoc.class, BlogpostDTO.class);
		}
	}

	@Autowired
	private CreatePostAdapter create;
	@Autowired
	private FindPostsAdapter find;
	@Autowired
	private DeletePostAdapter delete;

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
		create(create.save(postDTO)).expectNextMatches(this::matchPost).expectComplete().verify();
		verify(repo).save(any(entityClass));
	}

	@Test
	void whenDeleteByID_thenReturnesNothing() {
		when(repo.deleteById(eq(testId1))).thenReturn(Mono.empty().then());
		create(delete.delete(testId1)).expectComplete().verify();
		verify(repo).deleteById(eq(testId1));
	}

	@Test
	void whenGetByID_thenRetursTheBlogpostObject() {
		when(repo.findById(eq(testId1))).thenReturn(just(postMongoDOc));
		create(find.getByID(testId1)).expectNextMatches(this::matchPost).expectComplete().verify();
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
