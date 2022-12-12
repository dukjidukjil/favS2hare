package com.favshare.pop.repository;

import com.favshare.pop.dto.comment.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryImplTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    public void 댓글목록_반환_querydsl(){
        int popId = 1;
        int userId = 1;
        List<CommentResponse> result =  commentRepository.getCommentList(popId, userId);


        for (CommentResponse commentResponse : result) {
            System.out.println("commentResponse = " + commentResponse.getCommentId());
        }
    }


}