package com.favshare.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "존재하지 않은 회원 ID 입니다."),
    FEED_NOT_FOUND(404, "피드가 존재하지 않습니다."),
    POP_NOT_FOUND(404,"팝이 존재하지 않습니다.")

    ;
    private final int status;
    private final String message;
}
