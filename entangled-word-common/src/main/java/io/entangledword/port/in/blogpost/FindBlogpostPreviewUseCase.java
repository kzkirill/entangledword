package io.entangledword.port.in.blogpost;

import io.entangledword.domain.post.BlogpostPreview;
import reactor.core.publisher.Flux;

public interface FindBlogpostPreviewUseCase {

	public Flux<BlogpostPreview> getAll();
	
}
