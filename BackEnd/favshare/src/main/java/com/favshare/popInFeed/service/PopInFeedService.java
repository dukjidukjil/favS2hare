package com.favshare.popInFeed.service;

import java.util.List;

import com.favshare._temp.dto.input.FeedPopIdDto;
import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.pop.entity.Pop;
import com.favshare.popInFeed.entity.PopInFeedEntity;
import com.favshare.popInFeed.repository.PopInFeedRepository;
import com.favshare.pop.repository.PopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopInFeedService {

	private final PopInFeedRepository popInFeedRepository;

	private final FeedRepository feedRepository;

	private final PopRepository popRepository;

	public void insertPopInFeed(FeedPopIdDto feedPopIdDto) {
		 

		for (int i = 0; i < feedPopIdDto.getPopId().size(); i++) {
			Feed feedEntity = feedRepository.findById(feedPopIdDto.getFeedId()).get();
			Pop pop = popRepository.findById(feedPopIdDto.getPopId().get(i)).get();
			PopInFeedEntity popInFeedEntity = PopInFeedEntity.builder().feedEntity(feedEntity).pop(pop).build();
			popInFeedRepository.save(popInFeedEntity);
		}

	}

	public void deletePopInFeed(FeedPopIdDto feedPopIdDto) {
		int feedId = feedPopIdDto.getFeedId();
		List<Integer> popIdList = feedPopIdDto.getPopId();
		for (int i = 0; i < popIdList.size(); i++) {
			popInFeedRepository.deleteByPopFeedId(feedId, popIdList.get(i));
		}
	}

}
