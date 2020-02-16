package com.nefaris.passwordmanager.demo.repositories;

import com.nefaris.passwordmanager.demo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
