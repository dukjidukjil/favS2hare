package com.favshare.popInFeed.controller;

import com.favshare.popInFeed.dto.PopInFeedRequest;
import com.favshare.popInFeed.service.PopInFeedService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class PopInFeedController {
    private final PopInFeedService popInFeedService;

    @ApiOperation(value = "Feed 설정 화면 - 피드 내 pop 추가", response = ResponseEntity.class)
    @PostMapping("/pop")
    public ResponseEntity addPopInFeed(@RequestBody PopInFeedRequest popInFeedRequest) {

        try {
            popInFeedService.insertPopInFeed(popInFeedRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @ApiOperation(value = "Feed 설정 화면 - 피드 내 pop 삭제", response = ResponseEntity.class)
    @DeleteMapping("/pop")
    public ResponseEntity deletePopInFeed(@RequestBody PopInFeedRequest popInFeedRequest) {
        try {
            popInFeedService.deletePopInFeed(popInFeedRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
