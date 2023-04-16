package com.favshare.youtube.dto;

import com.favshare._temp.dto.PopDto;
import com.favshare.youtube.entity.YoutubeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YoutubeDetailRequest {
	private int id;
	private String url;

	private List<PopDto> popList;

	public YoutubeDetailRequest(YoutubeEntity youtubeEntity) {
		this.id = youtubeEntity.getId();
		this.url = youtubeEntity.getUrl();
	}

}
