package com.favshare.pop.repository;



import com.favshare.pop.dto.comment.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentDto> getCommentList(int popId, int userId);

}
