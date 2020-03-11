package com.example.ReactiveSpring.fluxandmonoPlayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxandMonoBackPressureTest {

	@Test
	public void backPressureTest() {
		Flux<Integer> finiteFlux = Flux.range(1, 10)
				.log();
		
		StepVerifier.create(finiteFlux)
		.expectSubscription()
		.thenRequest(1)
		.expectNext(1)
		.thenRequest(1)
		.expectNext(2)
		.thenCancel()
		.verify();
	}
	
	@Test
	public void backPressure() {
		
		Flux<Integer> finiteFlux = Flux.range(1, 10)
				.log();
		finiteFlux.subscribe((element) -> System.out.println("element is: " + element)
				,(e) -> System.out.println("Exception is "+e)
				,() -> System.out.println("Done")
				,(subscription -> subscription.request(12)));
	}
	
	@Test
	public void backPressure_cancle() {
		
		Flux<Integer> finiteFlux = Flux.range(1, 10)
				.log();
		finiteFlux.subscribe((element) -> System.out.println("element is: " + element)
				,(e) -> System.out.println("Exception is "+e)
				,() -> System.out.println("Done")
				,(subscription -> subscription.cancel()));
	}
	
	@Test
	public void Customized_backPressure() {
		Flux<Integer> finiteFlux = Flux.range(1, 10)
				.log();
			finiteFlux.subscribe(new BaseSubscriber<Integer>() {
				protected void hookOnNext(Integer value) {
					request(1);
					System.out.println("value is " + value);
					if(value == 4) {
						cancel();
					}
				}
			});
	}
	
	
}
