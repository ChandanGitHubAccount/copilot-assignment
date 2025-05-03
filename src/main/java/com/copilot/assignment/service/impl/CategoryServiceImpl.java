package com.copilot.assignment.service.impl;

import com.copilot.assignment.controller.request.CategoryRequest;
import com.copilot.assignment.controller.response.CategoryResponse;
import com.copilot.assignment.dataaccess.repository.CategoryRepository;
import com.copilot.assignment.entity.Category;
import com.copilot.assignment.exception.ValidationException;
import com.copilot.assignment.service.CategoryService;
import com.copilot.assignment.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.copilot.assignment.constants.ResultInfoConstants.CATEGORY_ALREADY_EXIST;
import static com.copilot.assignment.constants.ResultInfoConstants.CATEGORY_NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.SUCCESS;

@Service
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> saveOrUpdateCategory(CategoryRequest request) {
        return Objects.isNull(request.getId()) ? save(request) : update(request);
    }

    /**
     * Fetches the details of all categories sorted by name in ascending order.
     *
     * @return a ResponseEntity containing a list of CategoryResponse objects and a success result status
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> fetchCategoryDetails() {
        val categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        val response = categories.stream().map(this::convertToResponse).toList();
        return ResultUtil.generateResponse(SUCCESS, response);
    }

    /**
     * Saves a new category.
     *
     * @param request the category request containing category details
     * @return a ResponseEntity containing the result of the save operation
     */
    private ResponseEntity<Object> save(CategoryRequest request) {
        checkAuthorNameExist(request.getName(), null);
        val category = Category.builder()
                .name(request.getName())
                .deleted(Boolean.FALSE)
                .build();
        val categoryResponse = categoryRepository.save(category);
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(categoryResponse));
    }

    /**
     * Updates an existing category.
     *
     * @param request the category request containing updated category details
     * @return a ResponseEntity containing the result of the update operation
     */
    private ResponseEntity<Object> update(CategoryRequest request) {
        checkAuthorNameExist(request.getName(), request.getId());
        val category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new ValidationException(CATEGORY_NOT_FOUND));
        category.setName(request.getName());
        val categoryResponse = categoryRepository.save(category);
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(categoryResponse));
    }

    /**
     * Checks if a category with the given name and ID already exists.
     *
     * @param name the name of the category
     * @param id   the ID of the category (can be null for new categories)
     * @throws ValidationException if a category with the same name and ID already exists
     */
    private void checkAuthorNameExist(String name, Integer id) {
        categoryRepository.findByNameAndId(name, id)
                .ifPresent(category -> {
                    throw new ValidationException(CATEGORY_ALREADY_EXIST);
                });
    }

    /**
     * Converts a Category entity to a CategoryResponse object.
     *
     * @param category the Category entity to convert
     * @return the converted CategoryResponse object
     */
    private CategoryResponse convertToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}