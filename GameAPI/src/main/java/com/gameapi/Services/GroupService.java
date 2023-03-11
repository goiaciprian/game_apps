package com.gameapi.Services;

import com.gameapi.Models.Group;
import com.gameapi.Models.User;
import com.gameapi.Repositories.IGroupRepository;
import com.gameapi.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "cache", name = "enabled", havingValue = "false")
public class GroupService {
    private final IGroupRepository _groupRepository;
    private final UserRepository _userRepository;

    public Mono<Group> createGroup(Group group) {
        return this._groupRepository.findById(group.id)
                .flatMap( __ -> Mono.error(new Exception("Group alredy exists")))
                .switchIfEmpty(Mono.defer(() -> this._groupRepository.insert(group)))
                .cast(Group.class);
    }

    public Mono<Group> patchGroup(String id, String userId, Group group) {
        return this._groupRepository.findByIdAndCreatorId(id, userId)
                .switchIfEmpty(Mono.error(new Exception("Group not found")))
                .flatMap(__ -> {
                    group.id = id;
                    return this._groupRepository.save(group);
                });
    }

    public Mono<Group> deleteGroup(String id, String userId) {
        return this._groupRepository.findByIdAndCreatorId(id, userId)
                .switchIfEmpty(Mono.error(new Exception("Group not found")))
                .map( group -> {
                    this._groupRepository.delete(group);
                    return group;
                })
                .cast(Group.class);
    }

    public Flux<User> getUsersFromGroup(String id, String userId) {
        return this._groupRepository.findByIdAndCreatorId(id, userId)
                .switchIfEmpty(Mono.error(new Exception("Group not found")))
                .flatMapIterable(group -> group.users)
                .flatMap(this._userRepository::findById);
    }

    public Flux<Group> getGroupsForUser(String userid) {
        return this._groupRepository.findAll()
                .filter(group -> group.users.contains(userid));
    }


}
