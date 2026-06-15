package com.klu.mongolog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentLogRepository extends MongoRepository<EnrollmentLog, String> {
}
