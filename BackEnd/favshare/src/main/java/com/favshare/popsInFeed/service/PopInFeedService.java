package com.favshare.popsInFeed.service;

import java.util.List;

import com.favshare._temp.dto.input.FeedPopIdDto;
import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.pops.entity.PopEntity;
import com.favshare.popsInFeed.entity.PopInFeedEntity;
import com.favshare.popsInFeed.repository.PopInFeedRepository;
import com.favshare.pops.repository.PopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
			PopEntity popEntity = popRepository.findById(feedPopIdDto.getPopId().get(i)).get();
			PopInFeedEntity popInFeedEntity = PopInFeedEntity.builder().feedEntity(feedEntity).popEntity(popEntity).build();
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
