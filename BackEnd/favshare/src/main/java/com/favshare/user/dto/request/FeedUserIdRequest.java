package com.favshare.user.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedUserIdRequest {
	int feedId;
	int userId;
}
