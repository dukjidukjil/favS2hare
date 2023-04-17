package com.favshare.popInFeed.entity;

import javax.persistence.*;

import com.favshare.feed.entity.Feed;
import com.favshare.pop.entity.Pop;
import lombok.*;

@Entity
@Table(name = "pop_in_feed")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class PopInFeedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pop_id", nullable = false)
	private Pop pop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id", nullable = false)
	private Feed feedEntity;

}
