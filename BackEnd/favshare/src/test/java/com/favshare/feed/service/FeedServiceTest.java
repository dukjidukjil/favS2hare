package com.favshare.feed.service;

import com.favshare.feed.dto.CreateFeedRequest;
import com.favshare.feed.dto.ModifyFeedImageRequest;
import com.favshare.feed.dto.ModifyFeedNameRequest;
import com.favshare.feed.dto.ModifyFirstFeedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(false)
class FeedServiceTest {
    @Autowired
    FeedService feedService;

    @Test
    public void 피드_추가(){
        int userId = 1;
        String feedName = "새롭게 추가한 피드";
        feedService.insertFeed(new CreateFeedRequest(userId,feedName));
    }

    @Test
    public void 피드_삭제(){
        feedService.deleteFeed(1);
    }

    @Test
    public void 피드이름_변경(){
        int feedId = 2;
        String feedName = "변경할 피드 이름";
        feedService.updateFeedName(new ModifyFeedNameRequest(feedId,feedName));
    }

    @Test
    public void 피드이미지_변경(){
        int feedId = 2;
        String feedName = "변경할 피드 이미지";
        feedService.updateFeedImage(new ModifyFeedImageRequest(feedId,feedName));
    }

    @Test
    public void 대표피드_변경(){
        feedService.updateFirstFeed(new ModifyFirstFeedRequest(1,5));
    }

}