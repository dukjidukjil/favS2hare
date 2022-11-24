package com.favshare._temp.service;

import java.util.List;

import com.favshare._temp.entity.IdolEntity;
import com.favshare._temp.repository.IdolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdolService {
	@Autowired
	private IdolRepository idolRepository;

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
