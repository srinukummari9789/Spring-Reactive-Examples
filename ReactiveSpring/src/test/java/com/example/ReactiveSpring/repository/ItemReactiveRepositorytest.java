package com.example.ReactiveSpring.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ReactiveSpring.document.Item;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositorytest {

	@Autowired
	ItemReactiveRpository itemReactiveRepository;
	
	List<Item> itemList = Arrays.asList(new Item(null, "samsung TV", 400.0), new Item(null, "LG TV", 420.0),
										new Item(null, "Apple Watch", 299.9),
										new Item(null, "Bose Headphones", 140.25));
	
	@Before
	public void setUp() {
		
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(itemList))
			.flatMap(itemReactiveRepository::save)
			.doOnNext((item -> {
					System.out.println("Inserted item is : " + item);
			}))
			.blockLast();
	}
	
	@Test
	public void getAllItems() {
		
		StepVerifier.create(itemReactiveRepository.findAll())
			.expectSubscription()
			.expectNextCount(4)
			.verifyComplete();
	}
	
	@Test
	public void getItemByID() {
		StepVerifier.create(itemReactiveRepository.findById("ABC"))
		.expectSubscription()
		.expectNextMatches((item -> item.getDescription().equals("Bose Headphones")))
		.verifyComplete();
	}
}
