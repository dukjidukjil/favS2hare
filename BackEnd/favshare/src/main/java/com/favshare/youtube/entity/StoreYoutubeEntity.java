package com.favshare.youtube.entity;


import javax.persistence.*;

import com.favshare.user.entity.User;
import lombok.*;

@Entity
@Table(name="store_youtube")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class StoreYoutubeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="youtube_id", nullable = false)
    private YoutubeEntity youtubeEntity;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    
}
