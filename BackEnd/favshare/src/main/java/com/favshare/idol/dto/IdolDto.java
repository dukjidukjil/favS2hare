package com.favshare.idol.dto;

import com.favshare.idol.entity.IdolEntity;

import lombok.*;

@Data
@NoArgsConstructor
public class IdolDto {
	private int id;
	private String name;
	private String content;
	private String idolImageUrl;

	public IdolDto(IdolEntity idolEntity) {
		this.id = idolEntity.getId();
		this.name = idolEntity.getName();
		this.content = idolEntity.getContent();
		this.idolImageUrl = idolEntity.getIdolImageUrl();
	}

}
