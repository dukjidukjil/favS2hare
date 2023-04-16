package com.favshare.user.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.favshare.user.entity.User;

import lombok.*;

@Data
@NoArgsConstructor
public class UserInfoDto {
	private int id;
	private String name;
	private String email;
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private String phone;

	public UserInfoDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.birthDate = user.getBirthDate();
		this.phone = user.getPhone();
	}

}
