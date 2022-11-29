package com.favshare.pop.controller;

import java.util.ArrayList;
import java.util.List;

import com.favshare._temp.dto.CommentDto;
import com.favshare.pop.entity.Comment;
import com.favshare.pop.service.CommentService;
import com.favshare.pop.service.LikeCommentService;
import com.favshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.favshare._temp.dto.input.UserCommentContentIdDto;
import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare._temp.dto.input.UserPopContentIdDto;
import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare._temp.dto.input.UserProfileDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pop/comment")
public class CommentController {
	private final CommentService commentService;
	private final UserService userService;
	private final LikeCommentService likeCommentService;

	@ApiOperation(value = "댓글 버튼 클릭시 - 댓글 리스트 반환", response = ResponseEntity.class)
	@PostMapping("/list")
	public ResponseEntity<List<CommentDto>> showCommentList(@RequestBody UserPopIdDto userPopIdDto) {
		int userId = userPopIdDto.getUserId();
		int popId = userPopIdDto.getPopId();

		try {
			List<Comment> commentList = commentService.getCommentList(popId);
			List<CommentDto> result = new ArrayList<>();

			for (int i = 0; i < commentList.size(); i++) {
				Comment comment = commentList.get(i);
				UserProfileDto user = userService.getUserProfileById(comment.getUser().getId());
				boolean isLiked = likeCommentService.isLiked(userId, comment.getId());

				result.add(new CommentDto(comment, user.getNickname(), user.getProfileImageUrl(), isLiked));
			}

			return new ResponseEntity<List<CommentDto>>(result, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "댓글 등록", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<?> addComment(@RequestBody UserPopContentIdDto userPopContentIdDto) {
		try {
			commentService.insertComment(userPopContentIdDto);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "댓글 수정", response = ResponseEntity.class)
	@PutMapping
	public ResponseEntity<?> changeComment(@RequestBody UserCommentContentIdDto userCommentContentIdDto) {
		try {
			commentService.updateComment(userCommentContentIdDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "댓글 삭제", response = ResponseEntity.class)
	@DeleteMapping
	public ResponseEntity<?> deleteComment(@RequestBody UserCommentIdDto userCommentIdDto) {
		try {
			commentService.deleteComment(userCommentIdDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}