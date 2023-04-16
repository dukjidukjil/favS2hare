package com.favshare.follow.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FromUserToUserDto {
	int fromUserId;
	int toUserId;
}
