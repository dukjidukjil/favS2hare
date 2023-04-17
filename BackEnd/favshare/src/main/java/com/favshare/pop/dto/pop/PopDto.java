package com.favshare.pop.dto.pop;

import java.time.LocalDateTime;

import com.favshare.pop.entity.Pop;

import lombok.*;

@Data
@NoArgsConstructor
public class PopDto {
	private int id;
	private String name;
	private int startSecond;
	private int endSecond;
	private String content;
	private LocalDateTime createDate;
	private int views;
	private int likeCount;
	private String youtubeUrl;
	private boolean isLiked;
	private int userId;
	private boolean isMuted;

	public PopDto(Pop pop, boolean isLiked) {
		this.id = pop.getId();
		this.name = pop.getName();
		this.startSecond = pop.getStartSecond();
		this.endSecond = pop.getEndSecond();
		this.content = pop.getContent();
		this.createDate = pop.getCreateDate();
		this.views = pop.getViews();
		this.likeCount = pop.getLikePopList().size();
		this.youtubeUrl = pop.getYoutubeEntity().getUrl();
		this.isLiked = isLiked;
		this.userId = pop.getUser().getId();
		this.isMuted = pop.isMuted();
	}
}
