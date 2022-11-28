package com.favshare.pops.service;


import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare.pops.entity.CommentEntity;
import com.favshare.pops.entity.LikeCommentEntity;
import com.favshare.user.entity.User;
import com.favshare.pops.repository.CommentRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.favshare.pops.repository.LikeCommentRepository;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

	private final LikeCommentRepository likeCommentRepository;

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	public void insertLike(@RequestBody UserCommentIdDto userCommentIdDto) {
		int userId = userCommentIdDto.getUserId();
		likeCommentRepository.findById(userId);

		LikeCommentEntity likeCommentEntity;

		User user = userRepository.findById(userCommentIdDto.getUserId()).get();
		CommentEntity commentEntity = commentRepository.findById(userCommentIdDto.getCommentId()).get();

		int duplicate = likeCommentRepository.findByUserIdCommentID(userId, commentEntity.getId());
		if (duplicate >= 1) {

		} else {

			likeCommentEntity = LikeCommentEntity.builder().user(user).commentEntity(commentEntity).build();
			likeCommentRepository.save(likeCommentEntity);
		}

	}

	public void deleteCommentLike(@RequestBody UserCommentIdDto userCommentIdDto) {
		likeCommentRepository.deleteLikeByUserCommentId(userCommentIdDto.getUserId(), userCommentIdDto.getCommentId());
	}

	public boolean isLiked(int userId, int commentId) {
		if (likeCommentRepository.isLiked(userId, commentId) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
