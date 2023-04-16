package com.favshare.pop.dto.pop;

import java.time.LocalDateTime;

import com.favshare.pop.entity.Pop;
import com.favshare.youtube.entity.YoutubeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopInfoDto {
	private int id;
	private String name;
	private int startSecond;
	private int endSecond;
	private String content;
	private LocalDateTime createDate;
	private int views;
	private int likeCount;
	private int userId;
	private int youtubeId;
	private String url;
	private boolean isLiked;
	private boolean isMuted;

	public PopInfoDto(Pop pop, YoutubeEntity youtubeEntity, boolean isLiked) {
		this.id = pop.getId();
		this.name = pop.getName();
		this.startSecond = pop.getStartSecond();
		this.endSecond = pop.getEndSecond();
		this.content = pop.getContent();
		this.createDate = pop.getCreateDate();
		this.views = pop.getViews();
		this.likeCount = pop.getLikePopList().size();
		this.youtubeId = youtubeEntity.getId();
		this.url = youtubeEntity.getUrl();
		this.isLiked = isLiked;
		this.userId = pop.getUser().getId();
		this.isMuted = pop.isMuted();
	}
}
