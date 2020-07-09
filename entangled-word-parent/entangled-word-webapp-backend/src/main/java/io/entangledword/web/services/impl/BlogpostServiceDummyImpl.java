package io.entangledword.web.services.impl;

import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

import io.entangledword.model.post.BlogpostDTO;
import io.entangledword.services.BlogpostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BlogpostServiceDummyImpl /* implements BlogpostService */ {

	private final String dummyAuthor = "Pelevin";

	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		return Mono.just(new BlogpostDTO("Title1", "Saved post", "hkjh12hkj12hk", dummyAuthor));
	}

	public Flux<BlogpostDTO> getAll() {
		return Flux.just(new BlogpostDTO("Title1", "From all", "N1", dummyAuthor), new BlogpostDTO("Title1", "From all", "N2", dummyAuthor));
	}

	public Mono<BlogpostDTO> getByID(String id) {
		return just(new BlogpostDTO("Title1", "Random text", id, dummyAuthor));
	}

	public Mono<BlogpostDTO> delete(BlogpostDTO newPost) {
		return empty();
	}

	public Flux<BlogpostDTO> getStream() {
		return Flux.interval(ofMillis(500)).take(50).onBackpressureBuffer(50)
				.map(index -> new BlogpostDTO(format("Title N%d", index),format("Streamed N%d", index), format("ID%d", index), dummyAuthor));

	}

}
