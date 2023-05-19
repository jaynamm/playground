package com.encore.playground.domain.feed.controller;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/feed")
@RestController
//@CrossOrigin(originPatterns = "http://localhost:*")
public class FeedAPIController {
    private final FeedService feedService;

    /**
     * 현재 DB에 저장된 모든 피드를 반환하는 메소드
     * @return JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/list")
    public List<FeedDto> feedMain() {
        return feedService.feedPage();
    }

    /**
     * 피드 글을 수정하기 위해 해당 글을 반환하는 메소드
     * @param feedDto 다음의 프로퍼티를 포함한 JSON 형태의 입력<br>
     * id: 수정할 피드 글 번호
     * @return JSON 형태의 피드 글 1개
     */
    @RequestMapping(value = "/view/{id}")
    public FeedDto getFeed(@PathVariable Long id, @RequestBody FeedDto feedDto) {
        return feedService.getFeed(feedDto);
    }

    /**
     * 피드 글을 작성하는 메소드
     * @param feedDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * memberId: 작성자 아이디<br>
     * content: 작성한 피드 내용<br>
     * @return 작성한 글을 추가한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/write")
    public List<FeedDto> write(@RequestBody FeedDto feedDto) {
        return feedService.write(feedDto);
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 수정하는 메소드
     * @param feedDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * id: 수정할 피드 글 번호<br>
     * content: 수정할 피드 글 내용
     * @return 글 수정사항을 반영한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/modify")
    public List<FeedDto> modify(@RequestBody FeedDto feedDto) {
        return feedService.modify(feedDto);
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 삭제하는 메소드
     * @param feedDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * id: 삭제할 피드 글 번호
     * @return 글 삭제를 반영한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/delete")
    public List<FeedDto> delete(@RequestBody FeedDto feedDto) {
        return feedService.delete(feedDto);
    }
}
