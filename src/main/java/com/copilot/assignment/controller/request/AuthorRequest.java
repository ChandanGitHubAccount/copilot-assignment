package com.copilot.assignment.controller.request;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@lombok.Data
public class AuthorRequest {
    private Integer id;
    @NotBlank(message = "Author name required")
    private String name;
    private String image;
    private String description;
}