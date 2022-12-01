package com.favshare.pop.service;

import java.time.LocalDateTime;
import java.util.*;

import com.favshare._temp.dto.IdolDto;
import com.favshare._temp.dto.PopAlgoDto;
import com.favshare._temp.dto.PopDto;
import com.favshare._temp.dto.PopInfoDto;
import com.favshare._temp.dto.input.IdolUserIdDto;
import com.favshare._temp.dto.input.UserProfileDto;
import com.favshare._temp.dto.input.YoutubeEditPopDto;
import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import com.favshare.pop.dto.pop.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.entity.InterestIdolEntity;
import com.favshare.pop.entity.Pop;
import com.favshare.popInFeed.entity.PopInFeedEntity;
import com.favshare.pop.entity.ShowPop;
import com.favshare.user.entity.User;
import com.favshare.youtube.entity.YoutubeEntity;
import com.favshare.idol.repository.IdolRepository;
import com.favshare.idol.repository.InterestIdolRepository;
import com.favshare.pop.repository.LikePopRepository;
import com.favshare.popInFeed.repository.PopInFeedRepository;
import com.favshare.pop.repository.PopRepository;
import com.favshare.pop.repository.ShowPopRepository;
import com.favshare.user.repository.UserRepository;
import com.favshare.youtube.repository.YoutubeRepository;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class PopService {

	private final PopRepository popRepository;

	private final YoutubeRepository youtubeRepository;

	private final UserRepository userRepository;

	private final PopInFeedRepository popInFeedRepository;

	private final FeedRepository feedRepository;

	private final IdolRepository idolRepository;

	private final ShowPopRepository showPopRepository;

	private final InterestIdolRepository interestIdolRepository;

	private final LikePopRepository likePopRepository;

	public void updatePopView(int popId) {
		Pop pop = popRepository.findById(popId).get();
		pop.changeView();
		popRepository.save(pop);
	}

	public PopInfoResponse showPopInfo(PopInfoRequest popInfoRequest){
		int popId = popInfoRequest.getPopId();
		int userId = popInfoRequest.getUserId();

		PopInfoDto popInfoDto = getPopInfoById(popId, userId);
		User user = userRepository.findById(userId).get();
		UserProfileDto userProfileDto = new UserProfileDto(user);

		this.insertShowPop(popId, userId);
		return PopInfoResponse.builder().popInfoDto(popInfoDto).userProfileDto(userProfileDto).build();
	}

	public PopInfoFromOriginYoutubeResponse getPopInfoFromOriginYoutube(@RequestBody PopInfoRequest popInfoRequest){
		int userId = popInfoRequest.getUserId();
		int popId = popInfoRequest.getPopId();

		PopInfoDto popInfoDto = getPopInfoById(popId, userId);
		List<PopDto> popList = getPopListById(userId, popId, popInfoDto.getYoutubeId());
		int popCnt = popList.size();
		return PopInfoFromOriginYoutubeResponse.builder().popCnt(popCnt).popList(popList).build();

	}

	public PopInfoDto getPopInfoById(int popId, int userId) {
		Pop pop = popRepository.findById(popId).get();
		boolean isLiked;

		if (userId == 0)
			isLiked = false;
		else
			isLiked = isLiked(userId, popId);

		PopInfoDto popInfoDto = new PopInfoDto(pop, pop.getYoutubeEntity(), isLiked);

		return popInfoDto;
	}

	public boolean isLiked(int userId, int popId) {
		if (likePopRepository.isLiked(userId, popId) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public PopDto getPopDtoById(int userId, int popId) {
		Pop pop = popRepository.findById(popId).get();

		boolean isLiked = isLiked(userId, popId);
		PopDto popDto = new PopDto(pop, isLiked);
		return popDto;
	}

	// 유튜브 찾은 후 그에 맞는
	public List<PopDto> getPopListById(int userId, int popId, int youtubeId) {
		YoutubeEntity youtubeEntity = youtubeRepository.findById(youtubeId).get();
		List<PopDto> popList = new ArrayList<>();
		for (int i = 0; i < youtubeEntity.getPopList().size(); i++) {
			boolean isLiked = isLiked(userId, popId);
			popList.add(new PopDto(youtubeEntity.getPopList().get(i), isLiked));
		}
		return popList;
	}

	public void insertPop(YoutubeEditPopDto youtubeEditPopDto) {
		User user = userRepository.findById(youtubeEditPopDto.getUserId()).get();
		YoutubeEntity youtubeEntity = youtubeRepository.findByUrl(youtubeEditPopDto.getYoutubeUrl());

		// youtube가 아직데이터 베이스에 없을 시 데이터베이스에 넣어주기
		if (youtubeEntity == null) {
			youtubeEntity = YoutubeEntity.builder().url(youtubeEditPopDto.getYoutubeUrl()).build();
			youtubeRepository.save(youtubeEntity);
		}

		

		// 팝에도 넣어주고
		Pop pop = Pop.builder().name(youtubeEditPopDto.getName())
				.startSecond(youtubeEditPopDto.getStartSecond()).endSecond(youtubeEditPopDto.getEndSecond())
				.content(youtubeEditPopDto.getContent()).createDate(LocalDateTime.now())
				.views(youtubeEditPopDto.getViews()).user(user).youtubeEntity(youtubeEntity).build();
		popRepository.save(pop);

		// popinfeed에도 넣어준다
		// 해당 user가 보유한 feed가 없을 때에는 feedId 값을 0으로 설정해서, 전체 피드에만 속할 수 있도록 수정
		if (youtubeEditPopDto.getFeedId() >= 1) {
			Feed feedEntity = feedRepository.findById(youtubeEditPopDto.getFeedId()).get();

			PopInFeedEntity popInFeedEntity = PopInFeedEntity.builder().pop(pop).feedEntity(feedEntity).build();
			popInFeedRepository.save(popInFeedEntity);
		}

	}

	public int getPopCount(int userId) {
		User user = userRepository.findById(userId).get();
		return user.getPopList().size();
	}

	public void deletePop(DeletePopRequest deletePopRequest) {
		List<Integer> popIdList = deletePopRequest.getPopIdList();
		for (int i = 0; i < popIdList.size(); i++) {
			popRepository.deleteById(popIdList.get(i));
		}
	}

	public boolean isWatched(int userId, int popId) {
		if (showPopRepository.searchByUserPopId(userId, popId) >= 1) {
			return true;
		} else
			return false;
	}

	public List<PopAlgoDto> getCustomPopList(int userId) {
		// 조회수, 좋아요수, 팔로워 수 를 통해 알고리즘 구현 (5 : 3 : 2 의 가중치 부여)

		List<Pop> popList = popRepository.findAll();
		List<PopAlgoDto> algoList = new ArrayList<PopAlgoDto>();
		double referenceValue; // maxValues의 중간값
		int[] value = new int[3]; // 순서대로 조회수, 좋아요수, 팔로워수
		int[] maxValue = new int[3]; //

		// 조회수, 좋아요수, 팔로워수의 최댓값을 구하고 / log취한 값을 algoList에 저장
		for (int i = 0; i < popList.size(); i++) {
			// 이미 시청한 pop의 경우 알고리즘 리스트에 넣지 않는다.
			if (isWatched(userId, popList.get(i).getId()))
				continue;

			value[0] = (int) (Math.log10(popList.get(i).getViews()) * 100);
			value[1] = (int) (Math.log10(popList.get(i).getLikePopList().size()) * 100);
			value[2] = (int) (Math.log10(popList.get(i).getUser().getToUserEntityList().size()) * 100);

			maxValue[0] = Math.max(maxValue[0], value[0]);
			maxValue[1] = Math.max(maxValue[1], value[1]);
			maxValue[2] = Math.max(maxValue[2], value[2]);
			algoList.add(new PopAlgoDto(popList.get(i).getId(), value[0], value[1], value[2], 0));
		}

		Arrays.sort(maxValue);
		referenceValue = maxValue[1];

		for (int i = 0; i < algoList.size(); i++) {
			value[0] = (int) (algoList.get(i).getViews() * (referenceValue / algoList.get(i).getViews()));
			value[1] = (int) (algoList.get(i).getLikeCount() * (referenceValue / algoList.get(i).getLikeCount()));
			value[2] = (int) (algoList.get(i).getFollowers() * (referenceValue / algoList.get(i).getFollowers()));

			int score = (value[0] * 5) + (value[1] * 3) + (value[2] * 2);
			algoList.get(i).setAlgoScore(score);
		}

		Collections.sort(algoList, new Comparator<PopAlgoDto>() {

			@Override
			public int compare(PopAlgoDto o1, PopAlgoDto o2) {
				return o2.getAlgoScore() - o1.getAlgoScore();
			}

		});

		return algoList;

	}

	public List<PopDto> getRandomPopList() {
		List<Pop> popList = popRepository.findAll();
		List<Pop> randomPopList = new ArrayList<Pop>();

		int[] randomList = new int[popList.size()];
		Random r = new Random();

		// 전체 pop의 개수 안에서 랜덤으로 순서 정함.
		for (int i = 0; i < popList.size(); i++) {
			randomList[i] = r.nextInt(popList.size());
			for (int j = 0; j < i; j++) {
				if (randomList[i] == randomList[j])
					i--;
			}
		}

		for (int i = 0; i < randomList.length; i++) {
			randomPopList.add(popList.get(randomList[i]));
		}

		List<PopDto> result = null;//Arrays.asList(modelMapper.map(randomPopList, PopDto[].class));

		return result;
	}

	public List<PopAlgoDto> getCategoryPopList(GetPopListRequest getPopListRequest) {
		double referenceValue; // maxValues의 중간값
		int[] value = new int[3]; // 순서대로 조회수, 좋아요수, 팔로워수
		int[] maxValue = new int[3]; //
		IdolEntity idol = idolRepository.findById(getPopListRequest.getIdolId()).get();
		String keyword = idol.getName();
		List<Pop> popList = popRepository.findByKeywordContains(keyword);

		List<PopAlgoDto> algoList = new ArrayList<PopAlgoDto>();

		// 조회수, 좋아요수, 팔로워수의 최댓값을 구하고 / log취한 값을 algoList에 저장
		for (int i = 0; i < popList.size(); i++) {
			// 이미 시청한 pop의 경우 알고리즘 리스트에 넣지 않는다.
			if (isWatched(getPopListRequest.getUserId(), popList.get(i).getId()))
				continue;

			value[0] = (int) (Math.log10(popList.get(i).getViews()) * 100);
			value[1] = (int) (Math.log10(popList.get(i).getLikePopList().size()) * 100);
			value[2] = (int) (Math.log10(popList.get(i).getUser().getToUserEntityList().size()) * 100);

			maxValue[0] = Math.max(maxValue[0], value[0]);
			maxValue[1] = Math.max(maxValue[1], value[1]);
			maxValue[2] = Math.max(maxValue[2], value[2]);
			algoList.add(new PopAlgoDto(popList.get(i).getId(), value[0], value[1], value[2], 0));
		}

		Arrays.sort(maxValue);
		referenceValue = maxValue[1];

		for (int i = 0; i < algoList.size(); i++) {
			value[0] = (int) (algoList.get(i).getViews() * (referenceValue / algoList.get(i).getViews()));
			value[1] = (int) (algoList.get(i).getLikeCount() * (referenceValue / algoList.get(i).getLikeCount()));
			value[2] = (int) (algoList.get(i).getFollowers() * (referenceValue / algoList.get(i).getFollowers()));

			int score = (value[0] * 5) + (value[1] * 3) + (value[2] * 2);
			algoList.get(i).setAlgoScore(score);
		}

		Collections.sort(algoList, new Comparator<PopAlgoDto>() {

			@Override
			public int compare(PopAlgoDto o1, PopAlgoDto o2) {
				return o2.getAlgoScore() - o1.getAlgoScore();
			}

		});

		return algoList;
	}

	public List<Integer> findSimilarIdolInterst(int userId, int idolId) {
		List<InterestIdolEntity> interestIdolEntityList = interestIdolRepository.findByIdolIdExceptUserId(userId,
				idolId);

		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < interestIdolEntityList.size(); i++) {
			result.add(interestIdolEntityList.get(i).getUser().getId());
		}
		return result;
	}

//	public List<Integer> findSimilarSongInterst(int userId, int songId) {
//		List<InterestSongEntity> interestSongEntityList = interestSongRepository.findBySongIdExceptUserId(userId,
//				songId);
//
//		List<Integer> result = new ArrayList<Integer>();
//		for (int i = 0; i < interestSongEntityList.size(); i++) {
//			result.add(interestSongEntityList.get(i).getUser().getId());
//		}
//		return result;
//	}

	public List<PopDto> popDtoListByUserId(int userId) {
		User user = userRepository.findById(userId).get();
		List<Pop> pop = user.getPopList();

		List<PopDto> popDtoList = null;//Arrays.asList(modelMapper.map(popEntity, PopDto[].class));
		return popDtoList;
	}

	public List<PopDto> popDtoListByKeyword(String keyword, int userId) {

		List<Pop> popList = popRepository.findByKeywordContains(keyword);

		List<PopDto> popDtoList = new ArrayList<PopDto>();
		for (int i = 0; i < popList.size(); i++) {
			boolean isLiked = isLiked(userId, popList.get(i).getId());
			popDtoList.add(new PopDto(popList.get(i), isLiked));
		}
		return popDtoList;
	}

	public List<IdolDto> getInterestIdolList(int userId) {
		List<InterestIdolEntity> idolList = interestIdolRepository.findAllByUserId(userId);
		List<IdolDto> result = new ArrayList<IdolDto>();

		for (int i = 0; i < idolList.size(); i++) {
			IdolEntity idolEntity = idolRepository.findById(idolList.get(i).getIdolEntity().getId()).get();
			result.add(new IdolDto(idolEntity));
		}

		return result;
	}

	public void insertShowPop(int popId, int userId) {
		Pop pop = popRepository.getById(popId);
		User user = userRepository.getById(userId);

		ShowPop showPop = ShowPop.builder().pop(pop).user(user).build();
		showPopRepository.save(showPop);

	}

}
