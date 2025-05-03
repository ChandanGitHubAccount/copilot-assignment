package com.copilot.assignment.service.impl;

import com.copilot.assignment.controller.request.AuthorRequest;
import com.copilot.assignment.controller.response.AuthorResponse;
import com.copilot.assignment.dataaccess.repository.AuthorRepository;
import com.copilot.assignment.entity.Author;
import com.copilot.assignment.exception.ValidationException;
import com.copilot.assignment.service.AuthorService;
import com.copilot.assignment.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.copilot.assignment.constants.ResultInfoConstants.AUTHOR_ALREADY_EXIST;
import static com.copilot.assignment.constants.ResultInfoConstants.AUTHOR_NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.SUCCESS;

@Service
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> save(AuthorRequest request) {
        return Objects.isNull(request.getId()) ? saveAuthor(request) : updateAuthor(request);
    }

    /**
     * Fetches the details of an author by their ID.
     *
     * @param id the ID of the author to fetch
     * @return a ResponseEntity containing the author details if found
     * @throws ValidationException if the author is not found
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> fetchAuthorDetails(Integer id) {
        val author = authorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(AUTHOR_NOT_FOUND));
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(author));
    }

    /**
     * Saves a new author to the database.
     *
     * @param request the author request containing new author details
     * @return a ResponseEntity containing the result of the operation
     * @throws ValidationException if the author name already exists
     */
    private ResponseEntity<Object> saveAuthor(AuthorRequest request) {
        checkAuthorNameExist(request.getName(), null);
        val author = Author.builder().name(request.getName())
                .image(request.getImage())
                .description(request.getDescription())
                .deleted(Boolean.FALSE)
                .build();
        val authorResponse = authorRepository.save(author);
        log.info("Saving new Author with name: {}", request.getName());
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(authorResponse));
    }

    /**
     * Updates an existing author in the database.
     *
     * @param request the author request containing updated author details
     * @return a ResponseEntity containing the result of the operation
     * @throws ValidationException if the author is not found or the name already exists
     */
    private ResponseEntity<Object> updateAuthor(AuthorRequest request) {
        checkAuthorNameExist(request.getName(), request.getId());
        log.info("Updating Author with id: {}", request.getId());
        val author = authorRepository.findById(request.getId())
                .orElseThrow(() -> new ValidationException(AUTHOR_NOT_FOUND));
        author.setName(request.getName());
        author.setImage(request.getImage());
        author.setDescription(request.getDescription());
        val authorResponse = authorRepository.save(author);
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(authorResponse));
    }

    /**
     * Checks if an author with the given name already exists, excluding the specified ID.
     *
     * @param name the name of the author to check
     * @param id   the ID to exclude from the check (can be null)
     * @throws ValidationException if an author with the same name already exists
     */
    private void checkAuthorNameExist(String name, Integer id) {
        authorRepository.findByNameAndId(name, id)
                .ifPresent(author -> {
                    throw new ValidationException(AUTHOR_ALREADY_EXIST);
                });
    }

    /**
     * Converts an Author entity to an AuthorResponse object.
     *
     * @param author the Author entity to convert
     * @return the converted AuthorResponse object
     */
    private AuthorResponse convertToResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .image(author.getImage())
                .description(author.getDescription())
                .build();
    }

}