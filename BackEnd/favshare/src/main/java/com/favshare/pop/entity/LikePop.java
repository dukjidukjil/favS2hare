package com.favshare.pop.entity;

import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "like_pop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class LikePop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pop_id", nullable = false)
	private Pop pop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
