package com.favshare._temp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "feed")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name;
	@Column(name = "is_first", nullable = false)
	private boolean isFirst;
	@Column(name = "feed_image_url")
	private String feedImageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "feedEntity", cascade = CascadeType.ALL)
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