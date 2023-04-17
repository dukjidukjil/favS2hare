package com.favshare.feed.controller;


import com.favshare.feed.dto.CreateFeedRequest;
import com.favshare.feed.dto.ModifyFeedImageRequest;
import com.favshare.feed.dto.ModifyFeedNameRequest;
import com.favshare.feed.dto.ModifyFirstFeedRequest;
import com.favshare.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
	private final FeedService feedService;

	@ApiOperation(value = "Feed 설정 화면 - 피드 추가하기", response = ResponseEntity.class)
	@PostMapping("/create")
	public ResponseEntity addNewFeed(@RequestBody CreateFeedRequest createFeedRequest) {
		try {
			feedService.insertFeed(createFeedRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드 삭제", response = ResponseEntity.class)
	@DeleteMapping("/{feedId}")
	public ResponseEntity deleteFeed(@PathVariable int feedId) {
		try {
			feedService.deleteFeed(feedId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드이름 수정")
	@PutMapping("/name")
	public ResponseEntity changeFeedName(@RequestBody ModifyFeedNameRequest modifyFeedNameRequest) {
		try {
			feedService.updateFeedName(modifyFeedNameRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드 이미지 변경")
	@PutMapping("/image")
	public ResponseEntity changeFeedImage(@RequestBody ModifyFeedImageRequest modifyFeedImageRequest) {
		try {
			feedService.updateFeedImage(modifyFeedImageRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 대표 피드 설정")
	@PutMapping("/star")
	public ResponseEntity<?> setFirstFeed(@RequestBody ModifyFirstFeedRequest modifyFirstFeedRequest) {
		try {
			feedService.updateFirstFeed(modifyFirstFeedRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
