package com.favshare.user.controller;

import java.util.List;

import com.favshare.idol.dto.IdolDto;
import com.favshare._temp.dto.input.InterestSaveDto;
import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.service.IdolService;
import com.favshare.idol.service.InterestIdolService;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/interest")
public class UserInterestController {
	@Autowired
	private IdolService idolService;

	@Autowired
	private InterestIdolService interestIdolService;

	@ApiOperation(value = "유저가 선택한 취향 저장", response = ResponseEntity.class)
	@PostMapping
	public void saveInterest(@RequestBody InterestSaveDto interestSaveDto) {
		int userId = interestSaveDto.getUserId();
		List<Integer> IdolList = interestSaveDto.getIdolList();
		for (int i = 0; i < IdolList.size(); i++) {
			interestIdolService.addIdolFavorite(userId, IdolList.get(i));
		}

	}

	@ApiOperation(value = "유저의 선호정보 반환", response = ResponseEntity.class)
	@GetMapping("/interestList/{userId}")
	public ResponseEntity<InterestSaveDto> showInterest(@PathVariable("userId") int userId) {
		try {
			List<Integer> IdolList = interestIdolService.findIdolListById(userId);
			InterestSaveDto interestSaveDto = new InterestSaveDto(userId, IdolList, null);
			return new ResponseEntity<InterestSaveDto>(interestSaveDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "유저의 선호정보 변경", response = ResponseEntity.class)
	@PutMapping("/interestList/{userId}")
	public void changeInterest(@RequestBody InterestSaveDto interestSaveDto) {
		deleteInterest(interestSaveDto.getUserId()); // 기존의 선호정보 삭제
		saveInterest(interestSaveDto); // 유저가 입력한 선호정보로 변경
	}

	@ApiOperation(value = "유저의 선호정보 삭제", response = ResponseEntity.class)
	@DeleteMapping("/interestList/{userId}")
	public void deleteInterest(int userId) {
		interestIdolService.deleteByUserId(userId);
	}


	@ApiOperation(value = "이름으로 아이돌 찾기", response = ResponseEntity.class)
	@GetMapping("/findIdol/{name}")
	public ResponseEntity<List<IdolDto>> findIdol(@PathVariable("name") String name) {
		try {
			List<IdolEntity> idolEntityList = idolService.getIdolContains(name);
			List<IdolDto> idolDtoList = null;//Arrays.asList(modelMapper.map(idolEntityList, IdolDto[].class));
			return new ResponseEntity<List<IdolDto>>(idolDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<IdolDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "모든 아이돌 정보 반환", response = ResponseEntity.class)
	@GetMapping("/idol")
	public ResponseEntity<List<IdolDto>> showIdol() {
		try {
			List<IdolEntity> idolEntityList = idolService.getIdolList();
			List<IdolDto> idolDtoList = null;//Arrays.asList(modelMapper.map(idolEntityList, IdolDto[].class));
			return new ResponseEntity<List<IdolDto>>(idolDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<IdolDto>>(HttpStatus.BAD_REQUEST);
		}
	}
}
