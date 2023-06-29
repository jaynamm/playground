package com.encore.playground.domain.likes.controller;

import com.encore.playground.domain.likes.dto.LikesGetIdDto;
import com.encore.playground.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/likes")
    public void likes(@RequestBody LikesGetIdDto likesGetIdDto) {
        likesService.likes(likesGetIdDto);
    }

    @PostMapping("/likesCancel")
    public void likesCancel(@RequestBody LikesGetIdDto likesGetIdDto) {
        likesService.likesCancel(likesGetIdDto);
    }



}
