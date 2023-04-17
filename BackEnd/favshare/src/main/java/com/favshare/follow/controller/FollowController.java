package com.favshare.follow.controller;

import com.favshare.follow.dto.FromUserToUserRequest;
import com.favshare.follow.service.FollowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/follow")
public class FollowController {
	@Autowired
	private FollowService followService;

	@ApiOperation(value = "내가 팔로우 하는 사람(팔로잉)", response = ResponseEntity.class)
	@GetMapping("/from/{userId}")
	public ResponseEntity showFollower(@PathVariable("userId") int userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(followService.findMyFollowingList(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "나를 팔로우 하는 사람(팔로워)", response = ResponseEntity.class)
	@GetMapping("to/{userId}")
	public ResponseEntity showFollowing(@PathVariable("userId") int userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(followService.findMyFollowerList(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "from이 to를 팔로우 함", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity addFollow(@RequestBody FromUserToUserRequest fromUserToUserRequest) {
		try {
			followService.insertFollow(fromUserToUserRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "팔로우 취소 버튼 - 내가 팔로우 하는 사람(팔로잉)을 삭제", response = ResponseEntity.class)
	@DeleteMapping("/from")
	public ResponseEntity deleteFollower(@RequestBody FromUserToUserRequest fromUserToUserRequest) {
		try {
			followService.DeleteFollowById(fromUserToUserRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "삭제 버튼 - 나를 팔로우 하는 사람(팔로워)을 삭제", response = ResponseEntity.class)
	@DeleteMapping("/to")
	public ResponseEntity deleteFollowing(@RequestBody FromUserToUserRequest fromUserToUserRequest) {
		try {
			followService.DeleteFollowById(fromUserToUserRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}