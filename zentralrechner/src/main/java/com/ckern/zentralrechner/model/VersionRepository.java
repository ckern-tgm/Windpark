package com.ckern.zentralrechner.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VersionRepository extends MongoRepository<Version, String> {
    public Version findByid(String id);
}
