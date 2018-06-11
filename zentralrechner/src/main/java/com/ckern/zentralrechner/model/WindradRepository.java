package com.ckern.zentralrechner.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WindradRepository extends MongoRepository<Windrad, String> {
    public Windrad getByid(String id);

    public Windrad findByTimeStamp(String timestamp);
}
