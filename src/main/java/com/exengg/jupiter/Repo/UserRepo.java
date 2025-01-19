package com.exengg.jupiter.Repo;

import com.exengg.jupiter.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User,String> {
    User findByUsername(String userName);
    User findByEmailId(String emailId);
}
