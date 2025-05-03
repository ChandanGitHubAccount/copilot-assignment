package com.copilot.assignment.controller.request;

import com.copilot.assignment.dataaccess.enmus.ArticleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@lombok.Data
public class ArticleRequest {
    private Integer id;
    @NotBlank(message = "Title is required")
    private String title;
    private String subTitle;
    @NotNull(message = "Author is required")
    private Integer author;
    @NotNull(message = "Category is required")
    private Integer category;
    @NotNull(message = "Article Type is required")
    private ArticleType type;
    private String image;
    @NotBlank(message = "Media URL is required")
    private String mediaUrl;
    @NotNull(message = "Publish Date is required")
    private LocalDate publishDate;
    private String description;
    private List<String> tags;
}