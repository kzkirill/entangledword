package io.entangledword.services;

import io.entangledword.model.post.BlogpostDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogpostService {
	public Mono<BlogpostDTO> save(BlogpostDTO newPost);
	public Mono<BlogpostDTO> delete(BlogpostDTO newPost);
	public Flux<BlogpostDTO> getAll();
	public Mono<BlogpostDTO> getByID(String id);
	public Flux<BlogpostDTO> getStream();
	public Mono<Void> delete(String id);
}
