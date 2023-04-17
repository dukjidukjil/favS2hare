package com.favshare.follow.service;

import com.favshare.follow.dto.FollowDto;
import com.favshare.follow.dto.FromUserToUserRequest;
import com.favshare.follow.entity.FollowEntity;
import com.favshare.follow.repository.FollowRepository;
import com.favshare.user.entity.User;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
	private final FollowRepository followRepository;

	private final UserRepository userRepository;

	public User getUserById(int id) {
		User user = userRepository.findById(id).get();
		return user;
	}

	public void insertFollow(FromUserToUserRequest fromUserToUserRequest) {
		int fromUserId = fromUserToUserRequest.getFromUserId();
		int toUserId = fromUserToUserRequest.getToUserId();
		if (followRepository.countFollowFByUserId(fromUserId, toUserId) < 1) {
			User fromUser = getUserById(fromUserId);
			User toUser = getUserById(toUserId);
			FollowEntity followEntity = FollowEntity.builder().toUser(toUser).fromUser(fromUser)
					.build();
			followRepository.save(followEntity);

		}
	}

	public List<FollowDto> findMyFollowingList(int userId){
		List<FollowEntity> followEntityList = getFollowingById(userId);

		List<FollowDto> result = new ArrayList<>();

		for (int i = 0; i < followEntityList.size(); i++) {
			int fromUserId = followEntityList.get(i).getFromUser().getId();
			int toUserId = followEntityList.get(i).getToUser().getId();
			String nickname = followEntityList.get(i).getToUser().getNickname();
			String profileImageUrl = followEntityList.get(i).getToUser().getProfileImageUrl();
			result.add(new FollowDto(fromUserId, toUserId, nickname, false, profileImageUrl));
		}
		return result;
	}

	public List<FollowDto> findMyFollowerList(int userId){
		List<FollowEntity> followEntityList = getFollowerById(userId);

		List<FollowDto> result = new ArrayList<>();

		for (int i = 0; i < followEntityList.size(); i++) {
			int fromUserId = followEntityList.get(i).getFromUser().getId();
			int toUserId = followEntityList.get(i).getToUser().getId();
			String nickname = followEntityList.get(i).getFromUser().getNickname();
			FromUserToUserRequest fromUserToUserRequest = FromUserToUserRequest.builder().fromUserId(fromUserId).toUserId(toUserId).build();
			boolean isFollowForFollow = getFollowForFollow(fromUserToUserRequest);
			String profileImageUrl = followEntityList.get(i).getFromUser().getProfileImageUrl();
			result.add(new FollowDto(fromUserId, toUserId, nickname, isFollowForFollow, profileImageUrl));
		}
		return result;
	}

	public boolean getFollowForFollow(FromUserToUserRequest fromUserToUserRequest) {
		int fromUserId = fromUserToUserRequest.getFromUserId();
		int toUserId = fromUserToUserRequest.getToUserId();
		int countFromTo = followRepository.countFollowFByUserId(fromUserId, toUserId);
		int countToFrom = followRepository.countFollowFByUserId(toUserId, fromUserId);

		return (countFromTo == 1 && countToFrom == 1);

	}

	public List<FollowEntity> getFollowerById(int userId) {
		User user;
		user = userRepository.findById(userId).get();
		List<FollowEntity> result = user.getToUserEntityList();

		return result;
	}

	public List<FollowEntity> getFollowingById(int userId) {
		User user;
		user = userRepository.findById(userId).get();
		List<FollowEntity> result = user.getFromUserEntityList();

		return result;
	}

	public void DeleteFollowById(FromUserToUserRequest fromUserToUserRequest) {
		User fromUser = getUserById(fromUserToUserRequest.getFromUserId());
		User toUser = getUserById(fromUserToUserRequest.getToUserId());
		followRepository.deleteFollowByUserId(fromUser, toUser);

	}

}
