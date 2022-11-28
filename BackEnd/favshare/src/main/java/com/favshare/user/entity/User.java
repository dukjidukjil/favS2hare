package com.favshare.user.entity;

import java.util.*;

import javax.persistence.*;

import com.favshare.feed.entity.Feed;
import com.favshare.follow.entity.FollowEntity;
import com.favshare.global.baseEntity.BaseEntity;
import com.favshare.idol.entity.InterestIdolEntity;
import com.favshare.pops.entity.*;
import com.favshare.youtube.entity.ShowYoutubeEntity;
import com.favshare.youtube.entity.StoreYoutubeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private String phone;
	private String content;
	@Column(name = "profile_image_url")
	private String profileImageUrl;

//	private String auth;

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PopEntity> popList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CommentEntity> commentList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feed> feedList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LikePopEntity> likePopList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ShowPopEntity> showPopList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<StoreYoutubeEntity> storeYoutubeList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ShowYoutubeEntity> showYoutubeList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LikeCommentEntity> likeCommentList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<InterestIdolEntity> interestIdolList = new ArrayList<>();


	@Builder.Default
	@OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
	private List<FollowEntity> fromUserEntityList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
	private List<FollowEntity> toUserEntityList = new ArrayList<>();

	public void changePassword(String password) {
		this.password = password;
	}

//	public void changeAuth(String auth) {
//		this.auth = auth;
//	}

	public void changeProfile(String nickname, String content, String profileImageUrl) {
		this.nickname = nickname;
		this.content = content;
		this.profileImageUrl = profileImageUrl;
	}

	public void changeUserInfo(String name, String password, String phone, Date birthDate) {
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.birthDate = birthDate;
	}

}
