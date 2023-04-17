package com.favshare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestSaveDto {
	int userId;
	List<Integer> IdolList;
	List<Integer> SongList;

}
