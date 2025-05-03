package com.copilot.assignment.service;

import com.copilot.assignment.controller.request.AuthorRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorService {
    ResponseEntity<Object> save(AuthorRequest request);

    ResponseEntity<Object> fetchAuthorDetails(Integer id);
}