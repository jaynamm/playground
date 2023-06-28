package com.encore.playground.domain.follow.controller;

import com.encore.playground.domain.follow.dto.FollowGetMemberIdDto;
import com.encore.playground.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public void follow(@RequestBody FollowGetMemberIdDto followGetMemberIdDto) {
        followService.follow(followGetMemberIdDto);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody FollowGetMemberIdDto followGetMemberIdDto) {
        System.out.println(followGetMemberIdDto);
        followService.unfollow(followGetMemberIdDto);
    }




}
