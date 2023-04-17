package com.favshare.popInFeed.service;

import java.util.List;

import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.global.exception.CustomException;
import com.favshare.pop.entity.Pop;
import com.favshare.popInFeed.dto.PopInFeedRequest;
import com.favshare.popInFeed.entity.PopInFeedEntity;
import com.favshare.popInFeed.repository.PopInFeedRepository;
import com.favshare.pop.repository.PopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.favshare.global.exception.ErrorCode.FEED_NOT_FOUND;
import static com.favshare.global.exception.ErrorCode.POP_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional
public class PopInFeedService {

	private final PopInFeedRepository popInFeedRepository;

	private final FeedRepository feedRepository;

	private final PopRepository popRepository;

	@Transactional
	public void insertPopInFeed(PopInFeedRequest popInFeedRequest) {
		for (int i = 0; i < popInFeedRequest.getPopId().size(); i++) {
			Feed feedEntity = feedRepository.findById(popInFeedRequest.getFeedId()).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));
			Pop pop = popRepository.findById(popInFeedRequest.getPopId().get(i)).orElseThrow(() -> new CustomException(POP_NOT_FOUND));
			PopInFeedEntity popInFeedEntity = PopInFeedEntity.builder().feedEntity(feedEntity).pop(pop).build();
			popInFeedRepository.save(popInFeedEntity);
		}

	}

	@Transactional
	public void deletePopInFeed(PopInFeedRequest popInFeedRequest) {
		int feedId = popInFeedRequest.getFeedId();
		List<Integer> popIdList = popInFeedRequest.getPopId();
		for (int i = 0; i < popIdList.size(); i++) {
			popInFeedRepository.deleteByPopFeedId(feedId, popIdList.get(i));
		}
	}

}
