package com.example.ReactiveSpring.fluxandmonoPlayground;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer.FromIntegerArguments;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxandMonoTest {
	
	List<String> names =  Arrays.asList("ana","rose","jenney");
	
	@Test
	public void fluxtest() {
		//fail("Not yet implemented");
		
		Flux<String> stringFlux = Flux.just("Spring", "Springboot", "Reactive Spring")
						//.concatWith(Flux.error(new RuntimeException("Exception occured..")))
						.concatWith(Flux.just("After Error"))
						.log();
		stringFlux.subscribe(System.out::println,
				(e) -> System.err.println("Exeption is "+e)
				, () -> System.out.println("Completed"));
	}
	
	
	@Test
	public void fluxTestElements_withoutError() {
		Flux<String> stringFlux = Flux.just("Spring", "Springboot", "Reactive Spring")
				.log();
		
		StepVerifier.create(stringFlux)
		.expectNext("Spring")
		.expectNext("Springboot")
		.expectNext("Reactive Spring")
		.verifyComplete();
	}
	
	@Test
	public void fluxTestElements_withError() {
		Flux<String> stringFlux = Flux.just("Spring", "Springboot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured..")))
				.log();
		
		StepVerifier.create(stringFlux)
		.expectNext("Spring")
		.expectNext("Springboot")
		.expectNext("Reactive Spring")
		//.expectError(RuntimeException.class)
		.expectErrorMessage("Exception Occured")
		.verify();
	}
	
	@Test
	public void fluxTestElementsCount_withError() {
		Flux<String> stringFlux = Flux.just("Spring", "Springboot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured..")))
				.log();
		
		StepVerifier.create(stringFlux)
		.expectNextCount(3)
		.expectErrorMessage("Exception Occured")
		.verify();
	}
	
	
	@Test
	public void transformUsingMap_filter() {
		Flux<String> namesFlux = Flux.fromIterable(names)
				.map(s-> s.toUpperCase())
				.log();
		
		StepVerifier.create(namesFlux)
		.expectNext("ANA")
		.expectNext("ROSE")
		.expectNext("JENNEY")
		.verifyComplete();
	}
	
	@Test
	public void transformUsingMap_filter_length() {
		Flux<String> namesFlux = Flux.fromIterable(names)
				.filter(s->s.length()>4)
				.map(s-> s.toUpperCase())
				.log();
		
		StepVerifier.create(namesFlux)
		.expectNext("JENNEY")
		.verifyComplete();
	}
	
	@Test
	public void transformUsingFlatMap() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G"))
				.flatMap(s -> {
					return Flux.fromIterable(convertToList(s));
				})
				.log();
		StepVerifier.create(stringFlux)
		.expectNextCount(12)
		.verifyComplete();
	}
	private List<String> convertToList(String s){
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(s, "newValue");
	}
	
	/*
	@Test
	public void transformUsingFlatMap_UsingParallel() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G"))
				.window(2)
				.flatMap((s) ->
					s.map(this::convertToList).subscribeOn(parallel()))
					.flatMap(s -> Flux.fromIterable(s))
				.log();
		StepVerifier.create(stringFlux)
		.expectNextCount(12)
		.verifyComplete();
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
