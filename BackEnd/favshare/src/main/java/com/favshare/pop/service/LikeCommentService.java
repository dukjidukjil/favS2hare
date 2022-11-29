package com.favshare.pop.service;


import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare.pop.entity.Comment;
import com.favshare.pop.entity.LikeComment;
import com.favshare.user.entity.User;
import com.favshare.pop.repository.CommentRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.favshare.pop.repository.LikeCommentRepository;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

	private final LikeCommentRepository likeCommentRepository;

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	public void insertLike(@RequestBody UserCommentIdDto userCommentIdDto) {
		int userId = userCommentIdDto.getUserId();
		likeCommentRepository.findById(userId);

		LikeComment likeComment;

		User user = userRepository.findById(userCommentIdDto.getUserId()).get();
		Comment comment = commentRepository.findById(userCommentIdDto.getCommentId()).get();

		int duplicate = likeCommentRepository.findByUserIdCommentID(userId, comment.getId());
		if (duplicate >= 1) {

		} else {

			likeComment = LikeComment.builder().user(user).comment(comment).build();
			likeCommentRepository.save(likeComment);
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
