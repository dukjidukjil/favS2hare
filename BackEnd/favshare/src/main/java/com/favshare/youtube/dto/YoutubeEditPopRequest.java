package com.favshare.youtube.dto;

import com.favshare.feed.entity.Feed;
import com.favshare.pop.entity.Pop;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class YoutubeEditPopRequest {

	private int userId;
	private String youtubeUrl;
	private int feedId;
	private String name;
	private int startSecond;
	private int endSecond;
	private String content;
	private LocalDateTime createDate;
	private int views;
	private boolean isMuted;

	public YoutubeEditPopRequest(Pop pop, Feed feedEntity) {
		this.userId = pop.getUser().getId();
		this.youtubeUrl = pop.getYoutubeEntity().getUrl();
		this.feedId = feedEntity.getId();
		this.name = pop.getName();
		this.startSecond = pop.getStartSecond();
		this.endSecond = pop.getEndSecond();
		this.content = pop.getContent();
		this.createDate = LocalDateTime.now();
		this.views = 0;
		this.isMuted = pop.isMuted();
	}
}
