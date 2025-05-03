package com.copilot.assignment.controller;

import com.copilot.assignment.constants.APIEndpoints;
import com.copilot.assignment.constants.AppConstants;
import com.copilot.assignment.controller.request.AuthorRequest;
import com.copilot.assignment.service.AuthorService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.copilot.assignment.constants.OpenAPITags.TAG_AUTHOR;
import static com.copilot.assignment.examples.AuthorRequests.AUTHOR_SAVE_REQ;
import static com.copilot.assignment.examples.AuthorRequests.AUTHOR_UPDATE_REQ;

@RestController
@RequestMapping(APIEndpoints.API_V1_AUTHOR)
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "API to create Or Update Author details", tags = {TAG_AUTHOR},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = AppConstants.SAVE, value = AUTHOR_SAVE_REQ),
                            @ExampleObject(name = AppConstants.UPDATE, value = AUTHOR_UPDATE_REQ)
                    })
            )
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> create(@Validated @RequestBody AuthorRequest request) {
        log.info("Create or Update Author with name:{} ", request.getName());
        return authorService.save(request);
    }

    @Operation(summary = "API to Fetch Author details", tags = {TAG_AUTHOR})
    @GetMapping(value = APIEndpoints.FETCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> fetch(@PathVariable Integer id) {
        log.info("Fetch Author with id:{} ", id);
        return authorService.fetchAuthorDetails(id);
    }

}