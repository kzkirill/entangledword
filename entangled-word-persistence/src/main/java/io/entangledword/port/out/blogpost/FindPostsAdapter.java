package io.entangledword.port.out.blogpost;

import static io.entangledword.persist.entity.QBlogpostMongoDoc.blogpostMongoDoc;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.persist.entity.BlogpostMongoDoc;
import io.entangledword.persist.repos.BlogpostRepository;
import io.entangledword.port.out.DTOMappingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FindPostsAdapter implements FindPostsPort {

	@Autowired
	private BlogpostRepository repo;
	@Autowired
	private DTOMappingService<BlogpostDTO, BlogpostMongoDoc> mapping;

	private static SetPath<String, StringPath> tags = blogpostMongoDoc.tags;
	private static StringPath author = blogpostMongoDoc.author;

	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return repo.findById(id).map(mapping::toDTO);
	}

	@Override
	public Flux<BlogpostDTO> getAll() {
		return repo.findAll().map(mapping::toDTO);
	}

	@Override
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues) {
		return findAll(tagsValues.stream().reduce(new BooleanBuilder(), (builder, value) -> {
			return builder.and(tags.contains(value));
		}, (t, u) -> t.and(u)));
	}

	@Override
	public Flux<BlogpostDTO> getByAuthorsList(Set<String> authorsIDs) {
		return findAll(author.in(authorsIDs));
	}

	protected Flux<BlogpostDTO> findAll(Predicate predicate) {
		return repo.findAll(predicate).map(mapping::toDTO);
	}

}
