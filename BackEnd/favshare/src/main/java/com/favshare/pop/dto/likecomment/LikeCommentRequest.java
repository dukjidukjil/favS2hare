package com.favshare.pop.dto.likecomment;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeCommentRequest {
    int userId;
    int commentId;
}
