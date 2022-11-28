package com.favshare._temp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favshare._temp.entity.SongEntity;

public interface SongRepository extends JpaRepository<SongEntity, Integer> {

}
