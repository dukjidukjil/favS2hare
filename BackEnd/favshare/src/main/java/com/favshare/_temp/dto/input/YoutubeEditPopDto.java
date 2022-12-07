package com.favshare._temp.dto.input;

import java.time.LocalDateTime;

import com.favshare.feed.entity.Feed;
import com.favshare.pop.entity.Pop;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeEditPopDto {

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

	public YoutubeEditPopDto(Pop pop, Feed feedEntity) {
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
