package com.copilot.assignment.exception.handler;

import com.copilot.assignment.controller.response.ResultInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletionException;

@lombok.Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ValidationCompletionException extends CompletionException {
   private final ResultInfo resultInfo;

    public ValidationCompletionException(ResultInfo errorInfo) {
        this.resultInfo = errorInfo;
    }

}