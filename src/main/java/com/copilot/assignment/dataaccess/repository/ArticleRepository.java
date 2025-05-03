package com.copilot.assignment.dataaccess.repository;

import com.copilot.assignment.dataaccess.enmus.ArticleType;
import com.copilot.assignment.entity.Article;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    /**
     * Checks if an article with the given title already exists.
     *
     * @param title the title of the article
     * @return true if an article with the given title exists, false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * Checks if an article with the given title and ID already exists.
     *
     * @param title the title of the article
     * @param id    the ID of the article
     * @return true if an article with the given title and ID exists, false otherwise
     */
    boolean existsByTitleAndIdNot(String title, Integer id);

    /**
     * Searches for articles based on the provided filters.
     *
     * @param categories  A list of category IDs to filter articles by. If null or empty, this filter is ignored.
     * @param type        The type of the article to filter by. If null, this filter is ignored.
     * @param author      The ID of the author to filter articles by. If null, this filter is ignored.
     * @param tag         A tag name to filter articles by. Articles with tags containing this value will be included.
     * @param pageRequest The pagination information for the query.
     * @return A list of articles matching the provided filters, ordered by publish date in descending order.
     */
    @Query("""
            SELECT article FROM Article article
                LEFT JOIN Tags tags ON article.id = tags.article.id
             WHERE (:type IS NULL OR article.type = :type)
                AND (:author IS NULL OR author.id = :author)
                AND (:doNotCheckCategory = TRUE OR article.category.id IN(:categories))
                AND (:tag IS NULL OR tags.name LIKE %:tag%)
            ORDER BY article.publishDate DESC""")
    List<Article> searchArticleDetails(Boolean doNotCheckCategory, List<Integer> categories, ArticleType type,
                                       Integer author, String tag, PageRequest pageRequest);

}