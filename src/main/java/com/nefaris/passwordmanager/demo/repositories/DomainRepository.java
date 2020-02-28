package com.nefaris.passwordmanager.demo.repositories;

import com.nefaris.passwordmanager.demo.models.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<Domain, String> {

}
