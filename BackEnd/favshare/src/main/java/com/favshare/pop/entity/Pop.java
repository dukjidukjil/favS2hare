package com.favshare.pop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.favshare.popInFeed.entity.PopInFeedEntity;
import com.favshare.youtube.entity.YoutubeEntity;
import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "pop")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name;
	@Column(name = "start_second", nullable = false)
	private int startSecond;
	@Column(name = "end_second", nullable = false)
	private int endSecond;
	private String content;
	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;
	@Column(nullable = false)
	private int views;
	@Column(name = "is_muted", nullable = false)
	private boolean isMuted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "youtube_id", nullable = false)
	private YoutubeEntity youtubeEntity;

	@Builder.Default
	@OneToMany(mappedBy = "pop", cascade = CascadeType.ALL)
	private List<Comment> commentList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "pop", cascade = CascadeType.ALL)
	private List<LikePop> likePopList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "pop", cascade = CascadeType.ALL)
	private List<ShowPop> showPopList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "pop", cascade = CascadeType.ALL)
	private List<PopInFeedEntity> popInFeedList = new ArrayList<>();

	public void changeView() {
		this.views++;
	}

}
