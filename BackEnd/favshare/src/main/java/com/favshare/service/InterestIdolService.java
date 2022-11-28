package com.favshare.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favshare.entity.IdolEntity;
import com.favshare.entity.InterestIdolEntity;
import com.favshare.entity.UserEntity;
import com.favshare.repository.IdolRepository;
import com.favshare.repository.InterestIdolRepository;
import com.favshare.repository.UserRepository;

@Service
public class InterestIdolService {
	@Autowired
	private InterestIdolRepository interestIdolRepository;

	@Autowired
	private IdolRepository idolRepository;

	@Autowired
	private UserRepository userRepository;

	public void addIdolFavorite(int userId, int idolId) {
		IdolEntity idolEntity = idolRepository.findById(idolId).get();
		UserEntity userEntity = userRepository.findById(userId).get();

		int duplicate = interestIdolRepository.findByIdolIdUserId(userEntity.getId(), idolEntity.getId());
		if (duplicate >= 1) {
		} else {
			InterestIdolEntity result = new InterestIdolEntity(idolEntity, userEntity);
			interestIdolRepository.save(result);
		}

	}

	public List<Integer> findIdolListById(int userId) {
		List<InterestIdolEntity> idolEntityList = interestIdolRepository.findAllByUserId(userId);
		List<Integer> idolList = new ArrayList<Integer>();
		for (int i = 0; i < idolEntityList.size(); i++) {
			idolList.add(idolEntityList.get(i).getIdolEntity().getId());
		}
		return idolList;
	}

	public void deleteByUserId(int userId) {
		interestIdolRepository.deleteByUserId(userId);
	}

}
