package com.copilot.assignment.service;

import com.copilot.assignment.controller.request.ArticleRequest;
import com.copilot.assignment.dataaccess.enmus.ArticleType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {
    ResponseEntity<Object> saveOrUpdateArticle(ArticleRequest request);

    ResponseEntity<Object> fetchArticleDetails(Integer id);

    ResponseEntity<Object> searchArticleDetails(List<Integer> categories, ArticleType type, Integer author, String tag, PageRequest pageRequest);
}