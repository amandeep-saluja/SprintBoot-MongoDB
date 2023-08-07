package com.spring.mongo.repository;

import com.spring.mongo.entity.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {
}
