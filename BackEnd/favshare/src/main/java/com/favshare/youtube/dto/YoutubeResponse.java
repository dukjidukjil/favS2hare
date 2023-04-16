package com.favshare.youtube.dto;

import com.favshare.youtube.entity.YoutubeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeResponse {
	private int id;
	private String url;

	public YoutubeResponse(YoutubeEntity youtubeEntity) {
		this.id = youtubeEntity.getId();
		this.url = youtubeEntity.getUrl();
	}
}
