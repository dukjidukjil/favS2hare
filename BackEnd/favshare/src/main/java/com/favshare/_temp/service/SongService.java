package com.favshare._temp.service;

import java.util.List;

import com.favshare._temp.entity.SongEntity;
import com.favshare._temp.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {
	@Autowired
	private SongRepository songRepository;

	public List<SongEntity> getSongList() {
		return songRepository.findAll();

	}

}
