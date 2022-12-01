package com.favshare.pop.repository;


import com.favshare.pop.dto.comment.CommentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.favshare.pop.entity.QComment.comment;
import static com.favshare.pop.entity.QPop.pop;
import static com.favshare.user.entity.QUser.user;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<CommentResponse> getCommentList(int popId, int userId) {
        return queryFactory.select(Projections.constructor(CommentResponse.class,
                    comment.id,
                    comment.content,
                    comment.createdDate,
                    comment.isModified,
                    user.id,
                    pop.id,
                    user.nickname,
                    user.profileImageUrl,
                    comment.likeCommentList.size(),
                    isLiked(userId,Integer.parseInt(comment.id.toString()))
                ))
                .from(comment)
                .innerJoin(comment.user,user)
                .innerJoin(comment.pop,pop)
                .where(
                        pop.id.eq(popId),
                        user.id.eq(userId)
                )
                .fetch();
    }
    private BooleanExpression isLiked(int userId, int commentId){
        int resultSize = queryFactory.selectFrom(comment)
                .where(comment.user.id.eq(userId),
                        comment.id.eq(commentId)).fetch().size();
        return (resultSize == 1)?Expressions.TRUE:Expressions.FALSE;
    }
}
