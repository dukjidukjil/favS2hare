package com.favshare.feed.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class ModifyFeedNameRequest {
    private int feedId;
    private String feedName;
}
