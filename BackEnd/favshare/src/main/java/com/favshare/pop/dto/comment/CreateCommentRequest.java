package com.favshare.pop.dto.comment;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CreateCommentRequest {
    int userId;
    int popId;
    String content;
}
