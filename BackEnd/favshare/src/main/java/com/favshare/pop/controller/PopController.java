package com.favshare.pop.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.favshare._temp.dto.input.FriendFeedDto;
import com.favshare._temp.dto.input.UserPopIdDto;
import com.favshare._temp.dto.input.UserProfileDto;
import com.favshare.pop.dto.UserIdRequest;
import com.favshare.pop.dto.pop.DeletePopRequest;
import com.favshare.pop.dto.pop.GetPopListRequest;
import com.favshare.pop.dto.pop.PopInfoRequest;
import com.favshare.pop.service.LikePopService;
import com.favshare.pop.service.PopService;
import com.favshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favshare._temp.dto.PopAlgoDto;
import com.favshare._temp.dto.IdolDto;
import com.favshare._temp.dto.PopDto;
import com.favshare._temp.dto.PopInfoDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pop")
public class PopController {
	private final PopService popService;
	private final UserService userService;
	private final LikePopService likePopService;

	@ApiOperation(value = "사용자에게 맞는 팝 리스트", response = List.class)
	@PostMapping
	public ResponseEntity<List<PopDto>> showPopList(@RequestBody GetPopListRequest getPopListRequest) {

		int idolId = getPopListRequest.getIdolId();
		int userId = getPopListRequest.getUserId();

		try {
			List<PopDto> result = new ArrayList<PopDto>();

			// userId랑 idolId가 모두 1 이상이라면, 로그인한 유저가 세부 카테고리를 선택했다는 것
			if (userId >= 1 && idolId >= 1) {
				List<PopAlgoDto> algoList = popService.getCategoryPopList(getPopListRequest);

				for (int i = 0; i < algoList.size(); i++) {
					PopDto popDto = popService.getPopDtoById(userId, algoList.get(i).getId());
					result.add(popDto);
				}
			}
			// userId랑 idolId가 모두 0이라면 로그인하지 않은 유저라는 것 => 전체 pop을 랜덤 알고리즘으로 반환
			else if (userId == 0 && idolId == 0) {

				result = popService.getRandomPopList();
			}
			// 둘 다 아니라는 것은 로그인한 유저가 전체 pop을 보고있다는 것 => customAlgo를 사용
			else {
				List<PopAlgoDto> algoList = popService.getCustomPopList(getPopListRequest.getUserId());

				for (int i = 0; i < algoList.size(); i++) {
					PopDto popDto = popService.getPopDtoById(userId, algoList.get(i).getId());
					result.add(popDto);
				}
			}
			return new ResponseEntity<List<PopDto>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<PopDto>>(HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "팝 시청시 조회수 증가", response = ResponseEntity.class)
	@PutMapping("/detail/{popId}")
	public ResponseEntity<?> modifyPopView(@PathVariable("popId") int popId) {
		try {
			popService.updatePopView(popId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "팝 정보", response = PopInfoDto.class)
	@PostMapping("/info")
	public ResponseEntity<HashMap<String, Object>> showPopInfo(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			PopInfoDto popInfoDto = popService.getPopInfoById(popInfoRequest.getPopId(), popInfoRequest.getUserId());

			UserProfileDto userProfileDto = userService.getUserProfileById(popInfoRequest.getUserId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("popInfoDto", popInfoDto);
			map.put("userProfileDto", userProfileDto);

			popService.insertShowPop(popInfoRequest.getPopId(), popInfoRequest.getUserId());

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<HashMap<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "팝 좋아요", response = ResponseEntity.class)
	@PostMapping("/like")
	public ResponseEntity<?> likePop(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			likePopService.insertLikePop(popInfoRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "팝 좋아요 취소", response = ResponseEntity.class)
	@DeleteMapping("/like")
	public ResponseEntity<?> dislikePop(@RequestBody PopInfoRequest popInfoRequest) {
		try {
			likePopService.deleteLikePop(popInfoRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "유튜브 원본 영상에 만들어진 팝 리스트", response = PopInfoDto.class)
	@PostMapping("/youtube")
	public ResponseEntity<HashMap<String, Object>> showOrginYoutubePopInfo(@RequestBody PopInfoRequest popInfoRequest) {
		int userId = popInfoRequest.getUserId();
		int popId = popInfoRequest.getPopId();
		try {
			HashMap<String, Object> result = new HashMap<String, Object>();
			PopInfoDto popInfoDto = popService.getPopInfoById(popId, userId);
			List<PopDto> popList = popService.getPopListById(userId, popId, popInfoDto.getYoutubeId());
			int countPopByYoutubeId = popList.size();

			result.put("countPopList", countPopByYoutubeId);
			result.put("popInfo", popList);

			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<HashMap<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "팝 삭제 - 한개, 여러개 모두 삭제 가능", response = PopInfoDto.class)
	@DeleteMapping
	public ResponseEntity<?> deletePop(@RequestBody DeletePopRequest deletePopRequest) {
		try {
			popService.deletePop(deletePopRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "팝 탭 상단의 카테고리 리스트 - 아이돌1, 아이돌 2 등등", response = PopInfoDto.class)
	@GetMapping("/idolList")
	public ResponseEntity<List<IdolDto>> getInterestIdolList(@RequestBody UserIdRequest userIdRequest) {
		int userId = userIdRequest.getUserId();
		try {
			List<IdolDto> result = popService.getInterestIdolList(userId);
			return new ResponseEntity<List<IdolDto>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<IdolDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "친구피드 탭을 클릭했을 때", response = PopInfoDto.class)
	@GetMapping("/friend")
	public ResponseEntity<List<FriendFeedDto>> showFriendList(@RequestBody UserIdRequest userIdRequest) {
		int userId = userIdRequest.getUserId();

		// 친구숫자 세기 (친구: 내가 팔로우 한 사람)
		List<FriendFeedDto> friendsPopList = userService.getFollowingList(userId);

		if (friendsPopList.size() == 0) { // 친구가 없으면 -> 알고리즘으로 뿌려주기
			List<FriendFeedDto> result = new ArrayList<FriendFeedDto>();

			// 1. 나를 팔로우 한 사람
			List<UserProfileDto> UserProfileDtoList = userService.getFollowerList(userId);
			for (int i = 0; i < UserProfileDtoList.size(); i++) {
				int id = UserProfileDtoList.get(i).getId();
				// id로 이사람의 userProfileDto 가져오기
				UserProfileDto userProfileDto = userService.getUserProfileById(id);
				// id로 이사람의 popDtoList가져오기
				List<PopDto> popDtoList = popService.popDtoListByUserId(id);
				// popDtoList를 for문으로 돌면서
				for (int j = 0; j < popDtoList.size(); j++) {
					// new FriendFeedDto한걸 result에 넣어주기
					result.add(new FriendFeedDto(userProfileDto, popDtoList.get(j)));
				}
			}

			// 3. 관심사가 비슷한 사람
			// 내 관심사
			List<IdolDto> myInterestIdolList = userService.getInterestIdolList(userId);

			// 나와 관심사가 동일한 사람
			HashSet<Integer> similarPersonId = new HashSet<Integer>();



			Iterator<Integer> iter = similarPersonId.iterator();
			while (iter.hasNext()) {
				int id = iter.next();
				// id로 이사람의 userProfileDto 가져오기
				UserProfileDto userProfileDto = userService.getUserProfileById(id);
				// id로 이사람의 popDtoList가져오기
				List<PopDto> popDtoList = popService.popDtoListByUserId(id);
				// popDtoList를 for문으로 돌면서
				for (int j = 0; j < popDtoList.size(); j++) {
					// new FriendFeedDto한걸 result에 넣어주기
					result.add(new FriendFeedDto(userProfileDto, popDtoList.get(j)));
				}

			}
			return new ResponseEntity<List<FriendFeedDto>>(result, HttpStatus.OK);

			// 4. 랜덤
		} else { // 친구가 있으면 -> 내 친구들의 팝들 최신순으로 보여주기

			friendsPopList.sort(new Comparator<FriendFeedDto>() {
				@Override
				public int compare(FriendFeedDto o1, FriendFeedDto o2) {
					return o2.getPopDto().getCreateDate().compareTo(o1.getPopDto().getCreateDate());
				}
			});
			return new ResponseEntity<List<FriendFeedDto>>(friendsPopList, HttpStatus.OK);
		}

	}
}
