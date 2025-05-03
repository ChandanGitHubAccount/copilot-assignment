package com.copilot.assignment.exception;

import com.copilot.assignment.controller.response.ResultInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuthenticationException extends RuntimeException{
    final ResultInfo resultInfo;

    public AuthenticationException(ResultInfo errorInfo) {
        this.resultInfo = errorInfo;
    }
}
