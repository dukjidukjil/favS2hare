package com.favshare.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.favshare.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	@Query(value = "SELECT * FROM user where nickname LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
	public List<User> findByKeywordContains(@Param("keyword") String keyword);

}
