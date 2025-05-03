package com.copilot.assignment.controller.response;

import com.copilot.assignment.dataaccess.enmus.ArticleType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@lombok.Data
@Builder
public class ArticleResponse {
    private Integer id;
    private String title;
    private String subTitle;
    private AuthorResponse author;
    private CategoryResponse category;
    private ArticleType type;
    private String image;
    private String mediaUrl;
    private LocalDate publishDate;
    private String description;
    private List<String> tags;
}