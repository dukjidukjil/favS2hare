package com.favshare._temp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.favshare.user.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAllDto {
	private int id;
	private String name;
	private String email;
	private String password;
	private String nickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private String phone;
	private String content;
	private String profileImageUrl;

	public UserAllDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.nickname = user.getNickname();
		this.birthDate = user.getBirthDate();
		this.phone = user.getPhone();
		this.content = user.getContent();
		this.profileImageUrl = user.getProfileImageUrl();
	}

}