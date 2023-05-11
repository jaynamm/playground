package com.encore.playground.domain.feed.controller;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 피드 글을 반환하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    // 피드 메인페이지
    @GetMapping(value = {"", "/"})
    public String feedMain(Model model) {
        model.addAttribute("feeds", feedService.feedPage());
        return "feed/feed_main";
    }
    // 게시글 작성
    @PostMapping(value = "/write")
    public String write(String id, String article, Model model) {
        List<FeedDto> feedAfterWrite = feedService.write(id, article);
        model.addAttribute("feeds", feedAfterWrite);
        return "feed/feed_main";
    }

    // 게시글 수정 페이지로 이동
    @GetMapping(value = "/modify")
    public String modify(long feedNo, Model model) {
        FeedDto feedToModify = feedService.getFeed(feedNo);
        model.addAttribute("feed", feedToModify);
        return "feed/feed_modify";
    }

    // 게시글 수정
    @PostMapping(value = "/modify")
    public String modify(long feedNo, String article, Model model) {
        List<FeedDto> feedAfterModify = feedService.modify(feedNo, article);
        model.addAttribute("feeds", feedAfterModify);
        return "feed/feed_main";
    }

    // 게시글 삭제
    @GetMapping(value = "/delete")
    public String delete(long feedNo, Model model) {
        List<FeedDto> feedAfterDel = feedService.delete(feedNo);
        model.addAttribute("feeds", feedAfterDel);
        return "feed/feed_main";
    }
}
