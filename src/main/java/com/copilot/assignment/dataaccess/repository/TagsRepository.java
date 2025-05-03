package com.copilot.assignment.dataaccess.repository;

import com.copilot.assignment.entity.Article;
import com.copilot.assignment.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

    void deleteAllByArticle(Article article);

    List<Tags> findAllByArticle(Article article);
}