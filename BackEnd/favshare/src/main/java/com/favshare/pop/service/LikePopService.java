package com.favshare.pop.service;

import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare.pop.dto.pop.PopInfoRequest;
import com.favshare.pop.entity.LikePop;
import com.favshare.pop.entity.Pop;
import com.favshare.user.entity.User;
import com.favshare.pop.repository.LikePopRepository;
import com.favshare.pop.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikePopService {

	private final LikePopRepository likePopRepository;

	private final UserRepository userRepository;

	private final PopRepository popRepository;

	@Transactional
	public void insertLikePop(PopInfoRequest popInfoRequest) {
		LikePop likePop;

		User user = userRepository.findById(popInfoRequest.getUserId()).get();
		Pop pop = popRepository.findById(popInfoRequest.getPopId()).get();

		boolean hasDuplicateVal = (likePopRepository.findByPopIdUserId(user.getId(), pop.getId())>=1)?true:false;
		if (hasDuplicateVal) {
			likePop = LikePop.builder().user(user).pop(pop).build();
			likePopRepository.save(likePop);
		}

	}

	@Transactional
	public void deleteLikePop(PopInfoRequest popInfoRequest) {
		LikePop likePop = likePopRepository.searchByUserIdAndPopId(popInfoRequest.getUserId(), popInfoRequest.getPopId());
		likePopRepository.deleteById(likePop.getId());
	}
}
