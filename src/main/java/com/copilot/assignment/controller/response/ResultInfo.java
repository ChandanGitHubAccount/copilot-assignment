package com.copilot.assignment.controller.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class ResultInfo implements Serializable {
    private String codeId;
    private String code;
    private String message;
    private String status;
}
