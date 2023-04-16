package com.favshare.youtube.controller;

import java.util.List;


import com.favshare.youtube.dto.YoutubeEditPopRequest;
import com.favshare.youtube.dto.YoutubeInfoRequest;
import com.favshare.youtube.dto.YoutubeRequest;
import com.favshare.youtube.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube/edit")
public class YoutubeEditController {

	@Autowired
	private YoutubeService youtubeService;

	@Autowired
	private com.favshare.pop.service.PopService PopService;

	@ApiOperation(value = "유튜브 편집 화면 정보", response = List.class)
	@PostMapping("/info")
	public ResponseEntity<YoutubeInfoRequest> showYoutubeEdit(@RequestBody YoutubeRequest youtubeUserIdDto) {
		try {
			YoutubeInfoRequest youtubeEditDto = youtubeService.getEditInfoByUrl(youtubeUserIdDto);
			return new ResponseEntity<YoutubeInfoRequest>(youtubeEditDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<YoutubeInfoRequest>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 편집 정보 저장", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<?> saveYoutubeEdit(@RequestBody YoutubeEditPopRequest youtubeEditPopDto) {
		try {
			PopService.insertPop(youtubeEditPopDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
