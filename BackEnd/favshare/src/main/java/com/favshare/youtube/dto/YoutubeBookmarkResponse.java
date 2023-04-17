package com.favshare.youtube.dto;

import com.favshare.user.entity.User;
import com.favshare.youtube.entity.StoreYoutubeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class YoutubeBookmarkResponse {

	private int userId;

	private List<StoreYoutubeEntity> storeYoutubeList = new ArrayList<>();

	public YoutubeBookmarkResponse(User user) {
		this.userId = user.getId();
		this.storeYoutubeList = user.getStoreYoutubeList();
	}
}
