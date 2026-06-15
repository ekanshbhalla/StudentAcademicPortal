package com.klu.mongolog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginLogRepository extends MongoRepository<LoginLog, String> {
}
