package com.favshare.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifyFeedImageRequest {
    private int feedId;
    private String feedImageUrl;
}
