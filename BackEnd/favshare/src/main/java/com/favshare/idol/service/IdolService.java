package com.favshare.idol.service;

import java.util.List;

import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IdolService {
	private final IdolRepository idolRepository;

	public IdolEntity getIdolByName(String name) {
		return idolRepository.findByName(name);
	}

	public List<IdolEntity> getIdolContains(String name) {
		return idolRepository.findByKeywordContains(name);
	}

	public List<IdolEntity> getIdolList() {
		return idolRepository.findAll();
	}

}
