package com.favshare.user.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailPasswordDto {
	String email;
	String password;
}
