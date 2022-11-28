package com.favshare._temp.dto;

import java.util.ArrayList;
import java.util.List;

import com.favshare.youtube.entity.StoreYoutubeEntity;
import com.favshare.user.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeBookmarkDto {

	private int userId;

	private List<StoreYoutubeEntity> storeYoutubeList = new ArrayList<>();

	public YoutubeBookmarkDto(User user) {
		this.userId = user.getId();
		this.storeYoutubeList = user.getStoreYoutubeList();
	}
}
