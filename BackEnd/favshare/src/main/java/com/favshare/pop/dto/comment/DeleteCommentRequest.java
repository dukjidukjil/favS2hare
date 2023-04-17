package com.favshare.pop.dto.comment;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DeleteCommentRequest {
    int userId;
    int commentId;
}
