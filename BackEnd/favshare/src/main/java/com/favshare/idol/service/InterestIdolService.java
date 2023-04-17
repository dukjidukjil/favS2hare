package com.favshare.idol.service;

import java.util.ArrayList;
import java.util.List;

import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.entity.InterestIdolEntity;
import com.favshare.user.entity.User;
import com.favshare.idol.repository.IdolRepository;
import com.favshare.idol.repository.InterestIdolRepository;
import com.favshare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestIdolService {
	private final InterestIdolRepository interestIdolRepository;

	private final IdolRepository idolRepository;

	private final UserRepository userRepository;

	public void addIdolFavorite(int userId, int idolId) {
		IdolEntity idolEntity = idolRepository.findById(idolId).get();
		User user = userRepository.findById(userId).get();

		int duplicate = interestIdolRepository.findByIdolIdUserId(user.getId(), idolEntity.getId());
		if (duplicate >= 1) {
		} else {
			InterestIdolEntity result = new InterestIdolEntity(idolEntity, user);
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
