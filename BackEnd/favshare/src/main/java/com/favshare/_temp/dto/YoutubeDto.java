package com.favshare._temp.dto;

import com.favshare._temp.entity.YoutubeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeDto {
	private int id;
	private String url;

	public YoutubeDto(YoutubeEntity youtubeEntity) {
		this.id = youtubeEntity.getId();
		this.url = youtubeEntity.getUrl();
	}
}
