package com.copilot.assignment.controller;

import com.copilot.assignment.constants.APIEndpoints;
import com.copilot.assignment.constants.AppConstants;
import com.copilot.assignment.controller.request.ArticleRequest;
import com.copilot.assignment.dataaccess.enmus.ArticleType;
import com.copilot.assignment.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.copilot.assignment.constants.OpenAPITags.TAG_ARTICLE;
import static com.copilot.assignment.examples.ArticleRequests.ARTICLE_SAVE_REQ;
import static com.copilot.assignment.examples.ArticleRequests.ARTICLE_UPDATE_REQ;

@RestController
@RequestMapping(APIEndpoints.API_V1_ARTICLE)
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "API to create Or Update Article Details", tags = {TAG_ARTICLE},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = AppConstants.SAVE, value = ARTICLE_SAVE_REQ),
                            @ExampleObject(name = AppConstants.UPDATE, value = ARTICLE_UPDATE_REQ)
                    })
            )
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> create(@Validated @RequestBody ArticleRequest request) {
        log.info("Create or Update Article with title: {}, category: {} and author: {}"
                , request.getTitle(), request.getCategory(), request.getAuthor());
        return articleService.saveOrUpdateArticle(request);
    }

    @Operation(summary = "API to Fetch Article details", tags = {TAG_ARTICLE})
    @GetMapping(value = APIEndpoints.FETCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> fetch(@PathVariable Integer id) {
        log.info("Fetch Article with id:{} ", id);
        return articleService.fetchArticleDetails(id);
    }

    @Operation(summary = "API to Fetch Article details", tags = {TAG_ARTICLE})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> fetch(
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) ArticleType type, @RequestParam(required = false) Integer author,
            @RequestParam(required = false) String tag, @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "Minimum page should be 1") Integer page, @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Page size should not be Zero")
            @Max(value = 100, message = "Page size cannot exceed 100") Integer size) {
        log.info("Search Article with categories:{}, type: {}, author: {} or tag {}",
                categories, type, author, tag);
        int pageNumber = page - 1;
        var pageRequest = PageRequest.of(pageNumber, size);
        return articleService.searchArticleDetails(categories, type, author, tag, pageRequest);
    }

}