package com.favshare.youtube.service;

import java.util.ArrayList;
import java.util.List;

import com.favshare._temp.dto.YoutubeDto;
import com.favshare._temp.dto.input.YoutubeUserIdDto;
import com.favshare.youtube.entity.StoreYoutubeEntity;
import com.favshare.user.entity.User;
import com.favshare.youtube.entity.YoutubeEntity;
import com.favshare.youtube.repository.StoreYoutubeRepository;
import com.favshare.user.repository.UserRepository;
import com.favshare.youtube.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreYoutubeService {

	private final StoreYoutubeRepository storeYoutubeRepository;

	private final YoutubeRepository youtubeRepository;

	private final  UserRepository userRepository;

	public void insertBookmark(YoutubeUserIdDto youtubeUserIdDto) {

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

	public void insertYoutube(YoutubeUserIdDto youtubeUserIdDto) {
		if (youtubeRepository.isDuplicated(youtubeUserIdDto.getYoutubeUrl()) < 1) {

			YoutubeEntity youtubeEntity = YoutubeEntity.builder().url(youtubeUserIdDto.getYoutubeUrl()).build();
			youtubeRepository.save(youtubeEntity);
		}
	}

	public List<YoutubeDto> getYoutubeBookmarkById(int id) {
		User user;
		user = userRepository.findById(id).get();

		List<StoreYoutubeEntity> storeYoutubeList = user.getStoreYoutubeList();

		List<YoutubeDto> result = new ArrayList<YoutubeDto>();
		for (int i = 0; i < storeYoutubeList.size(); i++) {
			result.add(new YoutubeDto(storeYoutubeList.get(i).getYoutubeEntity()));
		}
		return result;
	}

	public void deleteYoutubeBookMarkById(YoutubeUserIdDto youtubeUserIdDto) {
		StoreYoutubeEntity storeYoutubeEntity;
		YoutubeEntity youtubeEntity = youtubeRepository.findByUrl(youtubeUserIdDto.getYoutubeUrl());

		storeYoutubeEntity = storeYoutubeRepository.searchByUserIdAndYoutubeId(youtubeUserIdDto.getUserId(),
				youtubeEntity.getId());
		storeYoutubeRepository.deleteById(storeYoutubeEntity.getId());
	}
}
