package io.entangledword.services.blogpost;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.domain.post.BlogpostPreview;
import io.entangledword.domain.user.UserDTO;
import io.entangledword.port.in.FindUseCase;
import io.entangledword.port.in.blogpost.FindBlogpostSpecificUseCase;
import io.entangledword.port.out.blogpost.FindPort;
import io.entangledword.port.out.blogpost.FindPostsPort;
import io.entangledword.port.out.user.FindUserPort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FindPostsService implements FindUseCase<BlogpostDTO>, FindBlogpostSpecificUseCase {

	private final FindPort<BlogpostDTO> findPort;
	private final FindPostsPort findPostsPort;
	private final FindUserPort findUsers;
	private static UserDTO userNotFound = UserDTO.newInstance("Usr", "Not Found", "", "", "", "");

	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return findPort.getByID(id);
	}

	@Override
	public Flux<BlogpostDTO> getStream() {
		return findPort.getAll();
	}

	@Override
	public Flux<BlogpostDTO> getByTagsList(Set<String> tagsValues) {
		return findPostsPort.getByTagsList(tagsValues);
	}

	@Override
	public Flux<BlogpostPreview> getAllPreviews() {
		return this.findPort.getAll().flatMap((BlogpostDTO blogpost) -> this.findUsers.getByID(blogpost.getAuthor())
				.defaultIfEmpty(userNotFound).map((UserDTO user) -> {
					return new BlogpostPreview(blogpost, user.getFullName(), user.getPictureURL());
				}));
	}

}
