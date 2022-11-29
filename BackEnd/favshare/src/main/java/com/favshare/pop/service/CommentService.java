package com.favshare.pop.service;

import java.time.LocalDateTime;
import java.util.List;

import com.favshare._temp.dto.input.UserCommentContentIdDto;
import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare._temp.dto.input.UserPopContentIdDto;
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

	public List<Comment> getCommentList(int popId) {
		return commentRepository.findAllByPopId(popId);
	}

	public void insertComment(UserPopContentIdDto userPopContentIdDto) {

		User user = userRepository.findById(userPopContentIdDto.getUserId()).get();
		Pop pop = popRepository.findById(userPopContentIdDto.getPopId()).get();

		Comment comment = Comment.builder().content(userPopContentIdDto.getContent())
				.createDate(LocalDateTime.now()).isModify(false).user(user).pop(pop).build();

		commentRepository.save(comment);
	}

	public void updateComment(UserCommentContentIdDto userCommentContentIdDto) {
		Comment comment;
		comment = commentRepository.findByUserCommentId(userCommentContentIdDto.getUserId(),
				userCommentContentIdDto.getCommentId());
		comment.changeComment(userCommentContentIdDto.getContent());
		commentRepository.save(comment);
	}

	public void deleteComment(UserCommentIdDto userCommentIdDto) {
		commentRepository.deleteByUserCommentId(userCommentIdDto.getUserId(), userCommentIdDto.getCommentId());
	}

}
