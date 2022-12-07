package com.favshare.pop.dto.comment;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetCommentListRequest {
    private int userId;
    private int popId;
}
