package io.entangledword.web.services.impl;

import static io.entangledword.model.post.BlogpostDTO.newInstance;
import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

import io.entangledword.model.post.BlogpostDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BlogpostServiceDummyImpl /* implements BlogpostService */ {

	private final String dummyAuthor = "Pelevin";

	public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
		return Mono.just(newInstance("Title1", "Saved post", "hkjh12hkj12hk", dummyAuthor));
	}

	public Flux<BlogpostDTO> getAll() {
		return Flux.just(newInstance("Title1", "From all", "N1", dummyAuthor),
				newInstance("Title1", "From all", "N2", dummyAuthor));
	}

	public Mono<BlogpostDTO> getByID(String id) {
		return just(newInstance("Title1", "Random text", id, dummyAuthor));
	}

	public Mono<BlogpostDTO> delete(BlogpostDTO newPost) {
		return empty();
	}

	public Flux<BlogpostDTO> getStream() {
		return Flux.interval(ofMillis(500)).take(50).onBackpressureBuffer(50)
				.map(index -> newInstance(format("Title N%d", index), format("Streamed N%d", index),
						format("ID%d", index), dummyAuthor));
	}

}
