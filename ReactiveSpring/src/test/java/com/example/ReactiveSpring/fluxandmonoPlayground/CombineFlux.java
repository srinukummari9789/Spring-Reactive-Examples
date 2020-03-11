package com.example.ReactiveSpring.fluxandmonoPlayground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CombineFlux {

	@Test
	public void combineFluxMerge() {
		Flux<String> f1 = Flux.just("A","B","C");
		Flux<String> f2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.merge(f1,f2);
		
		StepVerifier.create(mergeFlux.log())
		.expectNext("A","B","C","D","E","F")
		.verifyComplete();
	}
	
	@Test
	public void combineFluxMerge_withDelay() {
		Flux<String> f1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
		Flux<String> f2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));
		
		Flux<String> mergeFlux = Flux.merge(f1,f2);
		
		StepVerifier.create(mergeFlux.log())
		.expectNextCount(6)
		//.expectNext("A","B","C","D","E","F")
		.verifyComplete();
	}
	
	
	@Test
	public void combineFluxConcat() {
		Flux<String> f1 = Flux.just("A","B","C");
		Flux<String> f2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.concat(f1,f2);
		
		StepVerifier.create(mergeFlux.log())
		.expectNext("A","B","C","D","E","F")
		.verifyComplete();
	}

	
	@Test
	public void combineFluxConcat_withDelay() {
		Flux<String> f1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
		Flux<String> f2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));
		
		Flux<String> mergeFlux = Flux.concat(f1,f2);
		
		StepVerifier.create(mergeFlux.log())
		.expectNext("A","B","C","D","E","F")
		.verifyComplete();
	}
	
	@Test
	public void combineFluxZip() {
		Flux<String> f1 = Flux.just("A","B","C");
		Flux<String> f2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.zip(f1,f2, (t1,t2) -> {
			return t1.concat(t2);
		});
		
		StepVerifier.create(mergeFlux.log())
		.expectNext("AD","BE","CF")
		.verifyComplete();
	}
	
	
	
	
	
	
}
