package com.favshare.pop.dto.comment;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ModifyCommentRequest {
    int userId;
    int commentId;
    String content;
}
