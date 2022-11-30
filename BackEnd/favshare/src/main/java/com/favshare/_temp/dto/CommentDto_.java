package com.favshare._temp.dto;

import java.time.LocalDateTime;

import com.favshare.pop.entity.Comment;

import lombok.*;

@Data
@NoArgsConstructor
public class CommentDto_ {

	private int id;
	private String content;
	private LocalDateTime createdDate;
	private boolean isModified;
	private int userId;
	private int popId;
	private String nickname;
	private String profileImageUrl;
	private int countCommentLikes;
	private boolean isLiked;

	public CommentDto_(Comment comment, String nickname, String profileImageUrl, boolean isLiked) {
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createDate = comment.getCreateDate();
		this.isModify = comment.isModify();
		this.userId = comment.getUser().getId();
		this.popId = comment.getPop().getId();
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.countCommentLikes = comment.getLikeCommentList().size();
		this.isLiked = isLiked;
	}

}
