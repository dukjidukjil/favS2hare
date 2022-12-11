package com.favshare.feed.service;

import com.favshare.feed.dto.CreateFeedRequest;
import com.favshare.feed.dto.ModifyFeedImageRequest;
import com.favshare.feed.dto.ModifyFeedNameRequest;
import com.favshare.feed.dto.ModifyFirstFeedRequest;
import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.global.exception.CustomException;
import com.favshare.user.entity.User;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.favshare.global.exception.ErrorCode.FEED_NOT_FOUND;
import static com.favshare.global.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedService {
	private final FeedRepository feedRepository;
	private final UserRepository userRepository;

	@Transactional
	public void insertFeed(CreateFeedRequest createFeedRequest) {
		int userId = createFeedRequest.getUserId();
		Feed feed;
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
		feed = Feed.builder()
				.name(createFeedRequest.getFeedName())
				.isFirst(false)
				.feedImageUrl(null)
				.user(user)
				.build();

		if (feedRepository.countFeedByUserId(userId) == 0){
			feed.changeToFirst();
		}
		feedRepository.save(feed);
	}

	@Transactional
	public void deleteFeed(int feedId) {
		feedRepository.deleteById(feedId);
	}

	@Transactional
	public void updateFeedName(ModifyFeedNameRequest modifyFeedNameRequest) {
		Feed feedEntity = feedRepository.findById(modifyFeedNameRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		feedEntity.changeName(modifyFeedNameRequest.getFeedName());
	}

	@Transactional
	public void updateFeedImage(ModifyFeedImageRequest modifyFeedImageRequest) {
		Feed feedEntity;
		feedEntity = feedRepository.findById(modifyFeedImageRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		feedEntity.changeImageUrl(modifyFeedImageRequest.getFeedImageUrl());
	}

	@Transactional
	public void updateFirstFeed(ModifyFirstFeedRequest modifyFirstFeedRequest) {
		Feed newFeedEntity, oldFeedEntity;
		newFeedEntity = feedRepository.findById(modifyFirstFeedRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		newFeedEntity.changeToFirst();

		int oldFirstFeedId = feedRepository.findFirstId(modifyFirstFeedRequest.getUserId());
		oldFeedEntity = feedRepository.findById(oldFirstFeedId).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
		oldFeedEntity.changeNotToFirst();
	}

}
