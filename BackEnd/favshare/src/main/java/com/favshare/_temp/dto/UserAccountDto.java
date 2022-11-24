package com.favshare._temp.dto;

import com.favshare._temp.entity.UserEntity;

import lombok.*;

@Data
@NoArgsConstructor
public class UserAccountDto {
	private int id;
	private String email;
	private String password;

	public UserAccountDto(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.email = userEntity.getEmail();
		this.password = userEntity.getPassword();

	}

}
