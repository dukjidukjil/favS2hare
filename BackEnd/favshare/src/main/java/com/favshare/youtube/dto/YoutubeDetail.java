package com.favshare.youtube.dto;

import com.favshare.pop.dto.pop.PopDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class YoutubeDetail {

	private String url;
	private boolean isBookmarked;
	private List<PopDto> popList = new ArrayList<>();

	public YoutubeDetail(String url, boolean isBookmarked) {
		this.url = url;
		this.isBookmarked = isBookmarked;
	}

}
