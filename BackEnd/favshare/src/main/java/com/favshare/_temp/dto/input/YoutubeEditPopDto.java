package com.favshare._temp.dto.input;

import java.time.LocalDateTime;

import com.favshare.feed.entity.Feed;
import com.favshare.pops.entity.PopEntity;

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

	public YoutubeEditPopDto(PopEntity popEntity, Feed feedEntity) {
		this.userId = popEntity.getUser().getId();
		this.youtubeUrl = popEntity.getYoutubeEntity().getUrl();
		this.feedId = feedEntity.getId();
		this.name = popEntity.getName();
		this.startSecond = popEntity.getStartSecond();
		this.endSecond = popEntity.getEndSecond();
		this.content = popEntity.getContent();
		this.createDate = LocalDateTime.now();
		this.views = 0;
		this.isMuted = popEntity.isMuted();
	}
}
