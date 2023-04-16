package com.favshare.youtube.dto;


import com.favshare.feed.dto.FeedDto;
import com.favshare.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class YoutubeInfoRequest {

	private int userId;
	private String url;

	private List<FeedDto> feedList = new ArrayList<>();

	public YoutubeInfoRequest(User user, String url) {
		this.userId = user.getId();
		this.url = url;
	}
}
