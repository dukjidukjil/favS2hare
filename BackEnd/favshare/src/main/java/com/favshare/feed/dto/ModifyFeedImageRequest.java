package com.favshare.feed.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ModifyFeedImageRequest {
    private int feedId;
    private String feedImageUrl;
}
