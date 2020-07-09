package io.entangledword.services;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.entangledword.model.post.BlogpostDTO;
import io.entangledword.persist.entity.Blogpost;
import io.entangledword.persist.repos.BlogpostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BlogpostServicePersist implements BlogpostService {
	private static final int DELAY_PER_ITEM_MS = 100;

	private final BlogpostRepository repo;

	public BlogpostServicePersist(BlogpostRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		return repo.save(toEntity(newPost)).map(this::toDTO);
	}

	@Override
	public Mono<BlogpostDTO> delete(BlogpostDTO dtoToDelete) {
		return repo.delete(toEntity(dtoToDelete)).map(voidValue -> dtoToDelete);
	}

	@Override
	public Flux<BlogpostDTO> getAll() {
		return repo.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS)).map(this::toDTO);
	}

	@Override
	public Mono<BlogpostDTO> getByID(String id) {
		return repo.findById(id).map(this::toDTO);
	}

	@Override
	public Flux<BlogpostDTO> getStream() {
		return repo.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS)).map(this::toDTO);
	}

	protected Blogpost toEntity(BlogpostDTO dto) {
		return Blogpost.newInstance(dto.getID(), dto.getTitle(), dto.getText(), dto.getAuthor());
	}

	protected BlogpostDTO toDTO(Blogpost entity) {
		return BlogpostDTO.newInstance(entity.getId(), entity.getTitle(), entity.getText(), entity.getAuthor());
	}

}
