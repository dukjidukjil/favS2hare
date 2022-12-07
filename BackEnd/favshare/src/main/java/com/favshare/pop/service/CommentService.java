package com.favshare.pop.service;

import java.time.LocalDateTime;
import java.util.List;


import com.favshare.pop.dto.comment.*;
import com.favshare.pop.entity.Comment;
import com.favshare.pop.entity.Pop;
import com.favshare.user.entity.User;
import com.favshare.pop.repository.CommentRepository;
import com.favshare.pop.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	private final PopRepository popRepository;

	public List<CommentResponse> getCommentList(GetCommentListRequest getCommentListRequest) {
		int popId = getCommentListRequest.getPopId();
		int userId = getCommentListRequest.getUserId();
		// querydsl 검증 필요
		return commentRepository.getCommentList(popId, userId);

//		List<com.favshare.pop.entity.Comment> commentList = commentRepository.findAllByPopId(popId);
//		List<CommentDto_> result = new ArrayList<>();
//
//		for (int i = 0; i < commentList.size(); i++) {
//			com.favshare.pop.entity.Comment comment = commentList.get(i);
//			UserProfileDto user = userService.getUserProfileById(comment.getUser().getId());
//			boolean isLiked = likeCommentService.isLiked(userId, comment.getId());
//
//			result.add(new CommentDto_(comment, user.getNickname(), user.getProfileImageUrl(), isLiked));
//		}
	}

	public void insertComment(CreateCommentRequest createCommentRequest) {
		int userId = createCommentRequest.getUserId();
		int popId = createCommentRequest.getPopId();
		String content = createCommentRequest.getContent();

		User user = userRepository.findById(userId).get();
		Pop pop = popRepository.findById(popId).get();

		Comment commentDto = Comment.builder().content(content)
				.createdDate(LocalDateTime.now()).isModified(false).user(user).pop(pop).build();

		commentRepository.save(commentDto);
	}

	public void updateComment(ModifyCommentRequest modifyCommentRequest) {
		int userId = modifyCommentRequest.getUserId();
		int commentId = modifyCommentRequest.getCommentId();
		String content = modifyCommentRequest.getContent();

		Comment comment = commentRepository.findByUserCommentId(userId,commentId);
		comment.changeComment(content);
	}

	public void deleteComment(DeleteCommentRequest deleteCommentRequest) {
		int userId = deleteCommentRequest.getUserId();
		int commentId = deleteCommentRequest.getCommentId();
		commentRepository.deleteByUserCommentId(userId, commentId);
	}

}
