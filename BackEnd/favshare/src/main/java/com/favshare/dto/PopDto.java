package com.favshare.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.favshare.entity.PopEntity;

import lombok.*;

@Data
@NoArgsConstructor
public class PopDto {
	private int id;
	private String name;
	private int startSecond;
	private int endSecond; 
	private String content;
	private LocalDateTime createDate;
	private int views;
	private int likeCount;
	
	public PopDto(PopEntity popEntity) {
		this.id = popEntity.getId();
		this.name = popEntity.getName();
		this.startSecond = popEntity.getStartSecond();
		this.endSecond = popEntity.getEndSecond();
		this.content = popEntity.getContent();
		this.createDate = popEntity.getCreateDate();
		this.views = popEntity.getViews();
		this.likeCount = popEntity.getLikePopList().size();
	}
}
