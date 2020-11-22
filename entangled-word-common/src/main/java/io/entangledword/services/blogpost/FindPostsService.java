package io.entangledword.services.blogpost;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.post.BlogpostPreview;
import io.entangledword.domain.user.UserDTO;
import io.entangledword.port.in.FindUseCase;
import io.entangledword.port.in.blogpost.FindBlogpostPreviewUseCase;
import io.entangledword.port.out.blogpost.FindPostsPort;
import io.entangledword.port.out.user.FindUserPort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FindPostsService implements FindUseCase<BlogpostDTO>, FindBlogpostPreviewUseCase {

	private final FindPostsPort findPosts;
	private final FindUserPort findUsers;

	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return findPosts.getByID(id);
	}

	@Override
	public Flux<BlogpostDTO> getStream() {
		return findPosts.getStream();
	}

	@Override
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues) {
		return findPosts.getByTagsList(tagsValues);
	}

	@Override
	public Flux<BlogpostPreview> getAll() {
		return this.findPosts.getStream()
				.flatMap((BlogpostDTO blogpost) -> this.findUsers.getByID(blogpost.getAuthor()).map((UserDTO user) -> {
					return new BlogpostPreview(blogpost, user.getFullName(), user.getPictureURL());
				}));
	}

}
