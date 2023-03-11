package com.gameapi.Controllers;

import com.gameapi.DTOs.CreateGroupRequest;
import com.gameapi.Models.Group;
import com.gameapi.Services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/groups")
public class GroupsController {

    private GroupService _groupService;

    @GetMapping
    public Flux<Group> getUserGroups () {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getCredentials())
                .cast(String.class)
                .flux()
                .flatMap(userId -> this._groupService.getGroupsForUser(userId));
    }

    @PostMapping
    public Mono<Group> createGroup(@Valid @RequestBody CreateGroupRequest groupRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getCredentials())
                .cast(String.class)
                .flatMap(requestUserId -> this._groupService.createGroup(Group.builder().creatorId(requestUserId).name(groupRequest.name).users(groupRequest.getUserIds()).build()));
    }

}
