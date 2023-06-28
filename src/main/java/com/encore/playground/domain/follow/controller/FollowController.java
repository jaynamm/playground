package com.encore.playground.domain.follow.controller;

import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public void follow(@RequestBody FollowGetIdDto followGetIdDto) {
        followService.follow(followGetIdDto);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody FollowGetIdDto followGetIdDto) {
        System.out.println(followGetIdDto);
        followService.unfollow(followGetIdDto);
    }




}
