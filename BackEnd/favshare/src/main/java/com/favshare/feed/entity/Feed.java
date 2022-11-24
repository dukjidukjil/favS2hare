package com.favshare.feed.entity;

import com.favshare._temp.entity.PopInFeedEntity;
import com.favshare.global.baseEntity.BaseEntity;
import com.favshare.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class Feed extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_id")
	private int id;
	private String name;
	private boolean isFirst;

	private String feedImageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private List<PopInFeedEntity> popInFeedList = new ArrayList<>();

	public void changeName(String name) {
		this.name = name;
	}

	public void changeImageUrl(String feedImageUrl) {
		this.feedImageUrl = feedImageUrl;
	}

	public void changeIsFirst() {
		this.isFirst = true;

	}

	public void changeIsNotFirst() {
		this.isFirst = false;
	}

}