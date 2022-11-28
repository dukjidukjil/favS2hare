package com.favshare.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favshare.dto.FeedDto;
import com.favshare.dto.PopDto;
import com.favshare.dto.input.FeedUserIdDto;
import com.favshare.dto.input.FollowForFollowDto;
import com.favshare.dto.input.UserProfileDto;
import com.favshare.service.PopService;
import com.favshare.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private PopService popService;

	@ApiOperation(value = "프로필 보기 윗부분", response = ResponseEntity.class)
	@GetMapping("/{userId}")
	public ResponseEntity<UserProfileDto> showProfilehead(@PathVariable("userId") int userId) {
		try {

			int popCount = popService.getPopCount(userId);
			int[] temp = userService.countFollow(userId);
			int followerNum = temp[0];
			int followingNum = temp[1];

			UserProfileDto userProfileDto = userService.getUserProfileById(userId);

			userProfileDto.setPopCount(popCount);
			userProfileDto.setFollowerNum(followerNum);
			userProfileDto.setFollowingNum(followingNum);

			return new ResponseEntity<UserProfileDto>(userProfileDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserProfileDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "프로필 보기 중간부분(피드 리스트)", response = ResponseEntity.class)
	@GetMapping("/feed/{userId}")
	public ResponseEntity<List<FeedDto>> showMiddle(@PathVariable("userId") int userId) {
		List<FeedDto> feedDtoList = userService.getFeedList(userId);
		return new ResponseEntity<List<FeedDto>>(feedDtoList, HttpStatus.OK);
	}

	@ApiOperation(value = "프로필 보기 아래 부분 - 피드별 poplist 출력 ", response = ResponseEntity.class)
	@PostMapping("/popList")
	public ResponseEntity<List<PopDto>> showPopInFeed(@RequestBody FeedUserIdDto feedUserIdDto) {
		int userId = feedUserIdDto.getUserId();
		List<PopDto> popInFeedDtoList;

		// feedId가 0이라는 것은 전체 피드라는 의미
		if (feedUserIdDto.getFeedId() == 0) {
			popInFeedDtoList = userService.getAllPopList(feedUserIdDto);

		} else { // 전체 피드 말고, 각각의 피드일때
			popInFeedDtoList = userService.getPopInFeedList(feedUserIdDto.getFeedId(), userId);
		}
		return new ResponseEntity<List<PopDto>>(popInFeedDtoList, HttpStatus.OK);

	}

	@ApiOperation(value = "프로필 수정 화면 들어올 시", response = ResponseEntity.class)
	@GetMapping("/edit/{userId}")
	public ResponseEntity<UserProfileDto> showEditProfile(@PathVariable("userId") int userId) {
		try {

			UserProfileDto userProfileDto = userService.getUserProfileById(userId);

			return new ResponseEntity<UserProfileDto>(userProfileDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserProfileDto>(HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "프로필 수정", response = ResponseEntity.class)
	@PutMapping
	public ResponseEntity<?> changeProfile(@RequestBody UserProfileDto userProfileDto) {
		try {
			UserProfileDto result = userService.getUserProfileById(userProfileDto.getId());
			result.setNickname(userProfileDto.getNickname());
			result.setContent(userProfileDto.getContent());
			result.setProfileImageUrl(userProfileDto.getProfileImageUrl());

			userService.updateProfile(result);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "친구 프로필 보기의 윗부분", response = ResponseEntity.class)
	@PostMapping("/friend")
	public ResponseEntity<HashMap<String, Object>> showFreindProfileHead(
			@RequestBody FollowForFollowDto followForFollowDto) {
		try {

			boolean isFollowForFollow = userService.getFollowForFollow(followForFollowDto.getFromUserId(),
					followForFollowDto.getToUserId());
			boolean isFollow = userService.isFollow(followForFollowDto);

			int popCount = popService.getPopCount(followForFollowDto.getToUserId());
			int[] temp = userService.countFollow(followForFollowDto.getToUserId());
			int followerNum = temp[0];
			int followingNum = temp[1];

			UserProfileDto userProfileDto = userService.getUserProfileById(followForFollowDto.getToUserId());

			userProfileDto.setPopCount(popCount);
			userProfileDto.setFollowerNum(followerNum);
			userProfileDto.setFollowingNum(followingNum);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("friendProfileDto", userProfileDto);
			map.put("isFollowForFollow", isFollowForFollow);
			map.put("isFollow", isFollow);

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<HashMap<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}
}
