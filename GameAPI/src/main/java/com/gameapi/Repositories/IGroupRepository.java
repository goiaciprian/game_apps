package com.gameapi.Repositories;

import com.gameapi.Models.Group;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IGroupRepository extends ReactiveMongoRepository<Group, String> {
     Mono<Group> findByIdAndCreatorId(String id, String creatorId);
}
