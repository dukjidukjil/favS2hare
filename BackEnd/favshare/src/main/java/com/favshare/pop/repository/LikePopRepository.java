package com.favshare.pop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.favshare.pop.entity.LikePop;

@Repository
public interface LikePopRepository extends JpaRepository<LikePop, Integer> {

	@Query(value = "select * from like_pop where user_id = :userId and pop_id = :popId", nativeQuery = true)
	public LikePop searchByUserIdAndPopId(@Param("userId") int userId, @Param("popId") int popId);

	@Query(value = "select count(*) from like_pop where user_id = :userId and pop_id = :popId", nativeQuery = true)
	public int isLiked(@Param("userId") int userId, @Param("popId") int popId);

	@Query(value = "select count(*) from like_pop where pop_id = :popId and user_id = :userId", nativeQuery = true)
	public int findByPopIdUserId(@Param("userId") int userId, @Param("popId") int popId);

}
