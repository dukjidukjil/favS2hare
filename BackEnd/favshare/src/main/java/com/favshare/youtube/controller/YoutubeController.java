package com.favshare.youtube.controller;

import java.util.List;


import com.favshare.youtube.dto.YoutubeDetail;
import com.favshare.youtube.dto.YoutubeDetailRequest;
import com.favshare.youtube.dto.YoutubeRequest;
import com.favshare.youtube.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

	@Autowired
	private YoutubeService youtubeService;

	@ApiOperation(value = "사용자에게 맞는 유튜브 리스트", response = List.class)
	@GetMapping("/{userId}")
	public ResponseEntity<String> showYoutubeList(@PathVariable("userId") int userId) {
		try {
			String urlList;

			// 로그인하지 않은 유저의 userId는 0이다.
			if (userId == 0) {
				urlList = youtubeService.getAlgoUrlByNoId();
			} else {
				boolean hasInterestIdol = youtubeService.hasInterestIdol(userId);
				if (!hasInterestIdol) {
					urlList = youtubeService.getAlgoUrlByNoId();
				} else {
					urlList = youtubeService.getAlgoUrlByUserId(userId);
				}
			}

			return new ResponseEntity<String>(urlList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 관련 정보", response = YoutubeDetailRequest.class)
	@PostMapping("/detail")
	public ResponseEntity<YoutubeDetail> showYoutubeDetail(@RequestBody YoutubeRequest youtubeUserIdDto) {
		try {
			YoutubeDetail youtubeDetailDto = youtubeService.getDetailByUrl(youtubeUserIdDto);
			return new ResponseEntity<YoutubeDetail>(youtubeDetailDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<YoutubeDetail>(HttpStatus.BAD_REQUEST);
		}

	}

}
