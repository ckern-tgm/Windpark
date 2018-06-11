package com.ckern.zentralrechner.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WindparkRepository extends MongoRepository<Windpark, String> {
    public Windpark findByid(String id);

    public Windpark findByTimeStamp(String timestamp);

}
