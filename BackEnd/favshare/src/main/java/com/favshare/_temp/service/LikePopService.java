package com.favshare._temp.service;

import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare._temp.entity.LikePopEntity;
import com.favshare._temp.entity.PopEntity;
import com.favshare.user.entity.User;
import com.favshare._temp.repository.LikePopRepository;
import com.favshare._temp.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikePopService {

	@Autowired
	private LikePopRepository likePopRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PopRepository popRepository;

	public void insertLikePop(UserPopIdDto userPopIdDto) {
		LikePopEntity likePopEntity;

		User user = userRepository.findById(userPopIdDto.getUserId()).get();
		PopEntity popEntity = popRepository.findById(userPopIdDto.getPopId()).get();

		int duplicate = likePopRepository.findByPopIdUserId(user.getId(), popEntity.getId());
		if (duplicate >= 1) {

		} else {
			likePopEntity = LikePopEntity.builder().user(user).popEntity(popEntity).build();
			likePopRepository.save(likePopEntity);

		}

	}

	public void deleteLikePop(UserPopIdDto userPopIdDto) {
		LikePopEntity likePopEntity;
		likePopEntity = likePopRepository.searchByUserIdAndPopId(userPopIdDto.getUserId(), userPopIdDto.getPopId());
		likePopRepository.deleteById(likePopEntity.getId());
	}
}
