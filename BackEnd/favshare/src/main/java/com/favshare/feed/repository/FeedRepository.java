package com.favshare.feed.repository;

import com.favshare._temp.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedRepository extends JpaRepository<FeedEntity, Integer> {

	@Query(value = "select id from feed where is_first = true and user_id = :userId", nativeQuery = true)
	public int findFirstId(@Param("userId") int userId);

	@Query(value = "select count(*) from feed WHERE user_id = :userId", nativeQuery = true)
	public int countFeedByUserId(@Param("userId") int userId);

}
