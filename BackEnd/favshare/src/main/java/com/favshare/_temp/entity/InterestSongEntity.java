package com.favshare._temp.entity;

import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "interest_song")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class InterestSongEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "song_id", nullable = false)
	private SongEntity songEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public InterestSongEntity(SongEntity songEntity, User user) {
		super();
		this.songEntity = songEntity;
		this.user = user;
	}

}
