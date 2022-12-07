package com.favshare.pops.service;

import java.time.LocalDateTime;
import java.util.List;

import com.favshare._temp.dto.input.UserCommentContentIdDto;
import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare._temp.dto.input.UserPopContentIdDto;
import com.favshare.pops.entity.CommentEntity;
import com.favshare.pops.entity.PopEntity;
import com.favshare.user.entity.User;
import com.favshare.pops.repository.CommentRepository;
import com.favshare.pops.repository.PopRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	private final PopRepository popRepository;

	public List<CommentEntity> getCommentList(int popId) {
		return commentRepository.findAllByPopId(popId);
	}

	public void insertComment(UserPopContentIdDto userPopContentIdDto) {

		User user = userRepository.findById(userPopContentIdDto.getUserId()).get();
		PopEntity popEntity = popRepository.findById(userPopContentIdDto.getPopId()).get();

		CommentEntity commentEntity = CommentEntity.builder().content(userPopContentIdDto.getContent())
				.createDate(LocalDateTime.now()).isModify(false).user(user).popEntity(popEntity).build();

		commentRepository.save(commentEntity);
	}

	public void updateComment(UserCommentContentIdDto userCommentContentIdDto) {
		CommentEntity commentEntity;
		commentEntity = commentRepository.findByUserCommentId(userCommentContentIdDto.getUserId(),
				userCommentContentIdDto.getCommentId());
		commentEntity.changeComment(userCommentContentIdDto.getContent());
		commentRepository.save(commentEntity);
	}

	public void deleteComment(UserCommentIdDto userCommentIdDto) {
		commentRepository.deleteByUserCommentId(userCommentIdDto.getUserId(), userCommentIdDto.getCommentId());
	}

}