package com.favshare.feed.controller;

import com.favshare._temp.dto.input.FeedPopIdDto;
import com.favshare._temp.dto.input.FeedUserIdDto;
import com.favshare._temp.dto.input.IdFeedImageUrlDto;
import com.favshare._temp.dto.input.IdNameDto;
import com.favshare._temp.service.FeedService;
import com.favshare._temp.service.PopInFeedService;
import com.favshare.feed.dto.ModifyFeedImageRequest;
import com.favshare.feed.dto.ModifyFeedRequest;
import com.favshare.feed.dto.ModifyFirstFeedRequest;
import com.favshare.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	@PostMapping("/{userId}")
	public ResponseEntity addNewFeed(@PathVariable int userId) {
		try {
			feedService.insertFeed(userId);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseDto(HttpStatus.OK.value(),"피드 추가 완료","")
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseDto(HttpStatus.BAD_REQUEST.value(),"피드 추가 실패","")
			);
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드 삭제", response = ResponseEntity.class)
	@DeleteMapping("/{feedId}")
	public ResponseEntity deleteFeed(@PathVariable int feedId) {
		try {
			feedService.deleteFeed(feedId);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseDto(HttpStatus.OK.value(),"피드 삭제 완료","")
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseDto(HttpStatus.BAD_REQUEST.value(),"피드 삭제 실패","")
			);
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드이름 수정")
	@PutMapping("/name")
	public ResponseEntity changeFeedName(@RequestBody ModifyFeedRequest modifyFeedRequest) {
		try {
			feedService.updateFeedName(modifyFeedRequest);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseDto(HttpStatus.OK.value(),"피드이름 수정 완료","")
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseDto(HttpStatus.BAD_REQUEST.value(),"피드이름 수정 실패","")
			);
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 피드 이미지 변경")
	@PutMapping("/image")
	public ResponseEntity changeFeedImage(@RequestBody ModifyFeedImageRequest modifyFeedImageRequest) {
		try {
			feedService.updateFeedImage(modifyFeedImageRequest);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseDto(HttpStatus.OK.value(),"피드이미지 수정 완료","")
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseDto(HttpStatus.BAD_REQUEST.value(),"피드이미지 수정 실패","")
			);
		}
	}

	@ApiOperation(value = "Feed 설정 화면 - 대표 피드 설정")
	@PutMapping("/star")
	public ResponseEntity<?> setFirstFeed(@RequestBody ModifyFirstFeedRequest modifyFirstFeedRequest) {
		try {
			feedService.updateFirstFeed(modifyFirstFeedRequest);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseDto(HttpStatus.OK.value(),"대표피드 수정 완료","")
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseDto(HttpStatus.BAD_REQUEST.value(),"대표피드 수정 실패","")
			);
		}
	}


/*
 * popInFeed로 이동
 */
//	@ApiOperation(value = "Feed 설정 화면 - 피드 내 pop 추가", response = ResponseEntity.class)
//	@PostMapping("/pop")
//	public ResponseEntity<?> addPopInFeed(@RequestBody FeedPopIdDto feedPopIdDto) {
//		try {
//			popInFeedService.insertPopInFeed(feedPopIdDto);
//			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//		}
//	}
//
//	@ApiOperation(value = "Feed 설정 화면 - 피드 내 pop 삭제", response = ResponseEntity.class)
//	@DeleteMapping("/pop")
//	public ResponseEntity<?> deletePopInFeed(@RequestBody FeedPopIdDto feedPopIdDto) {
//		try {
//			popInFeedService.deletePopInFeed(feedPopIdDto);
//			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
//

}
