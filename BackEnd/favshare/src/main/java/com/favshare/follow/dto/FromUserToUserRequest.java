package com.favshare.follow.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromUserToUserRequest {
	int fromUserId;
	int toUserId;
}
