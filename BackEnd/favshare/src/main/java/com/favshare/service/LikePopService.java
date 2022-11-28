package com.favshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favshare.dto.input.UserPopIdDto;
import com.favshare.entity.LikePopEntity;
import com.favshare.entity.PopEntity;
import com.favshare.entity.UserEntity;
import com.favshare.repository.LikePopRepository;
import com.favshare.repository.PopRepository;
import com.favshare.repository.UserRepository;

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

		UserEntity userEntity = userRepository.findById(userPopIdDto.getUserId()).get();
		PopEntity popEntity = popRepository.findById(userPopIdDto.getPopId()).get();

		int duplicate = likePopRepository.findByPopIdUserId(userEntity.getId(), popEntity.getId());
		if (duplicate >= 1) {

		} else {
			likePopEntity = LikePopEntity.builder().userEntity(userEntity).popEntity(popEntity).build();
			likePopRepository.save(likePopEntity);

		}

	}

	public void deleteLikePop(UserPopIdDto userPopIdDto) {
		LikePopEntity likePopEntity;
		likePopEntity = likePopRepository.searchByUserIdAndPopId(userPopIdDto.getUserId(), userPopIdDto.getPopId());
		likePopRepository.deleteById(likePopEntity.getId());
	}
}
