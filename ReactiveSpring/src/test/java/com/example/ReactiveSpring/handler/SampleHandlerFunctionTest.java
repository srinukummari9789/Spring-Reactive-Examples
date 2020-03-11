package com.example.ReactiveSpring.handler;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
class SampleHandlerFunctionTest {

	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void flux_approch1() {
		
		Flux<Integer> integerFlux = webTestClient.get().uri("/functional/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.returnResult(Integer.class)
		.getResponseBody();
		
		StepVerifier.create(integerFlux)
		.expectSubscription()
		.expectNext(1)
		.expectNext(2)
		.expectNext(3)
		.expectNext(4)
		.verifyComplete();
	}

	
	/*
	 * @Test public void mono_approch1() {
	 * 
	 * Integer expected = new Integer(1);
	 * 
	 * webTestClient.get().uri("/functional/mono")
	 * .accept(MediaType.APPLICATION_JSON_UTF8) .exchange() .expectStatus().isOk()
	 * .returnResult(Integer.class) .consumeWith((response) -> { Mono<> };
	 * 
	 * }
	 */
}
