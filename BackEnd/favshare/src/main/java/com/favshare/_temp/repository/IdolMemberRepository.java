package com.favshare._temp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favshare._temp.entity.IdolEntity;
import com.favshare._temp.entity.IdolMemberEntity;

public interface IdolMemberRepository extends JpaRepository<IdolMemberEntity, Integer> {
	public IdolEntity findByName(String name);
}
