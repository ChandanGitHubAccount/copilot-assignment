package com.copilot.assignment.dataaccess.repository;

import com.copilot.assignment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * Finds an author by their name and ID, excluding the author with the specified ID if provided.
     *
     * @param name the name of the author to search for
     * @param id   the ID to exclude from the search, or null to include all IDs
     * @return an Optional containing the matching Author if found, or empty if no match is found
     */
    @Query("""
            SELECT category FROM Category category
             WHERE (:id IS NULL OR id != :id)
              AND name = :name
              AND deleted = false""")
    Optional<Category> findByNameAndId(String name, Integer id);
}