package com.example.ReactiveSpring.fluxandmonoPlayground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdandHotPublisherTest {

	@Test
	public void coldPublisherTest() throws InterruptedException {
		Flux<String> stringFlux = Flux.just("A","B","C","D,","E","F")
				.delayElements(Duration.ofSeconds(1));
		
		stringFlux.subscribe(s -> System.out.println("Subscribe 1 : " +s));
		
		Thread.sleep(2000);
		
		stringFlux.subscribe(s -> System.out.println("Subscribe 2 : " +s));
		
		Thread.sleep(4000);
	}
	
	
	@Test
	public void HotPublisherTest() throws InterruptedException {
		Flux<String> stringFlux = Flux.just("A","B","C","D,","E","F")
				.delayElements(Duration.ofSeconds(1));
		
		ConnectableFlux<String> cFlux = stringFlux.publish();
		cFlux.connect();
		cFlux.subscribe(s -> System.out.println("Subscribe 1 : " +s));
		
		Thread.sleep(3000);
		
		cFlux.subscribe(s -> System.out.println("Subscribe 2 : " +s));
		
		Thread.sleep(4000);
	}
	
	
	
	
	
	
}
