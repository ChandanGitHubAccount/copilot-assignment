package com.copilot.assignment.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@Builder
public class GenericResponse {

    private ResultInfo resultInfo;

    @JsonProperty("data")
    private Object data;
}