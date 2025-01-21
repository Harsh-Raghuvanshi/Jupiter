package com.exengg.jupiter.Repo;

import com.exengg.jupiter.Entity.Deals;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealsRepo extends MongoRepository<Deals,String> {
}
