package com.favshare._temp.dto;

import java.util.ArrayList;
import java.util.List;

import com.favshare.user.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeInfoDto {

	private int userId;
	private String url;

	private List<FeedDto> feedList = new ArrayList<>();

	public YoutubeInfoDto(User user, String url) {
		this.userId = user.getId();
		this.url = url;
	}
}
