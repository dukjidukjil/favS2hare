package com.favshare.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.favshare.dto.input.UserCommentIdDto;
import com.favshare.entity.CommentEntity;
import com.favshare.entity.LikeCommentEntity;
import com.favshare.entity.UserEntity;
import com.favshare.repository.CommentRepository;
import com.favshare.repository.LikeCommentRepository;
import com.favshare.repository.UserRepository;

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

		UserEntity userEntity = userRepository.findById(userCommentIdDto.getUserId()).get();
		CommentEntity commentEntity = commentRepository.findById(userCommentIdDto.getCommentId()).get();

		int duplicate = likeCommentRepository.findByUserIdCommentID(userId, commentEntity.getId());
		if (duplicate >= 1) {

		} else {

			likeCommentEntity = LikeCommentEntity.builder().userEntity(userEntity).commentEntity(commentEntity).build();
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
