package com.favshare._temp.entity;

import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "follow")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class FollowEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id", nullable = false)
	private User fromUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id", nullable = false)
	private User toUser;

}
