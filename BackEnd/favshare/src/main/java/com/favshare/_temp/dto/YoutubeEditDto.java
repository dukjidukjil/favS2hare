package com.favshare._temp.dto;

import com.favshare._temp.entity.UserEntity;
import com.favshare._temp.entity.YoutubeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeEditDto {

	private int userId;
	private int youtubeId;
	private int startSecond;
	private int endSecond;
	private String title;
	private String content;
	private int feedId;

	public YoutubeEditDto(UserEntity userEntity, YoutubeEntity youtubeEntity) {
		this.userId = userEntity.getId();
		this.youtubeId = youtubeEntity.getId();
	}

	public YoutubeEditDto(YoutubeEntity youtubeEntity) {
		this.youtubeId = youtubeEntity.getId();
	}

}
