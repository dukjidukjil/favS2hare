package com.favshare._temp.dto.input;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.favshare._temp.entity.UserEntity;

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

	public UserSignUpDto(UserEntity userEntity) {
		this.name = userEntity.getName();
		this.email = userEntity.getEmail();
		this.password = userEntity.getPassword();
		this.nickname = userEntity.getNickname();
		this.birthDate = userEntity.getBirthDate();
		this.phone = userEntity.getPhone();
	}

}
