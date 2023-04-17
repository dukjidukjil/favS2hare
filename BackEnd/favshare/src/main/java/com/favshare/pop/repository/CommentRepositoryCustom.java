package com.favshare.pop.repository;



import com.favshare.pop.dto.comment.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponse> getCommentList(int popId, int userId);

}
