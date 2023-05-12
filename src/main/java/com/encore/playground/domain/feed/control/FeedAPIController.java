package com.encore.playground.domain.feed.control;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/feed")
@RequiredArgsConstructor
@RestController
public class FeedAPIController {
    private final FeedService feedService;

    @GetMapping("/getallfeeds")
    public Map<String, List<FeedDto>> feedMain() {
        Map<String, List<FeedDto>> feeds = new HashMap<>();
        feeds.put("feeds", feedService.feedPage());
        System.out.println(feeds);
        return feeds;
    }
}
