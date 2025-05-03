package com.copilot.assignment.controller.response;

import lombok.Builder;

@lombok.Data
@Builder
public class AuthorResponse {
    private Integer id;
    private String name;
    private String image;
    private String description;
}