package com.favshare.feed.dto;

import com.favshare.feed.entity.Feed;
import lombok.*;

@Data
@NoArgsConstructor
public class FeedDto {
	private int id;
	private String name;
	private boolean isFirst;
	private String feedImageUrl;
	private int userId;

	public FeedDto(Feed feedEntity) {
		this.id = feedEntity.getId();
		this.name = feedEntity.getName();
		this.isFirst = feedEntity.isFirst();
		this.feedImageUrl = feedEntity.getFeedImageUrl();
		this.userId = feedEntity.getUser().getId();
	}
}
