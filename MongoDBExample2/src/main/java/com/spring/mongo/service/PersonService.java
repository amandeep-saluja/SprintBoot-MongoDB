package com.spring.mongo.service;

import com.spring.mongo.collections.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    public String save(Person person);

    public List<Person> getPersonStartWith(String name);

    public void delete(String id);

    public List<Person> getByPersonAge(Integer maxAge, Integer minAge);

    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    public List<Document> getOldestPersonByCity();

    public List<Document> getPopulationByCity();
}
