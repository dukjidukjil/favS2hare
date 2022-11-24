package com.favshare._temp.dto.input;

import com.favshare.user.entity.User;

import lombok.*;

@Data
@NoArgsConstructor
public class UserProfileDto {
	private int id;
	private String nickname;
	private String content;
	private String profileImageUrl;

	private int popCount;
	private int followerNum;
	private int followingNum;

	public UserProfileDto(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.content = user.getContent();
		this.profileImageUrl = user.getProfileImageUrl();

	}

}
