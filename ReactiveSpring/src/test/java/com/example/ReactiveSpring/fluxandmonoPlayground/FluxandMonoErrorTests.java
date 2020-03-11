package com.example.ReactiveSpring.fluxandmonoPlayground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxandMonoErrorTests {

	@Test
	public void fluxErrorHandling() {
			Flux<String> stringFlux = Flux.just("A", "B", "C")
					.concatWith(Flux.error(new RuntimeException("Excception Occured")))
					.concatWith(Flux.just("D"))
					.onErrorResume((e) -> {
							System.out.println("Exception is : "+ e);
							return Flux.just("Default","Default1");
							})
					.log();
			
			StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			//.expectError(RuntimeException.class)
			//.verify();
			.expectNext("Default", "Default1")
			.verifyComplete();
	}
	
	
	@Test
	public void fluxErrorHandling_OnErrorReturn() {
			Flux<String> stringFlux = Flux.just("A", "B", "C")
					.concatWith(Flux.error(new RuntimeException("Excception Occured")))
					.concatWith(Flux.just("D"))
					.onErrorReturn("default")
					.log();
			
			StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	public void fluxErrorHandling_OnErrorMap() {
			Flux<String> stringFlux = Flux.just("A", "B", "C")
					.concatWith(Flux.error(new RuntimeException("Excception Occured")))
					.concatWith(Flux.just("D"))
					.onErrorMap((e) -> new CustomException(e))
					.log();
			
			StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}
	
	@Test
	public void fluxErrorHandling_OnErrorMap_withRetry() {
			Flux<String> stringFlux = Flux.just("A", "B", "C")
					.concatWith(Flux.error(new RuntimeException("Excception Occured")))
					.concatWith(Flux.just("D"))
					.onErrorMap((e) -> new CustomException(e))
					.retry(2)
					.log();
			
			StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}
	
	@Test
	public void fluxErrorHandling_OnErrorMap_withRetryBackoff() {
			Flux<String> stringFlux = Flux.just("A", "B", "C")
					.concatWith(Flux.error(new RuntimeException("Excception Occured")))
					.concatWith(Flux.just("D"))
					.onErrorMap((e) -> new CustomException(e))
					.retryBackoff(2, Duration.ofSeconds(5))
					.log();
			
			StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectError(IllegalStateException.class)
			.verify();
	}
	
	
}
