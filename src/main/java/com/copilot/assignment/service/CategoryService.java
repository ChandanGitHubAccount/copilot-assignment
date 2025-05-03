package com.copilot.assignment.service;

import com.copilot.assignment.controller.request.CategoryRequest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<Object> saveOrUpdateCategory(CategoryRequest request);

    ResponseEntity<Object> fetchCategoryDetails();
}