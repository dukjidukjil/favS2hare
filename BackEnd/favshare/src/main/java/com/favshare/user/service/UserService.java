package com.favshare.user.service;

import java.util.ArrayList;

import java.util.List;

import com.favshare.feed.entity.Feed;
import com.favshare.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.favshare._temp.dto.FeedDto;
import com.favshare._temp.dto.IdolDto;
import com.favshare._temp.dto.PopDto;
import com.favshare._temp.dto.UserAccountDto;
import com.favshare._temp.dto.input.EmailPasswordDto;
import com.favshare._temp.dto.input.FeedUserIdDto;
import com.favshare._temp.dto.input.FollowForFollowDto;
import com.favshare._temp.dto.input.FriendFeedDto;
import com.favshare._temp.dto.input.UserInfoDto;
import com.favshare._temp.dto.input.UserProfileDto;
import com.favshare._temp.dto.input.UserSignUpDto;
import com.favshare.follow.entity.FollowEntity;
import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.entity.InterestIdolEntity;
import com.favshare.pop.entity.Pop;
import com.favshare.popInFeed.entity.PopInFeedEntity;
import com.favshare.user.entity.User;
import com.favshare.follow.repository.FollowRepository;
import com.favshare.idol.repository.InterestIdolRepository;
import com.favshare.pop.repository.LikePopRepository;
import com.favshare.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final  UserRepository userRepository;


	private final FeedRepository feedRepository;


	private final  FollowRepository followRepository;


	private final LikePopRepository likepopRepository;


	private final InterestIdolRepository interestIdolRepository;


	public UserAccountDto getByEmail(String email) {
		User user;
		user = userRepository.findByEmail(email);
		UserAccountDto result = new UserAccountDto(user);
		return result;
	}

	public void insertUser(UserSignUpDto userSignUpDto) {
		User user = User.builder().name(userSignUpDto.getName()).email(userSignUpDto.getEmail())
				.password(userSignUpDto.getPassword()).nickname(userSignUpDto.getNickname())
				.birthDate(userSignUpDto.getBirthDate()).phone(userSignUpDto.getPhone()).build();
		userRepository.save(user);
	}

	public void updatePassword(EmailPasswordDto emailPasswordDto) {
		User user;
		user = userRepository.findByEmail(emailPasswordDto.getEmail());
		user.changePassword(emailPasswordDto.getPassword());
		userRepository.save(user);
	}

	public UserProfileDto getUserProfileById(int id) {
		User user;
		user = userRepository.findById(id).get();
		UserProfileDto result = new UserProfileDto(user);
		return result;
	}

	public void updateProfile(UserProfileDto userProfileDto) {
		User user;
		user = userRepository.findById(userProfileDto.getId()).get();
		user.changeProfile(userProfileDto.getNickname(), userProfileDto.getContent(),
				userProfileDto.getProfileImageUrl());
		userRepository.save(user);
	}

	public UserInfoDto getUserInfoById(int id) {
		User user;
		user = userRepository.findById(id).get();
		UserInfoDto result = new UserInfoDto(user);
		return result;

	}

	public void updateUserInfo(UserInfoDto userInfoDto) {
		User user;
		user = userRepository.findById(userInfoDto.getId()).get();
		user.changeUserInfo(userInfoDto.getName(), userInfoDto.getPassword(), userInfoDto.getPhone(),
				userInfoDto.getBirthDate());
		userRepository.save(user);
	}

	public int[] countFollow(int userId) {
		User user = userRepository.findById(userId).get();
		int followingNum = user.getFromUserEntityList().size();
		int followerNum = user.getToUserEntityList().size();
		return new int[] { followerNum, followingNum };
	}

	public List<FeedDto> getFeedList(int userId) {
		User user = userRepository.findById(userId).get();
		List<Feed> feedEntityList = user.getFeedList();
		List<FeedDto> feedDtoList = null;//Arrays.asList(modelMapper.map(feedEntityList, FeedDto[].class));
		return feedDtoList;
	}

//	public void updateAuth(String email, String auth) {
//		User user;
//		user = userRepository.findByEmail(email);
//		user.changeAuth(auth);
//		userRepository.save(user);
//	}

	public List<PopDto> getAllPopList(FeedUserIdDto feedUserIdDto) {
		User user = userRepository.getById(feedUserIdDto.getUserId());

		List<PopDto> result = null;//Arrays.asList(modelMapper.map(user.getPopList(), PopDto[].class));

		return result;
	}

	public List<PopDto> getPopInFeedList(int feedId, int userId) {
		Feed feedEntity = feedRepository.findById(feedId).get();
		List<PopInFeedEntity> popInFeedEntityList = feedEntity.getPopInFeedList();

		List<PopDto> result = new ArrayList<>();
		for (int i = 0; i < popInFeedEntityList.size(); i++) {
			Pop pop = popInFeedEntityList.get(i).getPop();
			boolean isLiked = isLiked(userId, pop.getId());
			result.add(new PopDto(pop, isLiked));
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

//	public String getUserAuthByEmail(String email) {
//		User user;
//		user = userRepository.findByEmail(email);
//		return user.getAuth();
//	}

	public void deleteByUserId(int userId) {
		userRepository.deleteById(userId);
		return;
	}

	public List<FriendFeedDto> getFollowingList(int userId) {
		User user = userRepository.findById(userId).get();
		List<FollowEntity> followEntityList = user.getFromUserEntityList();
		List<FriendFeedDto> result = new ArrayList<FriendFeedDto>();
		for (int i = 0; i < followEntityList.size(); i++) {

			User person = followEntityList.get(i).getToUser();

			List<Pop> temp = followEntityList.get(i).getToUser().getPopList();

			UserProfileDto userProfileDto = getUserProfileById(person.getId());

			List<PopDto> popDtoList = null;//Arrays.asList(modelMapper.map(temp, PopDto[].class));
			if (popDtoList.size() == 0)
				continue;

			for (int j = 0; j < popDtoList.size(); j++) {
				result.add(new FriendFeedDto(userProfileDto, popDtoList.get(j)));
			}
		}
		return result;
	}

	public List<UserProfileDto> getFollowerList(int userId) {
		User user = userRepository.findById(userId).get();
		List<FollowEntity> followEntityList = user.getToUserEntityList();
		List<User> followerList = new ArrayList<User>();
		for (int i = 0; i < followEntityList.size(); i++) {
			followerList.add(followEntityList.get(i).getFromUser());
		}

		List<UserProfileDto> UserProfileDtoList = null;//Arrays.asList(modelMapper.map(followerList, UserProfileDto[].class));

		return UserProfileDtoList;
	}

	public List<IdolDto> getInterestIdolList(int userId) {
		List<InterestIdolEntity> interestIdolList = interestIdolRepository.findAllByUserId(userId);
		List<IdolEntity> idolEntityList = new ArrayList<IdolEntity>();
		for (int i = 0; i < interestIdolList.size(); i++) {
			idolEntityList.add(interestIdolList.get(i).getIdolEntity());
		}
		List<IdolDto> idolDtoList = null;//Arrays.asList(modelMapper.map(idolEntityList, IdolDto[].class));
		return idolDtoList;
	}

	public boolean isExistUserByEmail(String email) {
		User user;
		user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<UserProfileDto> userDtoListByKeyword(String keyword) {

		List<User> userList = userRepository.findByKeywordContains(keyword);
		List<UserProfileDto> userDtoList = null;//Arrays.asList(modelMapper.map(userList, UserProfileDto[].class));
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
