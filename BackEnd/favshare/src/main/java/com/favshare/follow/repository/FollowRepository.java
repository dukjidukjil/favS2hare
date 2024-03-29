package com.favshare.follow.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.favshare.follow.entity.FollowEntity;
import com.favshare.user.entity.User;

public interface FollowRepository extends JpaRepository<FollowEntity, Integer> {

	@Transactional 
	@Modifying
	@Query(value = "delete from follow where from_user_id = :fromUserId and to_user_id = :toUserId", nativeQuery = true)
	public void deleteFollowByUserId(@Param("fromUserId") User fromUserId,
			@Param("toUserId") User toUserId);

	@Query(value = "select count(*) from follow where from_user_id = :fromUserId and to_user_id = :toUserId", nativeQuery = true)
	public int countFollowFByUserId(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

}