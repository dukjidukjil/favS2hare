package com.favshare.user.dto;

import com.favshare.user.entity.User;

import lombok.*;

@Data
@NoArgsConstructor
public class UserAccountDto {
	private int id;
	private String email;
	private String password;

	public UserAccountDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();

	}

}
