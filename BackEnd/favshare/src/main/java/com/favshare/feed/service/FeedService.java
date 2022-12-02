package com.favshare.feed.service;

import com.favshare.feed.dto.ModifyFeedImageRequest;
import com.favshare.feed.dto.ModifyFeedRequest;
import com.favshare.feed.dto.ModifyFirstFeedRequest;
import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.global.exception.CustomException;
import com.favshare.user.entity.User;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.favshare.feed.exception.ErrorCode.FEED_NOT_FOUND;
import static com.favshare.feed.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedService {
	private final FeedRepository feedRepository;

	private final UserRepository userRepository;

	public void insertFeed(int userId) {
		Feed feedEntity;
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		if (feedRepository.countFeedByUserId(userId) == 0) {
			feedEntity = Feed.builder()
					.name("피드")
					.isFirst(true)
					.feedImageUrl(null)
					.user(user)
					.build();
		} else {
			feedEntity = Feed.builder()
					.name("피드")
					.isFirst(false)
					.feedImageUrl(null)
					.user(user)
					.build();
		}
		feedRepository.save(feedEntity);
	}

	public void deleteFeed(int feedId) {
		feedRepository.deleteById(feedId);
	}

	public void updateFeedName(ModifyFeedRequest modifyFeedRequest) {
		Feed feedEntity = feedRepository.findById(modifyFeedRequest.getId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		feedEntity.changeName(modifyFeedRequest.getName());
		// 변경감지로인해 save가 필요하지 않음
//		feedRepository.save(feedEntity);
	}

	public void updateFeedImage(ModifyFeedImageRequest modifyFeedImageRequest) {
		Feed feedEntity;
		feedEntity = feedRepository.findById(modifyFeedImageRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		feedEntity.changeImageUrl(modifyFeedImageRequest.getFeedImageUrl());
		// 변경감지로인해 save가 필요하지 않음
//		feedRepository.save(feedEntity);
	}

	public void updateFirstFeed(ModifyFirstFeedRequest modifyFirstFeedRequest) {
		Feed newFeedEntity, oldFeedEntity;
		newFeedEntity = feedRepository.findById(modifyFirstFeedRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		newFeedEntity.changeIsFirst();

		int oldFirstFeedId = feedRepository.findFirstId(modifyFirstFeedRequest.getUserId());
		oldFeedEntity = feedRepository.findById(oldFirstFeedId).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		oldFeedEntity.changeIsNotFirst();

		// 변경감지로인해 save가 필요하지 않음
//		feedRepository.save(newFeedEntity);
//		feedRepository.save(oldFeedEntity);
	}

}