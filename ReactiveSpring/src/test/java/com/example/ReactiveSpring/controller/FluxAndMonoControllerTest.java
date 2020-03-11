package com.example.ReactiveSpring.controller;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void flux_approch1() {
		Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.returnResult(Integer.class)
		.getResponseBody();
		
		StepVerifier.create(integerFlux.log())
		.expectSubscription()
		.expectNext(1)
		.expectNext(2)
		.expectNext(3)
		.expectNext(4)
		.verifyComplete();
	}
	
	
	@Test
	public void flux_approch2() {
		webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Integer.class);
	//	.hasSize(4);
		
	}
	
	@Test
	public void flux_approch3() {
		List<Integer> actual = Arrays.asList(1,2,3,4);
		EntityExchangeResult<List<Integer>> expected =  webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Integer.class)
		.returnResult();
		
		assertEquals(actual, expected.getResponseBody());
		
	}
	
}
