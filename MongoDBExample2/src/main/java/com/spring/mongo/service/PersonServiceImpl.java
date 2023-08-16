package com.spring.mongo.service;

import com.spring.mongo.collections.Person;
import com.spring.mongo.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer maxAge, Integer minAge) {
        return personRepository.findPersonByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (null != name && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name, "i"));
        }
        if (null != minAge && null != maxAge) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (null != city && !city.isEmpty()) {
            criteria.add(Criteria.where("addresses.city").is(city));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Person.class));

        return people;
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        //flatting the cities along with age -> Unwind operation
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        //sort by age in descending order
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        //group by city
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        List<Document> persons = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();

        return persons;
    }

    @Override
    public List<Document> getPopulationByCity() {
        //unwind operation
        //grouping
        //sort
        /*
           select a.city, count(1) as Population
           from Person p inner join address a
           on p.id = a.id
           group by a.city
           order by count(1) desc;
        */

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("popCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "popCount");

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }
}
