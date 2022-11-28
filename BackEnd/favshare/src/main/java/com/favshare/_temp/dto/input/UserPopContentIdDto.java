package com.favshare._temp.dto.input;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPopContentIdDto {
	int userId;
	int popId;
	String content;
}
