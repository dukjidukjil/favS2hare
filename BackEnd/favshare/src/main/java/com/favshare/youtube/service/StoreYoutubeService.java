package com.favshare.youtube.service;

import java.util.ArrayList;
import java.util.List;

import com.favshare.youtube.dto.YoutubeRequest;
import com.favshare.youtube.dto.YoutubeResponse;
import com.favshare.youtube.entity.StoreYoutubeEntity;
import com.favshare.user.entity.User;
import com.favshare.youtube.entity.YoutubeEntity;
import com.favshare.youtube.repository.StoreYoutubeRepository;
import com.favshare.user.repository.UserRepository;
import com.favshare.youtube.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreYoutubeService {

	private final StoreYoutubeRepository storeYoutubeRepository;

	private final YoutubeRepository youtubeRepository;

	private final  UserRepository userRepository;

	public void insertBookmark(YoutubeRequest youtubeUserIdDto) {

		insertYoutube(youtubeUserIdDto);

		StoreYoutubeEntity storeYoutubeEntity = new StoreYoutubeEntity();
		User user = userRepository.findById(youtubeUserIdDto.getUserId()).get();
		YoutubeEntity youtubeEntity = youtubeRepository.findByUrl(youtubeUserIdDto.getYoutubeUrl());

		if (storeYoutubeRepository.isBookmarked(user.getId(), youtubeEntity.getId()) < 1) {

			if (youtubeEntity == null) {
				youtubeEntity = YoutubeEntity.builder().url(youtubeUserIdDto.getYoutubeUrl()).build();
			}
			storeYoutubeEntity = StoreYoutubeEntity.builder().user(user).youtubeEntity(youtubeEntity)
					.build();

			storeYoutubeRepository.save(storeYoutubeEntity);
		}
	}

	public void insertYoutube(YoutubeRequest youtubeUserIdDto) {
		if (youtubeRepository.isDuplicated(youtubeUserIdDto.getYoutubeUrl()) < 1) {

			YoutubeEntity youtubeEntity = YoutubeEntity.builder().url(youtubeUserIdDto.getYoutubeUrl()).build();
			youtubeRepository.save(youtubeEntity);
		}
	}

	public List<YoutubeResponse> getYoutubeBookmarkById(int id) {
		User user;
		user = userRepository.findById(id).get();

		List<StoreYoutubeEntity> storeYoutubeList = user.getStoreYoutubeList();

		List<YoutubeResponse> result = new ArrayList<YoutubeResponse>();
		for (int i = 0; i < storeYoutubeList.size(); i++) {
			result.add(new YoutubeResponse(storeYoutubeList.get(i).getYoutubeEntity()));
		}
		return result;
	}

	public void deleteYoutubeBookMarkById(YoutubeRequest youtubeUserIdDto) {
		StoreYoutubeEntity storeYoutubeEntity;
		YoutubeEntity youtubeEntity = youtubeRepository.findByUrl(youtubeUserIdDto.getYoutubeUrl());

		storeYoutubeEntity = storeYoutubeRepository.searchByUserIdAndYoutubeId(youtubeUserIdDto.getUserId(),
				youtubeEntity.getId());
		storeYoutubeRepository.deleteById(storeYoutubeEntity.getId());
	}
}
