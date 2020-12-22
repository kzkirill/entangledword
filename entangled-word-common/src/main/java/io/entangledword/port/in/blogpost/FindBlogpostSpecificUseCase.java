package io.entangledword.port.in.blogpost;

import java.util.Set;

import io.entangledword.domain.post.BlogpostPreview;
import reactor.core.publisher.Flux;

public interface FindBlogpostSpecificUseCase {

	public Flux<BlogpostPreview> getAllPreviews();

	public Flux<BlogpostPreview> getByTagsList(Set<String> tagsValues);

	public Flux<BlogpostPreview> getByAuthorsList(Set<String> authorsIDs);

}
