package com.copilot.assignment.exception;

import com.copilot.assignment.controller.response.ResultInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FileProcessException extends RuntimeException {
    private final ResultInfo resultInfo;

    public FileProcessException(ResultInfo errorInfo) {
        this.resultInfo = errorInfo;
    }

}
