package com.copilot.assignment.exception;

import com.copilot.assignment.controller.response.ResultInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends RuntimeException {
   private final ResultInfo resultInfo;

    public ValidationException(ResultInfo errorInfo) {
        this.resultInfo = errorInfo;
    }

}