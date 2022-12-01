package com.favshare.pop.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentResponse {
    private int commentId;
    private String content;
    private LocalDateTime createdDate;
    private boolean isModified;
    private int userId;
    private int popId;
    private String nickname;
    private String profileImageUrl;
    private int countCommentLikes;
    private boolean isLiked;
}
