package com.gameapi.CacheServices;

import com.gameapi.Models.User;
import com.gameapi.Repositories.UserRepository;
import com.gameapi.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@ConditionalOnProperty(value = "cache.enabled", havingValue = "true")
public class UserCacheService extends UserService {

    private final String KEY = "USER_KEY";

    private final ReactiveHashOperations<String, String, User> _hashOps;

    public UserCacheService(UserRepository userRepository, ReactiveHashOperations<String, String, User> hashOps ) {
        super(userRepository);
        this._hashOps = hashOps;
        super.findAllUsers().subscribe(usr -> {
            log.info("cached user {}", usr.getId());
           _hashOps.put(KEY, usr.getId(), usr).subscribe();
        });
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return _hashOps.get(KEY, email)
                .switchIfEmpty(this.getFromDbByEmailAndCache(email));
    }

    @Override
    public Mono<User> getUserById(String userId) {
        return _hashOps.get(KEY, userId)
                .switchIfEmpty(this.getFromDbByIdAndCache(userId));
    }

    @Override
    public Flux<User> findAllUsers() {
        return _hashOps.values(KEY)
                .switchIfEmpty(this.getAllFromDbAndCache());
    }

    @Override
    public Mono<User> saveIfNotExists(User user) {
        return super.saveIfNotExists(user)
                .flatMap(usr -> _hashOps
                        .put(KEY, usr.getId(), usr)
                        .thenReturn(usr));
    }

    private Mono<User> getFromDbByEmailAndCache(String email) {
        return super.findByEmail(email)
                .flatMap(usr ->
                        _hashOps.put(KEY, usr.getId(), usr)
                                .thenReturn(usr));
    }

    private Mono<User> getFromDbByIdAndCache(String id) {
        return super.getUserById(id)
                .flatMap(usr ->
                        _hashOps.put(KEY, usr.getId(), usr)
                                .thenReturn(usr));
    }

    private Flux<User> getAllFromDbAndCache() {
        return super.findAllUsers()
                .flatMap(usr ->
                        _hashOps.put(KEY, usr.getId(), usr)
                                .thenReturn(usr));
    }


}
