package com.favshare.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifyFirstFeedRequest {
    private int feedId;
    private int userId;
}
