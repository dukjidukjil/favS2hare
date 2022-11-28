package com.favshare.youtube.entity;

import java.util.*;

import javax.persistence.*;

import com.favshare.idol.entity.IdolEntity;
import com.favshare.pops.entity.PopEntity;
import lombok.*;

@Entity
@Table(name = "youtube")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YoutubeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String url;

	@Builder.Default
	@OneToMany(mappedBy = "youtubeEntity")
	private List<PopEntity> popList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idol_id")
	private IdolEntity idolEntity;

	@Builder.Default
	@OneToMany(mappedBy = "youtubeEntity")
	private List<StoreYoutubeEntity> storeYoutubeList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "youtubeEntity")
	private List<ShowYoutubeEntity> showYoutubeList = new ArrayList<>();

}
