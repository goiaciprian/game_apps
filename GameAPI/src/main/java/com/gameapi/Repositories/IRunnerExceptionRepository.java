package com.gameapi.Repositories;

import com.gameapi.Models.RunnerException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRunnerExceptionRepository extends ReactiveMongoRepository<RunnerException, String> {
}
