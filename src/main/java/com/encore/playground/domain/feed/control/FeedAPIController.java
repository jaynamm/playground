package com.encore.playground.domain.feed.control;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/feed")
@RestController
public class FeedAPIController {
    private final FeedService feedService;

    /**
     * 현재 DB에 저장된 모든 피드를 반환하는 메소드
     * @param requestMap 현재 입력값 없음
     * @return JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/getallfeeds")
    public Map<String, List<FeedDto>> feedMain(@RequestBody Map<String, String> requestMap) {
        Map<String, List<FeedDto>> feeds = new HashMap<>();
        feeds.put("feeds", feedService.feedPage());
        return feeds;
    }

    /**
     * 피드 글을 수정하기 위해 해당 글을 반환하는 메소드
     * @param requestMap 다음의 프로퍼티를 포함한 JSON 형태의 입력<br>
     * feedno: 수정할 피드 글 번호
     * @return JSON 형태의 피드 글 1개
     */
    @RequestMapping(value = "/getfeed")
    public Map<String, FeedDto> getFeed(@RequestBody Map<String, String> requestMap) {
        int feedNo = Integer.parseInt(requestMap.get("feedno"));
        FeedDto feedToModify = feedService.getFeed(feedNo);
        Map<String, FeedDto> feedToModifyMap = new HashMap<>();
        feedToModifyMap.put("feed", feedToModify);
        return feedToModifyMap;
    }

    /**
     * 피드 글을 작성하는 메소드
     * @param requestMap 다음의 프로퍼티를 포함한 JSON 입력<br>
     * id: 작성자 아이디<br>
     * article: 작성한 피드 내용<br>
     * @return 작성한 글을 추가한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/write")
    public Map<String, List<FeedDto>> write(@RequestBody Map<String, String> requestMap) {
        String id = requestMap.get("id");
        String article = requestMap.get("article");
        List<FeedDto> feedsAfterWrite = feedService.write(id, article);
        Map<String, List<FeedDto>> feedsAfterWriteMap = new HashMap<>();
        feedsAfterWriteMap.put("feeds", feedsAfterWrite);
        return feedsAfterWriteMap;
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 수정하는 메소드
     * @param requestMap 다음의 프로퍼티를 포함한 JSON 입력<br>
     * feedno: 수정할 피드 글 번호<br>
     * article: 수정할 피드 글 내용
     * @return 글 수정사항을 반영한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/modify")
    public Map<String, List<FeedDto>> modify(@RequestBody Map<String, String> requestMap) {
        int feedno = Integer.parseInt(requestMap.get("feedno"));
        String article = requestMap.get("article");
        List<FeedDto> feedAfterModify = feedService.modify(feedno, article);
        Map<String, List<FeedDto>> feedAfterModifyMap = new HashMap<>();
        feedAfterModifyMap.put("feeds", feedAfterModify);
        return feedAfterModifyMap;
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 삭제하는 메소드
     * @param requestMap 다음의 프로퍼티를 포함한 JSON 입력<br>
     * feedno: 삭제할 피드 글 번호
     * @return 글 삭제를 반영한 JSON 형태의 피드 리스트
     */
    @RequestMapping(value = "/delete")
    public Map<String, List<FeedDto>> delete(@RequestBody Map<String, String> requestMap) {
        int feedno = Integer.parseInt(requestMap.get("feedno"));
        List<FeedDto> feedAfterDel = feedService.delete(feedno);
        Map<String, List<FeedDto>> feedAfterDeleteMap = new HashMap<>();
        feedAfterDeleteMap.put("feeds", feedAfterDel);
        return feedAfterDeleteMap;
    }
}
