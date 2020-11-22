package io.entangledword.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.web.controllers.BlogpostHandler;

@SpringBootTest
class BloggingWordWebApplicationSmokeTest {
	
	@Autowired
	BlogpostHandler controller;
	@Autowired
	RouterFunction<ServerResponse> blogpostRouterFunction;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(blogpostRouterFunction).isNotNull();
	}

}
