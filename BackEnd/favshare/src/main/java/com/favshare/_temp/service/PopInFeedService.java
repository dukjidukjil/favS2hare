package com.favshare._temp.service;

import java.util.List;

import com.favshare._temp.dto.input.FeedPopIdDto;
import com.favshare._temp.entity.FeedEntity;
import com.favshare._temp.entity.PopEntity;
import com.favshare._temp.entity.PopInFeedEntity;
import com.favshare._temp.repository.FeedRepository;
import com.favshare._temp.repository.PopInFeedRepository;
import com.favshare._temp.repository.PopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopInFeedService {

	@Autowired
	private PopInFeedRepository popInFeedRepository;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private PopRepository popRepository;

	public void insertPopInFeed(FeedPopIdDto feedPopIdDto) {
		 

		for (int i = 0; i < feedPopIdDto.getPopId().size(); i++) {
			FeedEntity feedEntity = feedRepository.findById(feedPopIdDto.getFeedId()).get();
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
