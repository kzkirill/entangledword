package io.entangledword.web.controllers;

import java.util.function.Function;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.entangledword.domain.user.User;
import io.entangledword.port.in.DeleteByIDUseCase;
import io.entangledword.port.in.blogpost.FindUseCase;
import io.entangledword.port.in.user.CreateUserUseCase;
import reactor.core.publisher.Mono;

@Component
public class UserHandler extends ReactiveRestHandlerAdapter<User>{

	public static final String URI_BASE = "/user";
	@Autowired
	private CreateUserUseCase createUC;

	public UserHandler(DeleteByIDUseCase deleteUC, FindUseCase<User> findUserService) {
		super(User.class, URI_BASE, deleteUC, findUserService);
	}

	@Override
	public Mono<ServerResponse> post(ServerRequest requestWithBlogpost) {
		Function<User, String> getID = (User dto) -> dto.getID();
		return super.post(requestWithBlogpost, this.createUC::save, getID);
	}

	@Override
	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		return super.put(serverRequest, this.createUC::save);
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

	@Override
	public Mono<ServerResponse> getPostsByQueryParams(ServerRequest serverRequest) {
		throw new NotImplementedException();
	}

}
