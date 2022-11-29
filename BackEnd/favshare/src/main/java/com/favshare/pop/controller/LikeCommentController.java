package com.favshare.pop.controller;

import com.favshare._temp.dto.input.UserCommentIdDto;
import com.favshare.pop.dto.likecomment.LikeCommentRequest;
import com.favshare.pop.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pop/likeComment")
public class LikeCommentController {
	private final LikeCommentService likeCommentService;

	@ApiOperation(value = "댓글 좋아요 클릭 시", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<?> addLikeComment(@RequestBody LikeCommentRequest likeCommentRequest) {
		try {
			likeCommentService.insertLike(likeCommentRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

	@ApiOperation(value = "댓글 좋아요 취소 시", response = ResponseEntity.class)
	@DeleteMapping
	public ResponseEntity<?> deleteLikeComment(@RequestBody LikeCommentRequest likeCommentRequest) {
		try {
			likeCommentService.deleteCommentLike(likeCommentRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}
}
