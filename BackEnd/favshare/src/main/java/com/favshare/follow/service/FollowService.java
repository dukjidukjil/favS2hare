package com.favshare.follow.service;

import java.util.List;

import com.favshare.follow.repository.FollowRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favshare.follow.entity.FollowEntity;
import com.favshare.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

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

	public void insertFollow(int fromUserId, int toUserId) {
		if (followRepository.countFollowFByUserId(fromUserId, toUserId) < 1) {
			User fromUser = getUserById(fromUserId);
			User toUser = getUserById(toUserId);
			FollowEntity followEntity = FollowEntity.builder().toUser(toUser).fromUser(fromUser)
					.build();
			followRepository.save(followEntity);

		}

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

	public void DeleteFollowById(int fromUserId, int toUserId) {
		User fromUser = getUserById(fromUserId);
		User toUser = getUserById(toUserId);
		followRepository.deleteFollowByUserId(fromUser, toUser);

	}

}
