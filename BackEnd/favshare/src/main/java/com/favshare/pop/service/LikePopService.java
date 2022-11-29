package com.favshare.pop.service;

import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare.pop.entity.LikePop;
import com.favshare.pop.entity.Pop;
import com.favshare.user.entity.User;
import com.favshare.pop.repository.LikePopRepository;
import com.favshare.pop.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikePopService {

	private final LikePopRepository likePopRepository;

	private final UserRepository userRepository;

	private final PopRepository popRepository;

	public void insertLikePop(UserPopIdDto userPopIdDto) {
		LikePop likePop;

		User user = userRepository.findById(userPopIdDto.getUserId()).get();
		Pop pop = popRepository.findById(userPopIdDto.getPopId()).get();

		int duplicate = likePopRepository.findByPopIdUserId(user.getId(), pop.getId());
		if (duplicate >= 1) {

		} else {
			likePop = LikePop.builder().user(user).pop(pop).build();
			likePopRepository.save(likePop);

		}

	}

	public void deleteLikePop(UserPopIdDto userPopIdDto) {
		LikePop likePop;
		likePop = likePopRepository.searchByUserIdAndPopId(userPopIdDto.getUserId(), userPopIdDto.getPopId());
		likePopRepository.deleteById(likePop.getId());
	}
}
