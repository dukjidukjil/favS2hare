package com.favshare.user.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.favshare.user.entity.User;

import lombok.*;

@Data
@NoArgsConstructor
public class UserSignUpDto {

	private String name;
	private String email;
	private String password;
	private String nickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private String phone;

	public UserSignUpDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.nickname = user.getNickname();
		this.birthDate = user.getBirthDate();
		this.phone = user.getPhone();
	}

}
