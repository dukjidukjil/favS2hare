package com.favshare.youtube.controller;

import java.util.List;


import com.favshare.youtube.dto.YoutubeBookmarkResponse;
import com.favshare.youtube.dto.YoutubeResponse;
import com.favshare.youtube.dto.YoutubeRequest;
import com.favshare.youtube.service.StoreYoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube/bookmark")
public class YoutubeBookmarkController {

	@Autowired
	private StoreYoutubeService storeYoutubeService;

	@ApiOperation(value = "유튜브 저장", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<?> storeYoutube(@RequestBody YoutubeRequest youtubeRequest) {
		try {
			storeYoutubeService.insertBookmark(youtubeRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 저장 목록 확인", response = YoutubeBookmarkResponse.class)
	@GetMapping("/{userId}")
	public ResponseEntity<List<YoutubeResponse>> showYoutubeBookmark(@PathVariable("userId") int userId) {
		try {
			List<YoutubeResponse> youtubeBookmarkList = storeYoutubeService.getYoutubeBookmarkById(userId);
			return new ResponseEntity<>(youtubeBookmarkList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 저장 삭제", response = ResponseEntity.class)
	@DeleteMapping
	public ResponseEntity<?> deleteYoutubeBookMark(@RequestBody YoutubeRequest youtubeRequest) {
		try {
			storeYoutubeService.deleteYoutubeBookMarkById(youtubeRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
