package com.favshare._temp.service;

import java.util.ArrayList;
import java.util.List;

import com.favshare._temp.entity.InterestSongEntity;
import com.favshare._temp.entity.SongEntity;
import com.favshare.user.entity.User;
import com.favshare._temp.repository.InterestSongRepository;
import com.favshare._temp.repository.SongRepository;
import com.favshare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestSongService {
	@Autowired
	private InterestSongRepository interestSongRepository;

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private UserRepository userRepository;

	public void addSongFavorite(int userId, int songId) {
		SongEntity songEntity = songRepository.findById(songId).get();
		User user = userRepository.findById(userId).get();

		int duplicate = interestSongRepository.findBySongIdUserId(user.getId(), songEntity.getId());
		if (duplicate >= 1) {
		} else {
			InterestSongEntity result = new InterestSongEntity(songEntity, user);
			interestSongRepository.save(result);
		}

	}

	public List<Integer> findSongListById(int userId) {
		List<InterestSongEntity> songEntityList = interestSongRepository.findAllByUserId(userId);
		List<Integer> songList = new ArrayList<Integer>();
		for (int i = 0; i < songEntityList.size(); i++) {
			songList.add(songEntityList.get(i).getSongEntity().getId());

		}
		return songList;
	}

	public void deleteByUserId(int userId) {
		interestSongRepository.deleteByUserId(userId);
	}
}
