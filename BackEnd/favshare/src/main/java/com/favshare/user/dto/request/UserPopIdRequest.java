package com.favshare.user.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPopIdRequest {
	private int userId;
	private int popId;
}
