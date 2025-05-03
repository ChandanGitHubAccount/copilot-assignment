package com.copilot.assignment.exception;

import com.copilot.assignment.controller.response.ResultInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = true)
@lombok.Data
@Getter
@Setter
public class TypeNotFoundException extends RuntimeException {
    private final ResultInfo resultInfo;

    public TypeNotFoundException(ResultInfo errorInfo) {
        this.resultInfo = errorInfo;
    }
}
