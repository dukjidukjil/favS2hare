package com.favshare._temp.service;


import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare._temp.entity.CommentEntity;
import com.favshare._temp.entity.LikeCommentEntity;
import com.favshare.user.entity.User;
import com.favshare._temp.repository.CommentRepository;
import com.favshare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.favshare._temp.repository.LikeCommentRepository;

@Service
public class LikeCommentService {

	@Autowired
	private LikeCommentRepository likeCommentRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

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
