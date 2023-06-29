package com.encore.playground.domain.like.controller;

import com.encore.playground.domain.like.dto.LikeGetIdDto;
import com.encore.playground.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public void like(@RequestBody LikeGetIdDto likeGetIdDto) {
        likeService.like(likeGetIdDto);
    }

    @PostMapping("/likeCancel")
    public void likeCancel(@RequestBody LikeGetIdDto likeGetIdDto) {
        likeService.likeCancel(likeGetIdDto);
    }



}
