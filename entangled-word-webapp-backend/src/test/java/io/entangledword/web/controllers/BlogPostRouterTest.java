package io.entangledword.web.controllers;

import static io.entangledword.domain.post.BlogpostDTO.newInstance;
import static io.entangledword.web.controllers.BlogpostHandler.URI_BASE;
import static io.entangledword.web.controllers.BlogpostHandler.URI_ID;
import static io.entangledword.web.controllers.BlogpostHandler.URI_SEARCH;
import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static reactor.core.publisher.Mono.just;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;

import io.entangledword.domain.post.BlogpostDTO;
import io.entangledword.web.routers.BlogpostRouter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient(timeout = "36000")
class BlogPostRouterTest {

	private WebTestClient testClient;
	private BlogpostDTO getRegustTestee;
	protected final String testAuthor = "Conan Doyle";
	private CRUDControllerMock controllerMock;

	@BeforeAll
	public void setUp() {
		controllerMock = new CRUDControllerMock();
		RouterFunction<ServerResponse> routerFunction = new BlogpostRouter().blogpostRouterFunction(controllerMock);
		assertThat(routerFunction).isNotNull();
		getRegustTestee = objectForGetTest();
		testClient = WebTestClient.bindToRouterFunction(routerFunction).configureClient().baseUrl(URI_BASE).build();
	}

	@Test
	void postTest() {
		testClient.post().bodyValue(newInstance("Today's post", "1112sasdewq", testAuthor, testAuthor)).exchange()
				.expectStatus().isCreated();
	}

	@Test
	void putTest() {
		testClient.put().uri("/" + getRegustTestee.getID())
				.bodyValue(newInstance("Put post", "put1112sasdewq", testAuthor, testAuthor)).exchange().expectStatus()
				.isOk();
	}

	@Test
	void getTest() {
		ResponseSpec response = testClient.get().uri("/" + getRegustTestee.getID()).exchange();
		response.expectStatus().isOk();
		response.expectBody(BlogpostDTO.class).isEqualTo(getRegustTestee);

		response = testClient.get().uri("/" + getRegustTestee.getID() + "dsfsdfsdfdf").exchange();
		response.expectStatus().isNotFound();
	}

	@Test
	void deleteTest() {
		testClient.delete().uri("/" + getRegustTestee.getID()).exchange().expectStatus().isNoContent();
	}

	@Test
	void getAllStreamTest() {
		FluxExchangeResult<BlogpostDTO> result = this.testClient.get().accept(TEXT_EVENT_STREAM).exchange()
				.expectStatus().isOk().expectHeader().contentTypeCompatibleWith(TEXT_EVENT_STREAM)
				.returnResult(BlogpostDTO.class);

		StepVerifier.create(result.getResponseBody())
				.expectNext(controllerMock.fromIndexForStream(0l), controllerMock.fromIndexForStream(1l),
						controllerMock.fromIndexForStream(2l))
				.expectNextCount(4).consumeNextWith(blogpost -> assertThat(blogpost.getText()).endsWith("7"))
				.thenCancel().verify();

	}

	@Test
	void whenGetWithQueryParamTags_thenCallsCorrectHandler() {
		FluxExchangeResult<BlogpostDTO> result = this.testClient.get()
				.uri(uriBuillder -> uriBuillder.path(URI_SEARCH).queryParam("tags", "tag1,tag2").build())
				.accept(TEXT_EVENT_STREAM).exchange().expectStatus().isOk().expectHeader()
				.contentTypeCompatibleWith(TEXT_EVENT_STREAM).returnResult(BlogpostDTO.class);

		StepVerifier.create(result.getResponseBody()).expectNextCount(4)
				.consumeNextWith(blogpost -> assertThat(blogpost.getText()).endsWith("4")).thenCancel().verify();

	}

	protected BlogpostDTO objectForGetTest() {
		return newInstance("Get request testee", "getrequesttestee111", testAuthor, testAuthor);
	}

	protected Flux<BlogpostDTO> produceGetAllFlux() {
		return Flux.just(getFirstForAll());
	}

	private BlogpostDTO getFirstForAll() {
		return newInstance("First for getAll", "getAll001", testAuthor, testAuthor);
	}

	protected class CRUDControllerMock implements RESTHandler {

		CRUDControllerMock() {
			super();
		}

		@Override
		public Mono<ServerResponse> post(ServerRequest serverRequest) {
			final Mono<BlogpostDTO> blogpost = serverRequest.bodyToMono(BlogpostDTO.class);
			return blogpost.flatMap(bp -> created(fromPath(URI_BASE + "/" + bp.getID()).build().toUri())
					.contentType(APPLICATION_JSON).body(blogpost, BlogpostDTO.class));
		}

		private BodyBuilder okWithJason() {
			return ok().contentType(MediaType.APPLICATION_JSON);
		}

		@Override
		public Mono<ServerResponse> get(ServerRequest serverRequest) {
			final String requestID = serverRequest.pathVariable(URI_ID);
			return just(getRegustTestee).filter(testee -> testee.getID().equals(requestID))
					.flatMap(found -> okWithJason().body(fromPublisher(just(found), BlogpostDTO.class)))
					.switchIfEmpty(notFound().build());
		}

		@Override
		public Mono<ServerResponse> put(ServerRequest serverRequest) {
			return okWithJason().body(fromPublisher(serverRequest.bodyToMono(BlogpostDTO.class), BlogpostDTO.class));
		}

		@Override
		public Mono<ServerResponse> delete(ServerRequest serverRequest) {
			return noContent().build();
		}

		@Override
		public Mono<ServerResponse> getStream(ServerRequest serverRequest) {
			return ok().contentType(TEXT_EVENT_STREAM).body(
					Flux.interval(ofMillis(100)).take(50).onBackpressureBuffer(50).map(this::fromIndexForStream),
					BlogpostDTO.class);
		}

		private BlogpostDTO fromIndexForStream(Long index) {
			BlogpostDTO result = newInstance(format("ID%d", index), format("Streamed N%d", index),
					format("Streamed text N%d", index), testAuthor);
			return result;
		}

		@Override
		public Mono<ServerResponse> getPostsByQueryParams(ServerRequest serverRequest) {
			return ok().contentType(TEXT_EVENT_STREAM).body(
					Flux.interval(ofMillis(10)).take(5).onBackpressureBuffer(5).map(this::fromIndexForStream),
					BlogpostDTO.class);
		}

	}

}
