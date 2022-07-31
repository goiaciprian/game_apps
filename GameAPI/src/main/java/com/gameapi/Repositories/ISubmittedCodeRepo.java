package com.gameapi.Repositories;

import com.gameapi.Models.SubmittedCode;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubmittedCodeRepo extends ReactiveMongoRepository<SubmittedCode, String> {
}
