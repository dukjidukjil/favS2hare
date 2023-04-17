package com.favshare.pop.controller;

import java.util.List;

import com.favshare.pop.dto.pop.FriendFeedDto;
import com.favshare.pop.dto.UserIdRequest;
import com.favshare.pop.dto.pop.DeletePopRequest;
import com.favshare.pop.dto.pop.GetPopListRequest;
import com.favshare.pop.dto.pop.PopInfoRequest;
import com.favshare.pop.service.LikePopService;
import com.favshare.pop.service.PopService;
import com.favshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favshare.idol.dto.IdolDto;
import com.favshare.pop.dto.pop.PopInfoDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pop")
public class PopController {
	private final PopService popService;
	private final UserService userService;
	private final LikePopService likePopService;

	@ApiOperation(value = "사용자에게 맞는 팝 리스트", response = List.class)
	@PostMapping
	public ResponseEntity showPopList(@RequestBody GetPopListRequest getPopListRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(popService.showPopList(getPopListRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}


	}

	@ApiOperation(value = "팝 시청시 조회수 증가", response = ResponseEntity.class)
	@PutMapping("/detail/{popId}")
	public ResponseEntity modifyPopView(@PathVariable("popId") int popId) {
		try {
			popService.updatePopView(popId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@ApiOperation(value = "팝 정보", response = PopInfoDto.class)
	@PostMapping("/info")
	public ResponseEntity showPopInfo(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(popService.showPopInfo(popInfoRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "팝 좋아요", response = ResponseEntity.class)
	@PostMapping("/like")
	public ResponseEntity likePop(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			likePopService.insertLikePop(popInfoRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "팝 좋아요 취소", response = ResponseEntity.class)
	@DeleteMapping("/like")
	public ResponseEntity dislikePop(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			likePopService.deleteLikePop(popInfoRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "유튜브 원본 영상에 만들어진 팝 리스트", response = PopInfoDto.class)
	@PostMapping("/youtube")
	public ResponseEntity showOrginYoutubePopInfo(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(popService.getPopInfoFromOriginYoutube(popInfoRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "팝 삭제 - 한개, 여러개 모두 삭제 가능", response = PopInfoDto.class)
	@DeleteMapping
	public ResponseEntity deletePop(@RequestBody DeletePopRequest deletePopRequest) {
		try {
			popService.deletePop(deletePopRequest);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "팝 탭 상단의 카테고리 리스트 - 아이돌1, 아이돌 2 등등", response = PopInfoDto.class)
	@GetMapping("/idolList")
	public ResponseEntity<List<IdolDto>> getInterestIdolList(@RequestBody UserIdRequest userIdRequest) {
		int userId = userIdRequest.getUserId();
		try {
			return ResponseEntity.status(HttpStatus.OK).body(popService.getInterestIdolList(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ApiOperation(value = "친구피드 탭을 클릭했을 때", response = PopInfoDto.class)
	@GetMapping("/friend")
	public ResponseEntity<List<FriendFeedDto>> showFriendList(@RequestBody UserIdRequest userIdRequest) {
		int userId = userIdRequest.getUserId();
		try {
			return ResponseEntity.status(HttpStatus.OK).body(popService.showFriendList(userId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}
}
