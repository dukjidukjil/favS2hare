package com.favshare.feed.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class ModifyFirstFeedRequest {
    private int userId;
    private int feedId;
}
