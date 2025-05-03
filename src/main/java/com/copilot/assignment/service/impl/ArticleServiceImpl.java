package com.copilot.assignment.service.impl;

import com.copilot.assignment.controller.request.ArticleRequest;
import com.copilot.assignment.controller.response.ArticleResponse;
import com.copilot.assignment.controller.response.AuthorResponse;
import com.copilot.assignment.controller.response.CategoryResponse;
import com.copilot.assignment.dataaccess.enmus.ArticleType;
import com.copilot.assignment.dataaccess.repository.ArticleRepository;
import com.copilot.assignment.dataaccess.repository.AuthorRepository;
import com.copilot.assignment.dataaccess.repository.CategoryRepository;
import com.copilot.assignment.dataaccess.repository.TagsRepository;
import com.copilot.assignment.entity.Article;
import com.copilot.assignment.entity.Author;
import com.copilot.assignment.entity.Category;
import com.copilot.assignment.entity.Tags;
import com.copilot.assignment.exception.ValidationException;
import com.copilot.assignment.service.ArticleService;
import com.copilot.assignment.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.copilot.assignment.constants.ResultInfoConstants.ARTICLE_ALREADY_EXIST;
import static com.copilot.assignment.constants.ResultInfoConstants.ARTICLE_NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.AUTHOR_NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.CATEGORY_NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.SUCCESS;

@Service
@Log4j2
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    private final TagsRepository tagsRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> saveOrUpdateArticle(ArticleRequest request) {
        return Objects.isNull(request.getId()) ? save(request) : update(request);
    }

    /**
     * Fetches the details of an article by its ID.
     *
     * @param id the ID of the article to fetch
     * @return a ResponseEntity containing the article details and associated tags
     * @throws ValidationException if the article is not found
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> fetchArticleDetails(Integer id) {
        val article = articleRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ARTICLE_NOT_FOUND));
        val tags = tagsRepository.findAllByArticle(article);
        val response = convertToResponse(article, tags);
        return ResultUtil.generateResponse(SUCCESS, response);
    }

    /**
     * Searches for articles based on the provided filters and pagination details.
     *
     * @param categories  a list of category IDs to filter articles
     * @param type        the type of the article to filter
     * @param author      the ID of the author to filter articles
     * @param tag         a specific tag to filter articles
     * @param pageRequest the pagination details for the search
     * @return a ResponseEntity containing a list of filtered articles and their associated tags
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> searchArticleDetails(
            List<Integer> categories, ArticleType type, Integer author, String tag, PageRequest pageRequest) {
        val doNotCheckCategory = Objects.isNull(categories) || categories.isEmpty();
        val articleDetails = articleRepository.searchArticleDetails(
                doNotCheckCategory, categories, type, author, tag, pageRequest);
        val responses = articleDetails.stream()
                .map(article -> {
                    val tags = tagsRepository.findAllByArticle(article);
                    return convertToResponse(article, tags);
                }).toList();
        return ResultUtil.generateResponse(SUCCESS, responses);
    }

    /**
     * Saves a new article to the database.
     *
     * @param request the article request containing article details
     * @return a ResponseEntity containing the result of the save operation
     * @throws ValidationException if the article title already exists or related entities are not found
     */
    private ResponseEntity<Object> save(ArticleRequest request) {
        if (articleRepository.existsByTitle(request.getTitle()))
            throw new ValidationException(ARTICLE_ALREADY_EXIST);
        val author = authorRepository.findById(request.getAuthor())
                .orElseThrow(() -> new ValidationException(AUTHOR_NOT_FOUND));
        val category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ValidationException(CATEGORY_NOT_FOUND));

        val article = Article.builder()
                .title(request.getTitle())
                .subTitle(request.getSubTitle())
                .category(category)
                .author(author)
                .type(request.getType())
                .image(request.getImage())
                .mediaUrl(request.getMediaUrl())
                .publishDate(request.getPublishDate())
                .description(request.getDescription())
                .build();
        val articleResponse = articleRepository.save(article);
        val tags = saveArticleTags(articleResponse, request.getTags());
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(articleResponse, tags));
    }

    /**
     * Updates an existing article in the database.
     *
     * @param request the article request containing updated article details
     * @return a ResponseEntity containing the result of the update operation
     * @throws ValidationException if the article title already exists, the article is not found, or related entities are not found
     */
    private ResponseEntity<Object> update(ArticleRequest request) {
        if (articleRepository.existsByTitleAndIdNot(request.getTitle(), request.getId()))
            throw new ValidationException(ARTICLE_ALREADY_EXIST);
        val article = articleRepository.findById(request.getId())
                .orElseThrow(() -> new ValidationException(ARTICLE_NOT_FOUND));
        val category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ValidationException(CATEGORY_NOT_FOUND));

        article.setCategory(category);
        article.setType(request.getType());
        article.setImage(request.getImage());
        article.setMediaUrl(request.getMediaUrl());
        article.setDescription(request.getDescription());
        val articleResponse = articleRepository.save(article);
        val tags = saveArticleTags(articleResponse, request.getTags());
        return ResultUtil.generateResponse(SUCCESS, convertToResponse(articleResponse, tags));
    }

    /**
     * Saves the tags associated with an article.
     *
     * @param article  the article entity to associate the tags with
     * @param tagsList the list of tag names to save
     * @return a list of saved Tags entities
     */
    private List<Tags> saveArticleTags(Article article, List<String> tagsList) {
        tagsRepository.deleteAllByArticle(article);
        List<Tags> tags = tagsList.stream()
                .map(tag -> Tags.builder().article(article).name(tag).build())
                .collect(Collectors.toUnmodifiableList());
        return tagsRepository.saveAll(tags);
    }

    /**
     * Saves or updates an article based on the presence of an ID in the request.
     *
     * @param article the article request containing article details
     * @return a ResponseEntity containing the result of the operation
     */
    private ArticleResponse convertToResponse(Article article, List<Tags> tags) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .subTitle(article.getSubTitle())
                .category(categoryResponse(article.getCategory()))
                .author(authorResponse(article.getAuthor()))
                .type(article.getType())
                .image(article.getImage())
                .mediaUrl(article.getMediaUrl())
                .publishDate(article.getPublishDate())
                .description(article.getDescription())
                .tags(tagsResponse(article))
                .build();
    }

    /**
     * Retrieves and converts the tags associated with an article to a list of tag names.
     *
     * @param article the Article entity whose tags are to be retrieved
     * @return a list of tag names
     */
    private List<String> tagsResponse(Article article) {
        val tags = tagsRepository.findAllByArticle(article);
        if (tags.isEmpty())
            return null;
        return tags.stream().map(Tags::getName).toList();
    }

    /**
     * Converts an Author entity to an AuthorResponse object.
     *
     * @param author the Author entity to convert
     * @return the converted AuthorResponse object
     */
    private AuthorResponse authorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .image(author.getImage())
                .description(author.getDescription())
                .build();
    }

    /**
     * Converts a Category entity to a CategoryResponse object.
     *
     * @param category the Category entity to convert
     * @return the converted CategoryResponse object
     */
    private CategoryResponse categoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}