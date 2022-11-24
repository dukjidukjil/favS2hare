package com.favshare._temp.dto.input;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentContentIdDto {
	int userId;
	int commentId;
	String content;
}
