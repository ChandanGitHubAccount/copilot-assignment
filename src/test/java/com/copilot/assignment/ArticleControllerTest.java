package com.copilot.assignment;

import com.copilot.assignment.controller.ArticleController;
import com.copilot.assignment.controller.request.ArticleRequest;
import com.copilot.assignment.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Test Title");
        request.setCategory(1);
        request.setAuthor(123);

        ResponseEntity<Object> expectedResponse = ResponseEntity.ok("Article created successfully");
        when(articleService.saveOrUpdateArticle(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> response = articleController.create(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(articleService, times(1)).saveOrUpdateArticle(request);
    }

    @Test
    void testCreate_ValidationError() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setTitle(""); // Invalid title
        request.setCategory(1);
        request.setAuthor(123);

        // Act & Assert
        // Validation errors are typically handled by Spring, so no direct test here.
        // You can test this in an integration test with MockMvc.
    }

    @Test
    void testCreate_Exception() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Test Title");
        request.setCategory(1);
        request.setAuthor(123);

        when(articleService.saveOrUpdateArticle(request)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        try {
            articleController.create(request);
        } catch (RuntimeException e) {
            assertEquals("Service error", e.getMessage());
        }
        verify(articleService, times(1)).saveOrUpdateArticle(request);
    }

    @Test
    void testCreate_NullRequest() {
        // Act & Assert
        try {
            articleController.create(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Request body cannot be null", e.getMessage());
        }
        verify(articleService, never()).saveOrUpdateArticle(any());
    }

    // Test case for missing required fields
    @Test
    void testCreate_MissingFields() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setCategory(1); // Missing title and author

        // Act & Assert
        try {
            articleController.create(request);
        } catch (RuntimeException e) {
            assertEquals("Validation failed for request", e.getMessage());
        }
        verify(articleService, never()).saveOrUpdateArticle(any());
    }

    // Test case for invalid category
    @Test
    void testCreate_InvalidCategory() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Test Title");
        request.setCategory(-1); // Invalid category
        request.setAuthor(123);

        // Act & Assert
        try {
            articleController.create(request);
        } catch (RuntimeException e) {
            assertEquals("Invalid category value", e.getMessage());
        }
        verify(articleService, never()).saveOrUpdateArticle(any());
    }

}