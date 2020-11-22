package io.entangledword.services;

import static java.time.LocalDateTime.now;
import static java.util.Set.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import io.entangledword.domain.post.Blogpost;
import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.tag.Tag;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.entity.TagMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import io.entangledword.persist.repos.TagRepository;
import io.entangledword.port.out.DTOMappingService;
import io.entangledword.port.out.blogpost.CreatePostAdapter;
import io.entangledword.port.out.blogpost.CreateTagPort;
import io.entangledword.port.out.blogpost.DeletePostAdapter;
import io.entangledword.port.out.blogpost.FindPort;
import io.entangledword.port.out.blogpost.FindPostsAdapter;
import io.entangledword.port.out.tag.TagPortAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class OutPortsAdaptersTest {

	@TestConfiguration
	static class OutPortsAdaptersTestConfiguration {
		@MockBean
		private TagRepository tagRepo;

		@Bean
		public CreateTagPort findTagPort() {
			return new TagPortAdapter(tagRepo, new DTOMappingService<>(Tag.class, TagMongoDoc.class));
		}

		@Bean
		public FindPort findPostsPort() {
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
			return new DTOMappingService<>(BlogpostDTO.class, BlogpostMongoDoc.class);
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
	void whenGetByTags_thenReturnsAllWithAtLeastTheseTags() {
		Set<String> tagsValues = of("tag1", "tag2");
		Set<String> tagsNoPosts = of("tagWithNoPosts");
		BlogpostMongoDoc withTags1 = (BlogpostMongoDoc) initialize(
				BlogpostMongoDoc.newInstance("tagsID1", "Tags title 1", "Tags title 1", "sdsdsa"));
		withTags1.setTags(tagsValues);
		BlogpostMongoDoc withTags2 = (BlogpostMongoDoc) initialize(
				BlogpostMongoDoc.newInstance("tagsID2", "Tags title 2", "Tags title 2", "sdsdsa"));
		withTags2.setTags(of("tag1", "tag2","tag3"));
		when(repo.findByTagsContaining(tagsValues)).thenReturn(Flux.just(withTags1, withTags2));
		when(repo.findByTagsContaining(tagsNoPosts)).thenReturn(Flux.empty());
		
		create(find.getByTagsList(tagsValues)).expectNextCount(2l).expectComplete().verify();
		create(find.getByTagsList(tagsNoPosts)).expectNextCount(0l).expectComplete().verify();
	}

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
