package com.encore.playground.domain.feed.controller;

import com.encore.playground.domain.feed.dto.FeedListDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import jakarta.servlet.http.HttpServletRequest;
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
    public String feedMain(Model model, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        model.addAttribute("feeds", feedService.feedPageAll());
        return "feed/feed_main";
    }
    // 게시글 작성
    @PostMapping(value = "/write")
    public String write(String memberId, String content, Model model) {
        List<FeedListDto> feedAfterWrite = feedService.write(memberId, content);
        model.addAttribute("feeds", feedAfterWrite);
        return "feed/feed_main";
    }

    // 게시글 수정 페이지로 이동
    @GetMapping(value = "/modify")
    public String modify(Long feedNo, Model model) {
        FeedListDto feedToModify = feedService.getFeed(feedNo);
        model.addAttribute("feed", feedToModify);
        return "feed/feed_modify";
    }

    // 게시글 수정
    @PostMapping(value = "/modify")
    public String modify(Long id, String content, Model model) {
        List<FeedListDto> feedAfterModify = feedService.modify(id, content);
        model.addAttribute("feeds", feedAfterModify);
        return "feed/feed_main";
    }

    // 게시글 삭제
    @GetMapping(value = "/delete")
    public String delete(Long id, Model model) {
        List<FeedListDto> feedAfterDel = feedService.delete(id);
        model.addAttribute("feeds", feedAfterDel);
        return "feed/feed_main";
    }
}
