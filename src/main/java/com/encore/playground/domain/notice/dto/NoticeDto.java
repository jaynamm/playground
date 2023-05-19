package com.encore.playground.domain.notice.dto;

import com.encore.playground.domain.notice.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NoticeDto {
    private Long id;
    private String title;
    private String memberId;
    private String content;
    private LocalDateTime createdDate;
    private int viewCount;

    /**
     * entity -> dto
     * @param notice
     */
    public NoticeDto (Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.memberId =  notice.getMemberId();
        this.content = notice.getContent();
        this.createdDate = notice.getCreatedDate();
        this.viewCount = notice.getViewCount();
    }

    /**
     * dto -> entity
     * @return notice
     */
    public Notice toEntity() {
        return Notice.builder()
                .id(id)
                .title(title)
                .memberId(memberId)
                .content(content)
                .createdDate(createdDate)
                .viewCount(viewCount)
                .build();
    }

}
