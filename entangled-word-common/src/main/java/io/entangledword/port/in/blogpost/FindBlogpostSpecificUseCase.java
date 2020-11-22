package io.entangledword.port.in.blogpost;

import java.util.Set;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.post.BlogpostPreview;
import reactor.core.publisher.Flux;

public interface FindBlogpostSpecificUseCase {

	public Flux<BlogpostPreview> getAllPreviews();

	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues);

}
