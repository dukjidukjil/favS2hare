package com.favshare.global.exception;

import com.favshare.feed.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

}
