package com.encore.playground.domain.notice.dto;

import com.encore.playground.domain.notice.entity.Notice;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NoticeDto {
    private Long noticeId;
    private String title;
    private String  author;
    private String contents;
    private LocalDateTime uploadTime;
    private int viewCount;

    /**
     * entity -> dto
     * @param notice
     */
    public NoticeDto (Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.author =  notice.getAuthor();
        this.contents = notice.getContents();
        this.uploadTime = notice.getUploadTime();
        this.viewCount = notice.getViewCount();
    }

    /**
     * dto -> entity
     * @return notice
     */
    public Notice toEntity() {
        return Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .author(author)
                .contents(contents)
                .uploadTime(uploadTime)
                .viewCount(viewCount)
                .build();
    }

}
