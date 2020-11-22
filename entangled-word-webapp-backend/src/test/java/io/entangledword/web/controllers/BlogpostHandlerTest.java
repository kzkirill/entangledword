package io.entangledword.web.controllers;

import static io.entangledword.domain.post.BlogpostDTO.newInstance;
import static java.lang.String.format;
import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.services.blogpost.BlogpostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestInstance(Lifecycle.PER_CLASS)
class BlogpostHandlerTest extends BlogPostRouterTest {

//	protected RESTHandler createHandler() {
//		return new BlogpostHandler();
//	}
//
	@Override
	protected BlogpostDTO objectForGetTest() {
		return newInstance("For get", "1", testAuthor, testAuthor);
	}

	private class ServiceMock implements BlogpostService {

		@Override
		public Mono<BlogpostDTO> save(BlogpostDTO newPost) {
			return Mono.just(newInstance("For save", "2", testAuthor, testAuthor));
		}

		@Override
		public Mono<BlogpostDTO> delete(BlogpostDTO newPost) {
			return Mono.just(newInstance("For delete", "3", testAuthor, testAuthor));
		}

		@Override
		public Flux<BlogpostDTO> getAll() {
			return Flux.just(newInstance("Get all 1", "11", testAuthor, testAuthor),
					newInstance("Get all 2", "12", testAuthor, testAuthor));
		}

		@Override
		public Mono<BlogpostDTO> getByID(String id) {
			BlogpostDTO result = objectForGetTest();
			return Mono.just(result).filter(m -> m.getID().equals(id));
		}

		@Override
		public Flux<BlogpostDTO> getStream() {
			return Flux.interval(ofMillis(100)).take(50).onBackpressureBuffer(50)
					.map(index -> newInstance(format("ID%d", index), format("Streamed N%d", index), "Streamed text",
							testAuthor));
		}

		@Override
		public Mono<Void> delete(String id) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
