package com.favshare.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favshare.dto.FeedDto;
import com.favshare.dto.IdolDto;
import com.favshare.dto.PopDto;
import com.favshare.dto.SongDto;
import com.favshare.dto.UserAccountDto;
import com.favshare.dto.input.EmailPasswordDto;
import com.favshare.dto.input.FeedUserIdDto;
import com.favshare.dto.input.FollowForFollowDto;
import com.favshare.dto.input.FriendFeedDto;
import com.favshare.dto.input.UserInfoDto;
import com.favshare.dto.input.UserProfileDto;
import com.favshare.dto.input.UserSignUpDto;
import com.favshare.entity.FeedEntity;
import com.favshare.entity.FollowEntity;
import com.favshare.entity.IdolEntity;
import com.favshare.entity.InterestIdolEntity;
import com.favshare.entity.InterestSongEntity;
import com.favshare.entity.PopEntity;
import com.favshare.entity.PopInFeedEntity;
import com.favshare.entity.SongEntity;
import com.favshare.entity.UserEntity;
import com.favshare.repository.FeedRepository;
import com.favshare.repository.FollowRepository;
import com.favshare.repository.InterestIdolRepository;
import com.favshare.repository.InterestSongRepository;
import com.favshare.repository.LikePopRepository;
import com.favshare.repository.PopRepository;
import com.favshare.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private LikePopRepository likepopRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InterestIdolRepository interestIdolRepository;

	@Autowired
	private InterestSongRepository interestSongRepository;

	public UserAccountDto getByEmail(String email) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(email);
		UserAccountDto result = new UserAccountDto(userEntity);
		return result;
	}

	public void insertUser(UserSignUpDto userSignUpDto) {
		UserEntity userEntity = UserEntity.builder().name(userSignUpDto.getName()).email(userSignUpDto.getEmail())
				.password(userSignUpDto.getPassword()).nickname(userSignUpDto.getNickname())
				.birthDate(userSignUpDto.getBirthDate()).phone(userSignUpDto.getPhone()).build();
		userRepository.save(userEntity);
	}

	public void updatePassword(EmailPasswordDto emailPasswordDto) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(emailPasswordDto.getEmail());
		userEntity.changePassword(emailPasswordDto.getPassword());
		userRepository.save(userEntity);
	}

	public UserProfileDto getUserProfileById(int id) {
		UserEntity userEntity;
		userEntity = userRepository.findById(id).get();
		UserProfileDto result = new UserProfileDto(userEntity);
		return result;
	}

	public void updateProfile(UserProfileDto userProfileDto) {
		UserEntity userEntity;
		userEntity = userRepository.findById(userProfileDto.getId()).get();
		userEntity.changeProfile(userProfileDto.getNickname(), userProfileDto.getContent(),
				userProfileDto.getProfileImageUrl());
		userRepository.save(userEntity);
	}

	public UserInfoDto getUserInfoById(int id) {
		UserEntity userEntity;
		userEntity = userRepository.findById(id).get();
		UserInfoDto result = new UserInfoDto(userEntity);
		return result;

	}

	public void updateUserInfo(UserInfoDto userInfoDto) {
		UserEntity userEntity;
		userEntity = userRepository.findById(userInfoDto.getId()).get();
		userEntity.changeUserInfo(userInfoDto.getName(), userInfoDto.getPassword(), userInfoDto.getPhone(),
				userInfoDto.getBirthDate());
		userRepository.save(userEntity);
	}

	public int[] countFollow(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		int followingNum = userEntity.getFromUserEntityList().size();
		int followerNum = userEntity.getToUserEntityList().size();
		return new int[] { followerNum, followingNum };
	}

	public List<FeedDto> getFeedList(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		List<FeedEntity> feedEntityList = userEntity.getFeedList();
		List<FeedDto> feedDtoList = Arrays.asList(modelMapper.map(feedEntityList, FeedDto[].class));
		return feedDtoList;
	}

	public void updateAuth(String email, String auth) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(email);
		userEntity.changeAuth(auth);
		userRepository.save(userEntity);
	}

	public List<PopDto> getAllPopList(FeedUserIdDto feedUserIdDto) {
		UserEntity userEntity = userRepository.getById(feedUserIdDto.getUserId());

		List<PopDto> result = Arrays.asList(modelMapper.map(userEntity.getPopList(), PopDto[].class));

		return result;
	}

	public List<PopDto> getPopInFeedList(int feedId, int userId) {
		FeedEntity feedEntity = feedRepository.findById(feedId).get();
		List<PopInFeedEntity> popInFeedEntityList = feedEntity.getPopInFeedList();

		List<PopDto> result = new ArrayList<>();
		for (int i = 0; i < popInFeedEntityList.size(); i++) {
			PopEntity popEntity = popInFeedEntityList.get(i).getPopEntity();
			boolean isLiked = isLiked(userId, popEntity.getId());
			result.add(new PopDto(popEntity, isLiked));
		}

		return result;
	}

	public boolean isLiked(int userId, int popId) {
		if (likepopRepository.isLiked(userId, popId) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getFollowForFollow(int fromUserId, int toUserId) {
		int countFromTo = followRepository.countFollowFByUserId(fromUserId, toUserId);
		int countToFrom = followRepository.countFollowFByUserId(toUserId, fromUserId);

		return (countFromTo == 1 && countToFrom == 1);

	}

	public String getUserAuthByEmail(String email) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(email);
		return userEntity.getAuth();
	}

	public void deleteByUserId(int userId) {
		userRepository.deleteById(userId);
		return;
	}

	public List<FriendFeedDto> getFollowingList(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		List<FollowEntity> followEntityList = userEntity.getFromUserEntityList();
		List<FriendFeedDto> result = new ArrayList<FriendFeedDto>();
		for (int i = 0; i < followEntityList.size(); i++) {

			UserEntity person = followEntityList.get(i).getToUserEntity();

			List<PopEntity> temp = followEntityList.get(i).getToUserEntity().getPopList();

			UserProfileDto userProfileDto = getUserProfileById(person.getId());

			List<PopDto> popDtoList = Arrays.asList(modelMapper.map(temp, PopDto[].class));
			if (popDtoList.size() == 0)
				continue;

			for (int j = 0; j < popDtoList.size(); j++) {
				result.add(new FriendFeedDto(userProfileDto, popDtoList.get(j)));
			}
		}
		return result;
	}

	public List<UserProfileDto> getFollowerList(int userId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		List<FollowEntity> followEntityList = userEntity.getToUserEntityList();
		List<UserEntity> followerList = new ArrayList<UserEntity>();
		for (int i = 0; i < followEntityList.size(); i++) {
			followerList.add(followEntityList.get(i).getFromUserEntity());
		}

		List<UserProfileDto> UserProfileDtoList = Arrays.asList(modelMapper.map(followerList, UserProfileDto[].class));

		return UserProfileDtoList;
	}

	public List<IdolDto> getInterestIdolList(int userId) {
		List<InterestIdolEntity> interestIdolList = interestIdolRepository.findAllByUserId(userId);
		List<IdolEntity> idolEntityList = new ArrayList<IdolEntity>();
		for (int i = 0; i < interestIdolList.size(); i++) {
			idolEntityList.add(interestIdolList.get(i).getIdolEntity());
		}
		List<IdolDto> idolDtoList = Arrays.asList(modelMapper.map(idolEntityList, IdolDto[].class));
		return idolDtoList;
	}

	public List<SongDto> getInterestSongList(int userId) {
		List<InterestSongEntity> interestSongList = interestSongRepository.findAllByUserId(userId);
		List<SongEntity> songEntityList = new ArrayList<SongEntity>();
		for (int i = 0; i < interestSongList.size(); i++) {
			songEntityList.add(interestSongList.get(i).getSongEntity());
		}
		List<SongDto> songDtoList = Arrays.asList(modelMapper.map(songEntityList, SongDto[].class));
		return songDtoList;
	}

	public boolean isExistUserByEmail(String email) {
		UserEntity userEntity;
		userEntity = userRepository.findByEmail(email);
		if (userEntity != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<UserProfileDto> userDtoListByKeyword(String keyword) {

		List<UserEntity> userEntityList = userRepository.findByKeywordContains(keyword);
		List<UserProfileDto> userDtoList = Arrays.asList(modelMapper.map(userEntityList, UserProfileDto[].class));
		return userDtoList;
	}

	public boolean isFollow(FollowForFollowDto followForFollowDto) {
		int fromUserId = followForFollowDto.getFromUserId();
		int toUserId = followForFollowDto.getToUserId();

		if (followRepository.countFollowFByUserId(fromUserId, toUserId) == 1)
			return true;
		else
			return false;
	}

}
