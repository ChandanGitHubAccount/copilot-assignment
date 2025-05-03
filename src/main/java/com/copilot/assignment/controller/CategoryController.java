package com.copilot.assignment.controller;

import com.copilot.assignment.constants.APIEndpoints;
import com.copilot.assignment.constants.AppConstants;
import com.copilot.assignment.controller.request.CategoryRequest;
import com.copilot.assignment.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.copilot.assignment.constants.OpenAPITags.TAG_CATEGORY;
import static com.copilot.assignment.examples.CategoryRequests.CATEGORY_SAVE_REQ;
import static com.copilot.assignment.examples.CategoryRequests.CATEGORY_UPDATE_REQ;

@RestController
@RequestMapping(APIEndpoints.API_V1_CATEGORY)
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "API to create Or Update Category", tags = {TAG_CATEGORY},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = AppConstants.SAVE, value = CATEGORY_SAVE_REQ),
                            @ExampleObject(name = AppConstants.UPDATE, value = CATEGORY_UPDATE_REQ)
                    })
            )
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> create(@Validated @RequestBody CategoryRequest request) {
        log.info("Create or Update Category with name:{} ", request.getName());
        return categoryService.saveOrUpdateCategory(request);
    }

    @Operation(summary = "API to Fetch All Category", tags = {TAG_CATEGORY})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> fetch() {
        log.info("Fetch All Category");
        return categoryService.fetchCategoryDetails();
    }

}