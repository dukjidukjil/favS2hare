package com.favshare.idol.entity;

import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name = "interest_idol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class InterestIdolEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idol_id", nullable = false)
	private IdolEntity idolEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public InterestIdolEntity(IdolEntity idolEntity, User user) {
		super();
		this.idolEntity = idolEntity;
		this.user = user;
	}

}
