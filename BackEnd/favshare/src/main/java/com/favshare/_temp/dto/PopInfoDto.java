package com.favshare._temp.dto;

import java.time.LocalDateTime;

import com.favshare._temp.entity.PopEntity;
import com.favshare._temp.entity.YoutubeEntity;

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

	public PopInfoDto(PopEntity popEntity, YoutubeEntity youtubeEntity, boolean isLiked) {
		this.id = popEntity.getId();
		this.name = popEntity.getName();
		this.startSecond = popEntity.getStartSecond();
		this.endSecond = popEntity.getEndSecond();
		this.content = popEntity.getContent();
		this.createDate = popEntity.getCreateDate();
		this.views = popEntity.getViews();
		this.likeCount = popEntity.getLikePopList().size();
		this.youtubeId = youtubeEntity.getId();
		this.url = youtubeEntity.getUrl();
		this.isLiked = isLiked;
		this.userId = popEntity.getUser().getId();
		this.isMuted = popEntity.isMuted();
	}
}
