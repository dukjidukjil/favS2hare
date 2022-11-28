package com.favshare.idol.entity;

import java.util.*;

import javax.persistence.*;

import com.favshare.youtube.entity.YoutubeEntity;
import lombok.*;

@Entity
@Table(name = "idol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class IdolEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	private String content;
	@Column(name = "idol_image_url")
	private String idolImageUrl;

	@Builder.Default
	@OneToMany(mappedBy = "idolEntity")
	private List<IdolMemberEntity> idolMemberList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "idolEntity")
	private List<YoutubeEntity> youtubeList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "idolEntity")
	private List<InterestIdolEntity> interestIdolList = new ArrayList<>();

}
