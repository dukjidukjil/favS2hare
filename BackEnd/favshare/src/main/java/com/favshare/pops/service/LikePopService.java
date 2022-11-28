package com.favshare.pops.service;

import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare.pops.entity.LikePopEntity;
import com.favshare.pops.entity.PopEntity;
import com.favshare.user.entity.User;
import com.favshare.pops.repository.LikePopRepository;
import com.favshare.pops.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikePopService {

	private final LikePopRepository likePopRepository;

	private final UserRepository userRepository;

	private final PopRepository popRepository;

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
