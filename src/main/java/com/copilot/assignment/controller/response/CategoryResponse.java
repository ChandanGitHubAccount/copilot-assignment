package com.copilot.assignment.controller.response;

import lombok.Builder;

@lombok.Data
@Builder
public class CategoryResponse {
    private Integer id;
    private String name;
}