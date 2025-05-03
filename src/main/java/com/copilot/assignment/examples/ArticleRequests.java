package com.copilot.assignment.examples;

public class ArticleRequests {

    /**
     * JSON representation of a request to save an article.
     * Contains details such as title, subtitle, author, category, type, image URL, media URL,
     * publish date, description, and tags.
     */
    public static final String ARTICLE_SAVE_REQ = """
            {
               "title": "Understanding Spring Boot",
               "subTitle": "A Comprehensive Guide",
               "author": 101,
               "category": 5,
               "type": "TECHNICAL",
               "image": "https://example.com/images/spring-boot.png",
               "mediaUrl": "https://example.com/media/spring-boot-guide",
               "publishDate": "2023-10-01",
               "description": "An in-depth guide to understanding and using Spring Boot for building modern Java applications.",
               "tags": ["Spring Boot", "Java", "Backend Development"]
             }""";


    /**
     * JSON representation of a request to update an article.
     * Includes the article ID, updated title, subtitle, author, category, type, image URL,
     * media URL, publish date, description, and tags.
     */
    public static final String ARTICLE_UPDATE_REQ = """
            {
              "id": 1,
              "title": "Updated Spring Boot Guide",
              "subTitle": "An Advanced Guide",
              "author": 102,
              "category": 6,
              "type": "TECHNICAL",
              "image": "https://example.com/images/updated-spring-boot.png",
              "mediaUrl": "https://example.com/media/updated-spring-boot-guide",
              "publishDate": "2023-11-01",
              "description": "An updated and advanced guide to mastering Spring Boot for modern Java applications.",
              "tags": ["Spring Boot", "Java", "Advanced Development"]
            }""";

}