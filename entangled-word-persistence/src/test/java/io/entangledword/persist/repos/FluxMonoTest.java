package io.entangledword.persist.repos;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoTest {

	@Test
	public void whenFluxMonoCombined_workTogether() {
		Set<String> inMemory = new HashSet<>();
		Mono<String> mono1 = just("Mono 1").map(value -> {
			inMemory.add(value);
			return value;
		});
		Flux<String> fromIterable = fromIterable(asList(1, 2)).map(i -> {
			String value = Integer.toString(i);
			inMemory.add(value);
			return value;
		});
		Mono<String> last = fromIterable.then(mono1);
		last.subscribe(System.out::println);
		assertTrue(inMemory.contains("Mono 1"));
		assertTrue(inMemory.contains("1"));
		assertTrue(inMemory.contains("2"));
		assertFalse(inMemory.contains("kkk"));
	}

}
