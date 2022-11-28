package com.favshare.idol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favshare.idol.entity.IdolEntity;
import com.favshare.idol.entity.IdolMemberEntity;

public interface IdolMemberRepository extends JpaRepository<IdolMemberEntity, Integer> {
	public IdolEntity findByName(String name);
}
