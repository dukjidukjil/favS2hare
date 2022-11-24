package com.favshare._temp.dto;

import java.util.ArrayList;
import java.util.List;

import com.favshare._temp.entity.StoreYoutubeEntity;
import com.favshare._temp.entity.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeBookmarkDto {

	private int userId;

	private List<StoreYoutubeEntity> storeYoutubeList = new ArrayList<>();

	public YoutubeBookmarkDto(UserEntity userEntity) {
		this.userId = userEntity.getId();
		this.storeYoutubeList = userEntity.getStoreYoutubeList();
	}
}
