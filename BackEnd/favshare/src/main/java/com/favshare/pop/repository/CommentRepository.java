package com.favshare.pop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.favshare.pop.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {

	@Query(value = "select * from comment where user_id = :userId and id = :commentId", nativeQuery = true)
	public Comment findByUserCommentId(@Param("userId") int userId, @Param("commentId") int commentId);

	@Transactional
	@Modifying
	@Query(value = "delete from comment where user_id = :userId and id = :commentId", nativeQuery = true)
	public void deleteByUserCommentId(@Param("userId") int userId, @Param("commentId") int commentId);

	@Query(value = "select * from comment where pop_id = :popId", nativeQuery = true)
	public List<Comment> findAllByPopId(@Param("popId") int popId);



}
