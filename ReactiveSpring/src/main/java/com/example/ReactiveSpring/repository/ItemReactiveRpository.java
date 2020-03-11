package com.example.ReactiveSpring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.ReactiveSpring.document.Item;

public interface ItemReactiveRpository extends ReactiveMongoRepository<Item, String> {

}
