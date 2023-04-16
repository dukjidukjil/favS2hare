package com.favshare.youtube.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeRequest {
	int userId;
	String youtubeUrl;

}
