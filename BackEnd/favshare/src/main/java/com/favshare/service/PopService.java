package com.favshare.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.favshare.dto.PopAlgoDto;
import com.favshare.dto.IdolDto;
import com.favshare.dto.PopDto;
import com.favshare.dto.PopInfoDto;
import com.favshare.dto.input.IdolUserIdDto;
import com.favshare.dto.input.YoutubeEditPopDto;
import com.favshare.entity.FeedEntity;
import com.favshare.entity.IdolEntity;
import com.favshare.entity.InterestIdolEntity;
import com.favshare.entity.InterestSongEntity;
import com.favshare.entity.PopEntity;
import com.favshare.entity.PopInFeedEntity;
import com.favshare.entity.ShowPopEntity;
import com.favshare.entity.UserEntity;
import com.favshare.entity.YoutubeEntity;
import com.favshare.repository.FeedRepository;
import com.favshare.repository.IdolRepository;
import com.favshare.repository.InterestIdolRepository;
import com.favshare.repository.InterestSongRepository;
import com.favshare.repository.LikePopRepository;
import com.favshare.repository.PopInFeedRepository;
import com.favshare.repository.PopRepository;
import com.favshare.repository.ShowPopRepository;
import com.favshare.repository.UserRepository;
import com.favshare.repository.YoutubeRepository;

@Service
public class PopService {

	@Autowired
	private PopRepository popRepository;

	@Autowired
	private YoutubeRepository youtubeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PopInFeedRepository popInFeedRepository;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private IdolRepository idolRepository;

	@Autowired
	private ShowPopRepository showPopRepository;

	@Autowired
	private InterestIdolRepository interestIdolRepository;

	@Autowired
	private InterestSongRepository interestSongRepository;

	@Autowired
	private LikePopRepository likePopRepository;

	@Autowired
	private ModelMapper modelMapper;

	public void updatePopView(int popId) {
		PopEntity popEntity = popRepository.findById(popId).get();
		popEntity.changeView();
		popRepository.save(popEntity);
	}

	public PopInfoDto getPopInfoById(int popId, int userId) {
		PopEntity popEntity = popRepository.findById(popId).get();
		boolean isLiked;

		if (userId == 0)
			isLiked = false;
		else
			isLiked = isLiked(userId, popId);

		PopInfoDto popInfoDto = new PopInfoDto(popEntity, popEntity.getYoutubeEntity(), isLiked);

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
		PopEntity popEntity = popRepository.findById(popId).get();

		boolean isLiked = isLiked(userId, popId);
		PopDto popDto = new PopDto(popEntity, isLiked);
		return popDto;
	}

	// ????????? ?????? ??? ?????? ??????
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
		UserEntity userEntity = userRepository.findById(youtubeEditPopDto.getUserId()).get();
		YoutubeEntity youtubeEntity = youtubeRepository.findByUrl(youtubeEditPopDto.getYoutubeUrl());

		// youtube??? ??????????????? ???????????? ?????? ??? ????????????????????? ????????????
		if (youtubeEntity == null) {
			youtubeEntity = YoutubeEntity.builder().url(youtubeEditPopDto.getYoutubeUrl()).build();
			youtubeRepository.save(youtubeEntity);
		}

		

		// ????????? ????????????
		PopEntity popEntity = PopEntity.builder().name(youtubeEditPopDto.getName())
				.startSecond(youtubeEditPopDto.getStartSecond()).endSecond(youtubeEditPopDto.getEndSecond())
				.content(youtubeEditPopDto.getContent()).createDate(LocalDateTime.now())
				.views(youtubeEditPopDto.getViews()).userEntity(userEntity).youtubeEntity(youtubeEntity).build();
		popRepository.save(popEntity);

		// popinfeed?????? ????????????
		// ?????? user??? ????????? feed??? ?????? ????????? feedId ?????? 0?????? ????????????, ?????? ???????????? ?????? ??? ????????? ??????
		if (youtubeEditPopDto.getFeedId() >= 1) {
			FeedEntity feedEntity = feedRepository.findById(youtubeEditPopDto.getFeedId()).get();

			PopInFeedEntity popInFeedEntity = PopInFeedEntity.builder().popEntity(popEntity).feedEntity(feedEntity).build();
			popInFeedRepository.save(popInFeedEntity);
		}

	}

	public int getPopCount(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		return userEntity.getPopList().size();
	}

	public void deletePop(List<Integer> popIdList) {
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
		// ?????????, ????????????, ????????? ??? ??? ?????? ???????????? ?????? (5 : 3 : 2 ??? ????????? ??????)

		List<PopEntity> popEntityList = popRepository.findAll();
		List<PopAlgoDto> algoList = new ArrayList<PopAlgoDto>();
		double referenceValue; // maxValues??? ?????????
		int[] value = new int[3]; // ???????????? ?????????, ????????????, ????????????
		int[] maxValue = new int[3]; //

		// ?????????, ????????????, ??????????????? ???????????? ????????? / log?????? ?????? algoList??? ??????
		for (int i = 0; i < popEntityList.size(); i++) {
			// ?????? ????????? pop??? ?????? ???????????? ???????????? ?????? ?????????.
			if (isWatched(userId, popEntityList.get(i).getId()))
				continue;

			value[0] = (int) (Math.log10(popEntityList.get(i).getViews()) * 100);
			value[1] = (int) (Math.log10(popEntityList.get(i).getLikePopList().size()) * 100);
			value[2] = (int) (Math.log10(popEntityList.get(i).getUserEntity().getToUserEntityList().size()) * 100);

			maxValue[0] = Math.max(maxValue[0], value[0]);
			maxValue[1] = Math.max(maxValue[1], value[1]);
			maxValue[2] = Math.max(maxValue[2], value[2]);
			algoList.add(new PopAlgoDto(popEntityList.get(i).getId(), value[0], value[1], value[2], 0));
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
		List<PopEntity> popEntityList = popRepository.findAll();
		List<PopEntity> randomPopList = new ArrayList<PopEntity>();

		int[] randomList = new int[popEntityList.size()];
		Random r = new Random();

		// ?????? pop??? ?????? ????????? ???????????? ?????? ??????.
		for (int i = 0; i < popEntityList.size(); i++) {
			randomList[i] = r.nextInt(popEntityList.size());
			for (int j = 0; j < i; j++) {
				if (randomList[i] == randomList[j])
					i--;
			}
		}

		for (int i = 0; i < randomList.length; i++) {
			randomPopList.add(popEntityList.get(randomList[i]));
		}

		List<PopDto> result = Arrays.asList(modelMapper.map(randomPopList, PopDto[].class));

		return result;
	}

	public List<PopAlgoDto> getCategoryPopList(IdolUserIdDto idolUserIdDto) {
		double referenceValue; // maxValues??? ?????????
		int[] value = new int[3]; // ???????????? ?????????, ????????????, ????????????
		int[] maxValue = new int[3]; //
		IdolEntity idol = idolRepository.findById(idolUserIdDto.getIdolId()).get();
		String keyword = idol.getName();
		List<PopEntity> popEntityList = popRepository.findByKeywordContains(keyword);

		List<PopAlgoDto> algoList = new ArrayList<PopAlgoDto>();

		// ?????????, ????????????, ??????????????? ???????????? ????????? / log?????? ?????? algoList??? ??????
		for (int i = 0; i < popEntityList.size(); i++) {
			// ?????? ????????? pop??? ?????? ???????????? ???????????? ?????? ?????????.
			if (isWatched(idolUserIdDto.getUserId(), popEntityList.get(i).getId()))
				continue;

			value[0] = (int) (Math.log10(popEntityList.get(i).getViews()) * 100);
			value[1] = (int) (Math.log10(popEntityList.get(i).getLikePopList().size()) * 100);
			value[2] = (int) (Math.log10(popEntityList.get(i).getUserEntity().getToUserEntityList().size()) * 100);

			maxValue[0] = Math.max(maxValue[0], value[0]);
			maxValue[1] = Math.max(maxValue[1], value[1]);
			maxValue[2] = Math.max(maxValue[2], value[2]);
			algoList.add(new PopAlgoDto(popEntityList.get(i).getId(), value[0], value[1], value[2], 0));
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
			result.add(interestIdolEntityList.get(i).getUserEntity().getId());
		}
		return result;
	}

	public List<Integer> findSimilarSongInterst(int userId, int songId) {
		List<InterestSongEntity> interestSongEntityList = interestSongRepository.findBySongIdExceptUserId(userId,
				songId);

		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < interestSongEntityList.size(); i++) {
			result.add(interestSongEntityList.get(i).getUserEntity().getId());
		}
		return result;
	}

	public List<PopDto> popDtoListByUserId(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		List<PopEntity> popEntity = userEntity.getPopList();

		List<PopDto> popDtoList = Arrays.asList(modelMapper.map(popEntity, PopDto[].class));
		return popDtoList;
	}

	public List<PopDto> popDtoListByKeyword(String keyword, int userId) {

		List<PopEntity> popEntityList = popRepository.findByKeywordContains(keyword);

		List<PopDto> popDtoList = new ArrayList<PopDto>();
		for (int i = 0; i < popEntityList.size(); i++) {
			boolean isLiked = isLiked(userId, popEntityList.get(i).getId());
			popDtoList.add(new PopDto(popEntityList.get(i), isLiked));
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
		PopEntity popEntity = popRepository.getById(popId);
		UserEntity userEntity = userRepository.getById(userId);

		ShowPopEntity showPopEntity = ShowPopEntity.builder().popEntity(popEntity).userEntity(userEntity).build();
		showPopRepository.save(showPopEntity);

	}

}
