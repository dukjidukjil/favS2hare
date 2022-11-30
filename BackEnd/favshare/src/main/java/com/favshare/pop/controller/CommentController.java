package com.favshare.pop.controller;

import com.favshare.pop.dto.comment.CreateCommentRequest;
import com.favshare.pop.dto.comment.DeleteCommentRequest;
import com.favshare.pop.dto.comment.GetCommentListRequest;
import com.favshare.pop.dto.comment.ModifyCommentRequest;
import com.favshare.pop.service.CommentService;
import com.favshare.pop.service.LikeCommentService;
import com.favshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity showCommentList(@RequestBody GetCommentListRequest getCommentListRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentList(getCommentListRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "댓글 등록", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<?> addComment(@RequestBody CreateCommentRequest createCommentRequest) {
		try {
			commentService.insertComment(createCommentRequest);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "댓글 수정", response = ResponseEntity.class)
	@PutMapping
	public ResponseEntity<?> changeComment(@RequestBody ModifyCommentRequest modifyCommentRequest) {
		try {
			commentService.updateComment(modifyCommentRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "댓글 삭제", response = ResponseEntity.class)
	@DeleteMapping
	public ResponseEntity<?> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
		try {
			commentService.deleteComment(deleteCommentRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}